package com.example.admin.last.broadcastIngMvp;

import android.support.design.widget.FloatingActionButton;

import java.util.ArrayList;

public class broadcastIngPresenterImpl implements broadcastIngPresenter{

    broadcastIngView mBroadcastIngView;
    broadcastIngModel mBroadcastIngModel;

    public broadcastIngPresenterImpl(broadcastIngView mBroadcastIngView) {
        this.mBroadcastIngView = mBroadcastIngView;
        mBroadcastIngModel = new broadcastIngModelImpl();
    }

    @Override
    public void setRecyclerView() {
        mBroadcastIngView.makeRecyclerView();
    }

    @Override
    public void startFloatingAction(FloatingActionButton floatingActionButton, int dx, int dy) {

        if (dy<0 && !floatingActionButton.isShown()) {

            mBroadcastIngView.makeShowFloating();

        }else if(dy>0 && floatingActionButton.isShown()) {

            mBroadcastIngView.makeHideFloating();

        }
    }

    @Override
    public void clickedBroadcastFloating() {
        mBroadcastIngView.clickBroadcast();
    }

    @Override
    public void setAllStreamingRoom(ArrayList arrayList, AdapterIng adapterIng) {
        mBroadcastIngModel.getAllStreamingRoom(arrayList, adapterIng);
    }

}
