package com.example.bakingtime.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bakingtime.R;
import com.example.bakingtime.model.Ingredient;
import com.example.bakingtime.model.Step;
import com.example.bakingtime.utilities.AppConstants;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity {

    StepsFragment stepsFragment;
    IngredientsFragment ingredientsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String recipeName = extras.getString(AppConstants.RECIPE_NAME);
            setTitle(recipeName);

            initializeStepsFragment(extras.getParcelableArrayList(AppConstants.RECIPE_STEPS));
//            initializeIngredientsFragment(extras.getParcelableArrayList(AppConstants.RECIPE_INGREDIENTS));
        }
    }

    private void initializeIngredientsFragment(ArrayList<Ingredient> ingredients) {
//        Bundle args = new Bundle();
//        args.putParcelableArrayList(AppConstants.RECIPE_INGREDIENTS, ingredients);
//        ingredientsFragment = (IngredientsFragment) getSupportFragmentManager().findFragmentById(R.id.ingredients_fragment);
//        if (ingredientsFragment != null) {
//            ingredientsFragment.setArguments(args);
//            ingredientsFragment.loadIngredients();
//        }
    }

    private void initializeStepsFragment(ArrayList<Step> steps) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(AppConstants.RECIPE_STEPS, steps);
        stepsFragment = (StepsFragment) getSupportFragmentManager().findFragmentById(R.id.steps_fragment);
        if (stepsFragment != null) {
            stepsFragment.setArguments(args);
            stepsFragment.loadSteps();
        }
    }

}
