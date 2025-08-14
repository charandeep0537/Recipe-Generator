package com.example.reciperegis;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FruityviceApi {
    @GET("fruit/all")
    Call<List<Fruit>> getAllFruits();
}
