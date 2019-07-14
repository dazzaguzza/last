package com.example.admin.last.broadcastEndMvp;

import android.content.Context;

import java.util.ArrayList;

public interface BroadcastEndModel {

    void getVodList(ArrayList arrayList,AdapterEnd adapterEnd,ArrayList copyArrayList,Context context);
    void searchVOD(ArrayList<ItemEnd> originArrayList,ArrayList<ItemEnd> copyArrayList,AdapterEnd adapterEnd, String string);

}
