package com.isil.grgrou.hoop_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.isil.grgrou.hoop_app.entity.User;
import com.isil.grgrou.hoop_app.util.Const;
import com.isil.grgrou.hoop_app.util.Util;

public class EditProfileActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private User user;

    AlertDialog alertDialog;

    private EditText txtName, txtBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Get User Object
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("userObj");

        databaseReference = FirebaseDatabase.getInstance().getReference(Const.USER_DATABASE_PATH).child(user.getId());

        txtName = findViewById(R.id.txtName);
        txtBio  = findViewById(R.id.txtBio);

        txtName.setText(user.getName());
        txtBio.setText(user.getBio());

        /*builder.setPositiveButton("Cerrar sesión",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {

                        SharedPreferences.Editor editor = getSharedPreferences(Const.USER_DEFAULTS, MODE_PRIVATE).edit();
                        editor.putString("USERNAME", null);
                        editor.putString("PASSWORD", null);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        // Close all activities & go to Login
                        dialog.dismiss();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });*/
    }

    public void back(View view) {
        finish();
    }

    public void done(View view) {
        String newName = txtName.getText().toString();
        String newBio = txtBio.getText().toString();

        databaseReference.child("name").setValue(newName);
        databaseReference.child("bio").setValue(newBio);

        alertDialog = new AlertDialog.Builder(EditProfileActivity.this)
                .setTitle("Editar perfil")
                .setMessage("Debes loguearte de nuevo para guardar los cambios.")
                .setCancelable(true)
                .setPositiveButton("Cerrar sesión", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        SharedPreferences.Editor editor = getSharedPreferences(Const.USER_DEFAULTS, MODE_PRIVATE).edit();
                        editor.putString("USERNAME", null);
                        editor.putString("PASSWORD", null);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        // Close all activities & go to Login
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                })
                .show();
    }
}
