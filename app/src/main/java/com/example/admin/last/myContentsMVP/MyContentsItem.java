package com.example.admin.last.myContentsMVP;

public class MyContentsItem {

    String myImgProfile;
    String myTxtId;
    String myThumbnail;
    String myTitle;
    String myAddress;
    String id;

    public MyContentsItem(String myImgProfile, String myTxtId, String myThumbnail, String myTitle, String myAddress, String id) {
        this.myImgProfile = myImgProfile;
        this.myTxtId = myTxtId;
        this.myThumbnail = myThumbnail;
        this.myTitle = myTitle;
        this.myAddress = myAddress;
        this.id = id;
    }

    public String getMyImgProfile() {
        return myImgProfile;
    }

    public void setMyImgProfile(String myImgProfile) {
        this.myImgProfile = myImgProfile;
    }

    public String getMyTxtId() {
        return myTxtId;
    }

    public void setMyTxtId(String myTxtId) {
        this.myTxtId = myTxtId;
    }

    public String getMyThumbnail() {
        return myThumbnail;
    }

    public void setMyThumbnail(String myThumbnail) {
        this.myThumbnail = myThumbnail;
    }

    public String getMyTitle() {
        return myTitle;
    }

    public void setMyTitle(String myTitle) {
        this.myTitle = myTitle;
    }

    public String getMyAddress() {
        return myAddress;
    }

    public void setMyAddress(String myAddress) {
        this.myAddress = myAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
