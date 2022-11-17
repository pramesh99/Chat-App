package dto;

import org.bson.Document;

public class AuthDto extends BaseDto{

  private String userName;
  private Long expireTime;
  private String hash;

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setHash(String hash) {
    this.hash = hash;
  }

  public void setExpireTime(Long expireTime) {
    this.expireTime = expireTime;
  }

  public String getUserName() {
    return userName;
  }

  public Long getExpireTime() {
    return expireTime;
  }

  public String getHash() {
    return hash;
  }

  @Override
  public Document toDocument() {
    // TODO
    return null;
  }

  public static AuthDto fromDocument(Document document){
    // TODO
    return null;
  }
}
