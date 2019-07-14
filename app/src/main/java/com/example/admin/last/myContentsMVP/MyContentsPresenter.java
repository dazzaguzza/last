package com.example.admin.last.myContentsMVP;

import android.content.Context;

import java.util.ArrayList;

public interface MyContentsPresenter {

    void setMyContentsListView();
    void setMyContentsList(ArrayList arrayList, MyContentsAdapter myContentsAdapter, Context context);
    void showDialogMyContents();
    void setMyContentsImgAndTitle(Context context,String img,String title, String id);
    void longClick(Context context,String img,String title, String id,ArrayList<MyContentsItem> arrayList,int position);
}
