package com.example.admin.last.profileMvp;

import android.content.Context;

import com.example.admin.last.SharedPreferenceUtil;

public class ProfilePresenterImpl implements ProfilePresenter {
    ProfileView mProfileView;
    ProfileModel mProfileModel;

    public ProfilePresenterImpl(ProfileView mProfileView) {
        this.mProfileView = mProfileView;
        mProfileModel = new ProfileModelImpl();
    }

    @Override
    public void logout() {
        mProfileView.goToLogin();
    }

    @Override
    public void reelButton(Context context,String naver,String kakao,SharedPreferenceUtil sharedPreferenceUtil) {
        if(mProfileModel.checkToken(context,naver,sharedPreferenceUtil) != null
                && mProfileModel.checkToken(context,kakao,sharedPreferenceUtil) == null){
            mProfileView.naverButtonShow();
            mProfileView.kakaoButtonHide();
        }else if(mProfileModel.checkToken(context,kakao,sharedPreferenceUtil) != null
                && mProfileModel.checkToken(context,naver,sharedPreferenceUtil) == null){
            mProfileView.kakaoButtonShow();
            mProfileView.naverButtonHide();
        }
    }
}
