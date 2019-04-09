package com.example.admin.last.broadcastIngMvp;

import android.support.design.widget.FloatingActionButton;

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
        mBroadcastIngModel.floatingAction(floatingActionButton,dx,dy,mBroadcastIngView);
    }

    @Override
    public void clickedBroadcastFloating() {
        mBroadcastIngView.clickBroadcast();
    }

}