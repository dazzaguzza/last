package com.example.admin.last.broadcastIngMvp;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.admin.last.R;
import com.example.admin.last.watchMvp.ActivityWatch;

import java.util.ArrayList;

public class AdapterIng extends RecyclerView.Adapter<AdapterIng.MyViewHolder> {

    private Context context;
    private ArrayList<ItemIng> arrayList;

    public AdapterIng(Context context, ArrayList<ItemIng> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public AdapterIng.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_ing_item, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterIng.MyViewHolder holder, final int position) {

        final ItemIng data = arrayList.get(position);

        holder.tv_txt_id.setText(data.getTxt_id());
       try{

           if(data.getRoom_name().equals(null)){
               holder.tv_txtRoomName.setVisibility(View.GONE);
           }else {
               holder.tv_txtRoomName.setVisibility(View.VISIBLE);
               holder.tv_txtRoomName.setText(data.getRoom_name());
           }

       }catch (Exception e){}

        Glide.with(context).load(data.getImg_contents()).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).error(R.drawable.noimage).into(holder.tv_img_contents);


        holder.tv_img_profile.setBackground(new ShapeDrawable(new OvalShape()));
        holder.tv_img_profile.setClipToOutline(true);

        Glide.with(context).load(data.getImg_profile()).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).error(R.drawable.noimage).into(holder.tv_img_profile);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityWatch.class);
                intent.putExtra("url", data.getUrl());
                intent.putExtra("key",data.getKey());
                context.startActivity(intent);
                Log.d("TAG", "onClick: " + data.getUrl());
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_txt_id, tv_txtRoomName;
        ImageView tv_img_contents, tv_img_profile;


        public MyViewHolder(View itemView) {
            super(itemView);

            tv_img_profile = (ImageView) itemView.findViewById(R.id.img_profile);
            tv_txt_id = (TextView) itemView.findViewById(R.id.txt_id);
            tv_img_contents = (ImageView) itemView.findViewById(R.id.img_contents);
            tv_txtRoomName = (TextView) itemView.findViewById(R.id.txtRoomName);
        }
    }
}
