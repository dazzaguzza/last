package com.example.admin.last.loginMvp;

import android.content.Context;

public interface LoginModel {

    void setNaverToken(Context context, String string);
    void setKakaoRenewUserId(Context context,String string);
    void setKakaoRenewUserImg(Context context,String string);
    void setNaverRenewUserId(Context context,String string);
    void setNaverRenewUserImg(Context context,String string);
    void setNullRefreshKakaoToken(Context context);
    void setNullRefreshNaverToken(Context context);

}
