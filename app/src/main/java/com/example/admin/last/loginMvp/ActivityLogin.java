package com.example.admin.last.loginMvp;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.admin.last.ActivityMain;
import com.example.admin.last.R;
import com.example.admin.last.SharedPreferenceUtil;
import com.example.admin.last.databinding.ActivityLoginBinding;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;


public class ActivityLogin extends AppCompatActivity implements LoginView {

    private LoginPersenter mLogin_Pregenter;
    public static OAuthLogin mOAuthLoginModule;
    ActivityLoginBinding binding;
    String kakaoAcessToken,kakaoRefreshToken;
    SharedPreferenceUtil sharedPreferenceUtil;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mLogin_Pregenter.goToLs();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((ViewGroup) findViewById(R.id.layout)).getLayoutTransition()
                    .enableTransitionType(LayoutTransition.CHANGING);
        }

        handler.postDelayed(runnable, 2000);
        mLogin_Pregenter = new LoginPresenterImpl(this);
        sharedPreferenceUtil = new SharedPreferenceUtil();

        kakao();
        naver();
    }

    //네이버
    void naver() {

        try{
            mOAuthLoginModule = OAuthLogin.getInstance();
            mOAuthLoginModule.init(this, "AvoGTmzyF6tLpxThYQQA", "kRB8dgvq7D", "dazzaguzza");
            String refreshToken = mOAuthLoginModule.getRefreshToken(ActivityLogin.this);
            String checkToken = sharedPreferenceUtil.getSharedPreference(ActivityLogin.this,"naverRefreshToken");
            Log.d("refreshTokenNaver", "naver: "+refreshToken);

            if(checkToken.equals(refreshToken)){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        flagIntent(ActivityLogin.this, ActivityMain.class);
                    }
                }, 1500);
            }



        }catch (Exception e){}



        binding.btnNaver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                naverLogin();
            }
        });
    }


    private void naverLogin() {
        mOAuthLoginModule.startOauthLoginActivity(this, mOAuthLoginHandler);
    }

    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginModule.getAccessToken(ActivityLogin.this);
                String refreshToken = mOAuthLoginModule.getRefreshToken(ActivityLogin.this);
                long expiresAt = mOAuthLoginModule.getExpiresAt(ActivityLogin.this);
                String tokenType = mOAuthLoginModule.getTokenType(ActivityLogin.this);
                String token = mOAuthLoginModule.getTokenType(ActivityLogin.this);

                sharedPreferenceUtil.putSharedPreference(ActivityLogin.this,"naverRefreshToken",refreshToken);

                //여기
                mLogin_Pregenter.goToMs();



            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(ActivityLogin.this).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(ActivityLogin.this);
            }
        }

        ;
    };


    // 여기서부터 kakao
    void kakao(){
        //카카오 세션있을시 자동로그인
        Session.getCurrentSession().checkAndImplicitOpen();
        //카카오 앱 종료후 다시 켰을때 피드(?)바뀐 후 세션 여부 묻기
        if (!Session.getCurrentSession().isOpenable()) {
            String checkToken = sharedPreferenceUtil.getSharedPreference(ActivityLogin.this,"kakaoRefreshToken");
            String getKakaoToken = Session.getCurrentSession().getTokenInfo().getRefreshToken();
            Log.d("logout", "kakao: "+checkToken);
            try {

                if (checkToken.equals(getKakaoToken)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            flagIntent(ActivityLogin.this, ActivityMain.class);
                        }
                    }, 1500);
                }

            } catch (Exception e) {
            }

        }

        //카카오 버튼클릭시
        binding.btnKakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createKakaoSession();
            }
        });
    }

    public class SessionCallback implements ISessionCallback {

        // 로그인에 성공한 상태
        @Override
        public void onSessionOpened() {
            requestMe();
            kakaoAcessToken = Session.getCurrentSession().getTokenInfo().getAccessToken();
            kakaoRefreshToken = Session.getCurrentSession().getTokenInfo().getRefreshToken();
            Log.d("token", "onSessionOpened:/Acess "+kakaoAcessToken);
            Log.d("token", "onSessionOpened:/Refresh "+kakaoRefreshToken);

            sharedPreferenceUtil.putSharedPreference(ActivityLogin.this,"kakaoRefreshToken",kakaoRefreshToken);
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
                    String nickname = userProfile.getNickname();
                    String email = userProfile.getEmail();
                    String profileImagePath = userProfile.getProfileImagePath();
                    String thumnailPath = userProfile.getThumbnailImagePath();
                    String UUID = userProfile.getUUID();
                    long id = userProfile.getId();
                    Log.e("Profile : ", nickname + "");
                    Log.e("Profile : ", email + "");
                    Log.e("Profile : ", profileImagePath + "");
                    Log.e("Profile : ", thumnailPath + "");
                    Log.e("Profile : ", UUID + "");
                    Log.e("Profile : ", id + "");

                flagIntent(ActivityLogin.this,ActivityMain.class);
                }

                // 사용자 정보 요청 실패
                @Override
                public void onFailure(ErrorResult errorResult) {
                    Log.e("SessionCallback :: ", "onFailure : " + errorResult.getErrorMessage());
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(new SessionCallback());
    }

    void createKakaoSession() {
        Session session = Session.getCurrentSession();
        session.addCallback(new SessionCallback());
        session.open(AuthType.KAKAO_LOGIN_ALL, ActivityLogin.this);
    }
    //여기까지 kakao

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLogin_Pregenter.getRequest_result(requestCode, permissions, grantResults);
    }

    //MVP view override

    @Override
    public void goToMainScreen() {
        startActivity(new Intent(this, ActivityMain.class));
        finish();
    }

    @Override
    public void goToLoginScreen() {
        binding.RelLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishScreen() {
        finish();
    }

    void flagIntent(Context context, Class<ActivityMain> activity){
        Intent intent = new Intent(context,activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
