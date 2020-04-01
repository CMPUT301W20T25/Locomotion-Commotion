package com.example.locomotioncommotion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RequestFinderList extends AppCompatActivity {
    public static final String REQUEST_MANAGE_MESSAGE = "com.example.locomotioncommotion.MANAGE_REQUEST";
    private FirebaseFirestore db;
    ListView requestList;
    ArrayAdapter<Request> requestAdapter;
    ArrayList<Request> requestDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_finder_list);

        requestList = findViewById(R.id.request_list);

        requestDataList = new ArrayList<>();

        requestAdapter = new RequestList(this, requestDataList);
        requestList.setAdapter(requestAdapter);


        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("requests");

        collectionReference
                .whereEqualTo("status", "Pending")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        requestDataList.clear();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            Request request = doc.toObject(Request.class);
                            if (!request.getRiderUsername().equals(CurrentUser.getInstance().getUser().getUserName())) {
                                request.setFirebaseID(doc.getId());
                                requestDataList.add(request);
                            }
                        }
                        requestAdapter.notifyDataSetChanged();
                    }
                });

        requestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TESTITEMCLICK","I am here");
                Intent intent = new Intent(getApplicationContext(), ViewDriverRequest.class);
                Request request = requestAdapter.getItem(position);
                intent.putExtra(REQUEST_MANAGE_MESSAGE, request);
                startActivity(intent);


            }
        });
    }


    public void backButton(View view) {
        finish();
    }


}
