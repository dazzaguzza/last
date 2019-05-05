package com.example.admin.last.recordMvp;

import android.content.Context;

import com.pedro.rtplibrary.rtmp.RtmpCamera1;

public interface RecordModel {
    void setStream(Context context, String key);
    String makeKey();
    void startStreamCamera1(RtmpCamera1 rtmpCamera1, String key);
    void stopStreamCamera1(RtmpCamera1 rtmpCamera1,String key);
    void stopPreview(RtmpCamera1 rtmpCamera1);
}
