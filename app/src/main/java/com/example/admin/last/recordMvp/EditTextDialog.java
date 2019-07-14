package com.example.admin.last.recordMvp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.last.R;
import com.example.admin.last.SharedPreferenceUtil;
import com.example.admin.last.retrofit.ApiClient;
import com.example.admin.last.retrofit.ApiInterface;

import me.shaohui.bottomdialog.BaseBottomDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTextDialog extends BaseBottomDialog {

    private EditText mEditText;
    TextView ok, cancle;
    public static ApiInterface apiInterface;
    SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_edit_text;
    }

    @Override
    public void bindView(View v) {
        mEditText = (EditText) v.findViewById(R.id.edit_text);
        mEditText.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm =
                        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEditText, 0);
            }
        });

        ok = (TextView) v.findViewById(R.id.ok);
        ok.post(new Runnable() {
            @Override
            public void run() {
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(getActivity());
                        String key = null;
                        String userId = null;
                        String userProfile = null;
                        String title = mEditText.getText().toString();
                        if (title.length() != 0) {
                            if (sharedPreferenceUtil.getSharedPreference("naverUserNumber") != null) {
                                key = sharedPreferenceUtil.getSharedPreference("naverUserNumber");
                                userId = sharedPreferenceUtil.getSharedPreference("naverUserId");
                                userProfile = sharedPreferenceUtil.getSharedPreference("naverUserImg");
                            }else if(sharedPreferenceUtil.getSharedPreference("kakaoUserNumber") != null){
                                key = sharedPreferenceUtil.getSharedPreference("kakaoUserNumber");
                                userId = sharedPreferenceUtil.getSharedPreference("kakaoUserId");
                                userProfile = sharedPreferenceUtil.getSharedPreference("kakaoUserImg");
                            }
                            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                            Call<RecordData> call = apiInterface.okRecord(userId,userProfile,title, key);
                            Log.d("TAG", "onResponse123: " + key+userId+userProfile);
                            call.enqueue(new Callback<RecordData>() {
                                @Override
                                public void onResponse(Call<RecordData> call, Response<RecordData> response) {
                                    Log.d("TAG", "onResponse: " + response.body().response);
                                    mEditText.setText("");
                                    //dialog();
                                    Toast.makeText(getContext(), "녹화된 방송을 게시하였습니다!", Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                }

                                @Override
                                public void onFailure(Call<RecordData> call, Throwable t) {

                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        cancle = (TextView) v.findViewById(R.id.cancle);
        cancle.post(new Runnable() {
            @Override
            public void run() {
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(getActivity());
                        String key = null;
                        if (sharedPreferenceUtil.getSharedPreference("naverUserNumber") != null) {
                            key = sharedPreferenceUtil.getSharedPreference("naverUserNumber");
                        }else if(sharedPreferenceUtil.getSharedPreference("kakaoUserNumber") != null){
                            key = sharedPreferenceUtil.getSharedPreference("kakaoUserNumber");
                        }
                        Log.d("TAG", "onResponse: " + key);
                        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                        Call<RecordData> call = apiInterface.cancleRecord(key);
                        call.enqueue(new Callback<RecordData>() {
                            @Override
                            public void onResponse(Call<RecordData> call, Response<RecordData> response) {
                                Log.d("TAG", "onResponse: " + response.body().response);
                                getActivity().finish();
                            }

                            @Override
                            public void onFailure(Call<RecordData> call, Throwable t) {

                            }
                        });
                    }
                });
            }
        });

    }

    @Override
    public float getDimAmount() {
        return 0.9f;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("알림");
        builder.setMessage("녹화된 방송을 게시하였습니다!");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }
}
