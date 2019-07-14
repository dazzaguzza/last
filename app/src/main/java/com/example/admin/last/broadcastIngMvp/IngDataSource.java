package com.example.admin.last.broadcastIngMvp;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.admin.last.retrofit.ApiClient;
import com.example.admin.last.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IngDataSource extends PageKeyedDataSource<Integer, ItemIng> {

    public static ApiInterface apiInterface;
    private static final int FIRST_PAGE = 1;
    ArrayList<ItemIng> arrayList;
    private int check = 0;
    private int num = 2;
    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, ItemIng> callback) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<RoomData>> call = apiInterface.getStreamingRoom("ok");
        call.enqueue(new Callback<List<RoomData>>() {
            @Override
            public void onResponse(Call<List<RoomData>> call, Response<List<RoomData>> response) {
                if(response.body().size() != 0){
                    arrayList = new ArrayList<>();
                    try{
                        for(int i = 0; i < 5; i++){
                            arrayList.add(new ItemIng(response.body().get(i).profileImg,response.body().get(i).id,response.body().get(i).imgPath,
                                    response.body().get(i).url,response.body().get(i).key,response.body().get(i).roomName));
                            check++;
                        }

                    }catch(Exception e){}

                    callback.onResult(arrayList,null,FIRST_PAGE+1);

                }
            }


            @Override
            public void onFailure(Call<List<RoomData>> call, Throwable t) {
                Log.d("TAG", "onResponse: fail"+t);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ItemIng> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, ItemIng> callback) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<RoomData>> call = apiInterface.getStreamingRoom("ok");
        call.enqueue(new Callback<List<RoomData>>() {
            @Override
            public void onResponse(Call<List<RoomData>> call, Response<List<RoomData>> response) {

                arrayList = new ArrayList<>();

                Log.d("TAG", "size: "+response.body().size());
                    for(int i = check; i < response.body().size(); i++) {

                        arrayList.add(new ItemIng(response.body().get(i).profileImg, response.body().get(i).id, response.body().get(i).imgPath,
                                response.body().get(i).url, response.body().get(i).key, response.body().get(i).roomName));
                        check++;
                        Log.d("TAG", "size: "+i+"/"+check);
                        if(check == num*15){
                            num+=1;
                            Log.d("TAG", "size123:"+num);
                            break;
                        }
                    }
                    callback.onResult(arrayList,params.key+1);



            }

            @Override
            public void onFailure(Call<List<RoomData>> call, Throwable t) {

            }


        });

    }
}
