package com.example.admin.last.profileMvp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public interface ProfileView {
    void goToLogin();
    void naverButtonHide();
    void kakaoButtonHide();
    void kakaoButtonShow();
    void naverButtonShow();
    void makeRoundImg(View view);
    void logingNaver();
    void logingKaKao();
    TextView setId();
    ImageView setProfileImg();
}
