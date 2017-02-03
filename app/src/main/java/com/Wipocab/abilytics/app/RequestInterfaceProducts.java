package com.Wipocab.abilytics.app;

import com.Wipocab.abilytics.app.Model.ProductResponse;
import com.Wipocab.abilytics.app.Model.ServerRequest;
import com.Wipocab.abilytics.app.Model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gautam on 24/1/17.
 */

public interface RequestInterfaceProducts {
    @POST("Abylitics/")
    Call<ProductResponse> operation(@Body ServerRequest request);
}
