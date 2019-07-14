package com.example.admin.last.myContentsMVP;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.ArrayList;

public class MyContentsPresenterImpl implements MyContentsPresenter {

    MyContentsView mMyContentsView;
    MyContentsModel mMyContentsModel;

    public MyContentsPresenterImpl(MyContentsView mMyContentsView) {
        this.mMyContentsView = mMyContentsView;
        mMyContentsModel = new MyContentsModelImpl();
    }


    @Override
    public void setMyContentsListView() {
        mMyContentsView.makeMyContentsListView();
    }

    @Override
    public void setMyContentsList(final ArrayList arrayList, MyContentsAdapter myContentsAdapter, Context context) {
        mMyContentsModel.getMyContentsList(arrayList,myContentsAdapter,context);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (arrayList.size() != 0) {
                    mMyContentsView.hideTxt();
                } else {
                    mMyContentsView.showTxt();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    @Override
    public void showDialogMyContents() {
        mMyContentsView.showDialog();
    }

    @Override
    public void setMyContentsImgAndTitle(Context context,String img,String title, String id) {
        mMyContentsModel.setMyContentsImg(context,img);
        mMyContentsModel.setMyContentsTitle(context,title);
        mMyContentsModel.setMyContentsId(context,id);
    }

    @Override
    public void longClick(final Context context, final String img, final String title, final String id, final ArrayList<MyContentsItem> arrayList, final int position) {
        final CharSequence[] items ={"수정하기","삭제하기","영상보기"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("선택하세요")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(items[i] == items[0]){
                            mMyContentsView.showDialog();
                            mMyContentsModel.setMyContentsImg(context,img);
                            mMyContentsModel.setMyContentsTitle(context,title);
                            mMyContentsModel.setMyContentsId(context,id);
                        }else if(items[i] == items[1]){

                            final AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setMessage("삭제하시겠습니까?");
                            builder1.setPositiveButton("예",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            mMyContentsModel.deleteMyContents(id);
                                            Intent intent = new Intent("notify");
                                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                            Log.d("TAG", "onClick:id "+id);
                                        }
                                    });
                            builder1.setNegativeButton("취소",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });

                            builder1.show();
                        }else{
                            mMyContentsView.goToWatchMyVod(arrayList,position);
                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
