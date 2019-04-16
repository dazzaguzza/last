package com.example.admin.last.retrofit;

import com.example.admin.last.recordMvp.RecordData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("streaming.php")
    Call<RecordData> setStream(@Field("url") String url, @Field("id") String id);
}
