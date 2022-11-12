package edu.northeastern.fall22_team34.sticker.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {

    public String username;
    public String REGISTRATION_TOKEN;
//    public List<String> imgSent;
//    public List<String> imgReceived;
//    public List<String> timeReceived;
//    public List<String> senderReceived;

    public User() {

    }

    public User(String username, String REGISTRATION_TOKEN) {
        this.username = username;
        this.REGISTRATION_TOKEN = REGISTRATION_TOKEN;
//        this.imgReceived = new ArrayList<>();
//        this.timeReceived = new ArrayList<>();
//        this.senderReceived = new ArrayList<>();
//        this.imgSent = new ArrayList<>();
    }
//, List<Date> timeReceived, List<String> senderReceived, Map<String, Integer> imgSent
//    public User(String username, String REGISTRATION_TOKEN, List<String> imgReceived) {
//        this.username = username;
//        this.REGISTRATION_TOKEN = REGISTRATION_TOKEN;
//        this.imgReceived = new ArrayList<>();
//        this.timeReceived = new ArrayList<>();
//        this.senderReceived = new ArrayList<>();
//        this.imgSent = new HashMap<>();
//    }

    public String getUsername() {
        return username;
    }

//    public String getToken() {
//        return REGISTRATION_TOKEN;
//    }
//


//
//    public List<String> getImgSent() {
//        return imgSent;
//    }
//
//    public List<String> getImgReceived() {
//        return imgReceived;
//    }
//
//    public List<String> getTimeReceived() {
//        return timeReceived;
//    }
//
//    public List<String> getSenderReceived() {
//        return senderReceived;
//    }
//
//
//    public void setImgSent(List<String> imageSent) {
//        imgSent = imageSent;
//    }
//
//    public void setImgReceived(List<String> imgReceived2) {
//        imgReceived = imgReceived2;
//    }
//
//    public void setTimeReceived(List<String> timeReceived2) {
//        timeReceived = timeReceived2;
//    }
//
//    public void setSenderReceived(List<String> senderReceived2) {
//        senderReceived = senderReceived2;
//    }


}