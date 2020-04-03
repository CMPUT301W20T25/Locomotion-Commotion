package com.example.locomotioncommotion.activities.driver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.locomotioncommotion.R;
import com.example.locomotioncommotion.activities.shared.SelectLocationActivity;
import com.example.locomotioncommotion.model.CurrentUser;
import com.example.locomotioncommotion.model.Location;
import com.example.locomotioncommotion.model.Request;
import com.example.locomotioncommotion.model.RequestList;
import com.example.locomotioncommotion.model.SortByDistance;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Lists requests that have not been accepted yet, can click on editText location to pick a location that it will organize the requests by that geolocation
 */
public class RequestFinderList extends AppCompatActivity {
    public static final String SELECT_LOCATION_MESSAGE = "com.example.locomotioncommotion.SELECT_LOCATION";
    public static final String REQUEST_MANAGE_MESSAGE = "com.example.locomotioncommotion.MANAGE_REQUEST";
    public static final int PICKUP_REQUEST = 3;
    private FirebaseFirestore db;
    ListView requestList;
    ArrayAdapter<Request> requestAdapter;
    ArrayList<Request> requestDataList;

    private EditText locationText;

    Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_finder_list);

        requestList = findViewById(R.id.request_list);

        requestDataList = new ArrayList<>();

        requestAdapter = new RequestList(this, requestDataList);
        requestList.setAdapter(requestAdapter);

        currentLocation = null;

        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("requests");

        locationText = findViewById(R.id.request_finder_list_location);
        locationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectLocationActivity.class);
                intent.putExtra(RequestFinderList.SELECT_LOCATION_MESSAGE, "pickup");
                startActivityForResult(intent, PICKUP_REQUEST);
            }
        });

        //gets the requests and shows them in a list
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

        //goes to view the clicked request
        requestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

    // gets the result from the acctivity that gets the geolocation and sorts the list based on closest start locations to that chosen geolocation
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            currentLocation = (Location) data.getExtras().getSerializable(SelectLocationActivity.SELECT_LOCATION_RETURN);
            locationText.setText(currentLocation.getName());
            Collections.sort(requestDataList, new SortByDistance(currentLocation));
            requestAdapter.notifyDataSetChanged();
        }
    }

}
