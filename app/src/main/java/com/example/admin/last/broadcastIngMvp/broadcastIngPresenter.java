package com.example.admin.last.broadcastIngMvp;

import android.support.design.widget.FloatingActionButton;

import java.util.ArrayList;

public interface broadcastIngPresenter {
    void setRecyclerView();
    void startFloatingAction(FloatingActionButton floatingActionButton, int dx, int dy);
    void clickedBroadcastFloating();
    void setAllStreamingRoom(ArrayList arrayList, AdapterIng adapterIng);
}
