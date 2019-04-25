package com.example.admin.last.broadcastIngMvp;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;

import com.example.admin.last.recordMvp.RecordData;
import com.example.admin.last.retrofit.ApiClient;
import com.example.admin.last.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class broadcastIngModelImpl implements broadcastIngModel {

    public static ApiInterface apiInterface;

    @Override
    public void getAllStreamingRoom(final ArrayList arrayList, final AdapterIng adapterIng) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<RoomData>> call = apiInterface.getStreamingRoom("ok");
        call.enqueue(new Callback<List<RoomData>>() {
            @Override
            public void onResponse(Call<List<RoomData>> call, Response<List<RoomData>> response) {
                Log.d("TAG", "onResponse: okok");
                try{
                    for (int i = 0; i < response.body().size(); i++) {
                        arrayList.add(new ItemIng(response.body().get(i).getUrl(),response.body().get(i).imgPath, "0"));
                        adapterIng.notifyDataSetChanged();
                        Log.d("TAG", "onResponse: "+response.body().get(i).getUrl());

                    }
                }catch (Exception e){

                }
                }


            @Override
            public void onFailure(Call<List<RoomData>> call, Throwable t) {
                Log.d("TAG", "onResponse: fail"+t);
            }
        });
    }
}
