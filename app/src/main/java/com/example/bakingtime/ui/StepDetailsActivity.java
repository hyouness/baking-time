package com.example.bakingtime.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bakingtime.R;
import com.example.bakingtime.model.Step;
import com.example.bakingtime.utilities.AppConstants;
import com.example.bakingtime.utilities.SharedPreferenceUtils;

import java.util.ArrayList;

public class StepDetailsActivity extends AppCompatActivity implements StepDetailsFragment.OnChangeStepListener {

    private ArrayList<Step> steps = new ArrayList<>();
    private StepDetailsFragment stepDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String recipeName = extras.getString(AppConstants.RECIPE_NAME);
            setTitle(recipeName);
            steps = extras.getParcelableArrayList(AppConstants.RECIPE_STEPS);
        }

        assert steps != null;
        stepDetailsFragment = (StepDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.step_details_container);
        if (stepDetailsFragment == null) {
            stepDetailsFragment = StepDetailsFragment.newInstance(steps);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_details_container, stepDetailsFragment)
                    .commit();
        }
    }

    @Override
    public void nextStep() {
        if (stepDetailsFragment != null) {
            int nextStepIndex = SharedPreferenceUtils.getSelectedStepIndex() + 1;
            reloadStepDetailsFragment(nextStepIndex % steps.size());
        }
    }

    @Override
    public void previousStep() {
        if (stepDetailsFragment != null) {
            int prevStepIndex = SharedPreferenceUtils.getSelectedStepIndex() - 1;
            reloadStepDetailsFragment(prevStepIndex >= 0 ? prevStepIndex : steps.size() - 1);
        }
    }

    private void reloadStepDetailsFragment(int stepIndex) {
        SharedPreferenceUtils.updateSelectedStepIndex(stepIndex);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_details_container, StepDetailsFragment.newInstance(steps))
                .commit();
    }
}
