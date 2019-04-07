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

    public ProfileModelImpl() {
    }

    @Override
    public void tryNaverLogout(Context context) {
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(context, "AvoGTmzyF6tLpxThYQQA", "kRB8dgvq7D", "dazzaguzza");
        mOAuthLoginModule.logoutAndDeleteToken(context);
    }

    @Override
    public String checkToken(Context context, String key) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        return sharedPreferenceUtil.getSharedPreference(key);
    }

    @Override
    public void setNullRefreshToken(Context context, String key) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference(key,null);
    }

    @Override
    public void setKakaoProfileRenew(final Context context,final TextView textView, final ImageView imageView) {
        KakaoTalkService.getInstance().requestProfile(new TalkResponseCallback<KakaoTalkProfile>() {
            @Override
            public void onNotKakaoTalkUser() {

            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onNotSignedUp() {

            }

            @Override
            public void onSuccess(KakaoTalkProfile result) {
                textView.setText(result.getNickName());
                Glide.with(context).load(result.getProfileImageUrl()).error(R.drawable.sa).into(imageView);
            }
        });
    }

}
