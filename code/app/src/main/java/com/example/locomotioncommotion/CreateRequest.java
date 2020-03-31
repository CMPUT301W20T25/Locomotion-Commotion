package com.example.locomotioncommotion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Activity for creating a request, as a rider.
 * Currently just a stub/placeholder
 */
public class CreateRequest extends AppCompatActivity implements OnMapReadyCallback {
    public static final String SELECT_LOCATION_MESSAGE = "com.example.locomotioncommotion.SELECT_LOCATION";
    public static final int START_REQUEST = 1;
    public static final int END_REQUEST = 2;

    ArrayList<Request> requestDataList;
    ArrayAdapter<Request> requestArrayAdapter;

    private String TAG = "Request_create";
    private EditText starting;
    private EditText ending;
    private Button createButton;
    private FirebaseFirestore db;

    private MapView mapView;
    private GoogleMap map;
    private Marker markerStart;
    private Marker markerEnd;

    private Location start;
    private Location end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_request);

        mapView = findViewById(R.id.create_request_map);
        mapView.onCreate(null);
        mapView.getMapAsync(this);

        starting = findViewById(R.id.start_edit_create);
        ending = findViewById(R.id.end_edit_create);
        createButton = findViewById(R.id.create_request_button);

        db = FirebaseFirestore.getInstance();

        starting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectLocationActivity.class);
                intent.putExtra(CreateRequest.SELECT_LOCATION_MESSAGE, "start");
                startActivityForResult(intent, START_REQUEST);
            }
        });

        ending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectLocationActivity.class);
                intent.putExtra(CreateRequest.SELECT_LOCATION_MESSAGE, "end");
                startActivityForResult(intent, END_REQUEST);
            }
        });



        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Only allow the creation if both start and end are set
                if (start != null && end != null) {
                    Request request = new Request(CurrentUser.getInstance().getUser().getUserName(), start, end, 0);

                    db.collection("requests")
                            .add(request)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });


                    //Stack overflow post https://stackoverflow.com/a/18463758 User: https://stackoverflow.com/users/1531657/muhammad-aamir-ali
                    SharedPreferences preferences = getPreferences(MODE_PRIVATE);

                    SharedPreferences.Editor prefEditor = preferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(request);
                    prefEditor.putString("RiderRequest",json);
                    prefEditor.commit();

                    finish();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == START_REQUEST) {
                start = (Location) data.getExtras().getSerializable(SelectLocationActivity.SELECT_LOCATION_RETURN);
                starting.setText(start.getName());
            } else if (requestCode == END_REQUEST) {
                end = (Location) data.getExtras().getSerializable(SelectLocationActivity.SELECT_LOCATION_RETURN);
                ending.setText(end.getName());
            }
        }

        updateLocation();

    }

    private void updateLocation() {
        if (start != null) {
            if (markerStart == null) {
                markerStart = map.addMarker(new MarkerOptions()
                        .position(new LatLng(0,0))
                        .title("Start")
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
            LatLng startLoc = new LatLng(start.getLat(), start.getLng());
            markerStart.setPosition(startLoc);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(startLoc, 13));
        }
        if (end != null) {
            if (markerEnd == null) {
                markerEnd = map.addMarker(new MarkerOptions()
                        .position(new LatLng(0,0))
                        .title("End")
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            }
            LatLng endLoc = new LatLng(end.getLat(), end.getLng());
            markerEnd.setPosition(endLoc);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(endLoc, 13));
        }
        if (start != null && end != null) {
            LatLng endLoc = new LatLng(end.getLat(), end.getLng());
            LatLng startLoc = new LatLng(start.getLat(), start.getLng());

            // Following code to update camera with two markers from: https://stackoverflow.com/a/14828739
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(startLoc);
            builder.include(endLoc);
            LatLngBounds bounds = builder.build();

            int padding = 120; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            map.moveCamera(cu);

            map.addPolyline(new PolylineOptions().add(startLoc, endLoc));
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap){
        map = googleMap;

        // Center the map on the classroom
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.523983, -113.523249), 13));
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setMapToolbarEnabled(false);
        uiSettings.setZoomControlsEnabled(true);

        updateLocation();

        // Uncomment to disable map movement
//        uiSettings.setZoomControlsEnabled(false);
//        uiSettings.setAllGesturesEnabled(false);


//        LatLng start = new LatLng(-44, 113);
//        LatLng end = new LatLng(-10, 154);
//
//        map.addPolyline(new PolylineOptions().add(start, end));
//
//        LatLngBounds bounds = new LatLngBounds(start, end);
//        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
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
