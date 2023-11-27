package com.example.tourisminjordan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class verify_email_address extends AppCompatActivity {
    private FirebaseAuth mAuth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email_address);
        mAuth = FirebaseAuth.getInstance();
    }

    public void verify(View view) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.reload().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (user.isEmailVerified()) {
                        Intent intent = new Intent(verify_email_address.this, login_user.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(verify_email_address.this, R.string.Please_verify_your_email_address, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(verify_email_address.this, R.string.Failed_to_reload_user, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}