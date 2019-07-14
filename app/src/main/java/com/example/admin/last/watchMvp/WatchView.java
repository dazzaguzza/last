package com.example.admin.last.watchMvp;


import com.google.android.exoplayer2.ui.PlayerView;

public interface WatchView {
    PlayerView setPlayer();
    void showDialog();
    void changUi();
    String edtTxt();
}
