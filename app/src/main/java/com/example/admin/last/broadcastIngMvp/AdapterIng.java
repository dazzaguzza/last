package com.example.admin.last.broadcastIngMvp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.last.R;

import java.util.ArrayList;

public class AdapterIng extends RecyclerView.Adapter<AdapterIng.MyViewHolder>{

    private Context context;
    private ArrayList<ItemIng> arrayList;

    public AdapterIng(Context context,ArrayList<ItemIng> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public AdapterIng.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_ing_item, parent,false);

        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterIng.MyViewHolder holder, int position) {

        ItemIng data = arrayList.get(position);

        holder.tv_txt_id.setText(data.getTxt_id());

        Glide.with(context).load(data.getImg_contents()).into(holder.tv_img_contents);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_txt_id;
        ImageView tv_img_contents;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_txt_id = (TextView) itemView.findViewById(R.id.txt_id);
            tv_img_contents = (ImageView) itemView.findViewById(R.id.img_contents);
        }
    }
}
