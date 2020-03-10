package com.example.locomotioncommotion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DriverOrRider extends AppCompatActivity {
    private Button riderSide;

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
    }

    public void riderClick(View view){
        Intent intent = new Intent(this, RiderMain.class);
        startActivity(intent);
    }

    public void driverClick(View view) {
        Intent intent = new Intent(this,DriverRequestsAll.class );
        startActivity(intent);
    }

}
