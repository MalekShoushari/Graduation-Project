package com.example.tourisminjordan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class About_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);
    }

    public void back_about(View view) {
        Intent intent = new Intent(About_page.this,application_UI.class);
        startActivity(intent);
        finish();
    }

    public void go_facebook(View view) {
        Intent go_facebook = new Intent(Intent.ACTION_VIEW);
        go_facebook.setData(Uri.parse("https://www.facebook.com/MSTechnology01/"));
            startActivity(go_facebook);
    }

    public void go_instagram(View view) {
        Intent go_instagram = new Intent(Intent.ACTION_VIEW);
        go_instagram.setData(Uri.parse("https://www.instagram.com/ms_technology0/"));
        startActivity(go_instagram);
    }
}