package com.example.admin.last.watchMvp;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.last.R;

import java.util.ArrayList;

public class WatchAdapter extends BaseAdapter {

    ArrayList<WatchItem> arrayList;
    Context context;
    LayoutInflater layoutInflater;

    public WatchAdapter(ArrayList<WatchItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        layoutInflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if(view == null){
            view = layoutInflater.inflate(R.layout.listview_watch_chat_item,viewGroup,false);
        }

        ImageView tv_watchChatProfile = (ImageView) view.findViewById(R.id.watch_chat_profile);
        TextView tv_watchChatTxt = (TextView) view.findViewById(R.id.watch_chat_txt);

        tv_watchChatProfile.setBackground(new ShapeDrawable(new OvalShape()));
        tv_watchChatProfile.setClipToOutline(true);
        Glide.with(context).load(arrayList.get(position).getWatch_chat_profile()).error(R.drawable.noimage).into(tv_watchChatProfile);

        tv_watchChatTxt.setText(arrayList.get(position).getWatch_chat_txt());

        return view;
    }
}
