package handler;

import dao.FriendsDao;
import dto.FriendsDto;
import handler.AuthFilter.AuthResult;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;
import java.util.*;

public class GetFriendRequestHandler implements BaseHandler{
    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request){
        FriendsDao friendsDao = FriendsDao.getInstance();
        AuthResult authResult = AuthFilter.doFilter(request);
        if (!authResult.isLoggedIn){
            return new HttpResponseBuilder().setStatus(StatusCodes.UNAUTHORIZED);
        }

        // Get param
        var incoming =  Integer.parseInt(request.getQueryParam("incoming"));
//        System.out.println("LOOKHEREYOUBITCH\n" + incoming);
//        System.out.println((incoming != "1") + " " + (incoming != "0"));
        // Sanitize input
//        System.out.println(incoming.contains("1"));
        if (incoming != 1 && incoming != 0) {
            return new HttpResponseBuilder().setStatus("400 Invalid");
        }

        List<FriendsDto> bodyData = new ArrayList<>();
        var filter = new Document();
        if (incoming == 1) {
            // Only want FriendDto with toId and status = 0
            filter.append("toId", authResult.userName);
            filter.append("status", 0);
            bodyData = friendsDao.query(filter);
        } else {
            // Get all friends that requested and have been accepted
            filter.append("toId", authResult.userName);
            filter.append("status", 1);
            bodyData = friendsDao.query(filter);
            // Get all outgoing requests, accepted and pending
            // No concerns for declined requests because they're deleted
            filter = new Document("fromId", authResult.userName);
            bodyData.addAll(friendsDao.query(filter));
        }

        var res = new RestApiAppResponse<>(true, friendsDao.query(filter), null);
        return new HttpResponseBuilder().setStatus("200 OK").setBody(res);
    }
}
