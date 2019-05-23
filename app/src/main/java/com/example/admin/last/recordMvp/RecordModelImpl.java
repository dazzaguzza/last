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
    String rtmpUrl = "rtmp://52.79.243.140/live/";
    SharedPreferenceUtil sharedPreferenceUtil;
    String userId, userImg;
    String img_profile, txt_id, key1, url, path;

    @Override
    public void setStream(Context context, String key) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        if (sharedPreferenceUtil.getSharedPreference("kakaoUserId") != null) {

            userId = sharedPreferenceUtil.getSharedPreference("kakaoUserId");
            userImg = sharedPreferenceUtil.getSharedPreference("kakaoUserImg");

        } else if (sharedPreferenceUtil.getSharedPreference("naverUserId") != null) {

            userId = sharedPreferenceUtil.getSharedPreference("naverUserId");
            userImg = sharedPreferenceUtil.getSharedPreference("naverUserImg");

        }

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<RecordData> call = apiInterface.setStream(userImg, userId, rtmpUrl + key, key);
        call.enqueue(new Callback<RecordData>() {
            @Override
            public void onResponse(Call<RecordData> call, Response<RecordData> response) {
                img_profile = response.body().profileImg;
                txt_id = response.body().id;
                key1 = response.body().key;
                url = response.body().url;
                path = response.body().imgPath;
            }

            @Override
            public void onFailure(Call<RecordData> call, Throwable t) {
                Log.d("TAG", "onResponse: 실패");
            }
        });
    }

    @Override
    public String makeKey(Context context) {
        Random random = new Random();
        int makeKey = random.nextInt(999999999);
        String key = String.valueOf(makeKey);
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("key", key);
        return key;
    }

    @Override
    public void startStreamCamera1(RtmpCamera1 rtmpCamera1, String key) {
        rtmpCamera1.startStream(rtmpUrl + key);
    }

    @Override
    public void stopStreamCamera1(RtmpCamera1 rtmpCamera1, String key) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<RecordData> call = apiInterface.stopStream(rtmpUrl + key, key);
        call.enqueue(new Callback<RecordData>() {
            @Override
            public void onResponse(Call<RecordData> call, Response<RecordData> response) {

            }

            @Override
            public void onFailure(Call<RecordData> call, Throwable t) {

            }
        });

        rtmpCamera1.stopStream();

        Log.d("TAG", "stopStreamCamera1: " + key);
    }

    @Override
    public void stopPreview(RtmpCamera1 rtmpCamera1) {
        rtmpCamera1.stopPreview();
    }

    @Override
    public String getKakaoUserNumber(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("kakaoUserNumber");
    }

    @Override
    public String getNaverUserNumber(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("naverUserNumber");
    }

    @Override
    public void endOfStream(RtmpCamera1 rtmpCamera1, Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        String key = null;
        if (sharedPreferenceUtil.getSharedPreference("naverUserNumber") != null) {
            key = sharedPreferenceUtil.getSharedPreference("naverUserNumber");
        } else if (sharedPreferenceUtil.getSharedPreference("kakaoUserNumber") != null) {
            key = sharedPreferenceUtil.getSharedPreference("kakaoUserNumber");
        }
        Log.d("TAG", "onResponse: " + key);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<RecordData> call = apiInterface.end(key);
        call.enqueue(new Callback<RecordData>() {
            @Override
            public void onResponse(Call<RecordData> call, Response<RecordData> response) {

            }

            @Override
            public void onFailure(Call<RecordData> call, Throwable t) {

            }
        });
        rtmpCamera1.stopStream();
    }

    @Override
    public void setRoomName(String rommName) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<RecordData> call = apiInterface.roomName(img_profile, txt_id, key1, url, path,rommName);
        call.enqueue(new Callback<RecordData>() {
            @Override
            public void onResponse(Call<RecordData> call, Response<RecordData> response) {

            }

            @Override
            public void onFailure(Call<RecordData> call, Throwable t) {

            }
        });
    }
}
