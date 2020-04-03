package com.example.locomotioncommotion.activities.rider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.locomotioncommotion.R;
import com.example.locomotioncommotion.model.CurrentUser;
import com.example.locomotioncommotion.model.Request;
import com.example.locomotioncommotion.model.RequestList;
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
    public static final String REQUEST_MANAGE_MESSAGE = "com.example.locomotioncommotion.MANAGE_REQUEST";
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

        assert CurrentUser.getInstance() != null;
        db.collection("requests")
                .whereEqualTo("riderUsername", CurrentUser.getInstance().getUser().getUserName())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        requestDataList.clear();

                        for(QueryDocumentSnapshot doc: queryDocumentSnapshots){
                            // Only displays requests not completed
                            if (!"Completed".equals((String) doc.getData().get("status"))) {
                                Request request = doc.toObject(Request.class);
                                request.setFirebaseID(doc.getId());
                                requestDataList.add(request);
                            }
                        }
                        requestArrayAdapter.notifyDataSetChanged();
                    }
                });

        requestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), RequestManager.class);
                Request request = requestArrayAdapter.getItem(position);
                intent.putExtra(REQUEST_MANAGE_MESSAGE, request);
                startActivity(intent);
            }
        });

    }
    public void history(View view){
        Intent intent = new Intent(this, RideHistory.class);
        startActivity(intent);
    }

    public void create(View view){
        Intent intent = new Intent(this, CreateRequest.class);
        startActivity(intent);
//        Intent intent = new Intent(getApplicationContext(), SelectLocationActivity.class);
//        intent.putExtra(CreateRequest.SELECT_LOCATION_MESSAGE, "end");
//        startActivity(intent);
    }


}
