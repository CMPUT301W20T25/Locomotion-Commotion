package com.example.locomotioncommotion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RequestFinderList extends AppCompatActivity {
    private FirebaseFirestore db;
    ListView requestList;
    ArrayAdapter<Request> requestAdapter;
    ArrayList<Request> requestDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_finder_list);

        requestList = findViewById(R.id.request_list);
        Request r = new Request();
        r.setStartLocation("Here");
        r.setEndLocation("There");
        requestDataList = new ArrayList<>();
        requestDataList.add(r);

        requestAdapter = new RequestList(this, requestDataList);
        requestList.setAdapter(requestAdapter);


       // db = FirebaseFirestore.getInstance();
        //final CollectionReference collectionReference = db.collection("requests");

       // collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
           // @Override
          //  public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

          //  }
       // });
    }


    public void backButton(View view) {
        finish();
    }
}
