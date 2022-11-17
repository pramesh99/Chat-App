package dto;

import org.bson.Document;

public class UserDto extends BaseDto{

  private String userName;
  private String password;

  public UserDto() {
    super();
  }

  public UserDto(String uniqueId) {
    super(uniqueId);
  }

  public String getPassword() {
    return password;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Document toDocument(){
    // TODO
    return null;
  }

  public static UserDto fromDocument(Document match) {
    // TODO
    return null;
  }
}
