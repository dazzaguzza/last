package com.example.admin.last.recordMvp;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

import com.example.admin.last.R;
import com.example.admin.last.databinding.ActivityReadyRecordBinding;
import com.example.admin.last.retrofit.ApiClient;
import com.example.admin.last.retrofit.ApiInterface;
import com.pedro.rtplibrary.rtmp.RtmpCamera1;

import net.ossrs.rtmp.ConnectCheckerRtmp;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityReadyRecord extends AppCompatActivity implements RecordView,ConnectCheckerRtmp, TextureView.SurfaceTextureListener{

    ActivityReadyRecordBinding binding;
    RecordPresenter mRecordPresenter;
    private RtmpCamera1 rtmpCamera1;
    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_ready_record);

        mRecordPresenter = new RecordPresenterImpl(this);

        mRecordPresenter.getRequest_permission(ActivityReadyRecord.this);

        rtmpCamera1 = new RtmpCamera1(binding.textureView, this);

        binding.textureView.setSurfaceTextureListener(this);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);



        binding.bStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!rtmpCamera1.isStreaming()) {
                    if (rtmpCamera1.isRecording()
                            || rtmpCamera1.prepareAudio() && rtmpCamera1.prepareVideo()) {
                        binding.txt1.setVisibility(View.GONE);
                        binding.txt2.setVisibility(View.GONE);
                        binding.image1.setVisibility(View.GONE);
                        binding.bStartStop.setImageResource(R.drawable.recording);
                        rtmpCamera1.startStream("rtmp://52.79.243.140/live/stream3");

                    } else {
                        Toast.makeText(ActivityReadyRecord.this, "Error preparing stream, This device cant do it", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.bStartStop.setImageResource(R.drawable.record);
                    rtmpCamera1.stopStream();
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

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
        rtmpCamera1.startPreview();
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        if (rtmpCamera1.isStreaming()) {
            rtmpCamera1.stopStream();
            binding.bStartStop.setImageResource(R.drawable.record);
         //   binding.bStartStop.setBackgroundColor(00000000);
        }
        rtmpCamera1.stopPreview();

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
                startStream();
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
                rtmpCamera1.stopStream();
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


    }

    void startStream(){
        Call<RecordData> call = apiInterface.setStream("rtmp://52.79.243.140/live/stream3","im");
       call.enqueue(new Callback<RecordData>() {
           @Override
           public void onResponse(Call<RecordData> call, Response<RecordData> response) {
               Log.d("TAG", "onResponse: 전송됨");
           }

           @Override
           public void onFailure(Call<RecordData> call, Throwable t) {
               Log.d("TAG", "onResponse: 실패");
           }
       });
    }

}
