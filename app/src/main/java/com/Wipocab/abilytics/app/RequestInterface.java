package com.Wipocab.abilytics.app;

import com.Wipocab.abilytics.app.Model.ServerRequest;
import com.Wipocab.abilytics.app.Model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("K/")
    Call<ServerResponse> operation(@Body ServerRequest request);

}