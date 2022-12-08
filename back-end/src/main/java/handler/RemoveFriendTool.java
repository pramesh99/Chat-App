package handler;

import dao.FriendsDao;
import dto.FriendsDto;
import org.bson.Document;

import java.util.Iterator;
import java.util.List;

public class RemoveFriendTool {
    public void friendCleanup(String userName){
        FriendsDao friendsDao = FriendsDao.getInstance();
        var filter = new Document("toId", userName);
        List<FriendsDto> friendList =  friendsDao.query(filter);
        Iterator<FriendsDto> iterator = friendList.iterator();
        if (iterator.hasNext()) {
            do {
                FriendsDto temp = iterator.next();
                if (temp.getStatus() == -1) {
                    friendsDao.remove(temp);
                }
            } while (iterator.hasNext());
        }

    }
}
