package handler;

import dao.FriendsDao;
import dto.FriendsDto;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;

public class SearchFriendsHandler implements BaseHandler {

    private final FriendsDao friendsDao;

    public SearchFriendsHandler(FriendsDao friendsDao) {
        this.friendsDao = friendsDao;
    }

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        HttpResponseBuilder res = new HttpResponseBuilder();
        String userName = request.getQueryParam("Name");

        // Search the friends list for matches with the given Name
        List<String> results = new ArrayList<>();
        for (FriendsDto friend : friendsDao.query(new Document())) {
            if (friend.getFriendId().toLowerCase().contains(userName.toLowerCase())) {
                results.add(friend.getFriendId());
            }
        }

        // Give out response based on the results
        HttpResponseBuilder response = new HttpResponseBuilder();
        response.setStatus(StatusCodes.OK);
        response.setBody(GsonTool.gson.toJson(results));
        return response;
    }
}
