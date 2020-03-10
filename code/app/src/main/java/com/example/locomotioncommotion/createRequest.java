package com.example.locomotioncommotion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class createRequest extends AppCompatActivity implements confirmRequestFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_request);

        final FloatingActionButton confirmButton = findViewById(R.id.nextToConfirm);
        confirmButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                new confirmRequestFragment().show(getSupportFragmentManager(),"Confirm Request");
            }
        });
    }

    @Override
    public void onOkPressed(){

    }








}
