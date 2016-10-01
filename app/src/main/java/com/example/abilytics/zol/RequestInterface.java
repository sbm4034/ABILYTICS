package com.example.abilytics.zol;

import com.example.abilytics.zol.Model.ServerRequest;
import com.example.abilytics.zol.Model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("K/")
    Call<ServerResponse> operation(@Body ServerRequest request);

}