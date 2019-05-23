package com.example.admin.last.loginMvp;

import android.content.Context;

import com.example.admin.last.SharedPreferenceUtil;

public interface LoginModel {

    void setNaverToken(Context context, String string);
    void setKakaoRenewUserId(Context context,String string);
    void setKakaoRenewUserImg(Context context,String string);
    void setNaverRenewUserId(Context context,String string);
    void setNaverRenewUserImg(Context context,String string);
    void setNullRefreshKakaoToken(Context context);
    void setNullRefreshNaverToken(Context context);
    void setNullNaverUserNumber(Context context);
    void setNullKakaoUserNumber(Context context);
}
