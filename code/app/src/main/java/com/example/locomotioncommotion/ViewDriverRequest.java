package com.example.locomotioncommotion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        TextView price = findViewById(R.id.view_driver_request_price);
        Button username = findViewById(R.id.view_driver_request_username);

        int dollars = request.getFareOffered() / 100;
        int cents = request.getFareOffered() % 100;




        price.setText(String.format("%d.%02d", dollars, cents));
        startLocation.setText(request.getStartLocation().getName());
        endLocation.setText(request.getEndLocation().getName());
        username.setText(request.getRiderUsername());




    }

    public void clickUserName(View view) {
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
    }

    public void backButton(View view) {
        finish();
    }
}
