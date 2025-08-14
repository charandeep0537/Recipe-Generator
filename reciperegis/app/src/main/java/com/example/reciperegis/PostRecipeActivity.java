package com.example.reciperegis;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PostRecipeActivity extends AppCompatActivity {

    private EditText titleInput, contentInput;
    private Button postButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postrecipeactivty); // Ensure correct layout name

        titleInput = findViewById(R.id.recipeTitle);
        contentInput = findViewById(R.id.recipeContent);
        postButton = findViewById(R.id.postButton);

        db = FirebaseFirestore.getInstance();

        postButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String content = contentInput.getText().toString().trim();

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            if (currentUser != null) {
                String email = currentUser.getEmail(); // or use getUid() if you prefer

                Map<String, Object> recipe = new HashMap<>();
                recipe.put("title", title);
                recipe.put("content", content);
                recipe.put("author", email); // 👈 Save who posted it

                db.collection("recipes")
                        .add(recipe)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(this, "Recipe posted!", Toast.LENGTH_SHORT).show();
                            titleInput.setText("");
                            contentInput.setText("");
                        })
                        .addOnFailureListener(e -> Toast.makeText(this, "Failed to post", Toast.LENGTH_SHORT).show());
            } else {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
