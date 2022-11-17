package dto;

import org.bson.Document;

public abstract class BaseDto{

  private String uniqueId;

  public BaseDto(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public BaseDto() {
  }

  public String getUniqueId(){
    return uniqueId;
  }

  public abstract Document toDocument();

}
