package com.example.tourisminjordan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class Profile_page extends AppCompatActivity {
ImageView profile_image,save,cancel;
TextView edit_picture,Name_profile;
    ProgressBar progressBar;
private static final int REQUEST_CODE_PICK_IMAGE = 1;
private Uri selectedImageUri;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        progressBar = findViewById(R.id.applicationProgressBar);
        profile_image = findViewById(R.id.profile_image);
        cancel = findViewById(R.id.header_profile).findViewById(R.id.cancel);
        save = findViewById(R.id.header_profile).findViewById(R.id.check);
        edit_picture = findViewById(R.id.edit_picture);
        edit_picture.setOnClickListener(v -> openGallery());
        save.setOnClickListener(v ->

                uploadImage()
        );
        save.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                progressBar.setVisibility(View.INVISIBLE);
                uploadImage();
            },2000);
        });
        String imageName = "user_profile_images/" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid() + ".jpg";
        FirebaseStorage.getInstance().getReference().child(imageName).getDownloadUrl()
                .addOnSuccessListener(uri -> Picasso.get().load(uri.toString()).into(profile_image))
                .addOnFailureListener(exception -> {
                });
        cancel.setOnClickListener(v -> {
            Intent intent = new Intent(Profile_page.this,application_UI.class);
            startActivity(intent);
            finish();
        });
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("name");
                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = snapshot.getValue(String.class);
                            Name_profile = findViewById(R.id.Name_profile);
                            Name_profile.setText(name);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PICK_IMAGE) {
            selectedImageUri = data.getData();
            profile_image.setImageURI(selectedImageUri);
            save = findViewById(R.id.header_profile).findViewById(R.id.check);
            save.setVisibility(View.VISIBLE);
        }

    }
    private void uploadImage() {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("user_profile_images/" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid() + ".jpg");
        storageRef.putFile(selectedImageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    Toast.makeText(this, R.string.the_image_has_been_uploaded_successfully, Toast.LENGTH_SHORT).show();
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> Toast.makeText(this, R.string.the_image_has_been_uploaded_successfully, Toast.LENGTH_SHORT).show());
                })
                .addOnFailureListener(exception -> Toast.makeText(this, R.string.an_error_occurred_image, Toast.LENGTH_SHORT).show());
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    public void edit_name(View view) {
        Intent intent = new Intent(Profile_page.this,edit_profile_name.class);
        startActivity(intent);
    }
}