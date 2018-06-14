package com.isil.grgrou.hoop_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isil.grgrou.hoop_app.entity.ListUsers;
import com.isil.grgrou.hoop_app.entity.User;
import com.isil.grgrou.hoop_app.util.Const;
import com.isil.grgrou.hoop_app.util.Util;

import java.util.ArrayList;

public class FindFriendsActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private String userId;

    private ListView listUsers;
    private ArrayList<ListUsers> users = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        // Get User Id
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        // Show Users List
        databaseReference = FirebaseDatabase.getInstance().getReference(Const.USER_DATABASE_PATH);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (!userId.equals(data.getKey())) {
                        // Save User in List
                        ListUsers listUsers = new ListUsers();
                        listUsers.setId(data.child("id").getValue().toString());
                        listUsers.setUrl(data.child("url").getValue().toString());
                        listUsers.setUsername(data.child("username").getValue().toString());

                        if (data.child("followers").exists()) {
                            if (data.child("followers").hasChild(userId)) {
                                listUsers.setFollow(true);
                            } else {
                                listUsers.setFollow(false);
                            }
                        } else {
                            listUsers.setFollow(false);
                        }

                        users.add(listUsers);
                    }
                }
                // Show Users in ListView
                listUsers = findViewById(R.id.listUsers);
                AdapterListUsers adapter = new AdapterListUsers(getApplicationContext(), users, userId);
                listUsers.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });


    }

    public void done(View view) {
        finish();
    }
}
