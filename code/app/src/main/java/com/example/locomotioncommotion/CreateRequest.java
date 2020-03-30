package com.example.locomotioncommotion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
//                startActivity(intent);
                startActivityForResult(intent, START_REQUEST);
            }
        });

        ending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectLocationActivity.class);
                intent.putExtra(CreateRequest.SELECT_LOCATION_MESSAGE, "end");
//                startActivity(intent);
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

                    finish();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_REQUEST) {
            start = (Location) data.getExtras().getSerializable(SelectLocationActivity.SELECT_LOCATION_RETURN);
            starting.setText(start.getName());
        } else if (requestCode == END_REQUEST) {
            end = (Location) data.getExtras().getSerializable(SelectLocationActivity.SELECT_LOCATION_RETURN);
            ending.setText(end.getName());
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap){
//        map = googleMap;
//
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
//        Log.d("Location tests", start.getName());
//        Log.d("Location tests", end.getName());
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
