package com.example.admin.last.profileMvp;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.last.SharedPreferenceUtil;

public interface ProfileModel {
    void tryNaverLogout(Context context);
    String checkToken(Context context,String key);
    void setNullRefreshToken(Context context,String key);
    void setKakaoProfileRenew(Context context, TextView textView, ImageView imageView);
   // void setNaverProfileRenew(Context context, TextView textView, ImageView imageView);
}
