package com.example.bakingtime;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.adapter.OnRecipeClickListener;
import com.example.bakingtime.adapter.RecipesAdapter;
import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.viewmodel.MainViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnRecipeClickListener {

    @BindView(R.id.recipes_rv)
    RecyclerView recyclerView;

    @BindView(R.id.recipes_pb)
    ProgressBar progressBar;

    @BindView(R.id.recipes_error_message)
    TextView errorMessageTv;

    @BindView(R.id.retry_btn)
    Button button;

    RecipesAdapter recipesAdapter;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        recipesAdapter = new RecipesAdapter(this);
        recyclerView.setAdapter(recipesAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), getSpanCount());
        recyclerView.setLayoutManager(layoutManager);

        button.setOnClickListener(v -> loadRecipes());

        loadRecipes();
    }

     void loadRecipes() {
        hideErrorMessage();
        showProgressBar();
        if (!viewModel.getRecipes().hasObservers()) {
            viewModel.getRecipes().observe(this, recipeResponseList -> {
                hideProgressBar();
                if (recipeResponseList != null && !recipeResponseList.getItems().isEmpty()) {
                    recipesAdapter.setRecipes(recipeResponseList.getItems());
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    showErrorMessage(recipeResponseList == null ? R.string.error_message : R.string.no_recipes);
                }
            });
        } else {
            viewModel.getRecipes();
        }
    }

    private int getSpanCount() {
        int spanCount = 1;

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        boolean isLandscape = getResources().getBoolean(R.bool.isLandscape);

        if (isLandscape && isTablet) {
            spanCount = 3;
        }

        if ((isTablet && !isLandscape) || (!isTablet && isLandscape)) {
            spanCount = 2;
        }

        return spanCount;
    }


    void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    void showErrorMessage(int messageId) {
        button.setVisibility(View.VISIBLE);

        errorMessageTv.setText(messageId);
        errorMessageTv.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    void hideErrorMessage() {
        button.setVisibility(View.INVISIBLE);
        errorMessageTv.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onRecipeClick(Recipe recipe) {

    }
}
