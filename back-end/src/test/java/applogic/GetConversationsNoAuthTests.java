package applogic;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import dao.AuthDao;
import dao.UserDao;
import dto.UserDto;
import handler.GsonTool;
import handler.HandlerFactory;
import handler.StatusCodes;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;
import request.ParsedRequest;

public class GetConversationsNoAuthTests {

  @Test
  public void getConversationsNoAuth() {
    FindIterable findIterable = Mockito.mock(FindIterable.class);

    MongoCollection mockUserCollection = Mockito.mock(MongoCollection.class);
    MongoCollection mockAuthCollection = Mockito.mock(MongoCollection.class);

    UserDao userDao = UserDao.getInstance(mockUserCollection);
    AuthDao authDao = AuthDao.getInstance(mockAuthCollection);

    List<Document> returnList = new ArrayList<>();
    returnList.add(new Document());
    Mockito.doReturn(returnList).when(findIterable).into(Mockito.any());
    Mockito.doReturn(findIterable).when(mockAuthCollection).find((Bson) Mockito.any());

    Mockito.doNothing().when(mockAuthCollection).insertOne(Mockito.any());

    ParsedRequest parsedRequest = new ParsedRequest();
    parsedRequest.setPath("/getConversations");
    var handler = HandlerFactory.getHandler(parsedRequest);
    var builder = handler.handleRequest(parsedRequest);
    var res = builder.build();
    Assert.assertEquals(res.status, StatusCodes.UNAUTHORIZED);
  }

  @Test
  public void getConversationsInvalidAuth() {
    FindIterable findIterable = Mockito.mock(FindIterable.class);

    MongoCollection mockUserCollection = Mockito.mock(MongoCollection.class);
    MongoCollection mockAuthCollection = Mockito.mock(MongoCollection.class);

    UserDao userDao = UserDao.getInstance(mockUserCollection);
    AuthDao authDao = AuthDao.getInstance(mockAuthCollection);

    List<Document> returnList = new ArrayList<>();
    Mockito.doReturn(returnList).when(findIterable).into(Mockito.any());
    Mockito.doReturn(findIterable).when(mockAuthCollection).find((Bson) Mockito.any());

    Mockito.doNothing().when(mockAuthCollection).insertOne(Mockito.any());

    ParsedRequest parsedRequest = new ParsedRequest();
    parsedRequest.setPath("/getConversations");
    parsedRequest.setHeaderValue("auth", String.valueOf(Math.random()));
    var handler = HandlerFactory.getHandler(parsedRequest);
    var builder = handler.handleRequest(parsedRequest);
    var res = builder.build();
    Assert.assertEquals(res.status, StatusCodes.UNAUTHORIZED);
  }
}
