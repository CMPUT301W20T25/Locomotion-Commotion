package com.example.locomotioncommotion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {


    private TextView thumbsDown1;
    private TextView thumbsDown2;
    private TextView thumbsUp1;
    private TextView thumbsUp2;
    private TextView name;
    private TextView email;
    private TextView phoneNumber;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);



        user = User.getInstance(); //TODO: Make sure that it's impossible for no userInstance to exist when this line runs

        thumbsDown1 = findViewById(R.id.user_profile_thumbs_down1);
        thumbsDown2 = findViewById(R.id.user_profile_thumbs_down2);
        thumbsUp1 = findViewById(R.id.user_profile_thumbs_up1);
        thumbsUp2 = findViewById(R.id.user_profile_thumbs_up2);
        name = findViewById(R.id.user_profile_name2);
        email = findViewById(R.id.user_profile_email2);
        phoneNumber = findViewById(R.id.user_profile_phone_number2);

        int posRating = user.getThumbsUp();
        if(posRating != -1){
            thumbsDown2.setText(Integer.toString(user.getThumbsDown()));
            thumbsUp2.setText(Integer.toString(user.getThumbsUp()));
        } else{
            thumbsDown1.setVisibility(View.INVISIBLE);
            thumbsUp1.setVisibility(View.INVISIBLE);
            thumbsDown2.setVisibility(View.INVISIBLE);
            thumbsUp2.setVisibility(View.INVISIBLE);
        }

        name.setText(user.getUserName());
        email.setText(user.getEmail());
        phoneNumber.setText(user.getPhoneNumber());

    }


    public void backButton (View view) {
        finish();
    }
}
