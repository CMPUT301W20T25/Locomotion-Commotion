package com.example.locomotioncommotion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        if(CurrentUser.getInstance().getUser().getRider() == null){
            Rider rider = new Rider();
            CurrentUser.getInstance().getUser().setRider(rider);
            CurrentUser.getInstance().getUser().updateDatabase();
        }
        startActivity(intent);
    }

    public void driverClick(View view) {
        Intent intent = new Intent(this,DriverRequestsAll.class );
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

}
