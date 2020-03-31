package com.example.locomotioncommotion;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class RequestManager  extends AppCompatActivity implements OnMapReadyCallback {
    String TAG = "request_manager";
    FirebaseFirestore db;

    Request request;

    MapView mapView;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_manager);

        Intent intent = getIntent();
        request = (Request) intent.getExtras().getSerializable(RiderMain.REQUEST_MANAGE_MESSAGE);

        TextView startLocation = findViewById(R.id.request_manager_start);
        startLocation.setText(request.getStartLocation().getName());

        TextView endLocation = findViewById(R.id.request_manager_end);
        endLocation.setText(request.getEndLocation().getName());

        TextView ridePrice = findViewById(R.id.request_manager_price);
        int dollars = request.getFareOffered() / 100;
        int cents = request.getFareOffered() % 100;
        ridePrice.setText(String.format("$ %d.%02d", dollars, cents));

        mapView = findViewById(R.id.request_manager_map);
        mapView.onCreate(null);
        mapView.getMapAsync(this);

        final Button completeButton = findViewById(R.id.request_manager_button_complete);
//        if (request.getStatus() == "In Progress") {
            completeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, request.getFirebaseID());
                    db = FirebaseFirestore.getInstance();
                    db.collection("requests")
                            .document(request.getFirebaseID())
                            .update("status", "Completed")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error updating document", e);
                                }
                            });
                    finish();
                }
            });
//        } else {
//            completeButton.setVisibility(View.GONE);
//        }

        final Button deleteButton = findViewById(R.id.request_manager_button_cancel);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, request.getFirebaseID());
                db = FirebaseFirestore.getInstance();
                db.collection("requests")
                        .document(request.getFirebaseID())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        double bottom;
        double upper;
        double left;
        double right;

        LatLng start = new LatLng(request.getStartLocation().getLat(), request.getStartLocation().getLng());
        LatLng end = new LatLng(request.getEndLocation().getLat(), request.getEndLocation().getLng());

        if(start.latitude < end.latitude){
            bottom = start.latitude;
            upper = end.latitude;
        } else {
            bottom = end.latitude;
            upper = start.latitude;
        }
        if(start.longitude < end.longitude){
            left = start.longitude;
            right = end.longitude;
        } else {
            left = end.longitude;
            right = start.longitude;
        }

        LatLngBounds bounds = new LatLngBounds(new LatLng(bottom, left), new LatLng(upper, right));

        map.addPolyline(new PolylineOptions().add(start, end));

        map.addMarker(new MarkerOptions().position(end));

        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 44));
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
