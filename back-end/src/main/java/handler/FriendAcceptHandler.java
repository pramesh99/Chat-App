package handler;

import dao.FriendsDao;
import dao.UserDao;
import dto.FriendsDto;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;

import java.util.List;
import java.util.Objects;

public class FriendAcceptHandler implements BaseHandler{
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        HttpResponseBuilder res = new HttpResponseBuilder();
        FriendsDto friendsDto = GsonTool.gson.fromJson(request.getBody(), dto.FriendsDto.class);
        FriendsDao friendsDao = FriendsDao.getInstance();
        UserDao userDao = UserDao.getInstance();
        String fromId = friendsDto.getFromId();
        String toId = friendsDto.getToId();

        // check for auth header, toId username exists or if fromId == toId
        AuthFilter.AuthResult authResult = AuthFilter.doFilter(request);
        if (!authResult.isLoggedIn){
            return res.setStatus(StatusCodes.UNAUTHORIZED);

        } else if (userDao.query(new Document("userName", toId)).size() == 0) {
            return res.setStatus("402 Invalid username");

        } else if (Objects.equals(fromId, toId)) {
            return res.setStatus("403 Friend request sever error");
        }
        String friendId = CreateMessageHandler.makeConvoId(fromId, toId);
        List<FriendsDto> temp = friendsDao.query(new Document("friendId", friendId));
        friendsDao.updateStatus(new Document("friendId", friendId),1);


        return res.setStatus(StatusCodes.OK);
    }
}
