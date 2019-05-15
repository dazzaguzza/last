package com.example.admin.last.retrofit;

import com.example.admin.last.broadcastIngMvp.RoomData;
import com.example.admin.last.recordMvp.RecordData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("streaming.php")
    Call<RecordData> setStream(@Field("img_profile") String img_profile,@Field("txt_id") String txt_id,@Field("url") String url,@Field("key") String key);

    @FormUrlEncoded
    @POST("getStreamingRoom.php")
    Call<List<RoomData>> getStreamingRoom(@Field("url") String url);

    @FormUrlEncoded
    @POST("doneStreaming.php")
    Call<RecordData> stopStream(@Field("url") String url,@Field("key") String key);

    @FormUrlEncoded
    @POST("okRecord.php")
    Call<RecordData> okRecord(@Field("title") String title,@Field("key") String key);

    @FormUrlEncoded
    @POST("cancleRecord.php")
    Call<RecordData> cancleRecord(@Field("key") String key);

}
