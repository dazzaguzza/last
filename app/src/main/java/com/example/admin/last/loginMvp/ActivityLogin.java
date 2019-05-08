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
import com.example.admin.last.kakaoLoginClass.SessionCallback;
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
import com.nhn.android.naverlogin.data.OAuthLoginState;


public class ActivityLogin extends AppCompatActivity implements LoginView {

    private LoginPersenter mLogin_Pregenter;
    ActivityLoginBinding binding;

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
        mLogin_Pregenter = new LoginPresenterImpl(this,ActivityLogin.this);

        kakao();
        naver();
    }


    void naver() {

        mLogin_Pregenter.checkNaverAutoLogin(ActivityLogin.this);

        binding.btnNaver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLogin_Pregenter.naverLogin(ActivityLogin.this);
            }
        });
    }


    void kakao() {

        mLogin_Pregenter.kakaoLogin(ActivityLogin.this);

        binding.btnKakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLogin_Pregenter.createKakaoSession();
            }
        });
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
       mLogin_Pregenter.removeKakaoSession();
    }

    @Override
    public void setCreateKakaoSession() {
        Session session = Session.getCurrentSession();
        session.addCallback(new SessionCallback(ActivityLogin.this, ActivityMain.class));
        session.open(AuthType.KAKAO_LOGIN_ALL, ActivityLogin.this);
    }

    @Override
    public void setRemoveKakaoSession() {
        Session.getCurrentSession().removeCallback(new SessionCallback(ActivityLogin.this, ActivityMain.class));
    }

    @Override
    public void goToMainScreen() {
        startActivity(new Intent(this, ActivityMain.class));
    }

    @Override
    public void goToLoginScreen() {
        binding.RelLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishScreen() {
        finish();
    }

    @Override
    public void flagIntent(Context context, Class<ActivityMain> activity) {
        Intent intent = new Intent(context, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }



}
