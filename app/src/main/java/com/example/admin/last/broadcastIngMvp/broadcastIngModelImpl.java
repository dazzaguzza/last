package com.example.admin.last.broadcastIngMvp;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;

import com.example.admin.last.SharedPreferenceUtil;
import com.example.admin.last.recordMvp.RecordData;
import com.example.admin.last.retrofit.ApiClient;
import com.example.admin.last.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class broadcastIngModelImpl implements broadcastIngModel {

    public static ApiInterface apiInterface;
    SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    public void getAllStreamingRoom(final ArrayList arrayList, final AdapterIng adapterIng) {
//        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//        Call<List<RoomData>> call = apiInterface.getStreamingRoom("ok");
//        call.enqueue(new Callback<List<RoomData>>() {
//            @Override
//            public void onResponse(Call<List<RoomData>> call, Response<List<RoomData>> response) {
//
//                arrayList.clear();
//
//                try{
//                    for (int i = 0; i < response.body().size(); i++) {
//
//                        arrayList.add(new ItemIng(response.body().get(i).profileImg,response.body().get(i).id,response.body().get(i).imgPath,
//                                response.body().get(i).url,response.body().get(i).key,response.body().get(i).roomName));
//
//                        Log.d("TAG", "onResponse: "+response.body().get(i).getUrl());
//                        Log.d("TAG", "onResponse: "+response.body().get(i).imgPath);
//                        Log.d("TAG", "onResponse1: "+response.body().get(i).url);
//
//                    }
//                  adapterIng.notifyDataSetChanged();
//                }catch (Exception e){
//
//                }
//                }
//
//
//            @Override
//            public void onFailure(Call<List<RoomData>> call, Throwable t) {
//                Log.d("TAG", "onResponse: fail"+t);
//            }
//        });
    }

    @Override
    public String checkKakaoToken(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("kakaoRefreshToken");
    }

    @Override
    public String checkNaverToken(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("naverRefreshToken");
    }

    @Override
    public String getKakaoUserId(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("kakaoUserId");
    }

    @Override
    public String getKakaoUserImg(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("kakaoUserImg");
    }

    @Override
    public String getKakaoUserNumber(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("kakaoUserNumber");
    }

    @Override
    public void setKakaoUserNumber(Context context, String string) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("kakaoUserNumber",string);
    }

    @Override
    public void setKakaoRenewUserId(Context context, String string) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("kakaoUserId",string);
    }

    @Override
    public void setKakaoRenewUserImg(Context context,String string) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("kakaoUserImg",string);
    }

    @Override
    public String getNaverUserId(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("naverUserId");
    }

    @Override
    public String getNaverUserImg(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("naverUserImg");
    }

    @Override
    public String getNaverUserNumber(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("naverUserNumber");
    }

    @Override
    public void setNaverUserNumber(Context context, String string) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("naverUserNumber",string);
    }

    @Override
    public void setNaverRenewUserId(Context context, String string) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("naverUserId",string);
    }

    @Override
    public void setNaverRenewUserImg(Context context, String string) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("naverUserImg",string);
    }
}
