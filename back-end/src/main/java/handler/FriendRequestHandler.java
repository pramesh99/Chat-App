package handler;

import dao.FriendsDao;
import dao.UserDao;
import dto.FriendsDto;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;

import java.util.Objects;


public class FriendRequestHandler implements BaseHandler {

    @Override
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

        // if request doesn't exist create one
        if (friendsDao.query(new Document("friendId", friendId)).size() == 0) {
            friendsDto.setFriendId(friendId);
            friendsDto.setStatus(0); // this is temporary change , revert when accept friend request is complete
            friendsDao.put(friendsDto);
            

        }

        return res.setStatus(StatusCodes.OK);
    }
}
