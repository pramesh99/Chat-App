package dto;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class ConversationDto extends BaseDto{

  private String conversationId;
  private String userName;

  public void setConversationId(String conversationId) {
    this.conversationId = conversationId;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getConversationId() {
    return conversationId;
  }

  public String getUserName() {
    return userName;
  }

  @Override
  public Document toDocument() {
    // TODO
    return null;
  }

  public static ConversationDto fromDocument(Document document) {
    // TODO
    return null;
  }
}
