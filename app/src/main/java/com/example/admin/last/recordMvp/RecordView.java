package com.example.admin.last.recordMvp;

import com.pedro.rtplibrary.rtmp.RtmpCamera1;

public interface RecordView {
   void sayStreaming();
   void sayStreamEnd();
   void viewGone();
   void setRecordImg();
   void setRecordingImg();
   RtmpCamera1 setTexturView();
   void showErorr();
   void getTexturViewListener();
   void cameraOn();
}
