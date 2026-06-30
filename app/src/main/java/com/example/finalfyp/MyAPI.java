package com.example.finalfyp;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MyAPI {
    @Multipart
    @POST("/ac") // Endpoint on your Flask server
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part image);

    @Multipart
    @POST("/bpd") // Endpoint on your Flask server for another operation
    Call<ResponseBody> uploadImage2(@Part MultipartBody.Part image);

    @Multipart
    @POST("/aloka") // Endpoint on your Flask server for another operation
    Call<ResponseBody> uploadImage3(@Part MultipartBody.Part image);

    @Multipart
    @POST("/volusonE6") // Endpoint on your Flask server for another operation
    Call<ResponseBody> uploadImage4(@Part MultipartBody.Part image);

    @Multipart
    @POST("/volusonS8") // Endpoint on your Flask server for another operation
    Call<ResponseBody> uploadImage5(@Part MultipartBody.Part image);


    @Multipart
    @POST("/volusonS10") // Endpoint on your Flask server for another operation
    Call<ResponseBody> uploadImage6(@Part MultipartBody.Part image);

}