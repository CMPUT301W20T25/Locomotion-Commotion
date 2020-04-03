package com.example.locomotioncommotion.activities.rider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.locomotioncommotion.R;
import com.example.locomotioncommotion.activities.rider.RiderMain;
import com.example.locomotioncommotion.model.CurrentUser;
import com.example.locomotioncommotion.model.User;

public class ThumbRating extends AppCompatActivity {

    Button rateUp;
    Button rateDown;
    User driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumb_rating);

        driver = CurrentUser.getInstance().getUser();
        rateUp = findViewById(R.id.thumbsUp);
        rateDown = findViewById(R.id.thumbsDown);

        rateUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current;
                current = driver.getThumbsUp();
                current += 1;
                driver.setThumbsUp(current);

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
