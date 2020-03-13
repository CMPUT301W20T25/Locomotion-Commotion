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
    private EditText userNameField;
    private EditText passWordField;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameField = findViewById(R.id.username);
        passWordField = findViewById(R.id.password);

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
        //TODO: Check for whether password is a match
        String userName = userNameField.getText().toString();
        String passWord = passWordField.getText().toString();
        User.getInstance(userName, passWord); //TODO: Make sure this doesn't break logging out and then logging back in!
        Intent intent = new Intent(this, DriverOrRider.class);
        startActivity(intent);
    }

    public void register(View view){
        Intent intent = new Intent(this, Registration.class);
        /*
        EditText userName = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        User user = User.getInstance(userName.getText().toString(), password.getText().toString());
        db = FirebaseFirestore.getInstance();
        //final CollectionReference collectionReference = db.collection("Users");
        db.collection("Users").document(user.getUserName()).set(user);
        */
        startActivity(intent);
    }
}
