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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class RequestManager  extends AppCompatActivity {
    String TAG = "request_manager";
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_manager);

        Intent intent = getIntent();
        final Request request = (Request) intent.getExtras().getSerializable(RiderMain.REQUEST_MANAGE_MESSAGE);

        TextView startLocation = findViewById(R.id.request_manager_start);
        startLocation.setText(request.getStartLocation());

        TextView endLocation = findViewById(R.id.request_manager_end);
        endLocation.setText(request.getEndLocation());

        TextView ridePrice = findViewById(R.id.request_manager_price);
        int dollars = request.getFareOffered() / 100;
        int cents = request.getFareOffered() % 100;
        ridePrice.setText(String.format("$ %d.%02d", dollars, cents));

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
}
