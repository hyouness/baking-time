package com.example.bakingtime.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

final public class SharedPreferenceUtils {

    private static final String SELECTED_STEP_INDEX = "selected_step_index";

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

    public static void updateSelectedStepIndex(int stepPos) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(SELECTED_STEP_INDEX, stepPos);
        editor.apply();
    }

    public static int getSelectedStepIndex() {
        return getSelectedStepIndex(-1);
    }


    public static int getSelectedStepIndex(int defValue) {
        return preferences.getInt(SELECTED_STEP_INDEX, defValue);
    }

    public static void clearSelectedStepId() {
        updateSelectedStepIndex(-1);
    }
}
