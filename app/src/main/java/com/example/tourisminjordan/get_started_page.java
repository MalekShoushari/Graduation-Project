package com.example.tourisminjordan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class get_started_page extends AppCompatActivity {
    private VideoView videoView;
    @Override
    protected void onPostResume() {
        videoView.resume();
        super.onPostResume();
    }

    @Override
    protected void onRestart() {
        videoView.start();
        super.onRestart();
    }

    @Override
    protected void onPause() {
        videoView.suspend();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        videoView.stopPlayback();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_started_page);
        videoView = findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.tourism_in_jordan_video);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(mp -> mp.setLooping(true));
        LinearLayout admin = findViewById(R.id.Admin_Button);
        LinearLayout user = findViewById(R.id.user_Button);
        admin.setOnClickListener(v -> {
            Intent intent = new Intent(get_started_page.this,login_admin.class);
            startActivity(intent);
            finish();
        });
        user.setOnClickListener(v -> {
            Intent intent = new Intent(get_started_page.this, login_user.class);
            startActivity(intent);
            finish();
        });
    }

}