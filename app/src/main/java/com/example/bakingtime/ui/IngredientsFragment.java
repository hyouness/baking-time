package com.example.bakingtime.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.R;
import com.example.bakingtime.adapter.IngredientsAdapter;
import com.example.bakingtime.model.Ingredient;
import com.example.bakingtime.utilities.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class IngredientsFragment extends Fragment {

    private List<Ingredient> ingredients = new ArrayList<>();
    private TextView errorMessageTV;
    private IngredientsAdapter ingredientsAdapter;


    public IngredientsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ingredients = getArguments().getParcelableArrayList(AppConstants.RECIPE_INGREDIENTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        ingredientsAdapter = new IngredientsAdapter();

        RecyclerView recyclerView = rootView.findViewById(R.id.ingredients_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(ingredientsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        errorMessageTV = rootView.findViewById(R.id.ingredients_error_message);

        loadIngredients();

        return rootView;
    }

    private void loadIngredients() {
        boolean ingredientsAvailable = ingredients != null && !ingredients.isEmpty();
        ingredientsAdapter.setIngredients(ingredientsAvailable ? ingredients : new ArrayList<>());
        errorMessageTV.setVisibility(ingredientsAvailable ? View.INVISIBLE : View.VISIBLE);
    }

    void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(AppConstants.RECIPE_INGREDIENTS, ingredients);
        setArguments(arguments);
        loadIngredients();
    }


    static IngredientsFragment newInstance(ArrayList<Ingredient> ingredients) {
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(AppConstants.RECIPE_INGREDIENTS, ingredients);
        ingredientsFragment.setArguments(args);
        return ingredientsFragment;
    }
}
