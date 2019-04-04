package com.example.admin.last;

public class ItemIng {

    String txt_id,img_contents;

    public ItemIng(String txt_id, String img_contents) {
        this.txt_id = txt_id;
        this.img_contents = img_contents;
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
}
