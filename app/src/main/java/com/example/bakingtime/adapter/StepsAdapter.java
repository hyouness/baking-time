package com.example.bakingtime.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.R;
import com.example.bakingtime.model.Step;
import com.example.bakingtime.utilities.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsViewHolder> {

    private List<Step> steps = new ArrayList<>();

    private OnItemClickListener<Step> onStepClickListener;

    public StepsAdapter(OnItemClickListener<Step> onStepClickListener) {
        this.onStepClickListener = onStepClickListener;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.steps_list_item, parent, false);
        return new StepsViewHolder(this, view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        holder.bind(steps.get(position), position == SharedPreferenceUtils.getSelectedStepIndex());
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    List<Step> getSteps() {
        return steps;
    }

    void onStepClick(Step step, int adapterPosition) {
        SharedPreferenceUtils.updateSelectedStepIndex(adapterPosition);
        notifyDataSetChanged();
        onStepClickListener.onItemClick(step);
    }
}
