package com.example.admin.last.retrofit;

import com.example.admin.last.broadcastEndMvp.VodRoomData;
import com.example.admin.last.broadcastIngMvp.RoomData;
import com.example.admin.last.myContentsMVP.MyContentsData;
import com.example.admin.last.recordMvp.RecordData;
import com.example.admin.last.watchMvp.ResponseCheck;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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
    Call<RecordData> okRecord(@Field("userId") String userId,@Field("userProfile") String userProfile,@Field("title") String title,@Field("key") String key);

    @FormUrlEncoded
    @POST("cancleRecord.php")
    Call<RecordData> cancleRecord(@Field("key") String key);

    @FormUrlEncoded
    @POST("vod.php")
    Call<List<VodRoomData>> getVodList(@Field("key") String key);

    @FormUrlEncoded
    @POST("myContents.php")
    Call<List<MyContentsData>> getMyContentsList(@Field("key") String key);

    @FormUrlEncoded
    @POST("modifyMyContents.php")
    Call<MyContentsData> modifyMyContents(@Field("key") String key,@Field("title") String title,@Field("id") String id);

    @Multipart
    @POST("modifyMyContents.php")
    Call<MyContentsData> modifyMyContentsAll(@Header("Authorization") String authorization,
                                             @PartMap Map<String, RequestBody> map,
                                             @Part("id") RequestBody id, @Part("title") RequestBody myTitle,@Part("key") RequestBody key,
                                             @Part("oldImg") RequestBody oldImg);

    @FormUrlEncoded
    @POST("end.php")
    Call<RecordData> end(@Field("key") String key);

    @FormUrlEncoded
    @POST("deleteMyContents.php")
    Call<MyContentsData> deleteMyContents(@Field("id") String id);

    @FormUrlEncoded
    @POST("roomName.php")
    Call<RecordData> roomName(@Field("img_profile") String img_profile,
                              @Field("txt_id") String txt_id,
                              @Field("key") String key,
                              @Field("url") String url,
                              @Field("path") String path,
                              @Field("room_name") String room_name);

    @FormUrlEncoded
    @POST("watch.php")
    Call<ResponseCheck> watch(@Field("key") String key);
}
