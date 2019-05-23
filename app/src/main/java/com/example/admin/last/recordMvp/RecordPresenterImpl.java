package com.example.admin.last.recordMvp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.last.R;
import com.pedro.rtplibrary.rtmp.RtmpCamera1;

public class RecordPresenterImpl implements RecordPresenter {

    RecordView mRecordView;
    RecordModel mRecordModel;
    String key;
    Context context;

    public RecordPresenterImpl(RecordView mRecordView,Context context) {
        this.mRecordView = mRecordView;
        mRecordModel = new RecordModelImpl();
        this.context = context;
      //  key = mRecordModel.makeKey(context);
        if(mRecordModel.getNaverUserNumber(context) != null) {
            key = mRecordModel.getNaverUserNumber(context);
        }else if(mRecordModel.getKakaoUserNumber(context) !=null){
            key = mRecordModel.getKakaoUserNumber(context);
        }
    }

    @Override
    public void getRequest_permission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA
                , Manifest.permission.RECORD_AUDIO
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE
                }, 100);
    }

    @Override
    public void getRequest_result(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 100
                && permissions.length == 4
                && permissions[0].equals(Manifest.permission.CAMERA)
                && permissions[1].equals(Manifest.permission.RECORD_AUDIO)
                && permissions[2].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && permissions[3].equals(Manifest.permission.READ_EXTERNAL_STORAGE)


                && grantResults[0] == PermissionChecker.PERMISSION_GRANTED
                && grantResults[1] == PermissionChecker.PERMISSION_GRANTED
                && grantResults[2] == PermissionChecker.PERMISSION_GRANTED
                && grantResults[3] == PermissionChecker.PERMISSION_GRANTED) {

            mRecordView.viewShow();
            mRecordView.setTexturView();
            mRecordView.getTexturViewListener();
            mRecordView.cameraOn();

        } else {
            mRecordView.viewGone();
        }


    }

    @Override
    public void startStreamPhp(Context context) {
        mRecordModel.setStream(context,key);
        mRecordView.sayStreaming();
        Log.d("TAG", "startStreamPhp: "+key);
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
                    mRecordView.showSetRoomName();
                    mRecordModel.startStreamCamera1(rtmpCamera1, key);

                } else {

                }
            } else {
                mRecordView.setRecordImg();
                mRecordModel.stopStreamCamera1(rtmpCamera1,key);
                mRecordView.hideSetRoomName();
                mRecordView.showDialog();

            }
        } catch (Exception e) {
        }
    }

    @Override
    public void connetionErorr(RtmpCamera1 rtmpCamera1) {
        mRecordModel.endOfStream(rtmpCamera1,context);
        mRecordView.showErorr();
        mRecordView.setRecordImg();
        mRecordView.hideSetRoomName();
    }

    @Override
    public void surfaceTextureDestroyed(RtmpCamera1 rtmpCamera1) {
        try {

            if (rtmpCamera1.isStreaming()) {
                mRecordModel.endOfStream(rtmpCamera1,context);
                mRecordView.setRecordImg();
                mRecordView.hideSetRoomName();

            }
            mRecordModel.stopPreview(rtmpCamera1);

        } catch (Exception e) {
        }
    }

    @Override
    public void end(RtmpCamera1 rtmpCamera1) {
        mRecordModel.endOfStream(rtmpCamera1,context);
        mRecordView.setRecordImg();
    }

    @Override
    public void makeRoomName(String string) {
        if(!string.isEmpty()) {
            mRecordModel.setRoomName(string);
        }else{
            mRecordView.toastFillRoomName();
        }

    }


}
