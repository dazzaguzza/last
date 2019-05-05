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

    public ProfilePresenterImpl(ProfileView mProfileView) {
        this.mProfileView = mProfileView;
        mProfileModel = new ProfileModelImpl();
    }

    @Override
    public void kakaoLogout(Context context) {
        mProfileModel.setNullRefreshKakaoToken(context);
        mProfileView.goToLogin();
        mProfileModel.setRenewUserId(context,null);
        mProfileModel.setRenewUserImg(context,null);

    }

    @Override
    public void naverLogout(Context context) {
        mProfileModel.setNullRefreshNaverToken(context);
        mProfileModel.tryNaverLogout(context);
        mProfileView.goToLogin();
        mProfileModel.setRenewUserId(context,null);
        mProfileModel.setRenewUserImg(context,null);
    }

    @Override
    public void setUserInfo(Context context) {
        if(mProfileModel.checkNaverToken(context) != null
                && mProfileModel.checkKakaoToken(context) == null){

                mProfileView.naverButtonShow();
                mProfileView.kakaoButtonHide();
                mProfileView.logingNaver();

            if (mProfileModel.getUserId(context) != null) {
                mProfileView.setId(mProfileModel.getUserId(context));
                mProfileView.setProfileImg(mProfileModel.getUserImg(context));

            }else{
                mProfileView.setId("새로고침을 눌러주세요");
                mProfileView.setProfileImg(null);
                mProfileView.hideProfileImg();
            }


        }else if(mProfileModel.checkKakaoToken(context) != null
                && mProfileModel.checkNaverToken(context) == null) {

            mProfileView.kakaoButtonShow();
            mProfileView.naverButtonHide();
            mProfileView.logingKaKao();

            if (mProfileModel.getUserId(context) != null) {
                mProfileView.setId(mProfileModel.getUserId(context));
                mProfileView.setProfileImg(mProfileModel.getUserImg(context));

            }else{
                mProfileView.setId("새로고침을 눌러주세요");
                mProfileView.setProfileImg(null);
                mProfileView.hideProfileImg();
            }
        }

    }

    @Override
    public void roundImg(View view) {
        mProfileView.makeRoundImg(view);
    }

    @Override
    public void profileRenew(final Context context) {

        if(mProfileModel.checkNaverToken(context) != null
                && mProfileModel.checkKakaoToken(context) == null){

            RequestApiTask requestApiTask = new RequestApiTask(context);
            requestApiTask.execute();


        }else if(mProfileModel.checkKakaoToken(context) != null
                && mProfileModel.checkNaverToken(context) == null){

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
                    mProfileModel.setRenewUserId(context,result.getNickName());
                    mProfileModel.setRenewUserImg(context,result.getProfileImageUrl());
                    mProfileView.setId(mProfileModel.getUserId(context));
                    mProfileView.setProfileImg(mProfileModel.getUserImg(context));
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
                Log.d("TAG", "onPostExecute: "+response);
                String id = response.getString("nickname");
                String imgUrl = response.getString("profile_image");

                mProfileModel.setRenewUserId(context,id);
                mProfileModel.setRenewUserImg(context,imgUrl);
                mProfileView.setId(mProfileModel.getUserId(context));
                mProfileView.setProfileImg(mProfileModel.getUserImg(context));
                mProfileView.showProfileImg();

            }catch (Exception e){}

        }
    }

}
