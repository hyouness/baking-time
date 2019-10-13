package com.example.bakingtime.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.R;
import com.example.bakingtime.model.Step;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class StepsViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.step_layout)
    ConstraintLayout stepLayout;

    @BindView(R.id.step_id_tv)
    TextView stepIdTV;

    @BindView(R.id.step_tv)
    TextView stepTV;

    @BindView(R.id.arrow_iv)
    ImageView arrowIV;

    @BindColor(android.R.color.tab_indicator_text)
    int defaultTextColor;

    @BindDrawable(R.drawable.recipe_step_gradient)
    Drawable recipeStepGradient;

    private StepsAdapter stepsAdapter;

    StepsViewHolder(StepsAdapter stepsAdapter, @NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.stepsAdapter = stepsAdapter;
    }

    void bind(Step step, boolean isSelected) {
        if (isSelected) {
            stepLayout.setBackground(recipeStepGradient);
        } else {
            stepLayout.setBackgroundColor(Color.WHITE);
        }

        stepTV.setText(step.getShortDescription());
        stepIdTV.setText(String.format("%s.", step.getId()));

        int textColor = isSelected ? Color.WHITE : defaultTextColor;
        stepTV.setTextColor(textColor);
        stepIdTV.setTextColor(textColor);

        arrowIV.setImageResource(isSelected ? R.drawable.arrow_selected_icon : R.drawable.arrow_icon);
    }

    @OnClick
    void onClick(View view) {
        Step step = stepsAdapter.getSteps().get(getAdapterPosition());
        this.stepsAdapter.onStepClick(step);
    }
}
