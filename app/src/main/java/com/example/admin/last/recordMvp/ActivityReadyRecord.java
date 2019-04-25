package com.example.admin.last.recordMvp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.SurfaceTexture;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

import com.example.admin.last.R;
import com.example.admin.last.databinding.ActivityReadyRecordBinding;
import com.example.admin.last.retrofit.ApiClient;
import com.example.admin.last.retrofit.ApiInterface;
import com.pedro.rtplibrary.rtmp.RtmpCamera1;
import com.pedro.rtplibrary.view.AutoFitTextureView;

import net.ossrs.rtmp.ConnectCheckerRtmp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityReadyRecord extends AppCompatActivity implements RecordView,ConnectCheckerRtmp, TextureView.SurfaceTextureListener{

    ActivityReadyRecordBinding binding;
    RecordPresenter mRecordPresenter;
    private RtmpCamera1 rtmpCamera1;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_ready_record);

        mRecordPresenter = new RecordPresenterImpl(this);

        mRecordPresenter.getRequest_permission(ActivityReadyRecord.this);



        binding.bStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             mRecordPresenter.streamOrNot(rtmpCamera1);
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

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
        rtmpCamera1.startPreview();
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        mRecordPresenter.surfaceTextureDestroyed(rtmpCamera1);
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
                mRecordPresenter.startStreamPhp();

            }
        });
    }

    @Override
    public void onConnectionFailedRtmp(final String reason) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRecordPresenter.connetionErorr(rtmpCamera1);
            }
        });
    }

    @Override
    public void onDisconnectRtmp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRecordPresenter.endStream();
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

    }


    @Override
    public void sayStreaming() {
        Toast.makeText(ActivityReadyRecord.this, "방송을 시작합니다", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sayStreamEnd() {
        Toast.makeText(ActivityReadyRecord.this, "방송을 종료합니다", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErorr() {
        Toast.makeText(ActivityReadyRecord.this, "문제가 발생하였습니다", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getTexturViewListener() {
        binding.textureView.setSurfaceTextureListener(this);
    }

    @Override
    public void cameraOn() {
        rtmpCamera1.startPreview();
    }

    @Override
    public void viewGone() {
        binding.txt1.setVisibility(View.GONE);
        binding.txt2.setVisibility(View.GONE);
        binding.image1.setVisibility(View.GONE);
    }

    @Override
    public void setRecordImg() {
        binding.bStartStop.setImageResource(R.drawable.record);
    }

    @Override
    public void setRecordingImg() {
        binding.bStartStop.setImageResource(R.drawable.recording);
    }

    @Override
    public RtmpCamera1 setTexturView() {
        return rtmpCamera1 = new RtmpCamera1(binding.textureView, this);
    }




}
