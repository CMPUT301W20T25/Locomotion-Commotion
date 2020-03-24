package com.example.locomotioncommotion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DriverRequestsAll extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_requests_all);

        //User user = User.getInstance();
       // if(user.getDriver().requestStatus() == null) {
            //hide stuff
        //} else {
         //   TextView pickUp = findViewById(R.id.driver_requests_all_dropoff);
         //   pickUp.setText(user.getDriver().getCurrentRequest().getStartLocation());
        //}
    }

    public void goToRequestFinder(View view) {
        Intent intent = new Intent(this,RequestFinderList.class);
        startActivity(intent);

    }

    public void backButton(View view) {
        finish();
    }
}
