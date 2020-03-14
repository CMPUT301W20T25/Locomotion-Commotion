package com.example.locomotioncommotion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RiderMain extends AppCompatActivity {
    ListView requestList;
    ArrayAdapter<Request> requestArrayAdapter;
    ArrayList<Request> requestDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rider);

        requestList = findViewById(R.id.activeRequests);

        requestDataList = new ArrayList<>();

        requestArrayAdapter = new RequestList(this, requestDataList);
        //show the data on this layout??
        requestList.setAdapter(requestArrayAdapter);
    }

    public void create(View view){
        Intent intent = new Intent(this, createRequest.class);
        startActivity(intent);
    }


}
