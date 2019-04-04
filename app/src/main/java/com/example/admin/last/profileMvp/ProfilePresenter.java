package com.example.admin.last.profileMvp;

import android.content.Context;

import com.example.admin.last.SharedPreferenceUtil;

public interface ProfilePresenter {
    void logout();
    void reelButton(Context context,String naver,String kakao,SharedPreferenceUtil sharedPreferenceUtil);
}
