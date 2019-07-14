package com.example.admin.last.myContentsMVP;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.last.R;
import com.example.admin.last.SharedPreferenceUtil;
import com.example.admin.last.retrofit.ApiClient;
import com.example.admin.last.retrofit.ApiInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.shaohui.bottomdialog.BaseBottomDialog;
import me.shaohui.bottomdialog.BottomDialog;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class EditTextDialogMyContents extends BaseBottomDialog {

    ImageView imgMycontents;
    TextView okContents, cancleContents;
    EditText edit_textMyContents;
    SharedPreferenceUtil sharedPreferenceUtil;
    ApiInterface apiInterface;
    private final int GET_GALLERY_IMAGE = 200;

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_edit_text_my_contents;
    }

    @Override
    public void bindView(View v) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(getActivity());
        edit_textMyContents = (EditText) v.findViewById(R.id.edit_textMyContents);
        edit_textMyContents.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm =
                        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edit_textMyContents, 0);
                String title = sharedPreferenceUtil.getSharedPreference("myContentsTitle");
                edit_textMyContents.setText(title);
            }
        });

        imgMycontents = (ImageView) v.findViewById(R.id.imgMycontents);
        imgMycontents.post(new Runnable() {
            @Override
            public void run() {
                String img = sharedPreferenceUtil.getSharedPreference("myContentsImg");
                Glide.with(getActivity()).load(img).error(R.drawable.noimage).into(imgMycontents);
                //sharedPreferenceUtil.putSharedPreference("myContentsImg",null);
                sharedPreferenceUtil.putSharedPreference("myContentsNewImg",null);
                imgMycontents.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, GET_GALLERY_IMAGE);
                    }
                });
            }
        });

        okContents = (TextView) v.findViewById(R.id.okContents);
        okContents.post(new Runnable() {
            @Override
            public void run() {
                okContents.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String key = null;
                        String id = null;
                        String title = edit_textMyContents.getText().toString();
                        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(getActivity());
                        if (sharedPreferenceUtil.getSharedPreference("kakaoUserNumber") != null) {
                            key = sharedPreferenceUtil.getSharedPreference("kakaoUserNumber");
                            id = sharedPreferenceUtil.getSharedPreference("myContentsId");
                        } else if (sharedPreferenceUtil.getSharedPreference("naverUserNumber") != null) {
                            key = sharedPreferenceUtil.getSharedPreference("naverUserNumber");
                            id = sharedPreferenceUtil.getSharedPreference("myContentsId");
                        }

                        if(edit_textMyContents.getText().toString().length() != 0
                                && sharedPreferenceUtil.getSharedPreference("myContentsNewImg") == null) {
                            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                            Call<MyContentsData> call = apiInterface.modifyMyContents(key, title,id);
                            call.enqueue(new Callback<MyContentsData>() {
                                @Override
                                public void onResponse(Call<MyContentsData> call, Response<MyContentsData> response) {
                                    getDialog().cancel();
                                    Intent intent = new Intent("notify");
                                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                                }

                                @Override
                                public void onFailure(Call<MyContentsData> call, Throwable t) {

                                }
                            });
                        }else if(edit_textMyContents.getText().toString().length() != 0
                                && sharedPreferenceUtil.getSharedPreference("myContentsNewImg") != null){

                            Map<String, RequestBody> map = new HashMap<>();
                            File file = new File(sharedPreferenceUtil.getSharedPreference("myContentsNewImg"));
                            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"),file);
                            map.put("file\"; filename=\""+key+"-"+file.getName() + "\"",requestBody);
                            Log.d("TAG", "onResponse123: "+map);
                            RequestBody eTitle = RequestBody.create(MediaType.parse("text/plain"),title);
                            RequestBody eId = RequestBody.create(MediaType.parse("text/plain"),id);
                            RequestBody eKey = RequestBody.create(MediaType.parse("text/plain"),key);
                            RequestBody eOldImg = RequestBody.create(MediaType.parse("text/plain"),sharedPreferenceUtil.getSharedPreference("myContentsImg"));

                            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                            Call<MyContentsData> call = apiInterface.modifyMyContentsAll("token",map,eId,eTitle,eKey,eOldImg);
                            call.enqueue(new Callback<MyContentsData>() {
                                @Override
                                public void onResponse(Call<MyContentsData> call, Response<MyContentsData> response) {
                                    getDialog().cancel();
                                    Intent intent = new Intent("notify");
                                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

                                    Log.d("TAG", "onResponse123: "+response.body().thumbnail+"/"+response.body().id);
                                }

                                @Override
                                public void onFailure(Call<MyContentsData> call, Throwable t) {
                                    Log.d("TAG", "onFailure: 실패"+t.getMessage());
                                }
                            });
                        }else if(edit_textMyContents.getText().toString().length() == 0){
                            Toast.makeText(getActivity(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        cancleContents = (TextView) v.findViewById(R.id.cancleContents);
        cancleContents.post(new Runnable() {
            @Override
            public void run() {
                cancleContents.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getDialog().cancel();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            sharedPreferenceUtil = SharedPreferenceUtil.getInstance(getActivity());
            Uri selectedImageUri = data.getData();
            String path = getRealPathFromURI(selectedImageUri);
            sharedPreferenceUtil.putSharedPreference("myContentsNewImg",path);
            Glide.with(getActivity()).load(selectedImageUri).into(imgMycontents);

        }
    }
    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}
