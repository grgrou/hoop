package com.isil.grgrou.hoop_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isil.grgrou.hoop_app.entity.ListUsers;
import com.isil.grgrou.hoop_app.util.Const;
import com.isil.grgrou.hoop_app.util.Util;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alumno-CT on 04/05/2018.
 */

public class AdapterListUsers extends BaseAdapter {
    private DatabaseReference databaseReference;

    private Context context;
    private ArrayList<ListUsers> items;
    private String userId;

    public AdapterListUsers(Context context, ArrayList<ListUsers> items, String userId) {
        this.context = context;
        this.items = items;
        this.userId = userId;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        TextView txtUsername;
        ImageView imgUser;
        final ImageButton btnFollow;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        final View itemView = inflater.inflate(R.layout.item_list_users, parent, false);

        txtUsername = itemView.findViewById(R.id.txtUsername);
        imgUser = itemView.findViewById(R.id.imgUser);
        btnFollow = itemView.findViewById(R.id.btnFollow);

        txtUsername.setText(items.get(position).getUsername());
        Util.setImageWithURL(items.get(position).getUrl(), imgUser);

        if (items.get(position).getFollow()) {
            btnFollow.setBackgroundResource(R.drawable.follow_btn_check);
        } else {
            btnFollow.setBackgroundResource(R.drawable.follow_btn_uncheck);
        }

        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference(Const.USER_DATABASE_PATH);
                HashMap<String, Object> followersMap = new HashMap<>();
                HashMap<String, Object> followingMap = new HashMap<>();

                if (items.get(position).getFollow()) {
                    // Unfollow
                    databaseReference.child(userId).child("following").child(items.get(position).getId()).removeValue();
                    databaseReference.child(items.get(position).getId()).child("followers").child(userId).removeValue();

                    items.get(position).setFollow(false);
                    btnFollow.setBackgroundResource(R.drawable.follow_btn_uncheck);
                } else {
                    // Follow
                    followersMap.put(userId, true);
                    followingMap.put(items.get(position).getId(), true);

                    databaseReference.child(userId).child("following").updateChildren(followingMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) { }
                    });
                    databaseReference.child(items.get(position).getId()).child("followers").updateChildren(followersMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference dbRef) {
                            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // Create Notification
                                    HashMap<String, Object> notificationMap = new HashMap<>();
                                    notificationMap.put("username", dataSnapshot.child("username").getValue().toString());
                                    notificationMap.put("url", dataSnapshot.child("url").getValue().toString());
                                    notificationMap.put("type", 0);

                                    String pushKey = databaseReference.child(items.get(position).getId()).child("notifications").push().getKey();
                                    databaseReference.child(items.get(position).getId()).child("notifications").child(pushKey).setValue(notificationMap);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {}
                            });
                        }
                    });

                    items.get(position).setFollow(true);
                    btnFollow.setBackgroundResource(R.drawable.follow_btn_check);
                }
            }
        });

        return itemView;
    }
}