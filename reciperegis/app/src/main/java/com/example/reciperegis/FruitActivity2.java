package com.example.reciperegis;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import java.util.List;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class FruitActivity2 extends AppCompatActivity {
    private EditText editText;
    private Button generateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit2);

        editText = findViewById(R.id.editText);
        generateButton = findViewById(R.id.generateButton);

        generateButton.setOnClickListener(view -> fetchFruit());
    }

    private void fetchFruit() {
        String fruitName = editText.getText().toString().trim();
        if (fruitName.isEmpty()) {
            Toast.makeText(this, "Please enter a fruit!", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.fruityvice.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FruityviceApi api = retrofit.create(FruityviceApi.class);
        api.getAllFruits().enqueue(new Callback<List<Fruit>>() {
            @Override
            public void onResponse(Call<List<Fruit>> call, Response<List<Fruit>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Fruit fruit : response.body()) {
                        if (fruit.getName().equalsIgnoreCase(fruitName)) {
                            Intent intent = new Intent(FruitActivity2.this, fruitusesActivity2.class);
                            intent.putExtra("fruit", new Gson().toJson(fruit));
                            startActivity(intent);
                            return;
                        }
                    }
                    Toast.makeText(FruitActivity2.this, "Fruit not found!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FruitActivity2.this, "API error.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Fruit>> call, Throwable t) {
                Toast.makeText(FruitActivity2.this, "API call failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
