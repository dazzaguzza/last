package com.example.admin.last.broadcastEndMvp;

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

public class AdapterEnd extends BaseAdapter {

    Context context;
    ArrayList<ItemEnd> arrayList;
    LayoutInflater layoutInflater;

    public AdapterEnd(Context context, ArrayList<ItemEnd> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
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

        if (view == null) {
            view = layoutInflater.inflate(R.layout.listview_end_item, viewGroup, false);
        }

        ImageView tv_imgProfile = (ImageView) view.findViewById(R.id.imgProfile);
        TextView tv_txtId = (TextView) view.findViewById(R.id.txtId);
        ImageView tv_thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        TextView tv_title = (TextView) view.findViewById(R.id.title);

        tv_imgProfile.setBackground(new ShapeDrawable(new OvalShape()));
        tv_imgProfile.setClipToOutline(true);

        Glide.with(context).load(arrayList.get(position).getImgProfile()).error(R.drawable.noimage).into(tv_imgProfile);
        Glide.with(context).load(arrayList.get(position).getThumbnail()).error(R.drawable.noimage).into(tv_thumbnail);
        tv_txtId.setText(arrayList.get(position).getTxtId());
        tv_title.setText(arrayList.get(position).getTitle());

        return view;
    }
}
