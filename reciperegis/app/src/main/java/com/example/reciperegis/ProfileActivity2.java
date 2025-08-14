package com.example.reciperegis;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity2 extends AppCompatActivity {

    private TextView emailTextView;
    private Button logoutButton, comButton, postButton;

    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        emailTextView = findViewById(R.id.userEmail);
        logoutButton = findViewById(R.id.logoutButton);
        comButton = findViewById(R.id.communityButton);
        postButton = findViewById(R.id.postButton);

        comButton.setOnClickListener(view -> {
            Intent i = new Intent(ProfileActivity2.this, CommunityFeedActivity.class);
            startActivity(i);
        });

        postButton.setOnClickListener(view -> {
            Intent i = new Intent(ProfileActivity2.this, PostRecipeActivity.class);
            startActivity(i);
        });

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String email = user.getEmail();
            emailTextView.setText("Email: " + email);
        } else {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            finish();
        }

        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(ProfileActivity2.this, LoginActivity2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
