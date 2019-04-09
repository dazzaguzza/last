package com.example.admin.last.broadcastIngMvp;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;

public class broadcastIngModelImpl implements broadcastIngModel {


    @Override
    public void floatingAction(FloatingActionButton floatingActionButton, int dx, int dy, final broadcastIngView broadcastIngView) {

        if (dy<0 && !floatingActionButton.isShown()) {
          //  new Handler().postDelayed(new Runnable() {
          //      @Override
          //      public void run() {
                    broadcastIngView.makeShowFloating();

          //      }
         //   },500);
        }else if(dy>0 && floatingActionButton.isShown()) {
         //   new Handler().postDelayed(new Runnable() {
         //       @Override
         //       public void run() {
                    broadcastIngView.makeHideFloating();

         //       }
        //    },500);
        }
    }
}
