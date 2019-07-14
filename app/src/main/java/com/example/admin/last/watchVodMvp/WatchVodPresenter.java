package com.example.admin.last.watchVodMvp;

import android.content.Context;
import android.os.Bundle;

import com.example.admin.last.broadcastEndMvp.ItemEnd;

import java.util.ArrayList;

public interface WatchVodPresenter {

    void playVod(Context context, String url);
    void initVodListView(Context context);
    void click(int position,Context context);
}
