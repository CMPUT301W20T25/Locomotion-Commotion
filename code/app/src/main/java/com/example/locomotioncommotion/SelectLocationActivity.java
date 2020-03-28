package com.example.locomotioncommotion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SelectLocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private GoogleMap map;
    private Marker marker;
    private Button confirmButton;
    private TextView title;
    private TextView addressDisplay;

    private Location addressFull;
    private String locationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        Intent intent = getIntent();
        locationName = intent.getStringExtra(CreateRequest.SELECT_LOCATION_MESSAGE);

        title = findViewById(R.id.select_location_title);
        title.setText("Select " + locationName + " location");

        addressDisplay = findViewById(R.id.select_location_address_display);
        confirmButton = findViewById(R.id.select_location_confirmation_button);

        mapView = findViewById(R.id.select_location_map);
        mapView.onCreate(null);
        mapView.getMapAsync(this);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (marker != null) {
                    CreateRequest.updateLocation(locationName, addressFull);
                    finish();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Center the map on the classroom
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.523983, -113.523249), 13));
        map.getUiSettings().setZoomControlsEnabled(true);

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(marker != null) {
                    marker.remove();
                }
                marker = map.addMarker(new MarkerOptions().position(latLng).title(locationName));

                Geocoder geocoder = new Geocoder(SelectLocationActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    String address = addresses.get(0).getAddressLine(0);
                    Log.d("geocoder_address", address);
                    addressFull = new Location(latLng.latitude, latLng.longitude, address);
                    addressDisplay.setText(address);
                    addressDisplay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Maybe unnecessary, remove late?
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if(marker.isInfoWindowShown()){
                            marker.hideInfoWindow();
                        } else {
                            marker.showInfoWindow();
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
