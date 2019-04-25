package com.example.admin.last.recordMvp;

import com.pedro.rtplibrary.rtmp.RtmpCamera1;

public interface RecordModel {
    void setStream(String key);
    String makeKey();
    void startStreamCamera1(RtmpCamera1 rtmpCamera1, String key);
    void stopStreamCamera1(RtmpCamera1 rtmpCamera1);
    void stopPreview(RtmpCamera1 rtmpCamera1);
}
