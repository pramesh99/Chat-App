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
    var doc = new Document();
    doc.append("userName", userName);
    doc.append("conversationId", conversationId);
    return doc;
  }

  public static ConversationDto fromDocument(Document document) {
    var res = new ConversationDto();
    res.setConversationId(document.getString("conversationId"));
    res.setUserName(document.getString("userName"));
    return res;
  }
}
