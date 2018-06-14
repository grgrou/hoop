package com.isil.grgrou.hoop_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isil.grgrou.hoop_app.entity.User;
import com.isil.grgrou.hoop_app.util.Const;
import com.isil.grgrou.hoop_app.util.Util;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private User user;

    private TextView txtName, txtUsername, txtFollowers, txtFollowing;
    private ImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Get User Object
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("userObj");

        txtName = findViewById(R.id.txtName);
        txtUsername = findViewById(R.id.txtUsername);
        txtFollowers = findViewById(R.id.txtFollowers);
        txtFollowing = findViewById(R.id.txtFollowing);
        imgProfile = findViewById(R.id.imgProfile);

        databaseReference = FirebaseDatabase.getInstance().getReference(Const.USER_DATABASE_PATH).child(user.getId());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("followers").exists()) {
                    HashMap<String, Boolean> followers = (HashMap<String, Boolean>) dataSnapshot.child("followers").getValue();
                    user.setFollowers(followers);
                    txtFollowers.setText(user.getFollowers().size()+"");
                }

                if (dataSnapshot.child("following").exists()) {
                    HashMap<String, Boolean> following = (HashMap<String, Boolean>) dataSnapshot.child("following").getValue();
                    user.setFollowing(following);
                    txtFollowing.setText(user.getFollowing().size()+"");
                }

                txtName.setText(user.getName());
                txtUsername.setText(user.getUsername());
                Util.setImageWithURL(user.getUrl(), imgProfile);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    public void back(View view) {
        finish();
    }

    public void options(View view) {

    }
}
