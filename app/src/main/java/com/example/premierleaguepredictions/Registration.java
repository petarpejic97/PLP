package com.example.premierleaguepredictions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class Registration extends AppCompatActivity {

    private ImageView ivUserPhoto;
    private EditText edFullname;
    private EditText edNickname;
    private EditText edEmail;
    private EditText edPassword;
    private EditText edConfPassword;
    private Button btnRegistration;

    private ProgressDialog progressDialog;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference myRef;

    static int PReqCode = 1;
    static int REQUESCODE = 1;

    Uri pickedImgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ivUserPhoto = findViewById(R.id.ivRegPicture);
        edFullname = findViewById(R.id.edFullName);
        edNickname = findViewById(R.id.edNickname);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        edConfPassword = findViewById(R.id.edConfPassword);
        btnRegistration=findViewById(R.id.btnRegistration);

        progressDialog = new ProgressDialog(this);
        firebaseAuth =FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef= mFirebaseDatabase.getReference();

        onButtonRegistrationClick();
        onImageClick();
    }

    private void onButtonRegistrationClick(){
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userRegistration();
            }
        });
    }

    private void onImageClick() {
        ivUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOnPhoto();
            }
        });
    }

    private void clickOnPhoto() {

        if(Build.VERSION.SDK_INT >= 22){
            checkAndRequesForPermission();
        }
        else{
            openGallery();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    private void checkAndRequesForPermission() {

        if(ContextCompat.checkSelfPermission(Registration.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(Registration.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(Registration.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();
            }
            else{
                ActivityCompat.requestPermissions(Registration.this,
                                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                    PReqCode);
            }
        }
        else{
            openGallery();
        }


    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == RESULT_OK && requestCode == REQUESCODE && data != null){
            pickedImgUri = data.getData();
            ivUserPhoto.setImageURI(pickedImgUri);

        }
    }

    private void userRegistration() {
        final String nickname = edNickname.getText().toString();
        final String email = edEmail.getText().toString();
        final String fullname = edFullname.getText().toString();
        final String password = edPassword.getText().toString();
        final String confPassword = edConfPassword.getText().toString();

        CheckRegistration checkRegistration = new CheckRegistration(fullname,nickname,email,password,confPassword);
        checkRegistration.setNicknameExist(nickname);

        if(!checkRegistration.checkIsEmpty()){
            Toast.makeText(Registration.this, "Some fields are empty!",
                    Toast.LENGTH_SHORT).show();
        }
        else if(!checkRegistration.checkPassword()){
            Toast.makeText(Registration.this, "You have problem with password!",
                    Toast.LENGTH_SHORT).show();
        }
        else if(!checkRegistration.isNickExistInDatabase()){
            Toast.makeText(Registration.this, "User with this nickname already exist!",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            createUser(fullname,nickname,email,password);
        }
    }

    private void createUser(final String fullname, final String nickname, final String email, String password) {
        progressDialog.setMessage("Wait for minute...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            updateUserInfo(fullname,nickname,email,pickedImgUri,firebaseAuth.getCurrentUser());
                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Registration.this, "Registration finished! Please check your email for verification !",
                                                Toast.LENGTH_LONG).show();
                                        startNewActivity();
                                    }
                                }
                            });
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(Registration.this, "Registration failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUserInfo(final String fullname,final String nickname,final String email, Uri pickedImgUri, final FirebaseUser currentUser){
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFileParh = mStorage.child(Objects.requireNonNull(pickedImgUri.getLastPathSegment()));
        imageFileParh.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageFileParh.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        SaveUserInDatabase(fullname,nickname,email,uri.toString());

                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(fullname)
                                .setPhotoUri(uri)
                                .build();

                        currentUser.updateProfile(profileUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressDialog.dismiss();
                                        if(task.isSuccessful()){

                                        }
                                    }
                                });
                    }
                });
            }
        });
    }

    private void SaveUserInDatabase(String fullname,String nickname,String email,String imgUrl) {
        User user = new User(fullname,nickname,email,imgUrl);

        myRef.child("users").child(nickname).setValue(user);

    }

    private void startNewActivity() {
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
    }
}
