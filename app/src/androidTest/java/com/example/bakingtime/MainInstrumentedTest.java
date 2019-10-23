package com.example.bakingtime;

import android.content.Context;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.bakingtime.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Credit: https://medium.com/@wasim__/espresso-test-cases-for-android-recyclerview-eb2ceaddc74f
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainInstrumentedTest {

    @Rule
    final public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private IdlingResource idlingResource;


    @Before
    public void registerIdlingResource() {
        idlingResource = activityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.bakingtime", appContext.getPackageName());
    }

    @Test
    public void onRecipeClick_recipeDetailsActivityIsShown() {
        int recipePosition = 0;
        String recipeName = "Nutella Pie";

        // Step 1: Check if Recycler View is loaded and Visible
        onView(withId(R.id.recipes_rv))
                .check(matches(isDisplayed()));

        // Step 2: Click on recycler view item
        onView(withId(R.id.recipes_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipePosition, click()));

        // Step 3: Validate new activity is a details view of the clicked recipe
        onView(withId(androidx.appcompat.R.id.action_bar))
                .check(matches(hasDescendant(withText(recipeName))));

    }

    @After
    public void unRegisterIdlingResource() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }

}
