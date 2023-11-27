package com.example.tourisminjordan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login_admin extends AppCompatActivity {
    private DatabaseReference myRef;
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        progressBar = findViewById(R.id.applicationProgressBar);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Admin");
        EditText id_editText = findViewById(R.id.id_editText);
        EditText password_login_Admin = findViewById(R.id.password_login_Admin);
        Button logIn_admin = findViewById(R.id.logIn_admin);
        logIn_admin.setOnClickListener(v -> {
              String id = id_editText.getText().toString();
            String password = password_login_Admin.getText().toString();
            String[] id_admin = {"1900901030","1900901078"};
            String[] pass_admin = {"X3538mlg","Yazan12345"};
            if (id.isEmpty() && password.isEmpty()){
                Toast.makeText(this, R.string.please_enter_id_and_password, Toast.LENGTH_SHORT).show();
            }else {
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> progressBar.setVisibility(View.INVISIBLE), 1500);
                for (String s : id_admin) {
                    if (s.equals(id)) {
                        for (String p : pass_admin) {
                            if (p.equals(password)){
                                String loginAdmin = "1";
                                AminoAcidModel aminoAcidModel = new AminoAcidModel(loginAdmin);
                                myRef.child("loginAdmin").setValue(aminoAcidModel);
                                Intent intent = new Intent(login_admin.this,dashboard_admin.class);
                                startActivity(intent);
                                finish();
                                break;
                            }else {
                                Toast.makeText(this, R.string.the_password_is_wrong, Toast.LENGTH_SHORT).show();
                            }
                        }break;
                    }else {
                        Toast.makeText(this, R.string.the_id_is_wrong, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void BackIcon(View view) {
        Intent intent = new Intent(login_admin.this,get_started_page.class);
        startActivity(intent);
        finish();
    }
}