package com.isil.grgrou.hoop_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.isil.grgrou.hoop_app.entity.Notification;
import com.isil.grgrou.hoop_app.util.Const;
import com.isil.grgrou.hoop_app.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    private String userId;

    private ListView listNotification;
    private ArrayList<Notification> notifications = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Get User Id
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        // Connect to DB
        databaseReference = FirebaseDatabase.getInstance().getReference(Const.USER_DATABASE_PATH);

        // Read From the Database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Create Notifications Array
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (userId.equals(data.child("receiver_id").getValue())) {
                        // New Notification
                        Notification notification = new Notification();

                        // Set data
                        String status = (String) data.child("status").getValue();
                        String username = (String) data.child("username").getValue();

                        notification.setMsg(status, username);

                        // Add to Notification List
                        notifications.add(notification);
                    }
                }

                // Show Notifications in ListView
                //listNotification = findViewById(R.id.);
                AdapterNotification adapter = new AdapterNotification(getApplicationContext(), notifications);
                listNotification.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void backActivity(View view) {
        finish();
    }
}