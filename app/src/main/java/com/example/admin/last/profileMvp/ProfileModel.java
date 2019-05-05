package com.example.admin.last.profileMvp;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.last.SharedPreferenceUtil;

public interface ProfileModel {
    void tryNaverLogout(Context context);
    String checkKakaoToken(Context context);
    String checkNaverToken(Context context);
    String getUserId(Context context);
    String getUserImg(Context context);
    void setRenewUserId(Context context,String string);
    void setRenewUserImg(Context context,String string);
    void setNullRefreshKakaoToken(Context context);
    void setNullRefreshNaverToken(Context context);
}
