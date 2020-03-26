package com.example.locomotioncommotion;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Controller to allow the person to register an account
 */
public class Registration extends AppCompatActivity {
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
    }

    /**
     * Creates an account and uploads it to the firebase
     * @param view
     *      the current view
     */
    public void createAccount(View view){
        EditText userName = findViewById(R.id.registration_username);
        EditText password = findViewById(R.id.registration_password);
        EditText email = findViewById(R.id.registration_email);

        String userString = userName.getText().toString();
        String passString = password.getText().toString();
        String emailString = email.getText().toString();

        boolean valid = true;

        if(userString.equals("")){
            userName.setError("Required");
            valid = false;
        }
        if(passString.equals("")){
            password.setError("Required");
            valid = false;
        }
        if(emailString.equals("")){
            email.setError("Required");
            valid = false;
        }

        if(!valid){
            return;
        }

        User user = new User(userString, passString);
        user.setEmail(emailString);
        db = FirebaseFirestore.getInstance();
        db.collection("Users").document(user.getUserName()).set(user);
        finish();
    }
}
