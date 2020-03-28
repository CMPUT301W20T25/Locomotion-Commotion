package com.example.locomotioncommotion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class RiderMap extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private GoogleMap map;

    private Marker start;
    private Marker end;

    private Marker current;

    private Button createButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rider_map);

        start = null;
        end = null;
        current = null;

        createButton = findViewById(R.id.rider_map_confirm);
        deleteButton = findViewById(R.id.rider_map_delete);

        mapView = findViewById(R.id.map);
        mapView.onCreate(null);
        mapView.getMapAsync(this);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String removed = current.getTitle();
                if(removed.equals("start")){
                    start = null;
                } else {
                    end = null;
                }

                current.remove();
                deleteButton.setVisibility(View.INVISIBLE);
                current = null;
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity(v);
            }
        });
    }

    public void nextActivity(View view){
        if(start != null && end != null) {
            Intent intent = new Intent(this, CreateRequest.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.getUiSettings().setZoomControlsEnabled(true);

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(start == null){
                    start = map.addMarker(new MarkerOptions().position(latLng).title("start"));
                }
                else if (end == null){
                    end = map.addMarker(new MarkerOptions().position(latLng).title("end"));
                }
                else{
                    deleteButton.setVisibility(View.INVISIBLE);
                    current = null;
                    return;
                }

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if(marker.isInfoWindowShown()){
                            marker.hideInfoWindow();
                            deleteButton.setVisibility(View.INVISIBLE);
                            current = null;
                        } else {
                            marker.showInfoWindow();
                            deleteButton.setVisibility(View.VISIBLE);
                            current = marker;
                        }
                        return false;
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}