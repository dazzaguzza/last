package com.example.admin.last.recordMvp;

import android.content.Context;
import android.util.Log;

import com.example.admin.last.SharedPreferenceUtil;
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
    SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    public void setStream(Context context, String key) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        String userId = sharedPreferenceUtil.getSharedPreference("UserId");
        String userImg = sharedPreferenceUtil.getSharedPreference("UserImg");

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<RecordData> call = apiInterface.setStream(userImg,userId,rtmpUrl+key,key);
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
    public void stopStreamCamera1(RtmpCamera1 rtmpCamera1,String key) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<RecordData> call = apiInterface.stopStream(rtmpUrl+key,key);
        call.enqueue(new Callback<RecordData>() {
            @Override
            public void onResponse(Call<RecordData> call, Response<RecordData> response) {

            }

            @Override
            public void onFailure(Call<RecordData> call, Throwable t) {

            }
        });

        rtmpCamera1.stopStream();
        Log.d("TAG", "stopStreamCamera1: "+key);
    }

    @Override
    public void stopPreview(RtmpCamera1 rtmpCamera1) {
        rtmpCamera1.stopPreview();
    }
}
