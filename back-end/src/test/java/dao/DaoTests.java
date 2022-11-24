package dao;

import com.mongodb.client.MongoCollection;
import dto.AuthDto;
import org.mockito.Mockito;
import org.testng.annotations.Test;

public class DaoTests {

  @Test
  public void testPut(){
    MongoCollection mongoCollection = Mockito.mock(MongoCollection.class);
    AuthDao dao = AuthDao.getInstance(mongoCollection);
    Mockito.doNothing().when(mongoCollection).insertOne(Mockito.any());
    var item = new AuthDto();
    dao.put(item);
    Mockito.verify(mongoCollection).insertOne(Mockito.any());
  }

}
