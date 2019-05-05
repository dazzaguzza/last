package com.example.admin.last.loginMvp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

import com.example.admin.last.ActivityMain;
import com.example.admin.last.SharedPreferenceUtil;
import com.kakao.auth.Session;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.data.OAuthLoginState;

public class LoginPresenterImpl implements LoginPersenter {

    LoginView mLogin_View;
    LoginModel mLogin_model;
    Context context;
    public static OAuthLogin mOAuthLoginModule;
    SharedPreferenceUtil sharedPreferenceUtil;

    public LoginPresenterImpl(LoginView mLogin_View,Context context) {
        this.mLogin_View = mLogin_View;
        mLogin_model = new LoginModelImpl();
        this.context = context;
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
    }

    @Override
    public void getRequest_permission(Activity activity) {

        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA
                ,Manifest.permission.RECORD_AUDIO}, 100);
    }

    @Override
    public void getRequest_result(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 100
                && permissions.length == 2
                && permissions[0].equals(Manifest.permission.CAMERA)
                && permissions[1].equals(Manifest.permission.RECORD_AUDIO)
                && grantResults[0] == PermissionChecker.PERMISSION_GRANTED
                && grantResults[1] ==PermissionChecker.PERMISSION_GRANTED) {

            mLogin_View.goToLoginScreen();

        }else{
            mLogin_View.finishScreen();
        }
    }

    @Override
    public void kakaoLogin(final Context context) {

        if (Session.getCurrentSession().checkAndImplicitOpen()) {
            try {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLogin_View.flagIntent(context, ActivityMain.class);
                    }
                }, 1500);

                Log.d("TAG", "kakaoLogin: 세션열림");
            } catch (Exception e) {
            }
        }else{
            mLogin_model.setNullRefreshKakaoToken(context);
            mLogin_model.setRenewUserId(context,null);
            mLogin_model.setRenewUserImg(context,null);
            Log.d("TAG", "kakaoLogin: 세션닫힘");
        }
    }

    @Override
    public void createKakaoSession() {
        mLogin_View.setCreateKakaoSession();
    }

    @Override
    public void removeKakaoSession() {
        mLogin_View.setRemoveKakaoSession();
    }

    @Override
    public void goToLs() {
        mLogin_View.goToLoginScreen();
    }

    @Override
    public void naverLogin(ActivityLogin activityLogin) {
        mOAuthLoginModule.startOauthLoginActivity(activityLogin, mOAuthLoginHandler);
    }

    @Override
    public void checkNaverAutoLogin(final Context context) {
        try {
            mOAuthLoginModule = OAuthLogin.getInstance();
            mOAuthLoginModule.init(context, "AvoGTmzyF6tLpxThYQQA", "kRB8dgvq7D", "dazzaguzza");

            OAuthLoginState status = mOAuthLoginModule.getState(context);
            Log.d("refreshTokenNaver", "naver: " + status);


            if (status.toString().equals("OK")) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLogin_View.flagIntent(context, ActivityMain.class);
                    }
                }, 1500);
            }else{
                mLogin_model.setNullRefreshNaverToken(context);
                mLogin_model.setRenewUserId(context,null);
                mLogin_model.setRenewUserImg(context,null);
            }


        } catch (Exception e) {
        }
    }

    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String refreshToken = mOAuthLoginModule.getRefreshToken(context);

                mLogin_model.setNaverToken(context,refreshToken);

                //여기
                mLogin_View.goToMainScreen();
                mLogin_View.finishScreen();

            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(context).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(context);
            }
        }

        ;
    };
}
