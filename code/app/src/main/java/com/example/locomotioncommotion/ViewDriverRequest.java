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

public class ViewDriverRequest extends AppCompatActivity {
    String TAG = "viewDriverRequest";
    Request request;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_driver_request);

        Intent intent = getIntent();
        request = (Request) intent.getExtras().getSerializable(RequestFinderList.REQUEST_MANAGE_MESSAGE);

        TextView startLocation = findViewById(R.id.view_driver_request_start_location);
        TextView endLocation = findViewById(R.id.view_driver_request_end_location);
        TextView price = findViewById(R.id.view_driver_request_price);
        Button username = findViewById(R.id.view_driver_request_username);

        int dollars = request.getFareOffered() / 100;
        int cents = request.getFareOffered() % 100;




        price.setText(String.format("%d.%02d", dollars, cents));
        startLocation.setText(request.getStartLocation().getName());
        endLocation.setText(request.getEndLocation().getName());
        username.setText(request.getRiderUsername());




    }

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
        CurrentUser.getInstance().getUser().getDriver().acceptRequest(request);
        finish();
    }

    public void clickUserName(View view) {
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
    }

    public void backButton(View view) {
        finish();
    }
}
