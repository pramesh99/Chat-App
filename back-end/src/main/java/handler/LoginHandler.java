package handler;

import dao.AuthDao;
import dao.UserDao;
import dto.AuthDto;
import java.time.Instant;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import request.ParsedRequest;
import response.CustomHttpResponse;
import response.HttpResponseBuilder;

class LoginDto{
  String userName;
  String password;
}

public class LoginHandler implements BaseHandler{

  @Override
  public HttpResponseBuilder handleRequest(ParsedRequest request) {
    var res = new HttpResponseBuilder();
    LoginDto userDto = GsonTool.gson.fromJson(request.getBody(), LoginDto.class);
    UserDao userDao = UserDao.getInstance();
    AuthDao authDao = AuthDao.getInstance();

    var userQuery = new Document()
        .append("userName", userDto.userName)
        .append("password",  DigestUtils.sha256Hex(userDto.password));

    var result = userDao.query(userQuery);
    if(result.size() == 0){
      res.setStatus("401 Unauthorized");
    }else{
      AuthDto authDto = new AuthDto();
      authDto.setExpireTime(Instant.now().getEpochSecond() + 60000);
      String hash = DigestUtils.sha256Hex(authDto.getUserName() + authDto.getExpireTime());
      authDto.setHash(hash);
      authDto.setUserName(userDto.userName);
      authDao.put(authDto);
      res.setStatus("200 OK");
      //res.setHeaders(Map.of("Set-Cookie", "auth=" + hash));
      res.setHeader("Set-Cookie", "auth=" + hash);
    }
    return res;
  }
}
