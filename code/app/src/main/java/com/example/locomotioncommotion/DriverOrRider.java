package com.example.locomotioncommotion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

class DriverOrRider extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rider_or_driver);
    }

    public void riderClick(View view){
        Intent intent = new Intent(this, Rider.class);
        startActivity(intent);
    }

}
