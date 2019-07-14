package com.example.admin.last.broadcastEndMvp;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;

public class BroadcastEndPresenterImpl implements BroadcastEndPresenter{

    BroadcastEndView mBroadcastEndView;
    BroadcastEndModel mBroadcastEndModel;

    public BroadcastEndPresenterImpl(BroadcastEndView mBroadcastEndView) {
        this.mBroadcastEndView = mBroadcastEndView;
        mBroadcastEndModel = new BroadcastEndModelImpl();
    }


    @Override
    public void setListView() {
        mBroadcastEndView.makeListView();
    }

    @Override
    public void setVodList(final ArrayList arrayList, final AdapterEnd adapterEnd,final ArrayList copyArrayList,Context context) {
        mBroadcastEndModel.getVodList(arrayList,adapterEnd,copyArrayList,context);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (arrayList.size() != 0) {
                    mBroadcastEndView.hideTxt();
                } else {
                    mBroadcastEndView.showTxt();
                }
            }
        };
        handler.postDelayed(runnable, 1000);

    }

    @Override
    public void clickVodListView(ArrayList<ItemEnd> arrayList, int position) {
        mBroadcastEndView.goToWatchVod(arrayList,position);
    }

    @Override
    public void startSearchVOD(ArrayList<ItemEnd> originArrayList,ArrayList<ItemEnd> copyArrayList,AdapterEnd adapterEnd, String string) {
        mBroadcastEndModel.searchVOD(originArrayList,copyArrayList,adapterEnd,string);
    }

}
