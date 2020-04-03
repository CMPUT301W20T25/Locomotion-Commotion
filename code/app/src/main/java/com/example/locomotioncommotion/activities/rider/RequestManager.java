package com.example.locomotioncommotion.activities.rider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class RequestManager  extends AppCompatActivity implements OnMapReadyCallback {
    String TAG = "request_manager";
    FirebaseFirestore db;
    TextView driverText;

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

        TextView statusText = findViewById(R.id.request_manager_status);
        statusText.setText(request.getStatus());

        // Displays cost as dollars and cents
        TextView ridePrice = findViewById(R.id.request_manager_price);
        int dollars = request.getFareOffered() / 100;
        int cents = request.getFareOffered() % 100;
        ridePrice.setText(String.format("$ %d.%02d", dollars, cents));

        TextView distance = findViewById(R.id.request_manager_distance);
        int dis = request.getStartLocation().distance(request.getEndLocation());
        distance.setText(String.format("%d m", dis));

        mapView = findViewById(R.id.request_manager_map);
        mapView.onCreate(null);
        mapView.getMapAsync(this);

        driverText = findViewById(R.id.request_manager_user);

        if(request.getDriverUsername() != null) {
            driverText.setText(request.getDriverUsername());
            driverText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inspectDriver(v);
                }
            });
        } else {
            driverText.setText("N/A");
        }

        final Button completeButton = findViewById(R.id.request_manager_button_complete);
        if (request.getStatus().equals("Confirmed")) {
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

                    db.collection("Users")
                            .document(request.getDriverUsername())
                            .update("currentRequest", null)
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

                    User user = CurrentUser.getInstance().getUser();
                    user.getRider().notifyRequestComplete();
                    user.updateDatabase();

                    completeClick(v);

                    finish();
                }
            });
        } else {
            completeButton.setVisibility(View.GONE);
        }

        final Button confirmButton = findViewById(R.id.request_manager_button_confirm);
        if (request.getStatus().equals("Accepted")) {
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d(TAG, request.getFirebaseID());
                    db = FirebaseFirestore.getInstance();
                    db.collection("requests")
                            .document(request.getFirebaseID())
                            .update("status", "Confirmed")
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
                    request.setStatus("Confirmed");

                    db.collection("Users")
                            .document(request.getDriverUsername())
                            .update("currentRequest", request)
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

                    db.collection("Users")
                            .document(request.getDriverUsername())
                            .update("notification", "Your Drive Request has been accepted!")
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

                    finish();
                }
            });
        } else {
            confirmButton.setVisibility(View.GONE);
        }

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

                db.collection("Users")
                        .document(request.getDriverUsername())
                        .update("notification", "Your Drive Request has been cancelled!")
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

                db.collection("Users")
                        .document(request.getDriverUsername())
                        .update("currentRequest", null)
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

                User user = CurrentUser.getInstance().getUser();
                user.getRider().cancelRequest();
                user.updateDatabase();

                finish();
            }
        });
    }

    public void inspectDriver(View view){
        Intent intent = new Intent(this, InspectProfile.class);
        intent.putExtra("username",driverText.getText().toString());
        startActivity(intent);
    }

    public void completeClick(View view){
        Intent intent = new Intent(this, QRCode.class);
        intent.putExtra("username",request.getDriverUsername());
        startActivity(intent);
    }

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
