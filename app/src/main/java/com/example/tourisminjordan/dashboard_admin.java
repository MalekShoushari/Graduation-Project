package com.example.tourisminjordan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class dashboard_admin extends AppCompatActivity {
    private DatabaseReference myRef;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        progressBar = findViewById(R.id.applicationProgressBar);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Admin");
    }

    public void logout_admin(View view) {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            progressBar.setVisibility(View.INVISIBLE);
            String loginAdmin = "0";
            AminoAcidModel aminoAcidModel = new AminoAcidModel(loginAdmin);
            myRef.child("loginAdmin").setValue(aminoAcidModel);
            Intent intent = new Intent(dashboard_admin.this,get_started_page.class);
            startActivity(intent);
            finish();
        },1000);

    }

    public void add_place(View view) {
        Intent intent = new Intent(dashboard_admin.this,entry_info.class);
        startActivity(intent);
    }

    public void home_page(View view) {
        Intent intent = new Intent(dashboard_admin.this,application_UI.class);
        startActivity(intent);
    }

}