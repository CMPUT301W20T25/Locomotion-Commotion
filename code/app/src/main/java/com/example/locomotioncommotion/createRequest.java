package com.example.locomotioncommotion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Activity for creating a request, as a rider.
 * Currently just a stub/placeholder
 */
public class createRequest extends AppCompatActivity {
    ArrayList<Request> requestDataList;
    private String starting;
    private String ending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_request);
        starting = findViewById(R.id.start_edit_create).toString();
        ending = findViewById(R.id.end_edit_create).toString();


    }



}
