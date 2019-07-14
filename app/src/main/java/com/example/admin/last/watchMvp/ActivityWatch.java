package com.example.admin.last.watchMvp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.admin.last.R;
import com.example.admin.last.databinding.ActivityWatchBinding;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;

public class ActivityWatch extends AppCompatActivity implements WatchView {

    ActivityWatchBinding binding;
    WatchPresenter mWatchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_watch);
        binding.exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        binding.listviewChatWatch.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        mWatchPresenter = new WatchPresenterImpl(this);

        String getUrl = getIntent().getStringExtra("url");
        String key = getIntent().getStringExtra("key");

        mWatchPresenter.play(ActivityWatch.this, getUrl,key);
        mWatchPresenter.socketOpenAndChat(ActivityWatch.this,binding.listviewChatWatch,key);

        binding.btnChatWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWatchPresenter.sendMsg(ActivityWatch.this);
            }
        });

    }


    @Override
    public PlayerView setPlayer() {
        return binding.exoPlayerView;
    }

    @Override
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityWatch.this);
        builder.setTitle("알림");
        builder.setMessage("종료된 방송입니다!");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        builder.show();
    }

    @Override
    public void changUi() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.edtChatWatch.setText("");
            }
        });
    }

    @Override
    public String edtTxt() {
        return binding.edtChatWatch.getText().toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWatchPresenter.socketDestroy();
    }
}
