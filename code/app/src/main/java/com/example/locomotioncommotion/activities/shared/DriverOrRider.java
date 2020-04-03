package com.example.locomotioncommotion.activities.shared;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.locomotioncommotion.R;
import com.example.locomotioncommotion.activities.driver.DriverRequestsAll;
import com.example.locomotioncommotion.activities.rider.RiderMain;
import com.example.locomotioncommotion.model.CurrentUser;

/**
 * DriverOrRider
 *
 * Class associated with rider_or_driver.xml
 * chooses between different modes for the user
 */
public class DriverOrRider extends AppCompatActivity {
    private Button riderSide;
    private Button driverSide;
    private Button profileButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rider_or_driver);

        riderSide = findViewById(R.id.rider);
        riderSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riderClick(v);
            }
        });

        driverSide = findViewById(R.id.driver);
        driverSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverClick(v);
            }
        });

        profileButton = findViewById(R.id.profile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileClick(v);
            }
        });

    }

    public void riderClick(View view){
        Intent intent = new Intent(this, RiderMain.class);
        assert(CurrentUser.getInstance() != null);
        startActivity(intent);
    }

    public void driverClick(View view) {
        Intent intent = new Intent(this, DriverRequestsAll.class );
        startActivity(intent);
    }

    public void profileClick(View view) {
        Intent intent = new Intent(this,UserProfile.class );
        startActivity(intent);
    }

}
