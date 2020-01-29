package com.ms.demo.siyu.mymessenger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ms.demo.siyu.mymessenger.entity.User;

import java.io.IOException;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    EditText mUserName, mEmail, mPassword;
    Button mRegisterBtn;
    Button mPhotoRegisterBtn;
    private FirebaseAuth mAuth;
    Uri mSelectPhotoUri;
    CircleImageView mCircleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mUserName = findViewById(R.id.text_username_login);
        mEmail = findViewById(R.id.text_password_login);
        mPassword = findViewById(R.id.text_password);
        mRegisterBtn = findViewById(R.id.button_login);
        mPhotoRegisterBtn = findViewById(R.id.button_select_photo);
        mCircleImageView = findViewById(R.id.circle_image_view);
    }

    public void onRegisterClick(View view) {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        Log.d(TAG, "Email is " + email);
        Log.d(TAG, "Password is " + password);

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email/password", Toast.LENGTH_SHORT).show();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            uploadImageToFirebaseStorage();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Failed to crate user ");
                    }
                });

    }

    private void uploadImageToFirebaseStorage() {
        if (mSelectPhotoUri == null) {
            return;
        }
        String fileName = UUID.randomUUID().toString();
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference("/images/"+fileName);
        storageReference.putFile(mSelectPhotoUri)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "Successful upload image:" + taskSnapshot.getMetadata().getPath());
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.d(TAG, "download url = " + uri.toString());
                            saveUserToFirebaseDatabase(uri);
                        }
                    });
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
    }

    public void onSelectPhotoClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            mSelectPhotoUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mSelectPhotoUri);
                mCircleImageView.setImageBitmap(bitmap);
                mPhotoRegisterBtn.setAlpha(0f);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void saveUserToFirebaseDatabase(Uri uri) {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null || uid.isEmpty()) {
            return;
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("/users/" + uid);
        databaseReference.setValue(new User(uid, mUserName.getText().toString(), uri.toString()))
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Finally we saved the user to Firebase Database");

                Intent intent = new Intent(RegisterActivity.this, LatestMessageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                RegisterActivity.this.startActivity(intent);
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Failed to Add user to DB " + e.getMessage());
            }
        });
    }


}
