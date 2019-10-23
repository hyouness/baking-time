package com.example.bakingtime.utilities;

import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.model.ResponseList;
import com.example.bakingtime.service.BakingApiService;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

final public class RetrofitUtils {

    private static Retrofit retrofit = null;
    private static BakingApiService apiService;

    private RetrofitUtils() {}

    private static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS).build();

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(new TypeToken<ResponseList<Recipe>>(){}.getType(), new RecipeListDeserializer());

            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .client(client)
                    .build();
        }

        return retrofit;
    }

    public static BakingApiService getBakingApiService() {
        if (apiService == null) {
            apiService = getClient().create(BakingApiService.class);
        }
        return apiService;
    }
}
