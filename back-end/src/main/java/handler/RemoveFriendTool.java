package handler;

import dao.FriendsDao;
import dto.FriendsDto;
import org.bson.Document;

import java.util.List;

public class RemoveFriendTool {
    public void friendCleanup(String userName){
        FriendsDao friendsDao = FriendsDao.getInstance();
        var filter = new Document("toId", userName);
        List<FriendsDto> friendList =  friendsDao.query(filter);
        for (FriendsDto temp : friendList) {
            if (temp.getStatus() == -1){
                friendsDao.remove(temp);
            }
        }
    }
}
