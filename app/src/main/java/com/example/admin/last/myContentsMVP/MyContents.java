package com.example.admin.last.myContentsMVP;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.admin.last.R;
import com.example.admin.last.databinding.ActivityMyContentsBinding;
import com.example.admin.last.recordMvp.EditTextDialog;
import com.example.admin.last.watchVodMvp.WatchVod;

import java.util.ArrayList;

public class MyContents extends AppCompatActivity implements MyContentsView{

    static MyContentsAdapter myContentsAdapter;
    ArrayList<MyContentsItem> arrayList;
    ActivityMyContentsBinding binding;
    MyContentsPresenter mMyContentsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_contents);

        mMyContentsPresenter = new MyContentsPresenterImpl(this);

        mMyContentsPresenter.setMyContentsListView();
        mMyContentsPresenter.setMyContentsList(arrayList,myContentsAdapter,this);

        binding.MyContentsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                mMyContentsPresenter.longClick(MyContents.this,arrayList.get(i).getMyThumbnail(),arrayList.get(i).getMyTitle(),arrayList.get(i).getId(),arrayList,i);


                return true;
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice,
                new IntentFilter("notify"));
    }

    @Override
    public void makeMyContentsListView() {
        arrayList = new ArrayList<>();
        myContentsAdapter = new MyContentsAdapter(MyContents.this,arrayList);
        binding.MyContentsListView.setAdapter(myContentsAdapter);
    }

    @Override
    public void showDialog() {
        EditTextDialogMyContents dialog = new EditTextDialogMyContents();
        dialog.show(getSupportFragmentManager());
    }

    @Override
    public void goToWatchMyVod(ArrayList<MyContentsItem> arrayList, int position) {
        Intent intent = new Intent(MyContents.this, WatchVod.class);
        intent.putExtra("vodUrl", arrayList.get(position).getMyAddress());
        startActivity(intent);
    }

    @Override
    public void showTxt() {
        binding.RelWithTxtMyContents.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTxt() {
        binding.RelWithTxtMyContents.setVisibility(View.GONE);
    }

    private BroadcastReceiver onNotice = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mMyContentsPresenter.setMyContentsList(arrayList,myContentsAdapter,MyContents.this);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(onNotice);
    }
}
