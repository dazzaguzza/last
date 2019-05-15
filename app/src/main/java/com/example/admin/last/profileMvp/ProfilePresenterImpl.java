package com.example.admin.last.profileMvp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.admin.last.R;
import com.example.admin.last.SharedPreferenceUtil;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.response.KakaoTalkProfile;
import com.kakao.kakaotalk.v2.KakaoTalkService;
import com.kakao.network.ErrorResult;
import com.nhn.android.naverlogin.OAuthLogin;

import org.json.JSONObject;

public class ProfilePresenterImpl implements ProfilePresenter {
    ProfileView mProfileView;
    ProfileModel mProfileModel;
    OAuthLogin mOAuthLoginModule;
    SharedPreferenceUtil sharedPreferenceUtil;
    Context context;
    String userId,userImg;

    public ProfilePresenterImpl(ProfileView mProfileView, Context context) {
        this.mProfileView = mProfileView;
        mProfileModel = new ProfileModelImpl();
        this.context = context;
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);

    }

    @Override
    public void kakaoLogout(Context context) {
        mProfileModel.setNullRefreshKakaoToken(context);
        mProfileView.goToLogin();
        mProfileModel.setKakaoRenewUserId(context, null);
        mProfileModel.setKakaoRenewUserImg(context, null);

    }

    @Override
    public void naverLogout(Context context) {
        mProfileModel.setNullRefreshNaverToken(context);
        mProfileModel.tryNaverLogout(context);
        mProfileView.goToLogin();
        mProfileModel.setNaverRenewUserId(context, null);
        mProfileModel.setNaverRenewUserImg(context, null);
        mProfileModel.setNullNaverUserNumber(context);
    }

    @Override
    public void setUserInfo(Context context) {
        if (mProfileModel.checkNaverToken(context) != null
                && mProfileModel.checkKakaoToken(context) == null) {

            mProfileView.naverButtonShow();
            mProfileView.kakaoButtonHide();
            mProfileView.logingNaver();

            if (mProfileModel.getNaverUserId(context) != null) {
                mProfileView.setId(mProfileModel.getNaverUserId(context));
                mProfileView.setProfileImg(mProfileModel.getNaverUserImg(context));
                Log.d("TAG", "setUserInfo: 네이버 저장 이미지");
            } else {
//                mProfileView.setId("새로고침을 눌러주세요");
//                mProfileView.setProfileImg(null);
//                mProfileView.hideProfileImg();
                RequestApiTask requestApiTask = new RequestApiTask(context);
                requestApiTask.execute();
                Log.d("TAG", "setUserInfo: 네이버 신규 이미지");
            }


        } else if (mProfileModel.checkKakaoToken(context) != null
                && mProfileModel.checkNaverToken(context) == null) {

            mProfileView.kakaoButtonShow();
            mProfileView.naverButtonHide();
            mProfileView.logingKaKao();

            if (mProfileModel.getKakaoUserId(context) != null) {
                mProfileView.setId(mProfileModel.getKakaoUserId(context));
                mProfileView.setProfileImg(mProfileModel.getKakaoUserImg(context));
                Log.d("TAG", "setUserInfo: 카카오톡 저장 이미지");
            } else {
//                mProfileView.setId("새로고침을 눌러주세요");
//                mProfileView.setProfileImg(null);
//                mProfileView.hideProfileImg();
                profileRenew(context);
                Log.d("TAG", "setUserInfo: 카카오톡 신규 이미지");
            }
        }

        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        if(sharedPreferenceUtil.getSharedPreference("kakaoUserId") != null){

            userId = sharedPreferenceUtil.getSharedPreference("kakaoUserId");
            userImg = sharedPreferenceUtil.getSharedPreference("kakaoUserImg");

        }else if(sharedPreferenceUtil.getSharedPreference("naverUserId") != null){

            userId = sharedPreferenceUtil.getSharedPreference("naverUserId");
            userImg = sharedPreferenceUtil.getSharedPreference("naverUserImg");

        }
        Log.d("TAG", "setUserInfo: user"+userId+"/"+userImg);
    }

    @Override
    public void roundImg(View view) {
        mProfileView.makeRoundImg(view);
    }

    @Override
    public void profileRenew(final Context context) {

        if (mProfileModel.checkNaverToken(context) != null
                && mProfileModel.checkKakaoToken(context) == null) {

            RequestApiTask requestApiTask = new RequestApiTask(context);
            requestApiTask.execute();


        } else if (mProfileModel.checkKakaoToken(context) != null
                && mProfileModel.checkNaverToken(context) == null) {

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
                    mProfileModel.setKakaoRenewUserId(context, result.getNickName());
                    mProfileModel.setKakaoRenewUserImg(context, result.getProfileImageUrl());
                    mProfileView.setId(mProfileModel.getKakaoUserId(context));
                    mProfileView.setProfileImg(mProfileModel.getKakaoUserImg(context));
                    mProfileView.showProfileImg();
                }
            });

        }
    }


    public class RequestApiTask extends AsyncTask<Void, Void, String> {

        Context context;

        public RequestApiTask(Context context) {
            this.context = context;
            mOAuthLoginModule = OAuthLogin.getInstance();
            mOAuthLoginModule.init(context, "AvoGTmzyF6tLpxThYQQA", "kRB8dgvq7D", "dazzaguzza");
        }

        @Override
        protected void onPreExecute() {
            mProfileView.setId((String) "");
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = "https://openapi.naver.com/v1/nid/me";
            String at = mOAuthLoginModule.getAccessToken(context);
            return mOAuthLoginModule.requestApi(context, at, url);
        }

        protected void onPostExecute(String content) {

            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject response = jsonObject.getJSONObject("response");
                Log.d("TAG", "onPostExecute: " + response);
                String id = response.getString("nickname");
                String imgUrl = response.getString("profile_image");

                mProfileModel.setNaverRenewUserId(context, id);
                mProfileModel.setNaverRenewUserImg(context, imgUrl);
                mProfileView.setId(mProfileModel.getNaverUserId(context));
                mProfileView.setProfileImg(mProfileModel.getNaverUserImg(context));
                mProfileView.showProfileImg();


            } catch (Exception e) {
            }

        }
    }

}
