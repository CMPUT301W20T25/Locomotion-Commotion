package com.example.locomotioncommotion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DriverRequestsAll extends AppCompatActivity {
    private Button scanCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_requests_all);



        scanCode = findViewById(R.id.driver_requests_all_scan);
        scanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                scanQR(v);
            }
        });
    }

    public void goToRequestFinder(View view) {
        Intent intent = new Intent(this,RequestFinderList.class);
        startActivity(intent);

    }
    public void scanQR(View view){
        Intent intent = new Intent(this, QRCodeScanner.class);
        startActivity(intent);
    }

    public void backButton(View view) {
        finish();
    }
}
