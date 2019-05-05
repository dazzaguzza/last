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

                arrayList.clear();

                try{
                    for (int i = 0; i < response.body().size(); i++) {

                        arrayList.add(new ItemIng(response.body().get(i).profileImg,response.body().get(i).id,response.body().get(i).imgPath, response.body().get(i).url,response.body().get(i).key));

                        Log.d("TAG", "onResponse: "+response.body().get(i).getUrl());
                        Log.d("TAG", "onResponse: "+response.body().get(i).imgPath);

                    }
                  adapterIng.notifyDataSetChanged();
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
