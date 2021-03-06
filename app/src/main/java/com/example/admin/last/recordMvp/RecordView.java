package com.example.admin.last.recordMvp;

import com.pedro.rtplibrary.rtmp.RtmpCamera1;

public interface RecordView {
   void sayStreaming();
   void sayStreamEnd();
   void viewGone();
   void viewShow();
   void setRecordImg();
   void setRecordingImg();
   RtmpCamera1 setTexturView();
   void showErorr();
   void getTexturViewListener();
   void cameraOn();
   void showDialog();
   void showSetRoomName();
   void hideSetRoomName();
   void toastFillRoomName();
   void switchCamera();
}
