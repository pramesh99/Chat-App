package handler;

import dao.AuthDao;
import org.bson.Document;
import request.ParsedRequest;

public class AuthFilter {

  static class AuthResult {
    boolean isLoggedIn;
    String userName;
  }

  static AuthResult doFilter(ParsedRequest parsedRequest){
    AuthDao authDao = AuthDao.getInstance();
    var result = new AuthResult();
    String hash = parsedRequest.getHeaderValue("auth");
    if(hash == null){
      return result;
    }
    Document filter = new Document("hash", hash);
    var authRes = authDao.query(filter);
    if(authRes.size() == 0){
        result.isLoggedIn = false;
        return result;
    }
    result.isLoggedIn = true;
    result.userName = authRes.get(0).getUserName();
    return result;
  }
}
