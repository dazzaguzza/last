package com.example.admin.last.watchVodMvp;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.admin.last.R;
import com.example.admin.last.SharedPreferenceUtil;
import com.example.admin.last.broadcastEndMvp.ItemEnd;
import com.example.admin.last.databinding.ActivityWatchVodBinding;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WatchVod extends AppCompatActivity implements WatchVodView {

    ActivityWatchVodBinding binding;
    WatchVodPresenter mWatchVodPresenter;
    ArrayList<ItemEnd> arrayList;
    ArrayList<ItemEnd> copyArrayList;
    WatchVodAdapter watchVodAdapter;

    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";

    private SimpleExoPlayerView mExoPlayerView;
    SimpleExoPlayer player;
    MediaSource mediaSource;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private ImageView mFullScreenIcon;
    private Dialog mFullScreenDialog;

    private int mResumeWindow;
    private long mResumePosition;
    String getVodUrl;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_watch_vod);
        //   binding.exoPlayerViewForVod.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);

        mWatchVodPresenter = new WatchVodPresenterImpl(this);

        getVodUrl = getIntent().getStringExtra("vodUrl");
        position = getIntent().getIntExtra("position",0);

        Log.d("TAG", "onCreate: " + getVodUrl);
        //  mWatchVodPresenter.playVod(this,getVodUrl);

        mWatchVodPresenter.initVodListView(WatchVod.this);

       binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mWatchVodPresenter.click(position,WatchVod.this);
           }
       });


        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }
    }

    @Override
    public PlayerView setPlayer() {
        return binding.exoPlayerViewForVod;
    }

    @Override
    public void setVodListView(String json) {
        //arrayList = new ArrayList<>();
        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<ItemEnd>>(){}.getType();
        arrayList = gson.fromJson(json,type);

        if(arrayList == null){
            arrayList = new ArrayList<>();
        }

       arrayList.remove(position);


        watchVodAdapter = new WatchVodAdapter(arrayList, WatchVod.this);
        binding.list.setAdapter(watchVodAdapter);
    }

    @Override
    public void setClick(int position,String copyJson) {
        Gson gson = new Gson();


        Type type = new TypeToken<ArrayList<ItemEnd>>(){}.getType();
        copyArrayList = gson.fromJson(copyJson,type);

        if(copyArrayList == null){
            copyArrayList = new ArrayList<>();
        }
        getVodUrl = arrayList.get(position).getAddress();
        arrayList.clear();
        arrayList.addAll(copyArrayList);
        for(int i =0; i<arrayList.size(); i++){
            if(arrayList.get(i).getAddress().equals(getVodUrl)){
                arrayList.remove(i);
            }
        }
        watchVodAdapter.notifyDataSetChanged();

        mediaSource = new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-codelab"))
                .createMediaSource(Uri.parse(getVodUrl));

        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);

        super.onSaveInstanceState(outState);
    }


    private void initFullscreenDialog() {

        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }


    private void openFullscreenDialog() {

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(WatchVod.this, R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }


    private void closeFullscreenDialog() {

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        ((FrameLayout) findViewById(R.id.main_media_frame)).addView(mExoPlayerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(WatchVod.this, R.drawable.ic_fullscreen_expand));
    }


    private void initFullscreenButton() {

        PlaybackControlView controlView = mExoPlayerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
            }
        });
    }


    private void initExoPlayer() {

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), trackSelector, loadControl);
        mExoPlayerView.setPlayer(player);

        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

        if (haveResumePosition) {
            mExoPlayerView.getPlayer().seekTo(mResumeWindow, mResumePosition);
        }

        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
    }


    @Override
    protected void onResume() {

        super.onResume();

        if (mExoPlayerView == null) {

            mExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exo_player_view_for_vod);
            initFullscreenDialog();
            initFullscreenButton();

            String streamUrl = getVodUrl;
            //  String userAgent = Util.getUserAgent(WatchVod.this, getApplicationContext().getApplicationInfo().packageName);
            // DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
            // DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(WatchVod.this, null, httpDataSourceFactory);
            //  Uri daUri = Uri.parse(streamUrl);

            mediaSource = new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-codelab"))
                    .createMediaSource(Uri.parse(streamUrl));
            // mVideoSource = new HlsMediaSource(daUri, dataSourceFactory, 1, null, null);
        }

        initExoPlayer();

        if (mExoPlayerFullscreen) {
            ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
            mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(WatchVod.this, R.drawable.ic_fullscreen_skrink));
            mFullScreenDialog.show();
        }
    }


    @Override
    protected void onPause() {

        super.onPause();

        if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {
            mResumeWindow = mExoPlayerView.getPlayer().getCurrentWindowIndex();
            mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());

            mExoPlayerView.getPlayer().release();
        }

        if (mFullScreenDialog != null)
            mFullScreenDialog.dismiss();
    }


}
