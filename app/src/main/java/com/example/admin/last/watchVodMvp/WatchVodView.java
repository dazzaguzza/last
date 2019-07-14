package com.example.admin.last.watchVodMvp;

import com.google.android.exoplayer2.ui.PlayerView;

public interface WatchVodView {
    PlayerView setPlayer();
    void setVodListView(String json);
    void setClick(int position,String copyJson);
}
