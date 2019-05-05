package com.example.admin.last.loginMvp;

import android.content.Context;

import com.example.admin.last.ActivityMain;

public interface LoginView {

    void goToMainScreen();
    void goToLoginScreen();
    void finishScreen();
    void setCreateKakaoSession();
    void setRemoveKakaoSession();
    void flagIntent(Context context, Class<ActivityMain> activity);

}
