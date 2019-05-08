package com.example.admin.last.profileMvp;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.last.R;
import com.example.admin.last.databinding.FragmentProfileBinding;
import com.example.admin.last.loginMvp.ActivityLogin;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment implements ProfileView {

    private FragmentProfileBinding binding;
    ProfilePresenter mProfilePresenter;


    public FragmentProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        View view = binding.getRoot();
        mProfilePresenter = new ProfilePresenterImpl(this,getActivity());

        mProfilePresenter.roundImg(binding.imgProfile);
        mProfilePresenter.setUserInfo(getActivity());

        binding.btnKakaoLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        mProfilePresenter.kakaoLogout(getActivity());
                        Session.getCurrentSession().clearCallbacks();
                    }
                });
            }
        });

        binding.btnNaverLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mProfilePresenter.naverLogout(getActivity());
                    }
                }).start();
            }
        });

        binding.renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProfilePresenter.profileRenew(getActivity());
            }
        });

        return view;
    }

    @Override
    public void goToLogin() {
        startActivity(new Intent(getActivity(), ActivityLogin.class));
        getActivity().finish();
    }

    @Override
    public void naverButtonHide() {
        binding.btnNaverLogout.setVisibility(View.GONE);
        binding.btnNaverLogout.setClickable(false);
    }

    @Override
    public void kakaoButtonHide() {
        binding.btnKakaoLogout.setVisibility(View.GONE);
        binding.btnKakaoLogout.setClickable(false);
    }

    @Override
    public void kakaoButtonShow() {
        binding.btnKakaoLogout.setVisibility(View.VISIBLE);
        binding.btnKakaoLogout.setClickable(true);
    }

    @Override
    public void naverButtonShow() {
        binding.btnNaverLogout.setVisibility(View.VISIBLE);
        binding.btnNaverLogout.setClickable(true);
    }

    @Override
    public void makeRoundImg(View view) {
        view.setBackground(new ShapeDrawable(new OvalShape()));
        view.setClipToOutline(true);
    }

    @Override
    public void logingNaver() {
        binding.txtLoginWhat.setText("네이버");
    }

    @Override
    public void logingKaKao() {
        binding.txtLoginWhat.setText("카카오");
    }

    @Override
    public void setId(String string) {
        binding.txtId.setText(string);
    }

    @Override
    public void setProfileImg(String string) {
        Glide.with(getActivity()).load(string).into(binding.imgProfile);
    }

    @Override
    public void hideProfileImg() {
        binding.imgProfile.setVisibility(View.GONE);
    }

    @Override
    public void showProfileImg() {
        binding.imgProfile.setVisibility(View.VISIBLE);
    }


}
