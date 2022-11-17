package dao;

import com.mongodb.client.MongoCollection;
import dto.FriendsDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;



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

    public List<FriendsDto> query(Document filter){
        List<FriendsDto> messageDtos;
        messageDtos = new ArrayList<>((collection.find(filter).into(new ArrayList<>())
                .stream().map(FriendsDto::fromDocument).collect(Collectors.toList())));
        return messageDtos;
    }

}
