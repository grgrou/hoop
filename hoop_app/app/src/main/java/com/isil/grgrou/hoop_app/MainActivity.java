package com.isil.grgrou.hoop_app;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.daimajia.androidanimations.library.Techniques;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isil.grgrou.hoop_app.entity.User;
import com.isil.grgrou.hoop_app.util.Const;
import com.isil.grgrou.hoop_app.util.Util;
import com.tapadoo.alerter.Alerter;



public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private User user;

    private Button btnNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get User Object
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("userObj");
        databaseReference = FirebaseDatabase.getInstance().getReference(Const.USER_DATABASE_PATH);

        btnNotification = findViewById(R.id.btnNotifications);

        databaseReference.child(user.getId()).child("notifications").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // Shake notification icon
                Util.animate(Techniques.Wobble, btnNotification, 500);

                // Display Notification
                com.isil.grgrou.hoop_app.entity.Notification notification = new com.isil.grgrou.hoop_app.entity.Notification();
                notification.setType(Integer.parseInt(dataSnapshot.child("type").getValue().toString()));
                notification.setUsername(dataSnapshot.child("username").getValue().toString());
                notification.setUrl(dataSnapshot.child("url").getValue().toString());
                notification.setMsg();

                NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify=new Notification.Builder
                        (getApplicationContext()).setContentTitle("Hoop app").setContentText(notification.getMsg()).
                        setContentTitle("Hoop app").setSmallIcon(R.drawable.ic_launcher_background).build();

                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notif.notify(0, notify);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void notifications(View view) {
        Intent intent = new Intent(getApplicationContext(), NotificationsActivity.class);
        intent.putExtra("userId", user.getId());
        startActivity(intent);
    }

    public void profile(View view) {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        intent.putExtra("userObj", user);
        startActivity(intent);
    }

    public void findFriends(View view) {
        Intent intent = new Intent(getApplicationContext(), FindFriendsActivity.class);
        intent.putExtra("userId", user.getId());
        startActivity(intent);
    }
}
