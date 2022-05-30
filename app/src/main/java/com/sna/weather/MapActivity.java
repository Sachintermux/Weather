package com.sna.weather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    MapView mapView;
    MapboxMap mapboxMap;
    MarkerOptions markerOptions1 = new MarkerOptions();
private Button button;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_map);
button = findViewById(R.id.button);
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick( View view ) {
        Intent intent = new Intent(MapActivity.this,MainActivity.class);
        intent.putExtra("Lat",String.valueOf(markerOptions1.getPosition().getLatitude()));
        intent.putExtra("Long",String.valueOf(markerOptions1.getPosition().getLongitude()));
        startActivity(intent);
    }
});
        mapView = findViewById(R.id.mapView);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);


    }

    @Override
    public void onMapReady( @NonNull MapboxMap mapboxMap ) {
        this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(Style.MAPBOX_STREETS,
                new Style.OnStyleLoaded() {

                    @Override
                    public void onStyleLoaded( @NonNull Style style ) {
                        button.setEnabled(true);
                        markerOptions1.setPosition(new LatLng(25.684817,85.815857));
                        mapboxMap.addMarker(markerOptions1);
                        mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                            @Override
                            public boolean onMapClick( @NonNull LatLng point ) {
                                mapboxMap.removeAnnotations();
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.setPosition(point);
                                markerOptions1 = markerOptions;
                                mapboxMap.addMarker(markerOptions);

                                return false;
                            }
                        });

                    }
                });


        mapboxMap.getUiSettings().setAttributionEnabled(false);
        mapboxMap.getUiSettings().setLogoEnabled(false);
        mapboxMap.getUiSettings().setZoomGesturesEnabled(true);
        mapboxMap.getUiSettings().setScrollGesturesEnabled(true);
        mapboxMap.getUiSettings().setAllGesturesEnabled(true);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    protected void onSaveInstanceState( @NonNull Bundle outState ) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}