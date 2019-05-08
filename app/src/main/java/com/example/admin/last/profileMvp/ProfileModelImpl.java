package com.example.admin.last.profileMvp;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.last.R;
import com.example.admin.last.SharedPreferenceUtil;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.response.KakaoTalkProfile;
import com.kakao.kakaotalk.v2.KakaoTalkService;
import com.kakao.network.ErrorResult;
import com.nhn.android.naverlogin.OAuthLogin;

public class ProfileModelImpl implements ProfileModel{

    public static OAuthLogin mOAuthLoginModule;
    SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    public void tryNaverLogout(Context context) {
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(context, "AvoGTmzyF6tLpxThYQQA", "kRB8dgvq7D", "dazzaguzza");
        mOAuthLoginModule.logoutAndDeleteToken(context);
    }

    @Override
    public String checkKakaoToken(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("kakaoRefreshToken");
    }

    @Override
    public String checkNaverToken(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("naverRefreshToken");
    }

//    @Override
//    public String checkLoginNaver(Context context) {
//        mOAuthLoginModule = OAuthLogin.getInstance();
//        String checkLoginNaver= mOAuthLoginModule.getState(context).toString();
//        Log.d("TAG", "checkLoginNaver: "+checkLoginNaver);
//        return checkLoginNaver;
//    }

    @Override
    public String getKakaoUserId(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("kakaoUserId");
    }

    @Override
    public String getKakaoUserImg(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("kakaoUserImg");
    }

    @Override
    public void setKakaoRenewUserId(Context context,String string) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("kakaoUserId",string);
    }

    @Override
    public void setKakaoRenewUserImg(Context context,String string) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("kakaoUserImg",string);
    }

    @Override
    public String getNaverUserId(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("naverUserId");
    }

    @Override
    public String getNaverUserImg(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference("naverUserImg");
    }

    @Override
    public void setNaverRenewUserId(Context context, String string) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("naverUserId",string);
    }

    @Override
    public void setNaverRenewUserImg(Context context, String string) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("naverUserImg",string);
    }

    @Override
    public void setNullRefreshKakaoToken(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("kakaoRefreshToken",null);
    }

    @Override
    public void setNullRefreshNaverToken(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("naverRefreshToken",null);
    }

}
