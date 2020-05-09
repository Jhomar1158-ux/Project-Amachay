package com.example.amachay.activities.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;

import com.example.amachay.R;
import com.example.amachay.activities.Client.MapClientActivity;
import com.example.amachay.activities.MainActivity;
import com.example.amachay.includes.MyToolbar;
import com.example.amachay.providers.AuthProvider;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapDoctorActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;
    private   AuthProvider mAuthProvider;

    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocation;
    private final static int LOCATION_REQUEST_CODE= 1;

    LocationCallback mLocationCallback = new LocationCallback()
    {
        @Override
        public void  onLocationResult(LocationResult locationResult)
        {
            for(Location location:locationResult.getLocations()){
                if(getApplicationContext()!= null)
                {
                    //OBTENER LA LOCALIZACIÓN DEL USURIAO EM TIEMPO REAL
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                                new CameraPosition.Builder()
                                .target(new LatLng(location.getLatitude(),location.getLongitude()))
                                .zoom(15f)
                                .build()

                        ));

                }

            }


        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_doctor);

        MyToolbar.show(this,"Mapa del Médico",false);

        mAuthProvider = new AuthProvider();

        //Con esta propiedad vamos a poder iniciar o detener la ubicación del usuario
        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);

        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mLocationRequest = new LocationRequest();
        //Tiempo de la ubicación en el mapa
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        //Para que haga uso del GPS con la mayor precisión posbile
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(5);

        startLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == LOCATION_REQUEST_CODE)
        {
            //Pregunatamos si el usuario concedió los permisos
            if(grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)

            {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                mFusedLocation.requestLocationUpdates(mLocationRequest,mLocationCallback, Looper.myLooper());

                }

            }
        }
    }
    private void startLocation()
    {
        //Versión 23 de Mashmello
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }
            else
            {

                checkLocationPermissions();
            }
        }
        else
        {
            mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());



        }


    }


    private void checkLocationPermissions()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                new AlertDialog.Builder(this)
                .setTitle("Proporciona los permisos para continuar")
                .setMessage("Esta aplicación requiere de los permisos de ubicación para poder utilizarse")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Esta línea lo que hace es habilitarnos los permisos para utilizarlo con el celular
                                ActivityCompat.requestPermissions(MapDoctorActivity.this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);

                            }
                        })
                        .create()
                        .show();

            }
            else {


                ActivityCompat.requestPermissions(MapDoctorActivity.this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);


            }

        }
    }

    //MENÚ
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.doctormenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_logout)
        {
            logout();


        }
        return super.onOptionsItemSelected(item);
    }
    void logout()
    {
        mAuthProvider.logout();
        //Cerrar sesión
        Intent intent = new Intent(MapDoctorActivity.this, MainActivity.class);
        startActivity(intent);

        finish();
    }
}
