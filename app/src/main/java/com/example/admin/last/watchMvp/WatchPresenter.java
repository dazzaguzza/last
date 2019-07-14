package com.example.admin.last.watchMvp;

import android.content.Context;
import android.widget.ListView;

public interface WatchPresenter {

    void play(Context context,String url, String key);
    void socketOpenAndChat(Context context, ListView listView, String key);
    void socketDestroy();
    void sendMsg(Context context);
}
