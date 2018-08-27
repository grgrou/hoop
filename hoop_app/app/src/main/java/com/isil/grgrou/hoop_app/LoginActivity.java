package com.isil.grgrou.hoop_app;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isil.grgrou.hoop_app.entity.User;
import com.isil.grgrou.hoop_app.util.Const;
import com.isil.grgrou.hoop_app.util.Util;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private EditText lblUsername, lblPassword;
    private Button btnLogin;
    private RelativeLayout layoutLogin;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseReference = FirebaseDatabase.getInstance().getReference(Const.USER_DATABASE_PATH);

        lblUsername = findViewById(R.id.lblUsername);
        lblPassword = findViewById(R.id.lblPassword);
        btnLogin = findViewById(R.id.btnLogin);
        layoutLogin = findViewById(R.id.layoutLogin);

        // Login Animation
        Util.animate(Techniques.FadeIn, layoutLogin, 1500);
    }

    public void login(View view) {
        alertDialog = new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Iniciando sesión...")
                .setMessage("Espere por favor")
                .setCancelable(true)
                .show();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Hide AlertDialog
                alertDialog.dismiss();

                String username = lblUsername.getText().toString();
                String password = lblPassword.getText().toString();
                boolean dataIsCorrect = false;

                // Check if Labels are Empty
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Check if Values are Correct
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (username.equals(data.child("username").getValue()) && password.equals(data.child("password").getValue())) {
                            // Successful Login
                            dataIsCorrect = true;
                            Util.toast(getApplicationContext(), "Login correcto.");

                            // Create New User Object
                            //User user = new User(data.getKey(), (String) data.child("url").getValue(), (String) data.child("name").getValue(), (String) data.child("username").getValue(), (String) data.child("password").getValue(), (HashMap<String, Boolean>) data.child("followers").getValue());

                            // New User Object
                            User user = new User();

                            // Set Data
                            user.setId(data.getKey());
                            user.setUrl(data.child("url").getValue().toString());
                            user.setName(data.child("name").getValue().toString());
                            user.setBio(data.child("bio").getValue().toString());
                            user.setUsername(data.child("username").getValue().toString());
                            user.setPassword(data.child("password").getValue().toString());

                            // Create new session
                            SharedPreferences.Editor editor = getSharedPreferences(Const.USER_DEFAULTS, MODE_PRIVATE).edit();
                            editor.putString("USERNAME", username);
                            editor.putString("PASSWORD", password);
                            editor.apply();

                            // Change Activity
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("userObj", user);
                            startActivity(intent);
                            finish();
                            break;
                        }
                    }

                    if (!dataIsCorrect) {
                        Util.toast(getApplicationContext(), "Login incorrecto.");
                        Util.animate(Techniques.Shake, btnLogin, 500);
                    }
                } else {
                    Util.toast(getApplicationContext(), "Hay uno o más campos vacíos.");
                    Util.animate(Techniques.Shake, btnLogin, 500);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Util.toast(getApplicationContext(), "Problemas de conexión\nInténtelo más tarde.");
            }
        });
    }

    public void register(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }

}
