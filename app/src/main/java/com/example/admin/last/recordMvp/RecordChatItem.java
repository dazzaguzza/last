package com.example.admin.last.recordMvp;

public class RecordChatItem {

    String chat_profile,chat_txt;

    public RecordChatItem(String chat_profile, String chat_txt) {
        this.chat_profile = chat_profile;
        this.chat_txt = chat_txt;
    }

    public String getChat_profile() {
        return chat_profile;
    }

    public void setChat_profile(String chat_profile) {
        this.chat_profile = chat_profile;
    }

    public String getChat_txt() {
        return chat_txt;
    }

    public void setChat_txt(String chat_txt) {
        this.chat_txt = chat_txt;
    }
}
