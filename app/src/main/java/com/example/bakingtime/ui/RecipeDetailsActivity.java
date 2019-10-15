package com.example.bakingtime.ui;

import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.bakingtime.R;
import com.example.bakingtime.adapter.OnItemClickListener;
import com.example.bakingtime.adapter.ViewPagerAdapter;
import com.example.bakingtime.model.Ingredient;
import com.example.bakingtime.model.Step;
import com.example.bakingtime.utilities.AppConstants;
import com.example.bakingtime.utilities.SharedPreferenceUtils;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindBool;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeDetailsActivity extends AppCompatActivity implements OnItemClickListener<Step> {

    private static final String INGREDIENTS = "Ingredients";
    private static final String STEPS = "Steps";

    @Nullable
    @BindView(R.id.player_view)
    PlayerView playerView;

    @Nullable
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Nullable
    @BindView(R.id.recipe_viewpager)
    ViewPager viewPager;

    @BindString(R.string.app_name)
    String appName;

    @BindBool(R.bool.isTwoPaneMode)
    boolean isTwoPaneMode;

    @BindBool(R.bool.isTablet)
    boolean isTablet;

    @BindBool(R.bool.isLandscape)
    boolean isLandscape;

    private SimpleExoPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        SharedPreferenceUtils.initialize(getApplicationContext());

        ArrayList<Step> steps = new ArrayList<>();
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            steps = extras.getParcelableArrayList(AppConstants.RECIPE_STEPS);
            ingredients = extras.getParcelableArrayList(AppConstants.RECIPE_INGREDIENTS);
            String recipeName = extras.getString(AppConstants.RECIPE_NAME);
            setTitle(recipeName);
        }

        if (!isTwoPaneMode && viewPager != null && tabLayout != null) {
            Step recipeIntro = getRecipeIntroStep(steps);
            long playbackPosition = savedInstanceState != null ? savedInstanceState.getLong(AppConstants.PLAYBACK_POSITION, 0) : 0;
            initializePlayer(recipeIntro.getVideoUrl(), playbackPosition);

            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(IngredientsFragment.newInstance(ingredients), INGREDIENTS);
            adapter.addFragment(StepsFragment.newInstance(steps), STEPS);
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private Step getRecipeIntroStep(ArrayList<Step> steps) {
        Step recipeIntro = null;
        if (steps != null && !steps.isEmpty()) {
            recipeIntro = steps.get(0);
            steps.remove(0);
        }
        return recipeIntro;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void initializePlayer(String videoUrl, long playbackPosition) {
        assert playerView != null;
        if (isLandscape) {
            playerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            Objects.requireNonNull(getSupportActionBar()).hide();
        } else {
            Objects.requireNonNull(getSupportActionBar()).show();
            playerView.getLayoutParams().height = 0;
        }

        System.out.println("Playback Position: " + playbackPosition);
        player = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), new DefaultTrackSelector());
        playerView.setPlayer(player);
        player.prepare(getMediaSource(videoUrl));
        player.setPlayWhenReady(false);
        player.seekTo(playbackPosition);
    }

    private MediaSource getMediaSource(String videoUrl) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, Util.getUserAgent(this, appName));
        Uri videoUri = Uri.parse(videoUrl);
        return new ProgressiveMediaSource.Factory(dataSourceFactory, new DefaultExtractorsFactory()).createMediaSource(videoUri);
    }

    private void releasePlayer() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(AppConstants.PLAYBACK_POSITION, player.getCurrentPosition());
    }

    @Override
    public void onItemClick(Step step) {
        
    }
}
