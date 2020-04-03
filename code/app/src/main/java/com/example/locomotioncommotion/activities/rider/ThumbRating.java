package com.example.locomotioncommotion.activities.rider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.locomotioncommotion.R;
import com.example.locomotioncommotion.activities.rider.RiderMain;
import com.example.locomotioncommotion.model.CurrentUser;
import com.example.locomotioncommotion.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ThumbRating extends AppCompatActivity {

    Button rateUp;
    Button rateDown;
    User driver;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumb_rating);
        Intent intent = getIntent();
        String userName = intent.getExtras().getString("username");
        db = FirebaseFirestore.getInstance();

        db.collection("Users").whereEqualTo("userName", userName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                driver = document.toObject(User.class);
                            }
                        } else {
                            Log.d("TAG", "Error getting document");
                        }
                    }
                });
        rateUp = findViewById(R.id.thumbsUp);
        rateDown = findViewById(R.id.thumbsDown);

        rateUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current;
                current = driver.getThumbsUp();
                current += 1;
                driver.setThumbsUp(current);
                driver.updateDatabase();
                riderClick(v);
            }
        });

        rateDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current;
                current = driver.getThumbsDown();
                current += 1;
                driver.setThumbsUp(current);
                driver.updateDatabase();
                riderClick(v);
            }
        });

    }
    //return back to main Rider page
    public void riderClick(View view){
        Intent intent = new Intent(this, RiderMain.class);
        startActivity(intent);
    }
}
