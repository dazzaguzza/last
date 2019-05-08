package com.example.admin.last.broadcastIngMvp;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import java.util.ArrayList;

public interface broadcastIngPresenter {
    void setRecyclerView();
    void startFloatingAction(FloatingActionButton floatingActionButton, int dx, int dy);
    void clickedBroadcastFloating();
    void setAllStreamingRoom(ArrayList arrayList, AdapterIng adapterIng);
    void setRefreshListner();
    void getProfile(Context context);
}
