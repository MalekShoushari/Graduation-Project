package com.example.tourisminjordan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Forget_Password extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mEmailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mAuth = FirebaseAuth.getInstance();
        mEmailEditText = findViewById(R.id.forget_email);
        Button resetPasswordButton = findViewById(R.id.sendEmail);
        resetPasswordButton.setOnClickListener(v -> resetPassword());

    }
    private void resetPassword() {
        String email = mEmailEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), R.string.please_registered_email_address, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(getApplicationContext(), R.string.please_enter_a_valid_email_address, Toast.LENGTH_SHORT).show();
            return;
        }

        sendPasswordResetEmail(email);
    }

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    private void sendPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Forget_Password.this, R.string.password_reset, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Forget_Password.this,Verification.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Forget_Password.this, R.string.error_occurred, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}