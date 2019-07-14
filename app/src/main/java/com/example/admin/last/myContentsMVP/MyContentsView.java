package com.example.admin.last.myContentsMVP;

import java.util.ArrayList;

public interface MyContentsView {

    void makeMyContentsListView();
    void showDialog();
    void goToWatchMyVod(ArrayList<MyContentsItem> arrayList, int position);
    void showTxt();
    void hideTxt();
}
