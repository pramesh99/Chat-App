package applogic;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import dao.AuthDao;
import dao.ConversationDao;
import dao.UserDao;
import handler.HandlerFactory;
import handler.StatusCodes;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;
import request.ParsedRequest;

public class GetConversationsAuthTests {

  @Test
  public void getConversations() {
    FindIterable findIterable = Mockito.mock(FindIterable.class);
    FindIterable findIterable2 = Mockito.mock(FindIterable.class);

    MongoCollection mockUserCollection = Mockito.mock(MongoCollection.class);
    MongoCollection mockAuthCollection = Mockito.mock(MongoCollection.class);
    MongoCollection mockConvoCollection = Mockito.mock(MongoCollection.class);

    UserDao userDao = UserDao.getInstance(mockUserCollection);
    AuthDao authDao = AuthDao.getInstance(mockAuthCollection);
    ConversationDao conversationDao = ConversationDao.getInstance(mockConvoCollection);

    List<Document> returnList = new ArrayList<>();
    returnList.add(new Document());
    Mockito.doReturn(returnList).when(findIterable).into(Mockito.any());
    Mockito.doReturn(new ArrayList<>()).when(findIterable2).into(Mockito.any());
    Mockito.doReturn(findIterable).when(mockAuthCollection).find((Bson) Mockito.any());
    Mockito.doReturn(findIterable2).when(mockConvoCollection).find((Bson) Mockito.any());

    Mockito.doNothing().when(mockAuthCollection).insertOne(Mockito.any());

    ParsedRequest parsedRequest = new ParsedRequest();
    parsedRequest.setPath("/getConversations");
    parsedRequest.setHeaderValue("auth", String.valueOf(Math.random()));

    var handler = HandlerFactory.getHandler(parsedRequest);
    var builder = handler.handleRequest(parsedRequest);
    var res = builder.build();
    Assert.assertEquals(res.status, StatusCodes.OK);
  }

  @Test
  public void getConversationsNoAuth() {
    FindIterable findIterable = Mockito.mock(FindIterable.class);
    FindIterable findIterable2 = Mockito.mock(FindIterable.class);

    MongoCollection mockUserCollection = Mockito.mock(MongoCollection.class);
    MongoCollection mockAuthCollection = Mockito.mock(MongoCollection.class);
    MongoCollection mockConvoCollection = Mockito.mock(MongoCollection.class);

    UserDao userDao = UserDao.getInstance(mockUserCollection);
    AuthDao authDao = AuthDao.getInstance(mockAuthCollection);
    ConversationDao conversationDao = ConversationDao.getInstance(mockConvoCollection);

    List<Document> returnList = new ArrayList<>();
    Mockito.doReturn(returnList).when(findIterable).into(Mockito.any());
    Mockito.doReturn(new ArrayList<>()).when(findIterable2).into(Mockito.any());
    Mockito.doReturn(findIterable).when(mockAuthCollection).find((Bson) Mockito.any());
    Mockito.doReturn(findIterable2).when(mockConvoCollection).find((Bson) Mockito.any());

    Mockito.doNothing().when(mockAuthCollection).insertOne(Mockito.any());

    ParsedRequest parsedRequest = new ParsedRequest();
    parsedRequest.setPath("/getConversations");
    parsedRequest.setHeaderValue("auth", String.valueOf(Math.random()));

    var handler = HandlerFactory.getHandler(parsedRequest);
    var builder = handler.handleRequest(parsedRequest);
    var res = builder.build();
    // because back end has no auth entry
    Assert.assertEquals(res.status, StatusCodes.UNAUTHORIZED);
  }
}
