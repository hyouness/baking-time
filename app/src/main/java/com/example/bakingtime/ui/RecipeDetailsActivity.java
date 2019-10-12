package com.example.bakingtime.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bakingtime.R;
import com.example.bakingtime.model.Ingredient;
import com.example.bakingtime.utilities.AppConstants;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity {

    IngredientsFragment ingredientsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ingredientsFragment = (IngredientsFragment) getSupportFragmentManager().findFragmentById(R.id.ingredients_fragment);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String recipeName = extras.getString(AppConstants.RECIPE_NAME);
            setTitle(recipeName);

            Bundle args = new Bundle();
            ArrayList<Ingredient> ingredients = extras.getParcelableArrayList(AppConstants.RECIPE_INGREDIENTS);
            args.putParcelableArrayList(AppConstants.RECIPE_INGREDIENTS, ingredients);
            ingredientsFragment.setArguments(args);
            ingredientsFragment.loadIngredients();
        }
    }
}
