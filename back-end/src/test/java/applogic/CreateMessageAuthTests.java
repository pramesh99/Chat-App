package applogic;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import dao.AuthDao;
import dao.ConversationDao;
import dao.MessageDao;
import dao.UserDao;
import dto.MessageDto;
import handler.GsonTool;
import handler.HandlerFactory;
import handler.StatusCodes;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;
import org.testng.Assert;
import org.testng.annotations.Test;
import request.ParsedRequest;

public class CreateMessageAuthTests {

  @Test
  public void createMessage() {
    FindIterable findIterable = Mockito.mock(FindIterable.class);
    FindIterable findIterable2 = Mockito.mock(FindIterable.class);
    FindIterable conversationIterable = Mockito.mock(FindIterable.class);

    MongoCollection mockUserCollection = Mockito.mock(MongoCollection.class);
    MongoCollection mockAuthCollection = Mockito.mock(MongoCollection.class);
    MongoCollection mockConvoCollection = Mockito.mock(MongoCollection.class);
    MongoCollection mockMessagesCollection = Mockito.mock(MongoCollection.class);

    UserDao userDao = UserDao.getInstance(mockUserCollection);
    AuthDao authDao = AuthDao.getInstance(mockAuthCollection);
    ConversationDao conversationDao = ConversationDao.getInstance(mockConvoCollection);
    MessageDao messageDao = MessageDao.getInstance(mockMessagesCollection);

    List<Document> returnList = new ArrayList<>();
    String userId = String.valueOf(Math.random());
    returnList.add(new Document("userName", userId));
    Mockito.doReturn(returnList).when(findIterable).into(Mockito.any());


    List<Document> userList = new ArrayList<>();
    userList.add(new Document());
    Mockito.doReturn(userList).when(findIterable2).into(Mockito.any());

    List<Document> convoList = new ArrayList<>();
    Mockito.doReturn(convoList).when(conversationIterable).into(Mockito.any());

    Mockito.doReturn(findIterable).when(mockAuthCollection).find((Bson) Mockito.any());
    Mockito.doReturn(findIterable2).when(mockUserCollection).find((Bson) Mockito.any());
    Mockito.doReturn(conversationIterable).when(mockConvoCollection).find((Bson) Mockito.any());

    Mockito.doNothing().when(mockAuthCollection).insertOne(Mockito.any());
    Mockito.doNothing().when(mockUserCollection).insertOne(Mockito.any());

    ParsedRequest parsedRequest = new ParsedRequest();
    String conversationId = String.valueOf(Math.random());
    parsedRequest.setPath("/createMessage");
    var messageTest = new MessageDto();
    messageTest.setMessage(String.valueOf(Math.random()));
    messageTest.setToId(String.valueOf(Math.random()));
    messageTest.setFromId(userId);

    parsedRequest.setBody(GsonTool.gson.toJson(messageTest));
    parsedRequest.setHeaderValue("auth", String.valueOf(Math.random()));

    var handler = HandlerFactory.getHandler(parsedRequest);
    var builder = handler.handleRequest(parsedRequest);
    var res = builder.build();
    Assert.assertEquals(res.status, StatusCodes.OK);

    ArgumentCaptor<Document> argument = ArgumentCaptor.forClass(Document.class);
    Mockito.verify(mockMessagesCollection).insertOne(argument.capture());

    ArgumentCaptor<Document> convoCaptor = ArgumentCaptor.forClass(Document.class);
    Mockito.verify(mockConvoCollection, Mockito.times(2)).insertOne(convoCaptor.capture());
  }

  @Test
  public void createMessageNoAuth() {
    FindIterable findIterable = Mockito.mock(FindIterable.class);
    FindIterable findIterable2 = Mockito.mock(FindIterable.class);
    FindIterable conversationIterable = Mockito.mock(FindIterable.class);

    MongoCollection mockUserCollection = Mockito.mock(MongoCollection.class);
    MongoCollection mockAuthCollection = Mockito.mock(MongoCollection.class);
    MongoCollection mockConvoCollection = Mockito.mock(MongoCollection.class);
    MongoCollection mockMessagesCollection = Mockito.mock(MongoCollection.class);

    UserDao userDao = UserDao.getInstance(mockUserCollection);
    AuthDao authDao = AuthDao.getInstance(mockAuthCollection);
    ConversationDao conversationDao = ConversationDao.getInstance(mockConvoCollection);
    MessageDao messageDao = MessageDao.getInstance(mockMessagesCollection);

    List<Document> returnList = new ArrayList<>();
    String userId = String.valueOf(Math.random());
    Mockito.doReturn(returnList).when(findIterable).into(Mockito.any());


    List<Document> userList = new ArrayList<>();
    userList.add(new Document());
    Mockito.doReturn(userList).when(findIterable2).into(Mockito.any());

    List<Document> convoList = new ArrayList<>();
    Mockito.doReturn(convoList).when(conversationIterable).into(Mockito.any());

    Mockito.doReturn(findIterable).when(mockAuthCollection).find((Bson) Mockito.any());
    Mockito.doReturn(findIterable2).when(mockUserCollection).find((Bson) Mockito.any());
    Mockito.doReturn(conversationIterable).when(mockConvoCollection).find((Bson) Mockito.any());

    Mockito.doNothing().when(mockAuthCollection).insertOne(Mockito.any());
    Mockito.doNothing().when(mockUserCollection).insertOne(Mockito.any());

    ParsedRequest parsedRequest = new ParsedRequest();
    String conversationId = String.valueOf(Math.random());
    parsedRequest.setPath("/createMessage");
    var messageTest = new MessageDto();
    messageTest.setMessage(String.valueOf(Math.random()));
    messageTest.setToId(String.valueOf(Math.random()));
    messageTest.setFromId(userId);

    parsedRequest.setBody(GsonTool.gson.toJson(messageTest));

    var handler = HandlerFactory.getHandler(parsedRequest);
    var builder = handler.handleRequest(parsedRequest);
    var res = builder.build();
    Assert.assertEquals(res.status, StatusCodes.UNAUTHORIZED);
  }

}
