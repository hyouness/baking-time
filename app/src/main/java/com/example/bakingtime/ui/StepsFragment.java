package com.example.bakingtime.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.R;
import com.example.bakingtime.adapter.OnItemClickListener;
import com.example.bakingtime.adapter.StepsAdapter;
import com.example.bakingtime.model.Step;
import com.example.bakingtime.utilities.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StepsFragment extends Fragment {

    private List<Step> steps = new ArrayList<>();

    private StepsAdapter stepsAdapter;
    private OnItemClickListener<Step> onStepClickListener;

    @BindView(R.id.steps_rv)
    RecyclerView recyclerView;

    @BindView(R.id.steps_error_message)
    TextView errorMessageTV;
    private Unbinder unbinder;

    public StepsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            steps = getArguments().getParcelableArrayList(AppConstants.RECIPE_STEPS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stepsAdapter = new StepsAdapter(onStepClickListener);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(stepsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        loadSteps();
    }

    void loadSteps() {
        boolean stepsAvailable = steps != null && !steps.isEmpty();
        stepsAdapter.setSteps(stepsAvailable ? steps : new ArrayList<>());
        errorMessageTV.setVisibility(stepsAvailable ? View.INVISIBLE : View.VISIBLE);
    }

    void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(AppConstants.RECIPE_STEPS, steps);
        setArguments(arguments);
        loadSteps();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnItemClickListener) {
            onStepClickListener = (OnItemClickListener<Step>) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemClickListener<Step>");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onStepClickListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    static StepsFragment newInstance(ArrayList<Step> steps) {
        StepsFragment stepsFragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(AppConstants.RECIPE_STEPS, steps);
        stepsFragment.setArguments(args);
        return stepsFragment;
    }
}
