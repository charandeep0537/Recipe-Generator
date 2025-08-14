package com.example.reciperegis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class vegeActivity2 extends AppCompatActivity {

    EditText inputFood;
    TextView marquee;
    Button searchBtn, btnSupercook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vege2); // << Ensure this matches your actual layout file

        inputFood = findViewById(R.id.e1);
        marquee= (TextView) findViewById(R.id.marquee);
        marquee.setSelected(true);
        searchBtn = findViewById(R.id.btngen);
        btnSupercook = findViewById(R.id.btnSupercook);

        searchBtn.setOnClickListener(v -> {
            String query = inputFood.getText().toString().trim();
            if (!query.isEmpty()) {
                fetchRecipe(query);
            } else {
                Toast.makeText(this, "Enter ingredients", Toast.LENGTH_SHORT).show();
            }
        });

        btnSupercook.setOnClickListener(v -> {
            Intent intent = new Intent(vegeActivity2.this, SupercookActivity.class);
            startActivity(intent);
        });
    }

    private void fetchRecipe(String ingredient) {
        OkHttpClient client = new OkHttpClient();

        try {
            String encoded = URLEncoder.encode(ingredient, "UTF-8");
            String url = "https://www.themealdb.com/api/json/v1/1/filter.php?i=" + encoded;

            Request request = new Request.Builder().url(url).get().build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> Toast.makeText(vegeActivity2.this, "API call failed", Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String body = response.body().string();

                    try {
                        JSONObject json = new JSONObject(body);
                        JSONArray meals = json.getJSONArray("meals");

                        if (meals.length() == 0) {
                            runOnUiThread(() -> Toast.makeText(vegeActivity2.this, "No recipes found", Toast.LENGTH_SHORT).show());
                            return;
                        }

                        JSONObject firstMeal = meals.getJSONObject(0);
                        String title = firstMeal.getString("strMeal");
                        String thumb = firstMeal.getString("strMealThumb");
                        String idMeal = firstMeal.getString("idMeal");

                        fetchRecipeDetails(idMeal, title, thumb);

                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(vegeActivity2.this, "Parsing error", Toast.LENGTH_SHORT).show());
                        Log.e("API", "Error: ", e);
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchRecipeDetails(String id, String title, String imageUrl) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + id;

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(vegeActivity2.this, "Details fetch failed", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    JSONObject recipe = json.getJSONArray("meals").getJSONObject(0);
                    String instructions = recipe.getString("strInstructions");

                    runOnUiThread(() -> {
                        Intent intent = new Intent(vegeActivity2.this, RecipeActivity.class);
                        intent.putExtra("title", title);
                        intent.putExtra("image", imageUrl);
                        intent.putExtra("instructions", instructions);
                        startActivity(intent);
                    });

                } catch (Exception e) {
                    runOnUiThread(() -> Toast.makeText(vegeActivity2.this, "Parsing error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
