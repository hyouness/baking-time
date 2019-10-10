package com.example.bakingtime.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.model.ResponseList;
import com.example.bakingtime.service.BakingApiService;
import com.example.bakingtime.utilities.RetrofitUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private BakingApiService apiService;
    private MutableLiveData<ResponseList<Recipe>> recipes = new MutableLiveData<>();

    public MainViewModel() {
        apiService = RetrofitUtils.getBakingApiService();
    }

    public MutableLiveData<ResponseList<Recipe>> getRecipes() {
        if (recipes.getValue() == null) {
            Call<ResponseList<Recipe>> recipesCall = apiService.getRecipes();
            recipesCall.enqueue(new Callback<ResponseList<Recipe>>() {
                @Override
                public void onResponse(@NonNull Call<ResponseList<Recipe>> call, @NonNull Response<ResponseList<Recipe>> response) {
                    recipes.setValue(response.body());
                }

                @Override
                public void onFailure(@NonNull Call<ResponseList<Recipe>> call, @NonNull Throwable t) {
                    recipes.setValue(recipes.getValue());
                }
            });
        }

        return recipes;
    }
}
