package edu.northeastern.fall22_team34.sticker.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

    public String username;
    public String REGISTRATION_TOKEN;
    public Map<String, Integer> imgSent;
    public List<String> imgReceived;
    public List<String> timeReceived;
    public List<String> senderReceived;
    public User() {

    }

    public User(String username, String REGISTRATION_TOKEN) {
        this.username = username;
        this.REGISTRATION_TOKEN = REGISTRATION_TOKEN;
        this.imgReceived = new ArrayList<>();
        this.timeReceived = new ArrayList<>();
        this.senderReceived = new ArrayList<>();
        this.imgSent = new HashMap<>();
    }

    public User(String username, String REGISTRATION_TOKEN, int imgSent, List<String> imgReceived) {
        this.username = username;
        this.REGISTRATION_TOKEN = REGISTRATION_TOKEN;
        this.imgReceived = new ArrayList<>();
    }
}
