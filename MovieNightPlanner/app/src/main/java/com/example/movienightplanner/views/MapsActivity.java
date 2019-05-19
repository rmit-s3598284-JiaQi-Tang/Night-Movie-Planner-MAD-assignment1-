package com.example.movienightplanner.views;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.movienightplanner.R;
import com.example.movienightplanner.models.AppEngineImpl;
import com.example.movienightplanner.models.EventImpl;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);


        for(EventImpl event : appEngine.get3SoonestEvents()) {
            LatLng marker = new LatLng(new Double(event.getLocation().split(",")[0].trim()), new Double(event.getLocation().split(",")[1].trim()));
            mMap.addMarker(new MarkerOptions().position(marker).title(event.getTittle()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
        }
    }
}
