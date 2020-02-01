package com.example.salus;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;


import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;


    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        //
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
         //       .findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
    }

    private void fetchLastLocation() {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
    }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
             if(location != null){
                currentLocation= location;
                Toast.makeText(getApplicationContext(), "This is your location's coordinates. " +currentLocation.getLatitude()
                + "   " + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                SupportMapFragment supportMapFragment = (SupportMapFragment)
                        getSupportFragmentManager().findFragmentById(R.id.map);
                supportMapFragment.getMapAsync(MainActivity.this);
             }
            }
        });
        

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                .title("Curent Location!");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));
        googleMap.addMarker(markerOptions);


        mMap = googleMap;

       // mMap.setMyLocationEnabled(true);
        LatLng LosAngeles = new LatLng(34.057906, -118.238845);
        mMap.addMarker(new MarkerOptions().position(LosAngeles).title("LA Hub"));
        LatLng SanFran = new LatLng(37.773359, -122.425250);
        mMap.addMarker(new MarkerOptions().position(SanFran).title("SanFran Hub"));
        LatLng SLO = new LatLng(35.283407, -120.661805);
        mMap.addMarker(new MarkerOptions().position(SLO).title("SLO Hub"));

     //   mMap.moveCamera(CameraUpdateFactory.newLatLng(LosAngeles));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if(grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                  fetchLastLocation();
                  break;
                }
        }
    }
}


