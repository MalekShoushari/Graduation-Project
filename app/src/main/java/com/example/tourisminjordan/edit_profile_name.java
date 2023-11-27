package com.example.tourisminjordan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class edit_profile_name extends AppCompatActivity {
Button save_btn;
    ProgressBar progressBar;

EditText Name_profile;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_name);
        progressBar = findViewById(R.id.applicationProgressBar);
        save_btn = findViewById(R.id.save_btn);
        save_btn.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                progressBar.setVisibility(View.INVISIBLE);
                edit_Name();
            },1500);
          });
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("name");
                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = snapshot.getValue(String.class);
                            Name_profile = findViewById(R.id.edit_name_profile);
                            Name_profile.setText(name);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
         });
    }
    private void edit_Name(){
            Name_profile = findViewById(R.id.edit_name_profile);
            String newName = Name_profile.getText().toString();
            if (newName.isEmpty()){
                Toast.makeText(this, R.string.please_enter_new_name, Toast.LENGTH_SHORT).show();
            }else {
                String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("name");
                userRef.setValue(newName);
            }
    }
}