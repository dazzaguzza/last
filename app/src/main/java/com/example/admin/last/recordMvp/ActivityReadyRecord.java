package com.example.admin.last.recordMvp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.last.R;

public class ActivityReadyRecord extends AppCompatActivity implements recordView{

    recordPresenter mRecordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_record);

        mRecordPresenter = new recordPresenterImpl(this);

        mRecordPresenter.getRequest_permission(ActivityReadyRecord.this);


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mRecordPresenter.getRequest_result(requestCode, permissions, grantResults);
    }
}
