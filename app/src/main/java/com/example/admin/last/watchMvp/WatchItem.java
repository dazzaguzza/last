package com.example.admin.last.watchMvp;

public class WatchItem {

    String watch_chat_profile,watch_chat_txt;

    public WatchItem(String watch_chat_profile, String watch_chat_txt) {
        this.watch_chat_profile = watch_chat_profile;
        this.watch_chat_txt = watch_chat_txt;
    }

    public String getWatch_chat_profile() {
        return watch_chat_profile;
    }

    public void setWatch_chat_profile(String watch_chat_profile) {
        this.watch_chat_profile = watch_chat_profile;
    }

    public String getWatch_chat_txt() {
        return watch_chat_txt;
    }

    public void setWatch_chat_txt(String watch_chat_txt) {
        this.watch_chat_txt = watch_chat_txt;
    }
}
