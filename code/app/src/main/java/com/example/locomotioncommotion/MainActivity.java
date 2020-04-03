package com.example.locomotioncommotion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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

        developNotificationInfrastructure(getApplicationContext());

        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Users");

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if(CurrentUser.getInstance() != null) {
                        if(CurrentUser.getInstance().getUser().getUserName().equals(doc.getId())){
                            CurrentUser.getInstance().setUser(doc.toObject(User.class));
                            String notification = CurrentUser.getInstance().getUser().getNotification();
                            if(notification.equals("No notifications pending") == false){
                                Context context = getApplicationContext();
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "LocomotionCommotion")
                                        .setSmallIcon(R.drawable.notification_icon)
                                        .setContentTitle(notification)
                                        .setContentText(notification)
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                        .setAutoCancel(true); // Just make the notification go away when you tap it for now

                                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                                // notificationId is a unique int for each notification that you must define
                                notificationManager.notify(1, builder.build());
                                CurrentUser.getInstance().getUser().setNotification("");
                                CurrentUser.getInstance().getUser().updateDatabase(); //If I've done this right then this won't cause an infinite loop
                            }
                        }
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
                                    completeLoginClick();
                                    Intent intent = new Intent(MainActivity.this, DriverOrRider.class);
                                    //startActivity(intent);
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

    public void completeLoginClick(){
        String notification = CurrentUser.getInstance().getUser().getNotification();
        String channelID = "LocomotionCommotion";
        String channelName = "Locomotion Request Channel";
        if(notification.equals("No notifications pending") == false){
            Context context = getApplicationContext();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle(notification)
                    .setContentText(notification)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true); // Just make the notification go away when you tap it for now

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
            //NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel notificationChannel = new NotificationChannel(channelID, channelName, importance);
            }
            //If the API is below 26 then notifications don't need a channel, happy ending
            //if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ builder.setChannelId("LocomotionCommotion"); }

            //NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            //NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(13, builder.build());
            CurrentUser.getInstance().getUser().setNotification("");
            CurrentUser.getInstance().getUser().updateDatabase(); //If I've done this right then this won't cause an infinite loop
        }
    }

    public void register(View view){
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

    public void developNotificationInfrastructure(Context context){

    }

}
