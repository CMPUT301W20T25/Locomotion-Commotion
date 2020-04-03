package com.example.locomotioncommotion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import io.opencensus.tags.Tag;

public class ThumbRating extends AppCompatActivity {
    String TAG = "ThumbRating";
    Button rateUp;
    Button rateDown;
    User currentDriver;

    Driver driver;

    TextView driverName;

    FirebaseFirestore db;
    Request request; //need to get driver profile




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumb_rating);

        currentDriver = CurrentUser.getInstance().getUser();
        rateUp = findViewById(R.id.thumbsUp);
        rateDown = findViewById(R.id.thumbsDown);
        driver = currentDriver.getDriver();

        Intent intent = getIntent();
        request = (Request) intent.getExtras().getSerializable(RiderMain.REQUEST_MANAGE_MESSAGE);

        driverName = findViewById(R.id.driverNameRate);
        driverName.setText(request.getDriverUsername());

        rateUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current;
                current = currentDriver.getThumbsUp();
                current += 1;
                currentDriver.setThumbsUp(current);
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
                riderClick(v);
                finish();
            }
        });


        rateDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current;
                current = currentDriver.getThumbsDown();
                current += 1;
                currentDriver.setThumbsUp(current);
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
                riderClick(v);
                finish();
            }
        });

    }
    //return back to main Rider page
    public void riderClick(View view){
        Intent intent = new Intent(this, RiderMain.class);
        startActivity(intent);
    }
}
