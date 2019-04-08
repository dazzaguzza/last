package com.example.admin.last.profileMvp;

import android.content.Context;
import android.view.View;

import com.example.admin.last.SharedPreferenceUtil;

public interface ProfilePresenter {
    void kakaoLogout(Context context);
    void naverLogout(Context context);
    void setUserInfo(Context context);
    void roundImg(View view);

}
