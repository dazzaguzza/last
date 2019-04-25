package com.example.admin.last.recordMvp;

import android.Manifest;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.widget.Toast;

import com.example.admin.last.R;
import com.pedro.rtplibrary.rtmp.RtmpCamera1;

public class RecordPresenterImpl implements RecordPresenter {

    RecordView mRecordView;
    RecordModel mRecordModel;
    String key;

    public RecordPresenterImpl(RecordView mRecordView) {
        this.mRecordView = mRecordView;
        mRecordModel = new RecordModelImpl();
        key = mRecordModel.makeKey();
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

                mRecordView.setTexturView();
                mRecordView.getTexturViewListener();

        }

        mRecordView.cameraOn();

    }

    @Override
    public void startStreamPhp() {
        mRecordModel.setStream(key);
        mRecordView.sayStreaming();
    }

    @Override
    public void endStream() {
        mRecordView.sayStreamEnd();
    }

    @Override
    public void streamOrNot(RtmpCamera1 rtmpCamera1) {
        try {

            if (!rtmpCamera1.isStreaming()) {
                if (rtmpCamera1.isRecording()
                        || rtmpCamera1.prepareAudio() && rtmpCamera1.prepareVideo()) {

                    mRecordView.viewGone();
                    mRecordView.setRecordingImg();
                    mRecordModel.startStreamCamera1(rtmpCamera1, key);

                } else {

                }
            } else {
                mRecordView.setRecordImg();
                mRecordModel.stopStreamCamera1(rtmpCamera1);
            }
        }catch (Exception e){}
    }

    @Override
    public void connetionErorr(RtmpCamera1 rtmpCamera1) {
        mRecordModel.stopStreamCamera1(rtmpCamera1);
        mRecordView.showErorr();
        mRecordView.setRecordImg();
    }

    @Override
    public void surfaceTextureDestroyed(RtmpCamera1 rtmpCamera1) {
        try {

            if (rtmpCamera1.isStreaming()) {
                mRecordModel.stopStreamCamera1(rtmpCamera1);
                mRecordView.setRecordImg();

            }
                mRecordModel.stopPreview(rtmpCamera1);

        }catch (Exception e){}
    }


}
