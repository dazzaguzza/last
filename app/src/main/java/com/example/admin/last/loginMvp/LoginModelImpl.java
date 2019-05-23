package com.example.admin.last.loginMvp;

import android.content.Context;

import com.example.admin.last.SharedPreferenceUtil;

public class LoginModelImpl implements LoginModel {

    SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    public void setNaverToken(Context context,String string) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("naverRefreshToken", string);
    }

    @Override
    public void setKakaoRenewUserId(Context context,String string) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("kakaoUserId",string);
    }

    @Override
    public void setKakaoRenewUserImg(Context context,String string) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("kakaoUserImg",string);
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


    @Override
    public void setNullRefreshKakaoToken(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("kakaoRefreshToken",null);
    }

    @Override
    public void setNullRefreshNaverToken(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("naverRefreshToken",null);
    }

    @Override
    public void setNullNaverUserNumber(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("naverUserNumber",null);
    }

    @Override
    public void setNullKakaoUserNumber(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("kakaoUserNumber",null);

    }
}
