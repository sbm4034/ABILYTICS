package com.Wipocab.abilytics.application;

import com.Wipocab.abilytics.application.Model.NoiResponse;
import com.Wipocab.abilytics.application.Model.ServerRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gautam on 24/1/17.
 */

public interface RequestInterfaceNoi {
    @POST("Abylitics/")
    Call<NoiResponse> operation(@Body ServerRequest request);
}
