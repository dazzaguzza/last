package com.example.admin.last.recordMvp;

import android.Manifest;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;

public class recordPresenterImpl implements recordPresenter {

    recordView mRecordView;
    recordModel mRecordModel;

    public recordPresenterImpl(recordView mRecordView) {
        this.mRecordView = mRecordView;
        mRecordModel = new recordModelImpl();
    }

    @Override
    public void getRequest_permission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA
                ,Manifest.permission.RECORD_AUDIO}, 100);
    }

    @Override
    public void getRequest_result(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 100
                && permissions.length == 2
                && permissions[0].equals(Manifest.permission.CAMERA)
                && permissions[1].equals(Manifest.permission.RECORD_AUDIO)
                && grantResults[0] == PermissionChecker.PERMISSION_GRANTED
                && grantResults[1] ==PermissionChecker.PERMISSION_GRANTED) {
        }

    }
}
