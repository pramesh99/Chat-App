package applogic;

import com.mongodb.client.MongoCollection;
import dao.AuthDao;
import dao.ConversationDao;
import dao.MessageDao;
import dao.UserDao;
import org.mockito.Mockito;

public class CollectionTestTools {

  static void setupMockCollections(){
    MongoCollection mockUserCollection = Mockito.mock(MongoCollection.class);
    MongoCollection mockAuthCollection = Mockito.mock(MongoCollection.class);
    MongoCollection mockConvoCollection = Mockito.mock(MongoCollection.class);
    MongoCollection mockMessagesCollection = Mockito.mock(MongoCollection.class);

    UserDao userDao = UserDao.getInstance(mockUserCollection);
    AuthDao authDao = AuthDao.getInstance(mockAuthCollection);
    ConversationDao conversationDao = ConversationDao.getInstance(mockConvoCollection);
    MessageDao messageDao = MessageDao.getInstance(mockMessagesCollection);
  }

}
