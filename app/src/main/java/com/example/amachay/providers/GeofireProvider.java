package com.example.amachay.providers;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GeofireProvider {


    private DatabaseReference mDatabase;
    private GeoFire mGeoFire;

    public GeofireProvider()
    {
        //Nos creamoa la data real
        mDatabase = FirebaseDatabase.getInstance().getReference().child("active_doctors");
        mGeoFire = new GeoFire(mDatabase);
    }
    public void saveLocation(String idDoctor, LatLng latLng)
    {
        mGeoFire.setLocation(idDoctor,new GeoLocation(latLng.latitude,latLng.longitude));
    }

    public void removeLocation(String idDoctor)
    {
        mGeoFire.removeLocation(idDoctor);

    }
    public GeoQuery getActiveDoctor(LatLng latLng)
    {
        //AQUÍ LE ESPECIFICO QUE TIENE EL USUARIO, 5km
        GeoQuery geoQuery = mGeoFire.queryAtLocation(new GeoLocation(latLng.latitude,latLng.longitude),  5);
        geoQuery.removeAllListeners();
        return  geoQuery;

    }
}
