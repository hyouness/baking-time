package com.example.bakingtime.service;

import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.model.ResponseList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BakingApiService {
    @GET("baking.json")
    Call<ResponseList<Recipe>> getRecipes();
}
