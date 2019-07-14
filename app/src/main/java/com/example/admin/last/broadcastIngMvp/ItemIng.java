package com.example.admin.last.broadcastIngMvp;

import java.util.ArrayList;
import java.util.List;

public class ItemIng {

    String img_profile,txt_id,img_contents,url,key,room_name;

    public ItemIng(String img_prifle,String txt_id, String img_contents, String url,String key,String room_name) {
        this.img_profile = img_prifle;
        this.txt_id = txt_id;
        this.img_contents = img_contents;
        this.url = url;
        this.key = key;
        this.room_name = room_name;

    }

    public String getImg_profile() {
        return img_profile;
    }

    public void setImg_profile(String img_profile) {
        this.img_profile = img_profile;
    }

    public String getTxt_id() {
        return txt_id;
    }

    public void setTxt_id(String txt_id) {
        this.txt_id = txt_id;
    }

    public String getImg_contents() {
        return img_contents;
    }

    public void setImg_contents(String img_contents) {
        this.img_contents = img_contents;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

}