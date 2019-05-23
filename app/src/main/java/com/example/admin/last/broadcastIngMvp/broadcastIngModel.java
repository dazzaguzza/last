package com.example.admin.last.broadcastIngMvp;

import android.content.Context;

import java.util.ArrayList;

public interface broadcastIngModel {
    void getAllStreamingRoom(ArrayList arrayList,final AdapterIng adapterIng);
    String checkKakaoToken(Context context);
    String checkNaverToken(Context context);

    String getKakaoUserId(Context context);
    String getKakaoUserImg(Context context);
    String getKakaoUserNumber(Context context);
    void setKakaoUserNumber(Context context,String string);
    void setKakaoRenewUserId(Context context, String string);
    void setKakaoRenewUserImg(Context context,String string);


    String getNaverUserId(Context context);
    String getNaverUserImg(Context context);
    String getNaverUserNumber(Context context);
    void setNaverUserNumber(Context context,String string);
    void setNaverRenewUserId(Context context,String string);
    void setNaverRenewUserImg(Context context,String string);
}
