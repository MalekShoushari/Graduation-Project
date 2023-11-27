package com.example.tourisminjordan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class successfully_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfully_page);


    }

    public void go_to_login_button(View view) {
        Intent intent = new Intent(successfully_page.this, login_user.class);
        startActivity(intent);
        finish();
    }
}