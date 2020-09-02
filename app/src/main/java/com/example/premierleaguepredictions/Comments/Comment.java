package com.example.premierleaguepredictions.Comments;

public class Comment {

    private String ivUserImage;
    private String nickname;
    private String time;
    private String text;

    public Comment() {
        this.nickname = "DEF";
        this.time = "def";
        this.text = "def";
        this.ivUserImage="def";
    }

    public Comment(String nickname, String time, String text, String ivUserImage) {
        this.nickname = nickname;
        this.time = time;
        this.text = text;
        this.ivUserImage=ivUserImage;

    }

    public String getIvUserImage() {
        return ivUserImage;
    }

    public void setIvUserImage(String ivUserImage) {
        this.ivUserImage = ivUserImage;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
