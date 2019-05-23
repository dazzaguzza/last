package com.example.admin.last.profileMvp;

import android.content.Context;

import com.example.admin.last.SharedPreferenceUtil;

public interface ProfileModel {
    void tryNaverLogout(Context context);
    String checkKakaoToken(Context context);
    String checkNaverToken(Context context);
    String getKakaoUserId(Context context);
    String getKakaoUserImg(Context context);
    void setNullKakaoUserNumber(Context context);
    void setKakaoRenewUserId(Context context,String string);
    void setKakaoRenewUserImg(Context context,String string);
    String getNaverUserId(Context context);
    String getNaverUserImg(Context context);
    void setNullNaverUserNumber(Context context);
    void setNaverRenewUserId(Context context,String string);
    void setNaverRenewUserImg(Context context,String string);
    void setNullRefreshKakaoToken(Context context);
    void setNullRefreshNaverToken(Context context);
}
