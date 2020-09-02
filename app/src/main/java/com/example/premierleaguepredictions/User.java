package com.example.premierleaguepredictions;

import java.io.Serializable;

public class User implements Serializable {

    private String fullname;
    private String nickname;
    private String email;
    private String filePath;

    public User() {}

    public User(String fullname, String nickname, String email, String filePath) {
        this.fullname = fullname;
        this.nickname = nickname;
        this.email = email;
        this.filePath = filePath;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
