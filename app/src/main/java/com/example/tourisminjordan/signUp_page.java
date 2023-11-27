package com.example.tourisminjordan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class signUp_page extends AppCompatActivity {
    private FirebaseAuth mAuth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        mAuth = FirebaseAuth.getInstance();
        TextView already = findViewById(R.id.already);
        Button registration = findViewById(R.id.registration);
        EditText sign_up_userName = findViewById(R.id.sign_up_userName);
        EditText sign_up_user_eMail = findViewById(R.id.sign_up_user_eMail);
        EditText create_password_user = findViewById(R.id.create_password_user);
        EditText Confirm_password_user = findViewById(R.id.Confirm_password_user);
        ProgressBar progressBar = findViewById(R.id.signUpProgressBar);
        registration.setOnClickListener(v -> {
            String userName = sign_up_userName.getText().toString();
            String eMailUser = sign_up_user_eMail.getText().toString();
            String create_password = create_password_user.getText().toString();
            String confirm_password = Confirm_password_user.getText().toString();
            progressBar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> progressBar.setVisibility(View.INVISIBLE), 500);
            if (userName.isEmpty() && eMailUser.isEmpty() && create_password.isEmpty() && confirm_password.isEmpty()) {
                Toast.makeText(this, R.string.enter_the_required_data, Toast.LENGTH_SHORT).show();
            } else {
                if (create_password.length() >= 6) {
                    if (create_password.equals(confirm_password)) {
                        if (isPasswordValid(create_password)){
                        mAuth.createUserWithEmailAndPassword(eMailUser, create_password).addOnCompleteListener(task -> {
                            try {
                                if (task.isSuccessful()) {
                                    String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("loginUser").child(uid).child("value");
                                    userRef.setValue("1");
                                    verifyEmailAddress();
                                    updateUserName(userName);
                                } else {
                                    throw Objects.requireNonNull(task.getException());
                                }
                            } catch (Exception e) {
                                Toast.makeText(signUp_page.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else{
                            Toast.makeText(this, R.string.combination, Toast.LENGTH_SHORT).show();

                        }
                    }
                        else {
                        Toast.makeText(this, R.string.password_does_not_match, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, R.string.at_least_6, Toast.LENGTH_SHORT).show();
                }
            }
        });
        already.setOnClickListener(v -> {
            Intent intent = new Intent(signUp_page.this, login_user.class);
            startActivity(intent);
            finish();
        });
    }
    public void updateUserName(String name) {
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("name");
        userRef.setValue(name).addOnSuccessListener(aVoid -> {
        }).addOnFailureListener(e -> {
        });
    }
    public boolean isPasswordValid(String password) {
        boolean hasLetter = false;
        boolean hasNumber = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            }
        }
        return hasLetter && hasNumber;
    }


    public void verifyEmailAddress() {
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        user.sendEmailVerification().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, R.string.A_verification_message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(signUp_page.this, verify_email_address.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, R.string.Failed_to_send_verification_email, Toast.LENGTH_SHORT).show();
            }
        });
    }

}

