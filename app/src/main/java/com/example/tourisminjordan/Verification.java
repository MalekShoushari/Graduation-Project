package com.example.tourisminjordan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Verification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

    }

    public void verify(View view) {
        Intent intent = new Intent(Verification.this,login_user.class);
        startActivity(intent);
        finish();
    }
}