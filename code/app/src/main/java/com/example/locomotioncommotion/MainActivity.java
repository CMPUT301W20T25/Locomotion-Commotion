package com.example.locomotioncommotion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private Button registerButton;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmLogin(v);
            }
        });

        registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
            }
        });
    }

    public void confirmLogin(View view){
        EditText userName = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        User user = new User(userName.getText().toString(), password.getText().toString());
        Intent intent = new Intent(this, DriverOrRider.class);
        startActivity(intent);
    }

    public void register(View view){
        Intent intent = new Intent(this, Registration.class);        EditText userName = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        User user = new User(userName.getText().toString(), password.getText().toString());
        db = FirebaseFirestore.getInstance();
        //final CollectionReference collectionReference = db.collection("Users");
        db.collection("Users").document(user.getUserName()).set(user);

        startActivity(intent);
    }
}
