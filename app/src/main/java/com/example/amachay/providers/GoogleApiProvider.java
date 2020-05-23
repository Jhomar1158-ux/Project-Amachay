package com.example.amachay.providers;

import android.content.Context;

import com.example.amachay.R;
import com.example.amachay.retrofit.IGoogleApi;
import com.example.amachay.retrofit.RetrofitClient;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

import retrofit2.Call;

public class GoogleApiProvider {
    private Context context;

    public GoogleApiProvider(Context context)

    {
        this.context = context;

    }
    public Call<String> getDirections(LatLng originLatLng,LatLng destinationLatLng)
    {

        String baseurl = "https://maps.googleapis.com";
        //Concatemos parámetros más generalizados
        String query = "/maps/api/directions/json?mode=driving&transit_routing_preferences=less_driving&"
                + "origin=" + originLatLng.latitude + "," + originLatLng.longitude + "&"
                + "destination=" + destinationLatLng.latitude + "," + destinationLatLng.longitude + "&"
                + "departure_time=" + (new Date().getTime() + (60*60*1000)) + "&"
                + "traffic_model=best_guess&"
                + "key=" + context.getResources().getString(R.string.google_maps_key);
        return RetrofitClient.getClient(baseurl).create(IGoogleApi.class).getDirections(baseurl+query);
    }
}
