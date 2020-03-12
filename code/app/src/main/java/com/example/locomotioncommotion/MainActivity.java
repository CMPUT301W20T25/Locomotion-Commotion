package com.example.locomotioncommotion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText userNameField;
    private EditText passWordField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmLogin(v);
            }
        });
        userNameField = findViewById(R.id.username);
        passWordField = findViewById(R.id.password);

    }

    public void confirmLogin(View view){
        //TODO: Check for whether password is a match
        String userName = userNameField.getText().toString();
        String passWord = passWordField.getText().toString();
        User.getInstance(userName, passWord); //TODO: Make sure this doesn't break logging out and then logging back in!
        Intent intent = new Intent(this, DriverOrRider.class);
        startActivity(intent);
    }
}
