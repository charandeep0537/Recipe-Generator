package com.example.reciperegis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText edEmail, edPassword, edUsername;
    ImageView eyeIcon;
    final boolean[] isPasswordVisible = {false};
    Button registerBtn, loginBtn;
    FirebaseAuth mAuth;
    int c=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edUsername = findViewById(R.id.et1);
        edEmail = findViewById(R.id.et2);
        edPassword = findViewById(R.id.et3);
        eyeIcon = findViewById(R.id.eye);
        registerBtn = findViewById(R.id.btn);
        loginBtn = findViewById(R.id.btn2);
        mAuth = FirebaseAuth.getInstance();

        // Toggle Password Visibility
        eyeIcon.setOnClickListener(v -> {
            if (isPasswordVisible[0]) {
                edPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                eyeIcon.setImageResource(R.drawable.eye);
            } else {
                edPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                eyeIcon.setImageResource(R.drawable.eye_off);
            }
            edPassword.setSelection(edPassword.getText().length());
            isPasswordVisible[0] = !isPasswordVisible[0];
        });

        // Register Button
        //get email and password from user
        registerBtn.setOnClickListener(v -> {
            String email = edEmail.getText().toString().trim();
            String password = edPassword.getText().toString().trim();
            //check emailformat -valid or not
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }
            //atleast 6 characters
            if (password.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }
            // Creates Account with Firebase
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, LoginActivity2.class));
                            finish();
                        } else {
                            Log.e("FIREBASE_ERROR", "Error", task.getException());
                            Toast.makeText(this, "Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        // Go to Login Page
        loginBtn.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity2.class)));
    }


}
