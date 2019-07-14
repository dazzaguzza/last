package com.example.admin.last.broadcastEndMvp;

public class ItemEnd {

    String imgProfile;
    String txtId;
    String thumbnail;
    String title;
    String address;

    public ItemEnd(String imgProfile, String txtId, String thumbnail, String title, String address) {
        this.imgProfile = imgProfile;
        this.txtId = txtId;
        this.thumbnail = thumbnail;
        this.title = title;
        this.address = address;
    }

    public String getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(String imgProfile) {
        this.imgProfile = imgProfile;
    }

    public String getTxtId() {
        return txtId;
    }

    public void setTxtId(String txtId) {
        this.txtId = txtId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
