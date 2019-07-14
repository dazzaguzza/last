package com.example.admin.last.kakaoLoginClass;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.admin.last.ActivityMain;
import com.example.admin.last.SharedPreferenceUtil;
import com.example.admin.last.loginMvp.ActivityLogin;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

public class SessionCallback implements ISessionCallback {

    Context context;
    Class<ActivityMain> activityMainClass;
    SharedPreferenceUtil sharedPreferenceUtil;

    public SessionCallback(Context context, Class<ActivityMain> activityMainClass) {
        this.context = context;
        this.activityMainClass = activityMainClass;
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
    }

    // 로그인에 성공한 상태
    @Override
    public void onSessionOpened() {
        requestMe();
        String kakaoAcessToken = Session.getCurrentSession().getTokenInfo().getAccessToken();
        String kakaoRefreshToken = Session.getCurrentSession().getTokenInfo().getRefreshToken();

        Log.d("token", "onSessionOpened:/Acess " + kakaoAcessToken);
        Log.d("token", "onSessionOpened:/Refresh " + kakaoRefreshToken);


        sharedPreferenceUtil.putSharedPreference("kakaoRefreshToken", kakaoRefreshToken);
    }

    // 로그인에 실패한 상태
    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
    }

    // 사용자 정보 요청
    public void requestMe() {

        // 사용자정보 요청 결과에 대한 Callback
        UserManagement.getInstance().requestMe(new MeResponseCallback() {

            // 세션 오픈 실패. 세션이 삭제된 경우,
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("SessionCallback :: ", "onSessionClosed : " + errorResult.getErrorMessage());
            }

            // 회원이 아닌 경우,
            @Override
            public void onNotSignedUp() {
                Log.e("SessionCallback :: ", "onNotSignedUp");
            }

            // 사용자정보 요청에 성공한 경우,
            @Override
            public void onSuccess(UserProfile userProfile) {
                Log.e("SessionCallback :: ", "onSuccess");

                String getUserId = userProfile.getNickname();
                String getUserImg = userProfile.getProfileImagePath();
                long kakaoUser = userProfile.getId();
                Log.d("TAG", "onSuccess: "+kakaoUser);
                sharedPreferenceUtil.putSharedPreference("kakaoUserNumber",""+kakaoUser);
                //sharedPreferenceUtil.putSharedPreference("userImg",getUserImg);

                Intent intent = new Intent(context,activityMainClass);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);

            }

            // 사용자 정보 요청 실패
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("SessionCallback :: ", "onFailure : " + errorResult.getErrorMessage());
            }
        });
    }
}

