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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity2 extends AppCompatActivity {
    TextView textView, tview, tv;
    EditText editText, ed;
    ImageView eye;
    final boolean[] isPasswordVisible = {false};
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        textView = findViewById(R.id.tvlogin);
        tview = findViewById(R.id.tv);
        tv = findViewById(R.id.tvp);
        editText = findViewById(R.id.e1); // Email
        ed = findViewById(R.id.e2);       // Password
        eye = findViewById(R.id.eye1);
        btn = findViewById(R.id.btn);
        //Show/Hide Password Feature
        eye.setOnClickListener(view -> {
            if (isPasswordVisible[0]) {
                ed.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                eye.setImageResource(R.drawable.eye);
            } else {
                ed.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                eye.setImageResource(R.drawable.eye_off);
            }
            ed.setSelection(ed.getText().length());
            isPasswordVisible[0] = !isPasswordVisible[0];
        });
        //Firebase Setup
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(view -> {
            String userEmail = editText.getText().toString().trim();
            String userPassword = ed.getText().toString().trim();
            //Input Validation
            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }
            //Firebase Login
            mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    //If Login is successful
                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, page1Activity2.class)); // Or your home page
                    finish();
                } else {
                    //If login fails
                    Exception exception = task.getException();
                    Log.e("FIREBASE_LOGIN_ERROR", "Exception: " + exception);
                    //No user found
                    if (exception instanceof FirebaseAuthInvalidUserException) {
                        Toast.makeText(this, "No user found with this email. Please register.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity2.this, page1Activity2.class);
                        intent.putExtra("email", userEmail);
                        startActivity(intent);
                        finish();
                    } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Login Failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}
