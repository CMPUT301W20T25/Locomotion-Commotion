package com.example.locomotioncommotion.activities.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.locomotioncommotion.R;
import com.example.locomotioncommotion.activities.shared.InspectProfile;
import com.example.locomotioncommotion.model.CurrentUser;
import com.example.locomotioncommotion.model.Request;
import com.example.locomotioncommotion.model.User;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
//views the current Request the Driver Accepted
public class ViewDriverRequest extends AppCompatActivity implements OnMapReadyCallback {
    String TAG = "viewDriverRequest";
    Request request;
    FirebaseFirestore db;

    MapView mapView;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_driver_request);

        Intent intent = getIntent();
        request = (Request) intent.getExtras().getSerializable(RequestFinderList.REQUEST_MANAGE_MESSAGE);

        // displaying  all of the relevant information from the request
        TextView startLocation = findViewById(R.id.view_driver_request_start_location);
        TextView endLocation = findViewById(R.id.view_driver_request_end_location);
        TextView price = findViewById(R.id.view_driver_request_price);
        TextView statusText = findViewById(R.id.view_driver_request_status);
        Button username = findViewById(R.id.view_driver_request_username);

        int dollars = request.getFareOffered() / 100;
        int cents = request.getFareOffered() % 100;

        price.setText(String.format("%d.%02d", dollars, cents));
        startLocation.setText(request.getStartLocation().getName());
        endLocation.setText(request.getEndLocation().getName());
        username.setText(request.getRiderUsername());
        statusText.setText(request.getStatus());

        mapView = findViewById(R.id.view_driver_request_map);
        mapView.onCreate(null);
        mapView.getMapAsync(this);

        // so the class can be reused to view an already accepted request
        if (request.getStatus().equals("Pending") != true) {
            Button accept = findViewById(R.id.view_driver_request_accept_button);
            accept.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * Accepts a request for a driver.
     * Sends notification to rider
     * @param view
     */
    public void acceptButton(View view) {
        db = FirebaseFirestore.getInstance();
        db.collection("requests")
                .document(request.getFirebaseID())
                .update("status", "Accepted")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"Document Successully updated");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"Error updating document",e);
                    }
                });

        db.collection("Users")
                .document(request.getRiderUsername())
                .update("notification", "Your Ride Request has been accepted!")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"Document Successfully updated");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"Error updating document",e);
                    }
                });

        db.collection("requests")
                .document(request.getFirebaseID())
                .update("driverUsername", CurrentUser.getInstance().getUser().getUserName())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"Document Successully updated");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"Error updating document",e);
                    }
                });

        Log.d(TAG, request.getFirebaseID());
        //update current request
        request.setDriverUsername(CurrentUser.getInstance().getUser().getUserName());
        request.setStatus("Accepted");
        CurrentUser.getInstance().getUser().getDriver().acceptRequest(request);
        CurrentUser.getInstance().getUser().updateDatabase();
        finish();
    }

    /**
     * Start the activity to view a rider's profile
     * @param view
     */
    public void clickUserName(View view) {
        Intent intent = new Intent(this, InspectProfile.class);
        intent.putExtra("username",request.getRiderUsername());
        startActivity(intent);
    }

    /**
     * returns to the previous activity
     * @param view
     */
    public void backButton(View view) {
        finish();
    }

    /**
     * Called when the map is initialized. Handles settings being updated.
     * Creates markers for both locations and centers map on them.
     * @param googleMap
     *      The googleMap object to assign to our map variable
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Map preference setup
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setMapToolbarEnabled(false);
        uiSettings.setZoomControlsEnabled(true);


        LatLng startLoc = new LatLng(request.getStartLocation().getLat(), request.getStartLocation().getLng());
        LatLng endLoc = new LatLng(request.getEndLocation().getLat(), request.getEndLocation().getLng());

        // Following code to update camera with two markers from: https://stackoverflow.com/a/14828739
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(startLoc);
        builder.include(endLoc);
        LatLngBounds bounds = builder.build();

        int padding = 120; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map.moveCamera(cu);

        map.addMarker(new MarkerOptions()
                .position(endLoc)
                .title("End")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        map.addMarker(new MarkerOptions()
                .position(startLoc)
                .title("Start")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        map.addPolyline(new PolylineOptions().add(startLoc, endLoc));
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
