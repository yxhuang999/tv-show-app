package edu.northeastern.fall22_team34.sticker.models;

import java.util.ArrayList;
import java.util.List;

public class Information extends User {
    public List<String> imgSent;
    public List<String> imgReceived;
    public List<String> timeReceived;
    public List<String> senderReceived;

    public Information() {

    }

    public Information(List<String> imgSent, List<String> imgReceived, List<String> timeReceived, List<String> senderReceived ) {
        this.imgReceived = new ArrayList<>();
        this.timeReceived = new ArrayList<>();
        this.senderReceived = new ArrayList<>();
        this.imgSent = new ArrayList<>();
    }



    public List<String> getImgSent() {
        return imgSent;
    }

    public List<String> getImgReceived() {
        return imgReceived;
    }

    public List<String> getTimeReceived() {
        return timeReceived;
    }

    public List<String> getSenderReceived() {
        return senderReceived;
    }


    public void setImgSent(List<String> imageSent) {
        imgSent = imageSent;
    }

    public void setImgReceived(List<String> imgReceived2) {
        imgReceived = imgReceived2;
    }

    public void setTimeReceived(List<String> timeReceived2) {
        timeReceived = timeReceived2;
    }

    public void setSenderReceived(List<String> senderReceived2) {
        senderReceived = senderReceived2;
    }

}
