package com.example.admin.last.broadcastEndMvp;

import android.content.Context;
import android.util.Log;

import com.example.admin.last.SharedPreferenceUtil;
import com.example.admin.last.broadcastIngMvp.RoomData;
import com.example.admin.last.retrofit.ApiClient;
import com.example.admin.last.retrofit.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BroadcastEndModelImpl implements BroadcastEndModel {

    public static ApiInterface apiInterface;
    SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    public void getVodList(final ArrayList arrayList, final AdapterEnd adapterEnd, final ArrayList copyArrayList, final Context context) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<VodRoomData>> call = apiInterface.getVodList("ok");
        call.enqueue(new Callback<List<VodRoomData>>() {
            @Override
            public void onResponse(Call<List<VodRoomData>> call, Response<List<VodRoomData>> response) {

                for (int i = 0; i < response.body().size(); i++) {
                    arrayList.add(new ItemEnd(response.body().get(i).userProfile, response.body().get(i).userId,
                            response.body().get(i).thumbnail, response.body().get(i).title, response.body().get(i).address));


                    copyArrayList.add(arrayList.get(i));


                    Log.d("TAG", "makeListView: " + copyArrayList);
                }
                adapterEnd.notifyDataSetChanged();
                save(context,arrayList,"save");
                save(context,arrayList,"saveCopy");
            }

            @Override
            public void onFailure(Call<List<VodRoomData>> call, Throwable t) {

            }
        });
    }

    @Override
    public void searchVOD(ArrayList<ItemEnd> originArrayList, ArrayList<ItemEnd> copyArrayList, AdapterEnd adapterEnd, String string) {

        originArrayList.clear();

        if (string.length() == 0) {

            originArrayList.addAll(copyArrayList);
            Log.d("TAG", "makeListView: " + originArrayList.size());
        } else {
            for (int i = 0; i < copyArrayList.size(); i++) {
                if (copyArrayList.get(i).getTitle().toLowerCase().contains(string)) {
                    originArrayList.add(copyArrayList.get(i));
                    Log.d("TAG", "makeListView: " + originArrayList.size());
                }
            }
        }
        adapterEnd.notifyDataSetChanged();
    }


    public void save(Context context, ArrayList<ItemEnd> arrayList,String string) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);

        sharedPreferenceUtil.putSharedPreference(string,json);
        Log.d("TAG", "save: "+arrayList.get(0).getTitle());
    }
}
