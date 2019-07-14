package com.example.admin.last.watchVodMvp;

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
import com.example.admin.last.broadcastEndMvp.ItemEnd;

import java.util.ArrayList;

public class WatchVodAdapter extends BaseAdapter {

    ArrayList<ItemEnd> arrayList;
    Context context;
    LayoutInflater layoutInflater;

    public WatchVodAdapter(ArrayList<ItemEnd> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
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
            view = layoutInflater.inflate(R.layout.listview_watch_vod_item, viewGroup, false);
        }

        ImageView tv_vodImgProfile = (ImageView) view.findViewById(R.id.vodImgProfile);
        TextView tv_vodTxtId = (TextView) view.findViewById(R.id.vodTxtId);
        ImageView tv_vodThumbnail = (ImageView) view.findViewById(R.id.vodThumbnail);
        TextView tv_vodTitle = (TextView) view.findViewById(R.id.vodTitle);

        tv_vodImgProfile.setBackground(new ShapeDrawable(new OvalShape()));
        tv_vodImgProfile.setClipToOutline(true);

        Glide.with(context).load(arrayList.get(position).getImgProfile()).error(R.drawable.noimage).into(tv_vodImgProfile);
        Glide.with(context).load(arrayList.get(position).getThumbnail()).error(R.drawable.noimage).into(tv_vodThumbnail);
        tv_vodTxtId.setText(arrayList.get(position).getTxtId());
        tv_vodTitle.setText(arrayList.get(position).getTitle());

        return view;
    }
}
