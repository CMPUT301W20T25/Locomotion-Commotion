package com.example.locomotioncommotion.activities.shared;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.locomotioncommotion.R;
import com.example.locomotioncommotion.activities.rider.CreateRequest;
import com.example.locomotioncommotion.model.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SelectLocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String SELECT_LOCATION_RETURN = "com.example.locomotioncommotion.SELECT_LOCATION_RETURN";

    private MapView mapView;
    private GoogleMap map;
    private Marker marker;
    private Button confirmButton;
    private TextView title;
    private TextView addressDisplay;

    private Location addressFull;
    private String locationName;

    /**
     * Called when the activity is initialized. Assigns views to variables and sets onClickListeners
     * @param savedInstanceState
     */
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
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(SELECT_LOCATION_RETURN, addressFull);
                    setResult(Activity.RESULT_OK, resultIntent);

                    finish();
                }
            }
        });
    }

    /**
     * Called when the map is initialized. Handles settings being updated.
     * Adds onMapClickListener to add markers
     * @param googleMap
     *      The googleMap object to assign to our map variable
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Center the map on the classroom
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.523983, -113.523249), 13));
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMapToolbarEnabled(false);

        // Places the marker when the map is clicked on
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(marker == null) {
                    marker = map.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(locationName)
                            .draggable(true));
                }
                marker.setPosition(latLng);
                updateLocationText(latLng);

                // Shows the marker title when clicked upon
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

        // Sets the address when the marker gets dragged
        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) { }

            @Override
            public void onMarkerDrag(Marker marker) { }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                updateLocationText(marker.getPosition());
            }
        });
    }

    /**
     * Uses a geocoder to update the address from the given coordinates
     * @param latLng
     *      the coordinates of the location to retrieve
     */
    private void updateLocationText(LatLng latLng) {
        // Youtube: https://www.youtube.com/watch?v=Ut_VK92QqEQ  Channel: https://www.youtube.com/channel/UCMNM0n24Yn59OLQYPyd3hNA
        Geocoder geocoder = new Geocoder(SelectLocationActivity.this, Locale.getDefault());
        String address;
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            address = addresses.get(0).getAddressLine(0);
            Log.d("geocoder_address", address);

        } catch (IOException e) {
            e.printStackTrace();
            address = String.valueOf(latLng.latitude) + " " + String.valueOf(latLng.longitude);
            Log.d("geocoder_address", "Error: goecoder not working");
        }

        addressFull = new Location(latLng.latitude, latLng.longitude, address);
        addressDisplay.setText(address);
        addressDisplay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
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
