package com.example.admin.last.recordMvp;

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

public class RecordChatAdapter extends BaseAdapter {

    ArrayList<RecordChatItem> arrayList;
    Context context;
    LayoutInflater layoutInflater;

    public RecordChatAdapter(ArrayList<RecordChatItem> arrayList, Context context) {
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

        if (view == null){
            view =layoutInflater.inflate(R.layout.listview_record_chat_item,viewGroup,false);
        }

        ImageView tv_imgProfile = (ImageView) view.findViewById(R.id.chat_profile);
        TextView tv_txtId = (TextView) view.findViewById(R.id.chat_txt);

        tv_imgProfile.setBackground(new ShapeDrawable(new OvalShape()));
        tv_imgProfile.setClipToOutline(true);
        Glide.with(context).load(arrayList.get(position).getChat_profile()).error(R.drawable.noimage).into(tv_imgProfile);

        tv_txtId.setText(arrayList.get(position).getChat_txt());

        return view;
    }
}
