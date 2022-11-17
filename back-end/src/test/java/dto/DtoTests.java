package dto;

import java.util.Random;
import org.bson.Document;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DtoTests {

  @Test
  public void convertMessageDto(){
    MessageDto messageDto = new MessageDto();
    messageDto.setMessage(String.valueOf(Math.random()));
    messageDto.setFromId(String.valueOf(Math.random()));
    messageDto.setToId(String.valueOf(Math.random()));
    messageDto.setConversationId(String.valueOf(Math.random()));
    Document document = messageDto.toDocument();
    Assert.assertEquals(document.get("message"), messageDto.getMessage());
    Assert.assertEquals(document.get("fromId"), messageDto.getFromId());
    Assert.assertEquals(document.get("toId"), messageDto.getToId());
    Assert.assertEquals(document.get("conversationId"), messageDto.getConversationId());
    Assert.assertNotNull(document.get("timestamp"));
  }

  @Test
  public void convertUserDto(){
    UserDto userDto = new UserDto();
    userDto.setUserName(String.valueOf(Math.random()));
    userDto.setPassword(String.valueOf(Math.random()));
    Document document = userDto.toDocument();
    Assert.assertEquals(document.getString("userName"), userDto.getUserName());
    Assert.assertEquals(document.getString("password"), userDto.getPassword());
  }

  @Test
  public void convertAuthDto(){
    AuthDto authDto = new AuthDto();
    authDto.setHash(String.valueOf(Math.random()));
    authDto.setUserName(String.valueOf(Math.random()));
    authDto.setExpireTime(new Random().nextLong());

    Document document = authDto.toDocument();
    Assert.assertEquals(document.getString("userName"), authDto.getUserName());
    Assert.assertEquals(document.getString("hash"), authDto.getHash());
    Assert.assertEquals(document.getLong("expireTime"), authDto.getExpireTime());
  }

  @Test
  public void convertConversationDto(){
    ConversationDto conversationDto = new ConversationDto();
    conversationDto.setConversationId(String.valueOf(Math.random()));
    conversationDto.setUserName(String.valueOf(Math.random()));
    Document document = conversationDto.toDocument();
    Assert.assertEquals(document.getString("userName"), conversationDto.getUserName());
    Assert.assertEquals(document.getString("conversationId"), conversationDto.getConversationId());
  }

}
