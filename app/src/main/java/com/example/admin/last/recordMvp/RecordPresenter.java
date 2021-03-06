package com.example.admin.last.recordMvp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ListView;

import com.pedro.rtplibrary.rtmp.RtmpCamera1;

import java.util.ArrayList;

public interface RecordPresenter {
    void getRequest_permission(Activity activity);
    void getRequest_result(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
    void startStreamPhp(Context context);
    void endStream();
    void streamOrNot(RtmpCamera1 rtmpCamera1);
    void connetionErorr(RtmpCamera1 rtmpCamera1);
    void surfaceTextureDestroyed(RtmpCamera1 rtmpCamera1);
    void end(RtmpCamera1 rtmpCamera1);
    void makeRoomName(String string);
    void socketOpenAndReceive(ListView listView , Context context);
    void socketDestroy();
    void changeCameraBackOrFront();
}
