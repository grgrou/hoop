package com.isil.grgrou.hoop_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.isil.grgrou.hoop_app.entity.User;
import com.isil.grgrou.hoop_app.util.Const;
import com.isil.grgrou.hoop_app.util.Util;

import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get User Object
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("userObj");
        databaseReference = FirebaseDatabase.getInstance().getReference(Const.USER_DATABASE_PATH);
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

    // Upload image
    public void uploadImage(View view) {
        RelativeLayout parentLayout = findViewById(R.id.parentLayout);
        getLayoutInflater().inflate(R.layout.activity_upload_image, parentLayout);
    }
}
