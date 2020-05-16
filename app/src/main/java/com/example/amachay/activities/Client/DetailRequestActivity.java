package com.example.amachay.activities.Client;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amachay.R;
import com.example.amachay.includes.MyToolbar;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class DetailRequestActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    private SupportMapFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_request);

        MyToolbar.show(this,"TUS DATOS",true);

        mMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //Para establecer el punto de nuestra ubicación exacta

    }
}
