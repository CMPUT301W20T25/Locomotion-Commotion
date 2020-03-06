package com.example.locomotioncommotion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Rider extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rider);
    }

    public void create(View view){
        Intent intent = new Intent(this, createRequest.class);
        startActivity(intent);
    }

}
