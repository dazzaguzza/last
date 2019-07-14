package com.example.admin.last.watchMvp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.last.profileMvp.ProfilePresenterImpl;
import com.example.admin.last.recordMvp.RecordChatItem;
import com.example.admin.last.recordMvp.RecordPresenterImpl;
import com.example.admin.last.retrofit.ApiClient;
import com.example.admin.last.retrofit.ApiInterface;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WatchPresenterImpl implements WatchPresenter{

    WatchView mWatchView;
    WatchModel mWatchModel;
    SimpleExoPlayer exoPlayer;
    ApiInterface apiInterface;
    Handler handler;
    SocketChannel socketChannel;
    String data,profile;
    ArrayList<WatchItem> arrayList;
    WatchAdapter adapter;
    private  static final  String HOST ="52.79.243.140";
    private static final int PORT = 5001;

    public WatchPresenterImpl(WatchView mWatchView) {
        this.mWatchView = mWatchView;
        mWatchModel = new WatchModelImpl();
    }

    @Override
    public void play(final Context context, final String url, String key) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseCheck> call = apiInterface.watch(key);
        call.enqueue(new Callback<ResponseCheck>() {
            @Override
            public void onResponse(Call<ResponseCheck> call, Response<ResponseCheck> response) {
                if (response.body().response.equals("OK")){

                    try{

                        //initiate Player
//Create a default TrackSelector
                        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
                        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

//Create the player
                        final SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
                        mWatchView.setPlayer().setPlayer(player);

                        RtmpDataSourceFactory rtmpDataSourceFactory = new RtmpDataSourceFactory();
// This is the MediaSource representing the media to be played.
                        MediaSource videoSource = new ExtractorMediaSource.Factory(rtmpDataSourceFactory)
                                .createMediaSource(Uri.parse(url));

// Prepare the player with the source.
                        player.prepare(videoSource);

//auto start playing
                        player.setPlayWhenReady(true);

                        player.addListener(new Player.EventListener() {
                            @Override
                            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

                            }

                            @Override
                            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                            }

                            @Override
                            public void onLoadingChanged(boolean isLoading) {

                            }

                            @Override
                            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                                if(playbackState == SimpleExoPlayer.STATE_ENDED){
                                    player.seekTo(0);
                                    player.setPlayWhenReady(true);
                                }
                            }

                            @Override
                            public void onRepeatModeChanged(int repeatMode) {

                            }

                            @Override
                            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                            }

                            @Override
                            public void onPlayerError(ExoPlaybackException error) {

                            }

                            @Override
                            public void onPositionDiscontinuity(int reason) {

                            }

                            @Override
                            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                            }

                            @Override
                            public void onSeekProcessed() {

                            }
                        });


                    }catch (Exception e){}

                }else{
                    mWatchView.showDialog();
                }
            }

            @Override
            public void onFailure(Call<ResponseCheck> call, Throwable t) {

            }
        });
    }

    @Override
    public void socketOpenAndChat(Context context, ListView listView, String key) {

        arrayList = new ArrayList<>();
        adapter = new WatchAdapter(arrayList,context);
        listView.setAdapter(adapter);

        handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    socketChannel = SocketChannel.open();
                    socketChannel.configureBlocking(true);
                    socketChannel.connect(new InetSocketAddress(HOST,PORT));
                }catch (Exception e){}

                checkUpdate.start();

            }

        }).start();

        try {
            Thread.sleep(1000);
            new sendRoomInfo().execute("roomInfo"+key);
        }catch (Exception e){}

    }

    private Thread checkUpdate = new Thread() {

        public void run() {
            try {
                receive();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    void receive() {
        while (true) {
            try {
                ByteBuffer byteBuffer = ByteBuffer.allocate(256);
                //서버가 비정상적으로 종료했을 경우 IOException 발생
                int readByteCount = socketChannel.read(byteBuffer); //데이터받기
                Log.d("readByteCount", readByteCount + "");
                //서버가 정상적으로 Socket의 close()를 호출했을 경우
                if (readByteCount == -1) {
                    throw new IOException();
                }

                byteBuffer.flip(); // 문자열로 변환
                Charset charset = Charset.forName("UTF-8");
                data = charset.decode(byteBuffer).toString();
                Log.d("receive", "msg :" + data);
                handler.post(showUpdate);
            } catch (IOException e) {
                Log.d("getMsg", e.getMessage() + "");
                try {
                    socketChannel.close();
                    break;
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
            }
        }
    }

    private Runnable showUpdate = new Runnable() {

        public void run() {
            String receive = data;
            String[] words = receive.split("!#&");

                arrayList.add(new WatchItem(words[0],words[1]));
                adapter.notifyDataSetChanged();

        }

    };

    class sendRoomInfo extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            try {
                socketChannel
                        .socket()
                        .getOutputStream()
                        .write(strings[0].getBytes("UTF-8")); // 서버로
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void socketDestroy() {
        try {
            socketChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMsg(Context context) {

        try {

            final String return_msg = mWatchView.edtTxt();
            if (!TextUtils.isEmpty(return_msg)) {

                if (mWatchModel.getNaverUserId(context) != null) {
                    profile = mWatchModel.getNaverUserImg(context);
                } else if(mWatchModel.getKakaoUserId(context) != null){
                    profile = mWatchModel.getKakaoUserImg(context);
                }

                new SendmsgTask().execute(profile+"!#&"+return_msg);
                arrayList.add(new WatchItem(profile,return_msg));
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class SendmsgTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            try {
                socketChannel
                        .socket()
                        .getOutputStream()
                        .write(strings[0].getBytes("UTF-8")); // 서버로
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mWatchView.changUi();
        }
    }
}
