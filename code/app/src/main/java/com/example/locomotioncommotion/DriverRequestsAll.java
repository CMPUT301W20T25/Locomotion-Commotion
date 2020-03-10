package com.example.locomotioncommotion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DriverRequestsAll extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_requests_all);
    }

    public void goToRequestFinder(View view) {
        Intent intent = new Intent(this,DriverRequestsAll.class);
        startActivity(intent);

    }

    public void backButton(View view) {
        finish();
    }
}
