package com.example.admin.last.profileMvp;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.last.ActivityMain;
import com.example.admin.last.R;
import com.example.admin.last.SharedPreferenceUtil;
import com.example.admin.last.databinding.FragmentProfileBinding;
import com.example.admin.last.loginMvp.ActivityLogin;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment implements ProfileView{

    private FragmentProfileBinding binding;
    ProfilePresenter mProfilePresenter;
    public static OAuthLogin mOAuthLoginModule;
    SharedPreferenceUtil sharedPreferenceUtil;

    public FragmentProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false);;
        View view = binding.getRoot();
        mProfilePresenter = new ProfilePresenterImpl(this);
        sharedPreferenceUtil = new SharedPreferenceUtil();

        mProfilePresenter.reelButton(getActivity(),"naverRefreshToken","kakaoRefreshToken",sharedPreferenceUtil);

        binding.btnKakaoLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        sharedPreferenceUtil.putSharedPreference(getActivity(),"kakaoRefreshToken",null);
                       mProfilePresenter.logout();
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
                        sharedPreferenceUtil.putSharedPreference(getActivity(),"naverRefreshToken",null);
                        mOAuthLoginModule = OAuthLogin.getInstance();
                        mOAuthLoginModule.init(getActivity(), "AvoGTmzyF6tLpxThYQQA", "kRB8dgvq7D", "dazzaguzza");
                        mOAuthLoginModule.logoutAndDeleteToken(getActivity());
                        mProfilePresenter.logout();
                    }
                }).start();
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

}
