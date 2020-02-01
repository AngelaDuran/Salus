package com.example.salus;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;


import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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


