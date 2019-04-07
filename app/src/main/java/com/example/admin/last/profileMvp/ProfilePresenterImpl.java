package com.example.admin.last.profileMvp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.admin.last.SharedPreferenceUtil;
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
    public void kakaoLogout(Context context,String key) {
        mProfileModel.setNullRefreshToken(context,key);
        mProfileView.goToLogin();
    }

    @Override
    public void naverLogout(Context context,String key) {
        mProfileModel.setNullRefreshToken(context,key);
        mProfileModel.tryNaverLogout(context);
        mProfileView.goToLogin();
    }

    @Override
    public void setUserInfo(Context context,String naver,String kakao) {
        if(mProfileModel.checkToken(context,naver) != null
                && mProfileModel.checkToken(context,kakao) == null){
            mProfileView.naverButtonShow();
            mProfileView.kakaoButtonHide();
            mProfileView.logingNaver();
            RequestApiTask requestApiTask = new RequestApiTask(context);
            requestApiTask.execute();
        }else if(mProfileModel.checkToken(context,kakao) != null
                && mProfileModel.checkToken(context,naver) == null){
            mProfileView.kakaoButtonShow();
            mProfileView.naverButtonHide();
            mProfileView.logingKaKao();
            mProfileModel.setKakaoProfileRenew(context,mProfileView.setId(),mProfileView.setProfileImg());
        }
    }

    @Override
    public void roundImg(View view) {
        mProfileView.makeRoundImg(view);
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
            mProfileView.setId().setText((String) "");
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
                mProfileView.setId().setText(id);
                Glide.with(context).load(imgUrl).into(mProfileView.setProfileImg());
            }catch (Exception e){}

        }
    }

}
