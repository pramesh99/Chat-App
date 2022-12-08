package handler;

import dao.FriendsDao;
import dto.FriendsDto;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class SearchFriendsHandler {

    private final FriendsDao friendsDao;

    public SearchFriendsHandler(FriendsDao friendsDao) {
        this.friendsDao = friendsDao;
    }

    public List<String> search(String query) {
        List<String> results = new ArrayList<>();

        // Convert the query to lowercase to make the search case-insensitive
        query = query.toLowerCase();

        // Search the friends list for matches with the given query
        for (FriendsDto friend : friendsDao.query(new Document())) {
            if (friend.getFriendId().toLowerCase().contains(query)) {
                results.add(friend.getFriendId());
            }
        }

        return results;
    }
}