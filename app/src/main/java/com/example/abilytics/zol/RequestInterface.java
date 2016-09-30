package com.example.abilytics.zol;

import com.example.abilytics.zol.Model.ServerRequest;
import com.example.abilytics.zol.Model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gautam on 9/6/16.
 */
public interface RequestInterface {

    @POST("K/")
    Call<ServerResponse> operation(@Body ServerRequest request);

}