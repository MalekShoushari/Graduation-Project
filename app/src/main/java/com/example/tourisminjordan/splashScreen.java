package com.example.tourisminjordan;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@SuppressLint("CustomSplashScreen")
public class splashScreen extends AppCompatActivity {
private static final int SPLASH_SCREEN=2500;
    FirebaseUser user = null;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        ImageView imageView = findViewById(R.id.splash_icon);
        TextView splash_Main_Text = findViewById(R.id.splash_Main_Text);
        TextView splash_SubMain_Text = findViewById(R.id.splash_SubMain_Text);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Admin");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String value = dataSnapshot.child("loginAdmin").child("loginValue").getValue(String.class);
                    if (value != null) {
                        num = Integer.parseInt(value);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        Animation top = AnimationUtils.loadAnimation(this, R.anim.top);
        Animation bottom = AnimationUtils.loadAnimation(this, R.anim.bottom);
        imageView.setAnimation(top);
        splash_Main_Text.setAnimation(bottom);
        splash_SubMain_Text.setAnimation(bottom);

        new Handler().postDelayed(() -> {
            user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Intent intent1 = new Intent(splashScreen.this, application_UI.class);
                startActivity(intent1);
                finish();
            } else if (num == 1) {
                Intent intent1 = new Intent(splashScreen.this, dashboard_admin.class);
                startActivity(intent1);
                finish();
            } else {
                Intent intent = new Intent(splashScreen.this, get_started_page.class);
                startActivity(intent);
                finish();
            }

        },SPLASH_SCREEN);
    }

}