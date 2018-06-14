package com.isil.grgrou.hoop_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.isil.grgrou.hoop_app.entity.User;
import com.tapadoo.alerter.Alerter;

public class MainActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get User Object
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("userObj");

        Alerter.create(this)
                .setTitle("Notificación")
                .setText("Esto es una notificación")
                .show();
    }

    public void notifications(View view) {
        /*Intent intent = new Intent(getApplicationContext(), NotificationsActivity.class);
        intent.putExtra("userId", user.getId());
        startActivity(intent);*/
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
