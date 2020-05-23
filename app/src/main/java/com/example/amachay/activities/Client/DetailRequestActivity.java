package com.example.amachay.activities.Client;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amachay.R;
import com.example.amachay.includes.MyToolbar;
import com.example.amachay.providers.GoogleApiProvider;
import com.example.amachay.utils.DecodePoints;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRequestActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    private SupportMapFragment mMapFragment;


    private double mExtraOriginLat;
    private double mExtraOriginLng;
    private double mExtraDestinationLat;
    private double mExtraDestinationLng;

    private LatLng mOriginLatLong;
    private LatLng mDestinationLatLong;

    private GoogleApiProvider mGoogleApiProvider;

    private List<LatLng> mPolylineList;
    private PolylineOptions mPolylineOptions;
    private TextView mTextViewOrigin;
    private TextView mTextViewDestination;
    private TextView mTextViewTime;
    private TextView mTextViewDistance;

    private String mExtraOrigen;
    private String mExtraDestination;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_request);

        MyToolbar.show(this, "TUS DATOS", true);

        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
        mExtraOriginLat = getIntent().getDoubleExtra("origin_lat", 0);
        mExtraOriginLng = getIntent().getDoubleExtra("origin_lgn", 0);
        mExtraDestinationLat = getIntent().getDoubleExtra("destination_lat", 0);
        mExtraDestinationLng = getIntent().getDoubleExtra("destination_lng", 0);
        mExtraOrigen = getIntent().getStringExtra("origin");
        mExtraDestination = getIntent().getStringExtra("destination");




        mOriginLatLong = new LatLng(mExtraOriginLat, mExtraOriginLng);
        mDestinationLatLong = new LatLng(mExtraDestinationLat, mExtraDestinationLng);
        //Instanciamos el google Api provider
        mGoogleApiProvider = new GoogleApiProvider(DetailRequestActivity.this);

        mTextViewOrigin = findViewById(R.id.textViewOrigin);
        mTextViewDestination = findViewById(R.id.textViewDestination);
        mTextViewTime = findViewById(R.id.textViewTime);
        mTextViewDistance = findViewById(R.id.textViewDistance);

        mTextViewOrigin.setText(mExtraOrigen);
        mTextViewDestination.setText(mExtraDestination);

    }

    private void drawRoute()
    {
        mGoogleApiProvider.getDirections(mOriginLatLong, mDestinationLatLong).enqueue(new Callback<String>() {

            //Donde vamos a estar recibiendo la respuesta de nuestro servidor
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try{

                    JSONObject jsonObject = new JSONObject(response.body());
                    JSONArray jsonArray =  jsonObject.getJSONArray("routes");
                    JSONObject route = jsonArray.getJSONObject(0);
                    JSONObject polylines = route.getJSONObject("overview_polyline");
                    String points = polylines.getString("points");

                    // Línea del trazdo de ruta
                    mPolylineList = DecodePoints.decodePoly(points);
                    mPolylineOptions = new PolylineOptions();
                    mPolylineOptions.color(Color.DKGRAY);
                    mPolylineOptions.width(13f);
                    mPolylineOptions.startCap(new SquareCap());
                    mPolylineOptions.jointType(JointType.ROUND);
                    mPolylineOptions.addAll(mPolylineList);
                    mMap.addPolyline(mPolylineOptions);

                    JSONArray legs = route.getJSONArray("legs");
                    JSONObject leg = legs.getJSONObject(0);
                    JSONObject distance = leg.getJSONObject("distance");
                    JSONObject duration = leg.getJSONObject("duration");
                    String distanceText = distance.getString("text");
                    String durationText = duration.getString("text");
                    mTextViewTime.setText(durationText);
                    mTextViewDistance.setText(distanceText);


                }catch (Exception e)
                {
                    Log.d("Error","Error encontrado"+ e.getMessage());

                }


            }
            //Aquí se ejecutará en caso falle nuestra petición al servidor
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //Para establecer el punto de nuestra ubicación exacta


        //Añadir un marcador

        mMap.addMarker(new MarkerOptions().position(mOriginLatLong).title("Origen").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_red)));
        mMap.addMarker(new MarkerOptions().position(mOriginLatLong).title("Destino").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_blue)));


        //Mover la cámara
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(mOriginLatLong)
                        .zoom(14f)
                        .build()
        ));
        drawRoute();
    }
}
