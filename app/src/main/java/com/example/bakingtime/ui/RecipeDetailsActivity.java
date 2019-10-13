package com.example.bakingtime.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.bakingtime.R;
import com.example.bakingtime.adapter.ViewPagerAdapter;
import com.example.bakingtime.model.Ingredient;
import com.example.bakingtime.model.Step;
import com.example.bakingtime.utilities.AppConstants;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String INGREDIENTS = "Ingredients";
    public static final String STEPS = "Steps";
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.recipe_viewpager)
    ViewPager viewPager;

    StepsFragment stepsFragment;
    IngredientsFragment ingredientsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        ArrayList<Step> steps = new ArrayList<>();
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            steps = extras.getParcelableArrayList(AppConstants.RECIPE_STEPS);
            ingredients = extras.getParcelableArrayList(AppConstants.RECIPE_INGREDIENTS);
            String recipeName = extras.getString(AppConstants.RECIPE_NAME);
            setTitle(recipeName);
        }

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(getIngredientsFragment(ingredients), INGREDIENTS);
        adapter.addFragment(getStepsFragment(steps), STEPS);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private IngredientsFragment getIngredientsFragment(ArrayList<Ingredient> ingredients) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(AppConstants.RECIPE_INGREDIENTS, ingredients);
        ingredientsFragment = new IngredientsFragment();
        ingredientsFragment.setArguments(args);
        return ingredientsFragment;
    }

    private StepsFragment getStepsFragment(ArrayList<Step> steps) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(AppConstants.RECIPE_STEPS, steps);
        stepsFragment = new StepsFragment();
        stepsFragment.setArguments(args);
        return stepsFragment;
    }

}
