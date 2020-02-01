package com.example.salus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng LosAngeles = new LatLng(34.057906, -118.238845);
        mMap.addMarker(new MarkerOptions().position(LosAngeles).title("LA Hub"));
        LatLng SanFran = new LatLng(37.773359, -122.425250);
        mMap.addMarker(new MarkerOptions().position(SanFran).title("SanFran Hub"));
        LatLng SLO = new LatLng(35.283407, -120.661805);
        mMap.addMarker(new MarkerOptions().position(SLO).title("SLO Hub"));

        //   mMap.moveCamera(CameraUpdateFactory.newLatLng(LosAngeles));
    }
}
