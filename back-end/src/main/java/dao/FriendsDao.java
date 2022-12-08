package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import dto.FriendsDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;
import org.bson.conversions.Bson;



public class FriendsDao extends BaseDao<FriendsDto> {

    private static FriendsDao instance;

    private FriendsDao(MongoCollection<Document> collection){
        super(collection);
    }


    public static FriendsDao getInstance(){
        if(instance != null){
            return instance;
        }
        instance = new FriendsDao (MongoConnection.getCollection("FriendsDao"));
        return instance;
    }

    public static FriendsDao getInstance(MongoCollection<Document> collection){
        instance = new FriendsDao(collection);
        return instance;
    }

    @Override
    public void put(FriendsDto friendsDto) {
        collection.insertOne(friendsDto.toDocument());
    }

    public  void  updateStatus(Document query,int status){
        Bson update = Updates.set("status",status);
        collection.updateOne(query,update);

    }

    public void remove(FriendsDto friendsDto){
        collection.deleteOne(friendsDto.toDocument());
    }

    public List<FriendsDto> query(Document filter){
        List<FriendsDto> messageDtos;
        messageDtos = new ArrayList<>((collection.find(filter).into(new ArrayList<>())
                .stream().map(FriendsDto::fromDocument).collect(Collectors.toList())));
        return messageDtos;
    }

}
