package com.example.locomotioncommotion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Activity to handle viewing and changing user attributes like contact information, ratings and username
 */
public class UserProfile extends AppCompatActivity {


    private TextView thumbsDown1;
    private TextView thumbsDown2;
    private TextView thumbsUp1;
    private TextView thumbsUp2;
    private TextView name;
    private TextView email;
    private TextView phoneNumber;
    private EditText phoneNumberField;
    private EditText emailField;
    private Button emailChangeButton;
    private Button phoneChangeButton;
    private User user;

    /**
     * Creates the class and handles references to and visibility of the views in the activity
     * @param savedInstanceState
     *      The saved instance of the previous activity, if any
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user = CurrentUser.getInstance().getUser(); //TODO: Make sure that it's impossible for no currentUserInstance to exist when this line runs

        thumbsDown1 = findViewById(R.id.user_profile_thumbs_down1);
        thumbsDown2 = findViewById(R.id.user_profile_thumbs_down2);
        thumbsUp1 = findViewById(R.id.user_profile_thumbs_up1);
        thumbsUp2 = findViewById(R.id.user_profile_thumbs_up2);
        name = findViewById(R.id.user_profile_name2);
        email = findViewById(R.id.user_profile_email2);
        phoneNumber = findViewById(R.id.user_profile_phone_number2);
        phoneNumberField = findViewById(R.id.user_profile_phone_number_edit);
        emailField = findViewById(R.id.user_profile_email_edit);

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

        emailChangeButton = findViewById(R.id.user_profile_email_button);
        emailChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEmail(v);
            }
        });

        phoneChangeButton = findViewById(R.id.user_profile_phone_button);
        phoneChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePhone(v);
            }
        });
    }

    /**
     * Function for the button that lets the user apply a chosen new email address to their account
     * @param view
     *      This is the button that calls this function
     */
    public void changeEmail(View view){
        String newAddress = emailField.getText().toString();

        if(newAddress.equals("")){
            emailField.setError("Required");
            return;
        }

        user = CurrentUser.getInstance().getUser();
        email.setText(newAddress);
        emailField.setText("");
        user.setEmail(newAddress);
    }

    /**
     * Function for the button that lets the user apply a chosen new phone number to their accoumt
     * @param view
     *      This is the button that calls this function
     */
    public void changePhone(View view){
        String newPhone = phoneNumberField.getText().toString();

        if(newPhone.equals("")){
            phoneNumberField.setError("Required");
            return;
        }

        user = CurrentUser.getInstance().getUser();
        phoneNumber.setText(newPhone);
        phoneNumberField.setText("");
        user.setPhoneNumber(newPhone);
    }

    /**
     *  Function for the back button, that ends the user profile activity and returns them to the previous activity
     * @param view
     *      This is the button that calls this function
     */
    public void backButton (View view) {
        finish();
    }
}
