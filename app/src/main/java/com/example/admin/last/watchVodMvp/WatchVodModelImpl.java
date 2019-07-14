package com.example.admin.last.watchVodMvp;

import android.content.Context;

import com.example.admin.last.SharedPreferenceUtil;
import com.example.admin.last.broadcastEndMvp.ItemEnd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WatchVodModelImpl implements WatchVodModel {

    SharedPreferenceUtil sharedPreferenceUtil;


    @Override
    public String load(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("save");
    }

    @Override
    public String copyLoad(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("saveCopy");
    }


}
