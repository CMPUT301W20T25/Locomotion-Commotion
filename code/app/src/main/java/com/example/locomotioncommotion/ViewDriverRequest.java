package com.example.locomotioncommotion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewDriverRequest extends AppCompatActivity {
    Request request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_driver_request);

        Intent intent = getIntent();
        request = (Request) intent.getExtras().getSerializable(RequestFinderList.REQUEST_MANAGE_MESSAGE);

        TextView startLocation = findViewById(R.id.view_driver_request_start_location);
        TextView endLocation = findViewById(R.id.view_driver_request_end_location);



        startLocation.setText(request.getStartLocation().getName());
        endLocation.setText(request.getEndLocation().getName());


    }

    public void clickUserName(View view) {
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
    }

    public void backButton(View view) {
        finish();
    }
}
