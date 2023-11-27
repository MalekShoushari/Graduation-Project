package com.example.tourisminjordan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class NewPassword extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);


    }

    public void submit_button(View view) {
        Intent intent = new Intent(NewPassword.this,successfully_page.class);
        startActivity(intent);
        finish();
    }
}