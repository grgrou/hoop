package com.isil.grgrou.hoop_app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.isil.grgrou.hoop_app.entity.User;
import com.isil.grgrou.hoop_app.util.Const;
import com.isil.grgrou.hoop_app.util.Util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private ImageView imgProfile;
    private EditText lblName, lblUsername, lblPassword;
    private Button btnRegister;
    private Uri imgUri;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(Const.USER_DATABASE_PATH);

        imgProfile = findViewById(R.id.imgProfile);
        lblName = findViewById(R.id.lblName);
        lblUsername = findViewById(R.id.lblUsername);
        lblPassword = findViewById(R.id.lblPassword);
        btnRegister = findViewById(R.id.btnRegister);
    }

    public void uploadImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), Const.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imgProfile.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void register(View view) {
        alertDialog = new AlertDialog.Builder(RegisterActivity.this)
                .setTitle("Completando registro...")
                .setMessage("Espere por favor")
                .setCancelable(true)
                .show();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String name = lblName.getText().toString();
                final String username = lblUsername.getText().toString();
                final String password = lblPassword.getText().toString();
                boolean userNotExists = true;

                // Check if Labels are Empty
                if (!username.isEmpty() && !password.isEmpty() && !name.isEmpty() && imgUri != null ) {
                    // Check if User exists in Database
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (username.equals(data.child("username").getValue())) {
                            Util.toast(getApplicationContext(), "Ya existe una cuenta registrada con ese usuario.");
                            Util.animate(Techniques.Shake, btnRegister, 500);
                            userNotExists = false;
                            break;
                        }
                    }

                    if (userNotExists) {
                        // Get the Storage Reference
                        StorageReference ref = storageReference.child(Const.USER_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imgUri));

                        // Add File to Reference
                        ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                String uploadId = databaseReference.push().getKey();

                                // New User Object
                                User user = new User();

                                // Set Data
                                user.setId(uploadId);
                                user.setUrl(taskSnapshot.getDownloadUrl().toString());
                                user.setName(name);
                                user.setUsername(username);
                                user.setPassword(password);

                                // Save Info in to Database
                                databaseReference.child(uploadId).setValue(user);

                                // Display Success Toast Message
                                Util.toast(getApplicationContext(), "Registro completado.");

                                // Back to Login
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Util.toast(getApplicationContext(), "Error: " + e);
                            }
                        });
                    }
                } else {
                    Util.toast(getApplicationContext(), "Hay uno o más campos vacíos.");
                    Util.animate(Techniques.Shake, btnRegister, 500);
                }
                // Hide AlertDialog
                alertDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Util.toast(getApplicationContext(), "Problemas de conexión\nInténtelo más tarde.");
            }
        });
    }

    public void back(View view) {
        finish();
    }
}
