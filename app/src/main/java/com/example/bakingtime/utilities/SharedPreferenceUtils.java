package com.example.bakingtime.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

final public class SharedPreferenceUtils {

    private static final String SELECTED_STEP_ID = "selected_step_id";

    private static SharedPreferences preferences;

    public static void initialize(Context context) {
        if (preferences == null) {
            synchronized (SharedPreferenceUtils.class) {
                if (preferences == null) {
                    preferences = PreferenceManager.getDefaultSharedPreferences(context);
                }
            }
        }
    }

    public static void updateSelectedStepId(long stepId) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(SELECTED_STEP_ID, stepId);
        editor.apply();
    }

    public static long getSelectedStepId() {
        return preferences.getLong(SELECTED_STEP_ID, -1);
    }

    public static void clearSelectedStepId() {
        updateSelectedStepId(-1);
    }
}
