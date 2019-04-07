package com.example.admin.last;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferenceUtil {

    private static SharedPreferenceUtil instance;
    private Context context;

    private SharedPreferenceUtil(Context context) {
        this.context = context;
    }


    public static SharedPreferenceUtil getInstance(Context context) {
        if(instance == null) {
            instance = new SharedPreferenceUtil(context);
        }

        return instance;
    }


    public void putSharedPreference(String key, String value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(key,value);
        editor.commit();
    }

    public String getSharedPreference(String key){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(key,null);
    }
}


