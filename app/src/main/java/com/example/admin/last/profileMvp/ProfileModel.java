package com.example.admin.last.profileMvp;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.last.SharedPreferenceUtil;

public interface ProfileModel {
    void tryNaverLogout(Context context);
    String checkKakaoToken(Context context);
    String checkNaverToken(Context context);
    void setNullRefreshKakaoToken(Context context);
    void setNullRefreshNaverToken(Context context);
    void setKakaoProfileRenew(Context context, TextView textView, ImageView imageView);
}
