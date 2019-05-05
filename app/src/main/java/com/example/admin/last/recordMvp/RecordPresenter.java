package com.example.admin.last.recordMvp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.pedro.rtplibrary.rtmp.RtmpCamera1;

public interface RecordPresenter {
    void getRequest_permission(Activity activity);
    void getRequest_result(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
    void startStreamPhp(Context context);
    void endStream();
    void streamOrNot(RtmpCamera1 rtmpCamera1);
    void connetionErorr(RtmpCamera1 rtmpCamera1);
    void surfaceTextureDestroyed(RtmpCamera1 rtmpCamera1);
}
