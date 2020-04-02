package com.example.locomotioncommotion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * The starting screen and main activity
 * Prompts the user to log in or register
 */

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

        developNotificationInfrastructure(this);

        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("user");

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                //requestDataList.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if(CurrentUser.getInstance() != null) {

                    }
                }
            }
        });
    }

    public void confirmLogin(View view){
        String userName = userNameField.getText().toString();
         final String passWord = passWordField.getText().toString();

        if(userName.equals("")){
            userNameField.setError("Required");
            return;
        }

        db = FirebaseFirestore.getInstance();

        db.collection("Users").whereEqualTo("userName", userName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                Log.d("TEST", user.getUserName());
                                if (passWord.equals(user.getPassWord())) {
                                    CurrentUser.getInstance(user);
                                    Intent intent = new Intent(MainActivity.this, DriverOrRider.class);
                                    startActivity(intent);
                                } else {
                                    passWordField.setError("Wrong password");
                                }

                            }
                        } else {
                            Log.d("TAG", "Eroor getting document");
                            userNameField.setError("Username not registered");
                        }
                    }
                });



    }
    public void login() {
        Intent intent = new Intent(this, DriverOrRider.class);
        startActivity(intent);
    }

    public void register(View view){
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

    public void developNotificationInfrastructure(Context context){
        String channelID = "LocomotionCommotion";
        String channelName = "Locomotion Request Channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelID, channelName, importance);
        }
        //If the API is below 26 then notifications don't need a channel, happy ending
    }

}
