package com.example.admin.last.loginMvp;

import android.content.Context;

public interface LoginModel {

    void setNaverToken(Context context, String string);
    void setRenewUserId(Context context,String string);
    void setRenewUserImg(Context context,String string);
    void setNullRefreshKakaoToken(Context context);
    void setNullRefreshNaverToken(Context context);

}
