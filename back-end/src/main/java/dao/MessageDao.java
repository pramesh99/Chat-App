package dao;

import com.mongodb.client.MongoCollection;
import dto.MessageDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;

// TODO fill this out
public class MessageDao extends BaseDao<MessageDto> {

  private static MessageDao instance;

  private MessageDao(MongoCollection<Document> collection){
    super(collection);
  }

  public static MessageDao getInstance(){
    if(instance != null){
      return instance;
    }
    instance = new MessageDao(MongoConnection.getCollection("MessageDao"));
    return instance;
  }

  public static MessageDao getInstance(MongoCollection<Document> collection){
    instance = new MessageDao(collection);
    return instance;
  }

  @Override
  public void put(MessageDto messageDto) {
    // TODO
  }

  public List<MessageDto> query(Document filter){
    // TODO
    return null;
  }

}
