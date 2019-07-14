package com.example.admin.last.watchMvp;

import android.content.Context;

import com.example.admin.last.SharedPreferenceUtil;

public class WatchModelImpl implements WatchModel {

    SharedPreferenceUtil sharedPreferenceUtil;

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
    public String getNaverUserId(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("naverUserId");
    }

    @Override
    public String getNaverUserImg(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("naverUserImg");
    }
}
