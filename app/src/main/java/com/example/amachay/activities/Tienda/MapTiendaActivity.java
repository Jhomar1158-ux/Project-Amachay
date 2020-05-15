package com.example.amachay.activities.Tienda;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.amachay.R;
import com.example.amachay.activities.MainActivity;
import com.example.amachay.includes.MyToolbar;
import com.example.amachay.providers.AuthProvider;
import com.example.amachay.providers.GeofireProvider;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapTiendaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private SupportMapFragment mMapFragment;

    private   AuthProvider mAuthProvider;


    private GeofireProvider mGeofireProvider;

    private LocationRequest mLocationRequest;

    private FusedLocationProviderClient mFusedLocation;

    private final static int LOCATION_REQUEST_CODE= 1;

    private final static int SETTINGS_REQUEST_CODE = 2;

    private Marker mMarker;

    private Button mButtonConnect;

    private boolean mIsConnect = false;

    private LatLng mCurrentLatlng;

    LocationCallback mLocationCallback = new LocationCallback()
    {
        @Override
        public void  onLocationResult(LocationResult locationResult)
        {
            for(Location location:locationResult.getLocations()){
                if(getApplicationContext()!= null)
                {
                    mCurrentLatlng = new LatLng(location.getLatitude(), location.getLongitude());
                    if(mMarker != null)
                    {
                        mMarker.remove();
                    }
                    mMarker = mMap.addMarker(new MarkerOptions().position(
                            new LatLng(location.getLatitude(),location.getLongitude())
                            )
                                    .title("Tu posición")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_tienda2))
                    );
                    //OBTENER LA LOCALIZACIÓN DEL USURIAO EM TIEMPO REAL
                    mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(location.getLatitude(),location.getLongitude()))
                                    .zoom(15f)
                                    .build()

                    ));

                    updateLocation();

                }

            }


        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_tienda);

        MyToolbar.show(this,"Tienda",false);

        mAuthProvider = new AuthProvider();
        mGeofireProvider = new GeofireProvider();
        //Con esta propiedad vamos a poder iniciar o detener la ubicación del usuario
        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);

        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
        mButtonConnect = findViewById(R.id.btnConnect);
        mButtonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsConnect)
                {

                    disconnect();
                }
                else
                {

                    startLocation();
                }
            }
        });

    }

    private void updateLocation()
    {
        if(mAuthProvider.existSession() && mCurrentLatlng != null)
        {
            mGeofireProvider.saveLocation(mAuthProvider.getId(),mCurrentLatlng);
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {

        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.getUiSettings().setZoomControlsEnabled(true);
        //Nos ubica un punto de referencia
        mLocationRequest = new LocationRequest();
        //Tiempo de la ubicación en el mapa
        mLocationRequest.setInterval(1000);

        mLocationRequest.setFastestInterval(1000);
        //Para que haga uso del GPS con la mayor precisión posbile
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mLocationRequest.setSmallestDisplacement(5);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (gpsActived()) {
                        mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mMap.setMyLocationEnabled(false);//Le damos false para omitir el punto azul que está por defecto

                    }
                    else {
                        showAlertDialogNOGPS();
                    }
                }
                else {
                    checkLocationPermissions();
                }
            }
            else {
                checkLocationPermissions();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_REQUEST_CODE && gpsActived())
        {
            mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mMap.setMyLocationEnabled(false);//Le damos false para omitir el punto azul que está por defecto
        }
        else {
            showAlertDialogNOGPS();
        }
    }


    private void showAlertDialogNOGPS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Por favor activa tu ubicacion para continuar")
                .setPositiveButton("Configuraciones", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), SETTINGS_REQUEST_CODE);
                    }
                }).create().show();
    }
    private boolean gpsActived() {
        boolean isActive = false;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            isActive = true;
        }
        return isActive;
    }

    private void disconnect()
    {
        mButtonConnect.setText("CONECTARSE");
        mIsConnect = false;
        if(mFusedLocation != null)
        {
            mButtonConnect.setText("CONECTARSE");
            mIsConnect = false;
            mFusedLocation.removeLocationUpdates(mLocationCallback);
            if(mAuthProvider.existSession())
            {
                mGeofireProvider.removeLocation(mAuthProvider.getId());

            }

        }
        else
        {

            Toast.makeText(this,"No te puedes desconectar",Toast.LENGTH_SHORT).show();
        }

    }

    private void startLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (gpsActived()) {
                    mButtonConnect.setText("DESCONECTARSE");
                    mIsConnect =true;
                    mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                    mMap.setMyLocationEnabled(false);//Le damos false para omitir el punto azul que está por defecto

                }
                else {
                    showAlertDialogNOGPS();
                }
            }
            else {
                checkLocationPermissions();
            }
        } else {
            if (gpsActived()) {
                mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mMap.setMyLocationEnabled(false);//Le damos false para omitir el punto azul que está por defecto

            }
            else {
                showAlertDialogNOGPS();
            }
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
                                ActivityCompat.requestPermissions(MapTiendaActivity.this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);

                            }
                        })
                        .create()
                        .show();

            }
            else
                {
                ActivityCompat.requestPermissions(MapTiendaActivity.this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
            }
        }
    }
    //MENÚ
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.tiendamenu,menu);
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
        disconnect();
        mAuthProvider.logout();
        //Cerrar sesión
        Intent intent = new Intent(MapTiendaActivity.this, MainActivity.class);
        startActivity(intent);

        finish();
    }
}
