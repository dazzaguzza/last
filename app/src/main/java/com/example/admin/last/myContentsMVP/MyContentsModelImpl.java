package com.example.admin.last.myContentsMVP;

import android.content.Context;
import android.util.Log;

import com.example.admin.last.SharedPreferenceUtil;
import com.example.admin.last.retrofit.ApiClient;
import com.example.admin.last.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyContentsModelImpl implements MyContentsModel {

    ApiInterface apiInterface;
    SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    public void getMyContentsList(final ArrayList arrayList, final MyContentsAdapter myContentsAdapter, Context context) {

        if (arrayList != null) {
            arrayList.clear();
        }
        String key = null;
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        if (sharedPreferenceUtil.getSharedPreference("kakaoUserNumber") != null) {
            key = sharedPreferenceUtil.getSharedPreference("kakaoUserNumber");
        } else if (sharedPreferenceUtil.getSharedPreference("naverUserNumber") != null) {
            key = sharedPreferenceUtil.getSharedPreference("naverUserNumber");
        }


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<MyContentsData>> call = apiInterface.getMyContentsList(key);
        call.enqueue(new Callback<List<MyContentsData>>() {
            @Override
            public void onResponse(Call<List<MyContentsData>> call, Response<List<MyContentsData>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    arrayList.add(new MyContentsItem(response.body().get(i).userProfile, response.body().get(i).userId,
                            response.body().get(i).thumbnail, response.body().get(i).title, response.body().get(i).address, response.body().get(i).id));
                    Log.d("TAG", "onResponseId: " + response.body().get(i).id);
                }
                myContentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MyContentsData>> call, Throwable t) {

            }
        });

    }

    @Override
    public void setMyContentsImg(Context context, String string) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("myContentsImg", string);
    }

    @Override
    public void setMyContentsTitle(Context context, String string) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("myContentsTitle", string);
    }

    @Override
    public void setMyContentsId(Context context, String string) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        sharedPreferenceUtil.putSharedPreference("myContentsId", string);
    }

    @Override
    public void deleteMyContents(String id) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<MyContentsData> call = apiInterface.deleteMyContents(id);
        call.enqueue(new Callback<MyContentsData>() {
            @Override
            public void onResponse(Call<MyContentsData> call, Response<MyContentsData> response) {
              //  Log.d("TAG", "onResponse: "+response.body().id);
            }

            @Override
            public void onFailure(Call<MyContentsData> call, Throwable t) {

            }
        });
    }
}
