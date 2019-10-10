package com.example.bakingtime;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.adapter.RecipesAdapter;
import com.example.bakingtime.model.Recipe;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_iv)
    ImageView recipeIV;
    @BindView(R.id.recipe_name_tv)
    TextView recipeNameTV;
    private RecipesAdapter recipesAdapter;

    public RecipesViewHolder(RecipesAdapter recipesAdapter, @NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.recipesAdapter = recipesAdapter;
    }

    public void bind(Recipe recipe) {
        recipeNameTV.setText(recipe.getName());
        int placeholder = R.drawable.recipe_placeholder;
        Picasso picasso = Picasso.get();
        RequestCreator requestCreator = recipe.getImageUrl().isEmpty() ? picasso.load(placeholder) : picasso.load(recipe.getImageUrl());
        requestCreator.placeholder(placeholder)
                    .error(placeholder)
                    .into(recipeIV);
    }

    @OnClick
    void onClick(View view) {
        Recipe recipe = recipesAdapter.getRecipes().get(getAdapterPosition());
        this.recipesAdapter.onRecipeClick(recipe);
    }
}
