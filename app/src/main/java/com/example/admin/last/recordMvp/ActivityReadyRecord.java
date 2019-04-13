package com.example.admin.last.recordMvp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.admin.last.R;
import com.example.admin.last.databinding.ActivityReadyRecordBinding;
import com.pedro.encoder.input.video.Camera2ApiManager;
import com.pedro.rtplibrary.rtmp.RtmpCamera2;

import net.ossrs.rtmp.ConnectCheckerRtmp;

public class ActivityReadyRecord extends AppCompatActivity implements recordView,ConnectCheckerRtmp, TextureView.SurfaceTextureListener{

    ActivityReadyRecordBinding binding;
    recordPresenter mRecordPresenter;
    private RtmpCamera2 rtmpCamera2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_ready_record);

        mRecordPresenter = new recordPresenterImpl(this);

        mRecordPresenter.getRequest_permission(ActivityReadyRecord.this);

        rtmpCamera2 = new RtmpCamera2(binding.textureView, this);

        binding.textureView.setSurfaceTextureListener(this);

        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        int orientation = windowManager.getDefaultDisplay().getRotation();
        Log.e("TTTTT", "RRRRRRRRRR" + orientation);

        binding.bStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!rtmpCamera2.isStreaming()) {
                    if (rtmpCamera2.isRecording()
                            || rtmpCamera2.prepareAudio() && rtmpCamera2.prepareVideo()) {
                        binding.bStartStop.setImageResource(R.drawable.recording);
                        rtmpCamera2.startStream("rtmp://52.79.243.140/live/stream3");

                    } else {
                        Toast.makeText(ActivityReadyRecord.this, "Error preparing stream, This device cant do it", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.bStartStop.setImageResource(R.drawable.record);
                    rtmpCamera2.stopStream();
                }
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mRecordPresenter.getRequest_result(requestCode, permissions, grantResults);
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
       int width = size.x;
        int height = size.y;


        binding.textureView.setAspectRatio(width, height);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
        rtmpCamera2.startPreview();
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        if (rtmpCamera2.isStreaming()) {
            rtmpCamera2.stopStream();
            binding.bStartStop.setImageResource(R.drawable.record);
         //   binding.bStartStop.setBackgroundColor(00000000);
        }
        rtmpCamera2.stopPreview();

        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    @Override
    public void onConnectionSuccessRtmp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ActivityReadyRecord.this, "방송을 시작합니다", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public void onConnectionFailedRtmp(final String reason) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ActivityReadyRecord.this, "문제가 발생하였습니다",
                        Toast.LENGTH_SHORT).show();
                rtmpCamera2.stopStream();
                binding.bStartStop.setImageResource(R.drawable.record);
               // binding.bStartStop.setBackgroundColor(00000000);
            }
        });
    }

    @Override
    public void onDisconnectRtmp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ActivityReadyRecord.this, "방송을 종료합니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAuthErrorRtmp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ActivityReadyRecord.this, "Auth error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAuthSuccessRtmp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ActivityReadyRecord.this, "Auth success", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        int orientation = windowManager.getDefaultDisplay().getRotation();
        Log.e("TTTTT", "RRRRRRRRRR" + orientation);
    }
}
