package com.example.admin.last.profileMvp;

import android.content.Context;

import com.example.admin.last.SharedPreferenceUtil;

public interface ProfileModel {
    String checkToken(Context context,String key,SharedPreferenceUtil sharedPreferenceUtil);
}
