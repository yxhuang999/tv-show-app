package edu.northeastern.fall22_team34.sticker.models;

import java.util.ArrayList;
import java.util.List;

public class User {

    public String username;
    public String REGISTRATION_TOKEN;
    public int imgSent;
    public List<String> imgHistory;

    public User() {

    }

    public User(String username, String REGISTRATION_TOKEN) {
        this.username = username;
        this.REGISTRATION_TOKEN = REGISTRATION_TOKEN;
        this.imgSent = 0;
        this.imgHistory = new ArrayList<>();
    }
}
