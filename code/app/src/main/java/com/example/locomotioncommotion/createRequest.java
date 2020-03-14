package com.example.locomotioncommotion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class createRequest extends AppCompatActivity {
    ArrayList<Request> requestDataList;
    ArrayAdapter<Request> requestArrayAdapter;

    private EditText starting;
    private EditText ending;

    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_request);
    }
    //should get the values and put them into proper place
    public void View(View v){
        starting = v.findViewById(R.id.start_edit_create);
        ending = v.findViewById(R.id.end_edit_create);

        String getStart = starting.getText().toString();
        String getEnd = ending.getText().toString();
        requestDataList = new ArrayList<>();
        requestDataList.add(new Request(null, getStart, getEnd, 0));
        requestArrayAdapter = new RequestList(this, requestDataList);

    }
    public void create(View view){
        Intent intent = new Intent(this, RiderMain.class);
        startActivity(intent);
    }



}
