package com.example.locomotioncommotion.activities.driver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.locomotioncommotion.R;
import com.example.locomotioncommotion.model.CurrentUser;
import com.example.locomotioncommotion.model.Request;
import com.example.locomotioncommotion.model.RequestList;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Page to see the current request the driver has open, find new requests and scan a QR code.
 * Can click on the current request to view details
 *
 */
public class DriverRequestsAll extends AppCompatActivity {
    public static final String REQUEST_MANAGE_MESSAGE = "com.example.locomotioncommotion.MANAGE_REQUEST";
    private Button scanCode;
    private FirebaseFirestore db;
    ListView requestList;
    ArrayAdapter<Request> requestAdapter;
    ArrayList<Request> requestDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_requests_all);

        requestList = findViewById(R.id.driver_requests_list);

        requestDataList = new ArrayList<>();

        requestAdapter = new RequestList(this, requestDataList);
        requestList.setAdapter(requestAdapter);




        scanCode = findViewById(R.id.driver_requests_all_scan);
        scanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                scanQR(v);
            }
        });

        //gets the current active request(s)
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("requests");

        collectionReference
                .whereEqualTo("driverUsername", CurrentUser.getInstance().getUser().getUserName())
               .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        requestDataList.clear();
                      for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            Request request = doc.toObject(Request.class);
                            if (request.getDriverUsername().equals(CurrentUser.getInstance().getUser().getUserName())) {
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
                viewRequest(view);
            }
        });
    }

    public void goToRequestFinder(View view) {
        Intent intent = new Intent(this, RequestFinderList.class);
        startActivity(intent);

    }
    public void scanQR(View view){
        Intent intent = new Intent(this, QRCodeScanner.class);
        startActivity(intent);
    }
    /**
    Once user clicks the active request this gets the request and moves to the next class
     */
    public void viewRequest(View view) {
        Intent intent = new Intent(getApplicationContext(), ViewDriverRequest.class);
        Request request = CurrentUser.getInstance().getUser().getDriver().getCurrentRequest();
        intent.putExtra(REQUEST_MANAGE_MESSAGE, request);
        startActivity(intent);
    }

    public void backButton(View view) {
        finish();
    }
}
