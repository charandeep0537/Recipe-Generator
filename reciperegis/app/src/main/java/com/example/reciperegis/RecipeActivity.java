package com.example.reciperegis;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class RecipeActivity extends AppCompatActivity {

    TextView recipeTitle, recipeInstructions;
    ImageView recipeImage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recipeTitle = findViewById(R.id.recipeTextView);
        recipeInstructions = findViewById(R.id.recipeInstructions);
        recipeImage = findViewById(R.id.recipeImage);

        String title = getIntent().getStringExtra("title");
        String imageUrl = getIntent().getStringExtra("image");
        String instructions = getIntent().getStringExtra("instructions");

        recipeTitle.setText(title);
        recipeInstructions.setText(instructions);
        Glide.with(this).load(imageUrl).into(recipeImage);
    }
}
