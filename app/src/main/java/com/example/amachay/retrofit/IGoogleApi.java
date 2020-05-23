package com.example.amachay.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IGoogleApi {

    //Tenmos que hacer una petición

    @GET
    Call<String> getDirections(@Url String url);
}
