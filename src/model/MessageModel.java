package model;

public class MessageModel {

    private String profile;
    private String nickname;
    private String message;
    private String timestamp;
    private String current_uid;
    private String destination_uid;
    private String room_no;
    private String image;

    public MessageModel(String profile, String nickname, String message, String timestamp, String current_uid, String destination_uid, String room_no, String image) {
        this.profile = profile;
        this.nickname = nickname;
        this.message = message;
        this.timestamp = timestamp;
        this.current_uid = current_uid;
        this.destination_uid = destination_uid;
        this.room_no = room_no;
        this.image = image;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCurrent_uid() {
        return current_uid;
    }

    public void setCurrent_uid(String current_uid) {
        this.current_uid = current_uid;
    }

    public String getDestination_uid() {
        return destination_uid;
    }

    public void setDestination_uid(String destination_uid) {
        this.destination_uid = destination_uid;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
