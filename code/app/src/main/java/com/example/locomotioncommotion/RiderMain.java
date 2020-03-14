package com.example.locomotioncommotion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Hub area for rider functionality, where they view their requests and create them
 */

public class RiderMain extends AppCompatActivity {

    String TAG = "Get_request_list";
    ListView requestList;
    ArrayAdapter<Request> requestArrayAdapter;
    ArrayList<Request> requestDataList;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rider);

        requestList = findViewById(R.id.activeRequests);

        requestDataList = new ArrayList<>();

        requestArrayAdapter = new RequestList(this, requestDataList);
        //show the data on this layout??
        requestList.setAdapter(requestArrayAdapter);

        db = FirebaseFirestore.getInstance();

        db.collection("requests")
                .whereEqualTo("currentRider", User.getInstance())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        requestDataList.clear();

                        for(QueryDocumentSnapshot doc: queryDocumentSnapshots){
                            Log.d(TAG, String.valueOf(doc.getId()));

                            String start = (String) doc.getData().get("startLocation");
                            String end = (String) doc.getData().get("endLocation");
                            int fare = Math.toIntExact((Long) doc.getData().get("fareOffered"));
                            requestDataList.add(new Request(User.getInstance(), start, end, fare));
                        }
                        requestArrayAdapter.notifyDataSetChanged();
                    }
                });

    }

    public void create(View view){
        Intent intent = new Intent(this, createRequest.class);
        startActivity(intent);
    }


}
