package com.example.locomotioncommotion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * Activity for creating a request, as a rider.
 * Currently just a stub/placeholder
 */
public class createRequest extends AppCompatActivity {
    ArrayList<Request> requestDataList;
    ArrayAdapter<Request> requestArrayAdapter;

    private String TAG = "Request_create";
    private EditText starting;
    private EditText ending;
    private Button createButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_request);

        starting = findViewById(R.id.start_edit_create);
        ending = findViewById(R.id.end_edit_create);
        createButton = findViewById(R.id.create_request_button);

        db = FirebaseFirestore.getInstance();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String start = starting.getText().toString();
                final String end = ending.getText().toString();

                Request request = new Request(CurrentUser.getInstance().getUser().getUserName(), start, end, 0);
//                Request request = User.getInstance().getRider().createRequest(start, end, 0);

//                DocumentReference newCityRef = db.collection("requests").document();

                db.collection("requests")
                        .add(request)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                       });

                finish();
                //db = FirebaseFirestore.getInstance();
               // db.collection("Users").document(user.getUserName()).set(user);
            }
        });
    }
}
