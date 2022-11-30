package dto;

import org.bson.Document;

import java.time.Instant;

public class FriendsDto extends BaseDto{
    private String fromId;
    private String toId;
    private Long timestamp;
    private String friendId;
    private Integer status; //-1 = rejected, 0 = unconfirmed, 1 = confirmed

    public FriendsDto() {
        this.timestamp =  Instant.now().toEpochMilli();
    }

    public FriendsDto(String uniqueId) {
        super(uniqueId);
        this.timestamp =  Instant.now().toEpochMilli();
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public Document toDocument() {
        Document newDocument = new Document();
        newDocument.append("fromId",this.fromId);
        newDocument.append("toId",this.toId);
        newDocument.append("friendId",this.friendId);
        newDocument.append("timestamp",this.timestamp);
        newDocument.append("status",this.status);
        return newDocument;
    }
    public static FriendsDto fromDocument(Document document) {
        FriendsDto messageDto = new FriendsDto();
        messageDto.fromId = document.getString("fromId");
        messageDto.toId = document.getString("toId");
        messageDto.friendId = document.getString("friendId");
        messageDto.timestamp = (Long) document.get("timestamp");
        messageDto.status = document.getInteger("status");
        return messageDto;
    }
}
