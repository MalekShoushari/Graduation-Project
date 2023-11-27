package com.example.tourisminjordan;

import static com.example.tourisminjordan.R.id.forgotPasswordPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class login_user extends AppCompatActivity {
    private FirebaseAuth mAuth = null;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user);
       TextView signupUser = findViewById(R.id.signupUser);
       TextView forgot_Password = findViewById(forgotPasswordPage);
       EditText eMail = findViewById(R.id.eMail);
       EditText password_login_user = findViewById(R.id.password_login_user);
       progressBar = findViewById(R.id.progressBar);
       Button logIn = findViewById(R.id.logIn);

        mAuth = FirebaseAuth.getInstance();
        logIn.setOnClickListener(v -> {
            String email = eMail.getText().toString();
            String password = password_login_user.getText().toString();
            if (email.isEmpty() && password.isEmpty()){
                Toast.makeText(this, R.string.enter_email_and_password, Toast.LENGTH_SHORT).show();
            }
            else {
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    try {
                        if (task.isSuccessful()) {
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("loginUser").child("value");
                            userRef.setValue("1");
                            Toast.makeText(login_user.this, R.string.you_are_signed_in, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login_user.this, application_UI.class);
                            startActivity(intent);
                            finish();
                        } else {
                            throw Objects.requireNonNull(task.getException());
                        }
                    } catch (Exception e) {
                        Toast.makeText(login_user.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        forgot_Password.setOnClickListener(v -> {
            Intent intent = new Intent(login_user.this,Forget_Password.class);
            startActivity(intent);
        });
        signupUser.setOnClickListener(v -> {
            Intent intent = new Intent(login_user.this,signUp_page.class);
            startActivity(intent);
        });
    }

    public void BackIcon(View view) {
        Intent intent = new Intent(this, get_started_page.class);
        startActivity(intent);
        finish();
    }
}

