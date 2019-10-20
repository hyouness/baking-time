package com.example.bakingtime.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.R;
import com.example.bakingtime.model.Ingredient;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

class IngredientsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ingredient_layout)
    ConstraintLayout layout;

    @BindView(R.id.ingredient_name)
    TextView nameTV;

    @BindView(R.id.ingredient_quantity)
    TextView quantityTV;

    @BindColor(R.color.ingredientItem)
    int oddColor;

    IngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bind(Ingredient ingredient, int position) {
        nameTV.setText(ingredient.getName());
        quantityTV.setText(ingredient.getQuantity());
        layout.setBackgroundColor(position % 2 == 0 ? Color.WHITE : oddColor);
    }
}
