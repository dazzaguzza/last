package com.example.admin.last.broadcastEndMvp;

import android.content.Context;

import java.util.ArrayList;

public interface BroadcastEndPresenter {

    void setListView();
    void setVodList(ArrayList arrayList,AdapterEnd adapterEnd,ArrayList copyArrayList,Context context);
    void clickVodListView(ArrayList<ItemEnd> arrayList, int position);
    void startSearchVOD(ArrayList<ItemEnd> originArrayList,ArrayList<ItemEnd> copyArrayList,AdapterEnd adapterEnd, String string);

}
