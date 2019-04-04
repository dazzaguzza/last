package com.example.admin.last.profileMvp;

import android.content.Context;

import com.example.admin.last.SharedPreferenceUtil;

public class ProfileModelImpl implements ProfileModel{

    @Override
    public String checkToken(Context context, String key, SharedPreferenceUtil sharedPreferenceUtil) {
        return sharedPreferenceUtil.getSharedPreference(context,key);
    }
}
