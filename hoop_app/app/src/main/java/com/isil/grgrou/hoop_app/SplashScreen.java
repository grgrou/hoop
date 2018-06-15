package com.isil.grgrou.hoop_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isil.grgrou.hoop_app.entity.User;
import com.isil.grgrou.hoop_app.util.Const;
import com.isil.grgrou.hoop_app.util.Util;

public class SplashScreen extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        databaseReference = FirebaseDatabase.getInstance().getReference(Const.USER_DATABASE_PATH);

        SharedPreferences prefs = getSharedPreferences(Const.USER_DEFAULTS, MODE_PRIVATE);
        final String user_username = prefs.getString("USERNAME", null);
        final String user_password = prefs.getString("PASSWORD", null);

        if (user_username != null && user_password != null) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (user_username.equals(data.child("username").getValue()) && user_password.equals(data.child("password").getValue())) {
                            // Successful Login
                            User user = new User();

                            // Set Data
                            user.setId(data.getKey());
                            user.setUrl(data.child("url").getValue().toString());
                            user.setName(data.child("name").getValue().toString());
                            user.setUsername(data.child("username").getValue().toString());
                            user.setPassword(data.child("password").getValue().toString());

                            // Change Activity
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("userObj", user);
                            startActivity(intent);
                            finish();
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Util.toast(getApplicationContext(), "Problemas de conexión\nInténtelo más tarde.");
                }
            });
        } else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
    }
}
