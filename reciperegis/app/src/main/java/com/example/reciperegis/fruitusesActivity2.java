package com.example.reciperegis;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.bumptech.glide.Glide;
import com.google.gson.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class fruitusesActivity2 extends AppCompatActivity {

    private ImageView recipeImage;
    private TextView recipeTextView, recipeInstructions;
    private final String SPOONACULAR_API_KEY = "623e9c84bf8741d3b3d0a5c758402bc8"; // replace with your key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruituses2);

        recipeImage = findViewById(R.id.recipeImage);
        recipeTextView = findViewById(R.id.recipeTextView);
        recipeInstructions = findViewById(R.id.recipeInstructions);

        String fruitJson = getIntent().getStringExtra("fruit");
        if (fruitJson == null) {
            Toast.makeText(this, "No fruit found!", Toast.LENGTH_SHORT).show();
            return;
        }

        Fruit fruit = new Gson().fromJson(fruitJson, Fruit.class);
        showFruitDetails(fruit);
        fetchRecipeFromSpoonacular(fruit.getName());
    }

    private void showFruitDetails(Fruit fruit) {
        Nutritions nut = fruit.getNutritions();
        String nutrition = String.format("Carbs: %.1fg | Sugar: %.1fg",
                nut.getCarbohydrates(), nut.getSugar());

        String uses = getFruitUses(fruit.getName());

        recipeTextView.setText("Uses of " + capitalize(fruit.getName()));
        recipeInstructions.setText(nutrition + "\n\nCommon Uses:\n" + uses);

        String imageUrl = "https://source.unsplash.com/600x400/?" + fruit.getName() + ",fruit";
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(recipeImage);
    }

    private void fetchRecipeFromSpoonacular(String query) {
        String url = "https://api.spoonacular.com/recipes/complexSearch?query=" +
                query + "%20juice&number=1&addRecipeInformation=true&apiKey=" + SPOONACULAR_API_KEY;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        if (results.length() > 0) {
                            JSONObject recipe = results.getJSONObject(0);
                            String title = recipe.getString("title");
                            String image = recipe.getString("image");
                            String summary = recipe.getString("summary").replaceAll("<.*?>", "");

                            recipeTextView.setText(title);
                            recipeInstructions.setText(summary);

                            Glide.with(this).load(image).into(recipeImage);
                        } else {
                            Toast.makeText(this, "No juice recipe found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing recipe", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("RECIPE_ERROR", "API Error: " + error.toString());
                    Toast.makeText(this, "Recipe API failed", Toast.LENGTH_SHORT).show();
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private String getFruitUses(String name) {
        switch (name.toLowerCase()) {
            case "banana": return "Milkshakes, Banana Bread, Juices";
            case "apple": return "Cider, Pies, Salads";
            case "mango": return "Smoothies, Ice Cream, Juices";
            default: return "Juices, Smoothies, Raw snacks";
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
