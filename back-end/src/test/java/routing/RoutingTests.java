package routing;

import handler.BaseHandler;
import handler.HandlerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.Assert;
import org.testng.annotations.Test;
import request.CustomParser;
import request.ParsedRequest;

public class RoutingTests {

  @Test
  public void testFactory(){
    var basicRequest = new ParsedRequest();
    basicRequest.setPath("/");
    var res = HandlerFactory.getHandler(basicRequest);
    Assert.assertNotNull(res);
    Assert.assertTrue(res instanceof BaseHandler);

    basicRequest.setPath("/createUser");
    var res2 = HandlerFactory.getHandler(basicRequest);
    Assert.assertNotNull(res2);
    Assert.assertTrue(res2 instanceof BaseHandler);
    Assert.assertNotSame(res, res2);
    Assert.assertNotSame(res.getClass(), res2.getClass());
  }

  @Test
  public void getRequestHeaders(){
    int min = 2;
    int max = 100;
    int headerLength = new Random().nextInt(max - min) + min;
    String httpRequest = "GET /someUrl HTTP1.1\n";
    Map<String,String> keyValues = new HashMap<>();
    for(int i = 0; i < headerLength; i++){
      String key = String.valueOf(Math.random());
      String value = String.valueOf(Math.random());
      keyValues.put(key, value);
      httpRequest += key + ": " + value + "\n";
    }
    httpRequest += "\n";
    System.out.println(httpRequest);
    ParsedRequest parsedRequest = CustomParser.parse(httpRequest);
    keyValues.entrySet().stream()
        .forEach(set -> {
          Assert.assertEquals(parsedRequest.getHeaderValue(set.getKey()), set.getValue());
        });
  }
}
