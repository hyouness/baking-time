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
import com.example.bakingtime.adapter.StepsAdapter;
import com.example.bakingtime.model.Step;
import com.example.bakingtime.utilities.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class StepsFragment extends Fragment {

    private List<Step> steps = new ArrayList<>();
    private TextView errorMessageTV;
    private StepsAdapter stepsAdapter;


    public StepsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

        stepsAdapter = new StepsAdapter(null);

        RecyclerView recyclerView = rootView.findViewById(R.id.steps_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(stepsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        errorMessageTV = rootView.findViewById(R.id.steps_error_message);
        return rootView;
    }

    void loadSteps() {
        if (getArguments() != null && steps.isEmpty()) {
            steps = getArguments().getParcelableArrayList(AppConstants.RECIPE_STEPS);
        }

        boolean stepsAvailable = steps != null && !steps.isEmpty();
        stepsAdapter.setSteps(stepsAvailable ? steps : new ArrayList<>());
        errorMessageTV.setVisibility(stepsAvailable ? View.INVISIBLE : View.VISIBLE);
    }
}
