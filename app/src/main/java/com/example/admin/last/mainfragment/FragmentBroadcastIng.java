package com.example.admin.last.mainfragment;


import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.last.AdapterIng;
import com.example.admin.last.ItemIng;
import com.example.admin.last.R;
import com.example.admin.last.databinding.FragmentBroadcastIngBinding;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBroadcastIng extends Fragment {

    LinearLayoutManager linearLayoutManager;
    AdapterIng adapter_ing;
    FragmentBroadcastIngBinding binding;
    ArrayList<ItemIng> arrayList = new ArrayList<>();
    bus bus;

    public interface bus {
        public void bus(RecyclerView recyclerView);
    }

    public FragmentBroadcastIng() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_broadcast_ing, container, false);
        View view = binding.getRoot();

        initView();

        bus.bus(binding.recycler);


        arrayList.add(new ItemIng("1",null));
        arrayList.add(new ItemIng("1",null));
        arrayList.add(new ItemIng("1",null));
        arrayList.add(new ItemIng("1",null));
        arrayList.add(new ItemIng("1",null));



        adapter_ing.notifyDataSetChanged();

        hideAndShowFab();

        return view;
    }

    private void initView() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(linearLayoutManager);
        adapter_ing = new AdapterIng(getContext(), R.layout.listview_ing_item, arrayList);
        binding.recycler.setAdapter(adapter_ing);
    }

    private void hideAndShowFab() {
        binding.recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy<0 && !binding.btnFab.isShown()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                    binding.btnFab.setClickable(true);
                    binding.btnFab.show();
                }
            },500);
                }else if(dy>0 && binding.btnFab.isShown()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.btnFab.hide();
                            binding.btnFab.setClickable(false);

                }
            },500);
                }
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        bus = (FragmentBroadcastIng.bus) activity;
    }

}
