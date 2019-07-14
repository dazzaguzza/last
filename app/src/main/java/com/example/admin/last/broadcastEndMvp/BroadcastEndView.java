package com.example.admin.last.broadcastEndMvp;

import java.util.ArrayList;

public interface BroadcastEndView {

    void makeListView();
    void goToWatchVod(ArrayList<ItemEnd> arrayList, int position);
    void showTxt();
    void hideTxt();
}
