package com.example.locomotioncommotion.activities.shared;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.locomotioncommotion.R;
import com.example.locomotioncommotion.activities.driver.DriverRequestsAll;
import com.example.locomotioncommotion.activities.loginRegistration.MainActivity;
import com.example.locomotioncommotion.activities.rider.RiderMain;
import com.example.locomotioncommotion.model.CurrentUser;
import com.example.locomotioncommotion.model.Driver;
import com.example.locomotioncommotion.model.Rider;

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
    private Button logoutButton;


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

        logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutClick(v);
            }
        });

    }

    public void riderClick(View view){
        Intent intent = new Intent(this, RiderMain.class);
        assert(CurrentUser.getInstance() != null);
        if(CurrentUser.getInstance().getUser().getRider() == null){
            Rider rider = new Rider();
            CurrentUser.getInstance().getUser().setRider(rider);
            CurrentUser.getInstance().getUser().updateDatabase();
        }
        startActivity(intent);
    }

    public void driverClick(View view) {
        Intent intent = new Intent(this, DriverRequestsAll.class );
        startActivity(intent);
        if(CurrentUser.getInstance().getUser().getDriver() == null){
            Driver driver = new Driver();
            CurrentUser.getInstance().getUser().setDriver(driver);
            CurrentUser.getInstance().getUser().updateDatabase();
        }
    }

    public void profileClick(View view) {
        Intent intent = new Intent(this,UserProfile.class );
        startActivity(intent);
    }

    public void logoutClick(View view) {
        CurrentUser.clearUser();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
