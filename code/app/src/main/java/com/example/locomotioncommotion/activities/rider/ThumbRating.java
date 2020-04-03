package com.example.locomotioncommotion.activities.rider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.locomotioncommotion.activities.shared.InspectProfile;
import com.example.locomotioncommotion.model.Request;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import io.opencensus.tags.Tag;

import com.example.locomotioncommotion.R;
import com.example.locomotioncommotion.activities.rider.RiderMain;
import com.example.locomotioncommotion.model.CurrentUser;
import com.example.locomotioncommotion.model.User;

public class ThumbRating extends AppCompatActivity {
    String TAG = "ThumbRating";
    Button rateUp;
    Button rateDown;
    User currentDriver;
    Button username;
    private User user;

    TextView driverName;

    FirebaseFirestore db;
    Request request; //need to get driver profile


    int currentUp = 0;
    int currentDown;
    int newCurrentUp;
    int newCurrentDown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumb_rating);

        Intent intent = getIntent();
        request = (Request) intent.getExtras().getSerializable(com.example.locomotioncommotion.activities.rider.RideHistory.REQUEST_MANAGE_MESSAGE);

        rateUp = findViewById(R.id.thumbs_up_Button);
        rateDown = findViewById(R.id.rate_down_button);
        TextView startLocation = findViewById(R.id.ride_history_start_location);
        TextView endLocation = findViewById(R.id.ride_history_end_location);
        TextView price = findViewById(R.id.ride_history_price);
        username = findViewById(R.id.ride_history_driver);

        int dollars = request.getFareOffered() / 100;
        int cents = request.getFareOffered() % 100;


        price.setText(String.format("%d.%02d", dollars, cents));
        startLocation.setText(request.getStartLocation().getName());
        endLocation.setText(request.getEndLocation().getName());
        username.setText(request.getDriverUsername());

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverInfo(v);
            }
        });

//        db.collection("Users").whereEqualTo("userName", username)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                user = document.toObject(User.class);
//                                currentUp = user.getThumbsUp();
//                                currentDown = user.getThumbsDown();
//                            }
//                        } else {
//                            Log.d("TAG", "Error getting document");
//                        }
//                    }
//                });

        rateUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, request.getFirebaseID());
                db = FirebaseFirestore.getInstance();


                db.collection("Users")
                        .document(request.getDriverUsername())
                        .update("thumbsUp", 1)
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
                Log.d(TAG, request.getFirebaseID());
                db = FirebaseFirestore.getInstance();

                db.collection("Users")
                        .document(request.getDriverUsername())
                        .update("thumbsDown", -1)
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

    public void driverInfo(View view){
        Intent intent = new Intent(this, InspectProfile.class);
        intent.putExtra("username",request.getDriverUsername());
        startActivity(intent);
    }
    //return back to main Rider page
    public void riderClick(View view){
        Intent intent = new Intent(this, RiderMain.class);
        startActivity(intent);
    }
}
