package com.example.admin.last.recordMvp;

import android.util.Log;

import com.example.admin.last.retrofit.ApiClient;
import com.example.admin.last.retrofit.ApiInterface;
import com.pedro.rtplibrary.rtmp.RtmpCamera1;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordModelImpl implements RecordModel {

    public static ApiInterface apiInterface;
    String rtmpUrl="rtmp://52.79.243.140/live/";

    @Override
    public void setStream(String key) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<RecordData> call = apiInterface.setStream(rtmpUrl+key,key);
        call.enqueue(new Callback<RecordData>() {
            @Override
            public void onResponse(Call<RecordData> call, Response<RecordData> response) {
                Log.d("TAG", "onResponse: 전송됨");
            }

            @Override
            public void onFailure(Call<RecordData> call, Throwable t) {
                Log.d("TAG", "onResponse: 실패");
            }
        });
    }

    @Override
    public String makeKey() {
        Random random = new Random();
        int makeKey = random.nextInt(222222222);
        String key = String.valueOf(makeKey);
        return key;
    }

    @Override
    public void startStreamCamera1(RtmpCamera1 rtmpCamera1,String key) {
        rtmpCamera1.startStream(rtmpUrl+key);
    }

    @Override
    public void stopStreamCamera1(RtmpCamera1 rtmpCamera1) {
        rtmpCamera1.stopStream();
    }

    @Override
    public void stopPreview(RtmpCamera1 rtmpCamera1) {
        rtmpCamera1.stopPreview();
    }
}
