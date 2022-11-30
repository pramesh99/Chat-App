package handler;

import dao.ConversationDao;
import handler.AuthFilter.AuthResult;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

public class GetConversationsHandler implements BaseHandler {

  @Override
  public HttpResponseBuilder handleRequest(ParsedRequest request) {
    ConversationDao conversationDao = ConversationDao.getInstance();
    AuthResult authResult = AuthFilter.doFilter(request);
    if(!authResult.isLoggedIn){
      return new HttpResponseBuilder().setStatus(StatusCodes.UNAUTHORIZED);
    }

    var filter = new Document("userName", authResult.userName);
    var res = new RestApiAppResponse<>(true, conversationDao.query(filter), null);
    return new HttpResponseBuilder().setStatus("200 OK").setBody(res);
  }

}
