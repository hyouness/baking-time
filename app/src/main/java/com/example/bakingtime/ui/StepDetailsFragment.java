package com.example.bakingtime.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.bakingtime.R;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindBool;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StepDetailsFragment extends Fragment {

    @BindView(R.id.player_view)
    PlayerView playerView;
    @BindView(R.id.title_tv)
    TextView titleTV;
    @BindView(R.id.description_tv)
    TextView descriptionTV;
    @BindView(R.id.no_video_tv)
    TextView noVideoTV;
    @BindView(R.id.next_btn)
    Button nextStepBtn;
    @BindView(R.id.prev_btn)
    Button prevStepBtn;

    @BindBool(R.bool.isLandscape)
    boolean isLandscape;
    @BindBool(R.bool.isTablet)
    boolean isTablet;

    @BindString(R.string.app_name)
    String appName;

    private SimpleExoPlayer player;
    private List<Step> steps = new ArrayList<>();
    private long playbackPosition;
    private Unbinder unbinder;
    private OnChangeStepListener onChangeStepListener;

    public StepDetailsFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        playbackPosition = savedInstanceState != null ? savedInstanceState.getLong(AppConstants.PLAYBACK_POSITION, 0) : 0;
        loadStepDetails();
    }

    private void loadStepDetails() {
        if (steps != null && !steps.isEmpty()) {
            Step step = steps.get(SharedPreferenceUtils.getSelectedStepIndex(0));
            titleTV.setText(step.getShortDescription());
            descriptionTV.setText(Html.fromHtml(step.getDescription()));
            initializePlayer(step.getVideoUrl());
        }
    }

    private void initializePlayer(String videoUrl) {
        if (!isTablet) {
            playerView.getLayoutParams().height = isLandscape ? ViewGroup.LayoutParams.MATCH_PARENT : 0;
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                if (isLandscape) {
                    actionBar.hide();
                } else {
                    actionBar.show();
                }
                prevStepBtn.setVisibility(isLandscape ? View.INVISIBLE : View.VISIBLE);
                nextStepBtn.setVisibility(isLandscape ? View.INVISIBLE : View.VISIBLE);
            }
        }

        if (videoUrl.isEmpty()) {
            playerView.setUseController(false);
            noVideoTV.setVisibility(View.VISIBLE);
            return;
        }

        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(Objects.requireNonNull(getActivity()), new DefaultTrackSelector());
            playerView.setPlayer(player);
            player.setPlayWhenReady(false);
        }

        player.prepare(getMediaSource(videoUrl));
        player.seekTo(playbackPosition);
    }

    private MediaSource getMediaSource(String videoUrl) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(Objects.requireNonNull(getContext()), Util.getUserAgent(getContext(), appName));
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

    static StepDetailsFragment newInstance(ArrayList<Step> steps) {
        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(AppConstants.RECIPE_STEPS, steps);
        stepDetailsFragment.setArguments(args);
        return stepDetailsFragment;
    }

    @OnClick(R.id.prev_btn)
    void previousStep() {
        onChangeStepListener.previousStep();
    }

    @OnClick(R.id.next_btn)
    void nextStep() {
        onChangeStepListener.nextStep();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(AppConstants.PLAYBACK_POSITION, playbackPosition);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
        }
    }

    @Override
    public void onDestroyView() {
        releasePlayer();
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnChangeStepListener) {
            onChangeStepListener = (OnChangeStepListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnChangeStepListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onChangeStepListener = null;
    }

    public interface OnChangeStepListener {
        void nextStep();
        void previousStep();
    }
}
