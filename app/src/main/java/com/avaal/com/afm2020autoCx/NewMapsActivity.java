package com.avaal.com.afm2020autoCx;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class NewMapsActivity extends FragmentActivity implements OnMapReadyCallback
         {

    private GoogleMap mMap;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    //Google ApiClient
    private GoogleApiClient googleApiClient;
    //To store longitude and latitude from map
    private double longitude;
    private double latitude;
    ImageView pin_;
TextView resutText,done;
             LatLng latLng;
             LatLng SelectlatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT < 22)
            setStatusBarTranslucent(false);
        else
            setStatusBarTranslucent(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        resutText = (TextView) findViewById(R.id.dragg_result);
        pin_=(ImageView)findViewById(R.id.pin_);
        done=(TextView)findViewById(R.id.done);
        mapFragment.getMapAsync(this);
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();

//        intent.putExtra("lati",""+location.getLatitude());
//        intent.putExtra("longi",""+location.getLongitude());
//        Log.e("hgsh",""+getIntent().getStringExtra("lati"));
        latLng = new LatLng(getIntent().getDoubleExtra("lati",0.00), getIntent().getDoubleExtra("longi",0.00));
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("location","true");
                data.putExtra("Latitude",""+SelectlatLng.latitude);
                data.putExtra("Longitude",""+SelectlatLng.longitude);
                setResult(RESULT_OK,data);
                finish();
            }
        });
 configureCameraIdle();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        // Add a marker in Sydney and move the camera
////        LatLng sydney = new LatLng(-34, 151);
////        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        GooglePlayServicesUtil
//                .isGooglePlayServicesAvailable(getApplicationContext());
////        mMap.setMyLocationEnabled(true);
//        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//
//        mMap.addMarker(new MarkerOptions()
//                .position(new LatLng(28.418589, 77.038267))
//                .title("Marker")
//                .draggable(true)
//                .snippet("Hello")
//                .icon(BitmapDescriptorFactory
//                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
//
//    }
    @Override
    protected void onStart() {
//        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
//        googleApiClient.disconnect();
        super.onStop();
    }
    private void configureCameraIdle() {
        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                mMap.clear();

                 SelectlatLng = mMap.getCameraPosition().target;
                Geocoder geocoder = new Geocoder(NewMapsActivity.this);

                try {
                    List<Address> addressList = geocoder.getFromLocation(SelectlatLng.latitude, SelectlatLng.longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        String locality = addressList.get(0).getAddressLine(0);
                        String country = addressList.get(0).getCountryName();
//                        mMap.addMarker(new MarkerOptions()
//                .position(latLng)
//                .title("Marker")
//                .draggable(true)
//                 .rotation(45)
//                .snippet("Hello")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car_marker)));
                        if (!locality.isEmpty() && !country.isEmpty())
                            Log.e("Lat/Long",""+SelectlatLng.latitude+","+SelectlatLng.longitude);
                            resutText.setText(locality + "  " + country);
//                        longitude=SelectlatLng.longitude;
//                        latitude=SelectlatLng.latitude;

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
    }
             protected void setStatusBarTranslucent(boolean makeTranslucent) {
                 if (makeTranslucent) {
                     getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                 } else {
                     getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                 }
             }
    //Getting current location
    private void getCurrentLocation() {
        mMap.clear();
        //Creating a location object
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            //moving the map to location
//            moveMap();
        }
    }

    //Function to move the map
//    private void moveMap() {
//        //String to display current latitude and longitude
//        String msg = latitude + ", "+longitude;
//
//        //Creating a LatLng Object to store Coordinates
//        LatLng latLng = new LatLng(latitude, longitude);
//
//        //Adding marker to map
//        mMap.addMarker(new MarkerOptions()
//                .position(latLng) //setting position
//                .draggable(true) //Making the marker draggable
//                .title("Current Location")); //Adding a title
//
//        //Moving the camera
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//
//        //Animating the camera
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
//
//        //Displaying current coordinates in toast
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
//
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnCameraIdleListener(onCameraIdleListener);
//        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        mMap.setIndoorEnabled(false);
//        mMap.setOnMarkerDragListener(this);
//        mMap.setOnMapLongClickListener(this);
//        mMap.setOnInfoWindowClickListener(this);
//        getCurrentLocation();
//        moveMap();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                pin_.setVisibility(View.VISIBLE);

            }
        });

    }

//    @Override
//    public void onConnected(Bundle bundle) {
//        getCurrentLocation();
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    public void onMapLongClick(LatLng latLng) {
//        //Clearing all the markers
//        mMap.clear();
//
//        //Adding a new marker to the current pressed position
//        mMap.addMarker(new MarkerOptions()
//                .position(latLng)
//                .draggable(true));
//    }
//
//    @Override
//    public void onMarkerDragStart(Marker marker) {
//
//    }
//
//    @Override
//    public void onMarkerDrag(Marker marker) {
//
//    }
//
//    @Override
//    public void onMarkerDragEnd(Marker marker) {
//        //Getting the coordinates
//        latitude = marker.getPosition().latitude;
//        longitude = marker.getPosition().longitude;
//
//        //Moving the map
//        moveMap();
//    }
//
//    @Override
//    public void onInfoWindowClick(Marker marker) {
//
//    }

//    @Override
//    public void onClick(View v) {
//        if(v == buttonCurrent){
//            getCurrentLocation();
//            moveMap();
//        }
//    }
}