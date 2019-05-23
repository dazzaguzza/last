package com.example.admin.last.recordMvp;

import android.content.Context;

import com.example.admin.last.SharedPreferenceUtil;
import com.pedro.rtplibrary.rtmp.RtmpCamera1;

public interface RecordModel {
    void setStream(Context context, String key);
    String makeKey(Context context);
    void startStreamCamera1(RtmpCamera1 rtmpCamera1, String key);
    void stopStreamCamera1(RtmpCamera1 rtmpCamera1,String key);
    void stopPreview(RtmpCamera1 rtmpCamera1);
    String getKakaoUserNumber(Context context);
    String getNaverUserNumber(Context context);
    void endOfStream(RtmpCamera1 rtmpCamera1,Context context);
    void setRoomName(String roomName);
}
