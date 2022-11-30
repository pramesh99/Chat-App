package handler;

import dao.ConversationDao;
import dao.MessageDao;
import dao.UserDao;
import dto.ConversationDto;
import dto.MessageDto;
import handler.AuthFilter.AuthResult;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;
import request.ParsedRequest;
import response.CustomHttpResponse;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

public class CreateMessageHandler implements BaseHandler {

  @Override
  public HttpResponseBuilder handleRequest(ParsedRequest request) {
    MessageDto messageDto = GsonTool.gson.fromJson(request.getBody(), dto.MessageDto.class);
    MessageDao messageDao = MessageDao.getInstance();
    UserDao userDao = UserDao.getInstance();

    AuthResult authResult = AuthFilter.doFilter(request);
    if(!authResult.isLoggedIn){
      return new HttpResponseBuilder().setStatus(StatusCodes.UNAUTHORIZED);
    }

    if (userDao.query(new Document("userName", messageDto.getToId())).size() == 0) {
      var res = new RestApiAppResponse<>(false, null,
          "Sending message to unknown user");
      return new HttpResponseBuilder().setStatus("200 OK").setBody(res);
    }

    String conversationId = makeConvoId(messageDto.getFromId(), messageDto.getToId());

    ConversationDao conversationDao = ConversationDao.getInstance();
    messageDto.setConversationId(conversationId);
    messageDto.setFromId(authResult.userName);
    messageDao.put(messageDto);

    if(conversationDao.query(new Document("conversationId", conversationId)).size() == 0){
      ConversationDto convo1 = new ConversationDto();
      convo1.setUserName(messageDto.getFromId());
      convo1.setConversationId(conversationId);

      ConversationDto convo2 = new ConversationDto();
      convo2.setUserName(messageDto.getToId());
      convo2.setConversationId(conversationId);

      conversationDao.put(convo1);
      conversationDao.put(convo2);
    }

    var res = new RestApiAppResponse<>(true, List.of(messageDto), null);
    return new HttpResponseBuilder().setStatus("200 OK").setBody(res);
  }

  public static String makeConvoId(String a, String b){
    return List.of(a,b).stream()
        .sorted(Comparator.naturalOrder())
        .collect(Collectors.joining("_"));
  }

}
