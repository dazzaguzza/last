package com.example.admin.last.broadcastIngMvp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;

import com.example.admin.last.profileMvp.ProfilePresenterImpl;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.response.KakaoTalkProfile;
import com.kakao.kakaotalk.v2.KakaoTalkService;
import com.kakao.network.ErrorResult;
import com.nhn.android.naverlogin.OAuthLogin;

import org.json.JSONObject;

import java.util.ArrayList;

public class broadcastIngPresenterImpl implements broadcastIngPresenter {

    broadcastIngView mBroadcastIngView;
    broadcastIngModel mBroadcastIngModel;
    OAuthLogin mOAuthLoginModule;

    public broadcastIngPresenterImpl(broadcastIngView mBroadcastIngView) {
        this.mBroadcastIngView = mBroadcastIngView;
        mBroadcastIngModel = new broadcastIngModelImpl();
    }

    @Override
    public void setRecyclerView() {
        mBroadcastIngView.makeRecyclerView();
    }

    @Override
    public void startFloatingAction(FloatingActionButton floatingActionButton, int dx, int dy) {

        if (dy < 0 && !floatingActionButton.isShown()) {

            mBroadcastIngView.makeShowFloating();

        } else if (dy > 0 && floatingActionButton.isShown()) {

            mBroadcastIngView.makeHideFloating();

        }
    }

    @Override
    public void clickedBroadcastFloating() {
        mBroadcastIngView.clickBroadcast();
    }

    @Override
    public void setAllStreamingRoom(ArrayList arrayList, AdapterIng adapterIng) {
        mBroadcastIngModel.getAllStreamingRoom(arrayList, adapterIng);
    }

    @Override
    public void setRefreshListner() {
        mBroadcastIngView.setRefresh();
    }

    @Override
    public void getProfile(final Context context) {

        if (mBroadcastIngModel.checkNaverToken(context) != null
                && mBroadcastIngModel.checkKakaoToken(context) == null
                && mBroadcastIngModel.getNaverUserId(context) == null) {

            RequestApiTask requestApiTask = new RequestApiTask(context);
            requestApiTask.execute();

            Log.d("TAG", "getProfile: 받음");

        } else if (mBroadcastIngModel.checkKakaoToken(context) != null
                && mBroadcastIngModel.checkNaverToken(context) == null
                && mBroadcastIngModel.getKakaoUserId(context) == null) {

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
                    mBroadcastIngModel.setKakaoRenewUserId(context, result.getNickName());
                    mBroadcastIngModel.setKakaoRenewUserImg(context, result.getProfileImageUrl());
                    Log.d("TAG", "getProfile: 받음" + mBroadcastIngModel.getKakaoUserId(context));
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

                mBroadcastIngModel.setNaverRenewUserId(context, id);
                mBroadcastIngModel.setNaverRenewUserImg(context, imgUrl);


            } catch (Exception e) {
            }

        }
    }
}
