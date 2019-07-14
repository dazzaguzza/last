package com.example.admin.last.watchVodMvp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.admin.last.broadcastEndMvp.ItemEnd;
import com.example.admin.last.watchMvp.WatchModel;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;

public class WatchVodPresenterImpl implements WatchVodPresenter {

    WatchVodView mWatchVodView;
    WatchVodModel mWatchVodModel;

    public WatchVodPresenterImpl(WatchVodView mWatchVodView) {
        this.mWatchVodView = mWatchVodView;
        mWatchVodModel = new WatchVodModelImpl();
    }


    @Override
    public void playVod(Context context, String url) {
        try {

            //initiate Player
//Create a default TrackSelector
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

//Create the player
            final SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            mWatchVodView.setPlayer().setPlayer(player);

            MediaSource mediaSource = new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-codelab"))
                    .createMediaSource(Uri.parse(url));

// Prepare the player with the source.
            player.prepare(mediaSource);

//auto start playing
            player.setPlayWhenReady(true);
        } catch (Exception e) {
        }
    }

    @Override
    public void initVodListView(Context context) {
        String json = mWatchVodModel.load(context);
        mWatchVodView.setVodListView(json);
    }

    @Override
    public void click(int position,Context context) {
        String copyJson =mWatchVodModel.copyLoad(context);
        mWatchVodView.setClick(position,copyJson);
    }
}
