package com.example.amachay.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String  url)
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    //Recibe la URL que tenemos por parámetro
                    .baseUrl(url)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

        }
        return retrofit;

    }
}
