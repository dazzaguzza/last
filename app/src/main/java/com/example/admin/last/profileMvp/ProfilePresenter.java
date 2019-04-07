package com.example.admin.last.profileMvp;

import android.content.Context;
import android.view.View;

import com.example.admin.last.SharedPreferenceUtil;

public interface ProfilePresenter {
    void kakaoLogout(Context context,String key);
    void naverLogout(Context context,String key);
    void setUserInfo(Context context,String naver,String kakao);
    void roundImg(View view);

}
