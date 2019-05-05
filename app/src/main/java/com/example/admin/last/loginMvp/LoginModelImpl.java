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
    public void setRenewUserId(Context context,String string) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("UserId",string);
    }

    @Override
    public void setRenewUserImg(Context context,String string) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("UserImg",string);
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
}
