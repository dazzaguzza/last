package com.example.admin.last.recordMvp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.last.R;
import com.pedro.rtplibrary.rtmp.RtmpCamera1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class RecordPresenterImpl implements RecordPresenter {

    RecordView mRecordView;
    RecordModel mRecordModel;
    String key;
    Context context;

    private  static final  String HOST ="52.79.243.140";
    private static final int PORT = 5001;
    Handler handler;
    SocketChannel socketChannel;
    String data;

    ArrayList<RecordChatItem> arrayList;
    RecordChatAdapter adapter;

    public RecordPresenterImpl(RecordView mRecordView,Context context) {
        this.mRecordView = mRecordView;
        mRecordModel = new RecordModelImpl();
        this.context = context;
      //  key = mRecordModel.makeKey(context);
        if(mRecordModel.getNaverUserNumber(context) != null) {
            key = mRecordModel.getNaverUserNumber(context);
        }else if(mRecordModel.getKakaoUserNumber(context) !=null){
            key = mRecordModel.getKakaoUserNumber(context);
        }
    }

    @Override
    public void getRequest_permission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA
                , Manifest.permission.RECORD_AUDIO
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE
                }, 100);
    }

    @Override
    public void getRequest_result(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 100
                && permissions.length == 4
                && permissions[0].equals(Manifest.permission.CAMERA)
                && permissions[1].equals(Manifest.permission.RECORD_AUDIO)
                && permissions[2].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && permissions[3].equals(Manifest.permission.READ_EXTERNAL_STORAGE)


                && grantResults[0] == PermissionChecker.PERMISSION_GRANTED
                && grantResults[1] == PermissionChecker.PERMISSION_GRANTED
                && grantResults[2] == PermissionChecker.PERMISSION_GRANTED
                && grantResults[3] == PermissionChecker.PERMISSION_GRANTED) {

            mRecordView.viewShow();
            mRecordView.setTexturView();
            mRecordView.getTexturViewListener();
            mRecordView.cameraOn();

        } else {
            mRecordView.viewGone();
        }


    }

    @Override
    public void startStreamPhp(Context context) {
        mRecordModel.setStream(context,key);
        mRecordView.sayStreaming();
        Log.d("TAG", "startStreamPhp: "+key);
    }

    @Override
    public void endStream() {
        mRecordView.sayStreamEnd();
    }

    @Override
    public void streamOrNot(RtmpCamera1 rtmpCamera1) {
        try {

            if (!rtmpCamera1.isStreaming()) {
                if (rtmpCamera1.isRecording()
                        || rtmpCamera1.prepareAudio() && rtmpCamera1.prepareVideo()) {

                    mRecordView.viewGone();
                    mRecordView.setRecordingImg();
                    mRecordView.showSetRoomName();
                    mRecordModel.startStreamCamera1(rtmpCamera1, key);

                } else {

                }
            } else {
                mRecordView.setRecordImg();
                mRecordModel.stopStreamCamera1(rtmpCamera1,key);
                mRecordView.hideSetRoomName();
                mRecordView.showDialog();

            }
        } catch (Exception e) {
        }
    }

    @Override
    public void connetionErorr(RtmpCamera1 rtmpCamera1) {
        mRecordModel.endOfStream(rtmpCamera1,context);
        mRecordView.showErorr();
        mRecordView.setRecordImg();
        mRecordView.hideSetRoomName();
    }

    @Override
    public void surfaceTextureDestroyed(RtmpCamera1 rtmpCamera1) {
        try {

            if (rtmpCamera1.isStreaming()) {
                mRecordModel.endOfStream(rtmpCamera1,context);
                mRecordView.setRecordImg();
                mRecordView.hideSetRoomName();

            }
            mRecordModel.stopPreview(rtmpCamera1);

        } catch (Exception e) {
        }
    }

    @Override
    public void end(RtmpCamera1 rtmpCamera1) {
        mRecordModel.endOfStream(rtmpCamera1,context);
        mRecordView.setRecordImg();
    }

    @Override
    public void makeRoomName(String string) {
        if(!string.isEmpty()) {
            mRecordModel.setRoomName(string);
        }else{
            mRecordView.toastFillRoomName();
        }

    }

    @Override
    public void socketOpenAndReceive(ListView listView , Context context) {

        arrayList = new ArrayList<>();
        adapter = new RecordChatAdapter(arrayList,context);
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

    @Override
    public void socketDestroy() {
        try {
            socketChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeCameraBackOrFront() {
        mRecordView.switchCamera();
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

            arrayList.add(new RecordChatItem(words[0],words[1]));
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

}
