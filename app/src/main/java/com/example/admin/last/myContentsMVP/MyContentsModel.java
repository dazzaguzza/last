package com.example.admin.last.myContentsMVP;

import android.content.Context;

import java.util.ArrayList;

public interface MyContentsModel {

    void getMyContentsList(ArrayList arrayList, MyContentsAdapter myContentsAdapter, Context context);
    void setMyContentsImg(Context context,String string);
    void setMyContentsTitle(Context context,String string);
    void setMyContentsId(Context context,String string);
    void deleteMyContents(String id);
}
