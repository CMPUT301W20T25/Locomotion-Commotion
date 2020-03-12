package com.example.locomotioncommotion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {

    private TextView thumbsDown;
    private TextView thumbsUp;
    private TextView name;
    private TextView email;
    private TextView phoneNumber;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user = User.getInstance(); //TODO: Make sure that it's impossible for no userInstance to exist when this line runs

        thumbsDown = findViewById(R.id.user_profile_thumbs_down2);
        thumbsUp = findViewById(R.id.user_profile_thumbs_up2);
        name = findViewById(R.id.user_profile_name2);
        email = findViewById(R.id.user_profile_email2);
        phoneNumber = findViewById(R.id.user_profile_phone_number2);

        Driver driver = user.getDriver();
        if(driver != null){
            thumbsDown.setText(user.getThumbsDown());
            thumbsUp.setText(user.getThumbsUp());
        } else{
            thumbsDown.setVisibility(View.INVISIBLE);
            thumbsUp.setVisibility(View.INVISIBLE);
        }

        name.setText(user.getUserName());
        email.setText(user.getEmail());
        phoneNumber.setText(user.getPhoneNumber());

    }

    public void backButton (View view) {
        finish();
    }
}
