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

public class LoginHandlerTests {
  @Test
  public void userTakenTest() {
    FindIterable findIterable = Mockito.mock(FindIterable.class);

    MongoCollection mockUserCollection = Mockito.mock(MongoCollection.class);
    MongoCollection mockAuthCollection = Mockito.mock(MongoCollection.class);

    UserDao userDao = UserDao.getInstance(mockUserCollection);
    AuthDao authDao = AuthDao.getInstance(mockAuthCollection);

    List<Document> returnList = new ArrayList<>();
    returnList.add(new Document());
    Mockito.doReturn(returnList).when(findIterable).into(Mockito.any());

    Mockito.doReturn(findIterable).when(mockUserCollection).find((Bson) Mockito.any());

    ArgumentCaptor<Document> argument = ArgumentCaptor.forClass(Document.class);

    Mockito.doNothing().when(mockAuthCollection).insertOne(Mockito.any());

    ParsedRequest parsedRequest = new ParsedRequest();
    parsedRequest.setPath("/login");
    var user = new UserDto();
    user.setPassword(String.valueOf(Math.random()));
    user.setUserName(String.valueOf(Math.random()));
    parsedRequest.setBody(GsonTool.gson.toJson(user));
    var handler = HandlerFactory.getHandler(parsedRequest);
    var builder = handler.handleRequest(parsedRequest);
    var res = builder.build();
    Assert.assertEquals(res.status, StatusCodes.OK);
    Mockito.verify(mockUserCollection).find(argument.capture());
    Assert.assertEquals(argument.getAllValues().size(), 1);
    Assert.assertEquals(argument.getAllValues().get(0).get("userName"), user.getUserName());
    Assert.assertEquals(argument.getAllValues().get(0).get("password"),
        DigestUtils.sha256Hex(user.getPassword()));
    Assert.assertTrue(res.headers.containsKey("Set-Cookie"));

    ArgumentCaptor<Document> authCaptor = ArgumentCaptor.forClass(Document.class);
    Mockito.verify(mockAuthCollection).insertOne(authCaptor.capture());
    Assert.assertEquals(authCaptor.getAllValues().get(0).get("userName"), user.getUserName());
    String hash = authCaptor.getAllValues().get(0).getString("hash");
    Assert.assertTrue(res.headers.get("Set-Cookie").contains(hash));
  }
}
