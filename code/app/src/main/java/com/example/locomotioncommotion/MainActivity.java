package com.example.locomotioncommotion;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        final CollectionReference collectionReference = db.collection("requests");

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                //requestDataList.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    String riderUserName = (String) doc.getData().get("riderUsername");
                    String driverUserName = (String) doc.getData().get("driverUsername");
                    if(CurrentUser.getInstance() != null) {
                        String currentUserName = CurrentUser.getInstance().getUser().getUserName();
                        DocumentReference docRef = null;
                        boolean isRider = false;
                        if (currentUserName.equals(riderUserName)) {
                            docRef = doc.getDocumentReference("riderNotificationIsPending");
                            isRider = true;
                        } else if (currentUserName.equals(driverUserName)) {//Reasonable assumption: The rider and driver for a request should be different people
                            docRef = doc.getDocumentReference("driverNotificationIsPending");
                        }
                        if(docRef != null && docRef.equals(true)){
                                String notificationTitle = "Your request has been ";
                                String requestStatus = (String) doc.getData().get("status");
                                notificationTitle = notificationTitle.concat(requestStatus);
                                Context context = getApplicationContext();
                                if(isRider) {
                                    CurrentUser.getInstance().getUser().getRider().getCurrentRequest().notifyRider(notificationTitle,notificationTitle, context);
                                } else {
                                    CurrentUser.getInstance().getUser().getDriver().getCurrentRequest().notifyDriver(notificationTitle,notificationTitle, context);
                                }
                                docRef.set(false);
                            }
                            //requestDataList.add(new Request(start,end));

                        }
                    }
                }
        });
    }

    public void confirmLogin(View view){
        String userName = userNameField.getText().toString();
        String passWord = passWordField.getText().toString();

        if(userName.equals("")){
            userNameField.setError("Required");
            return;
        }

        db = FirebaseFirestore.getInstance();

        User newUser = new User(userName, passWord);
        CurrentUser.getInstance(newUser); //TODO: Make sure this doesn't break logging out and then logging back in!
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
