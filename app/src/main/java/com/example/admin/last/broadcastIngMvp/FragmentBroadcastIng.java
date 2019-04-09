package com.example.admin.last.broadcastIngMvp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.last.recordMvp.ActivityReadyRecord;
import com.example.admin.last.R;
import com.example.admin.last.databinding.FragmentBroadcastIngBinding;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBroadcastIng extends Fragment implements broadcastIngView{

    LinearLayoutManager linearLayoutManager;
    AdapterIng adapter_ing;
    FragmentBroadcastIngBinding binding;
    broadcastIngPresenter mBroadcastIngPresenter;
    ArrayList<ItemIng> arrayList = new ArrayList<>();
    bus bus;

    @Override
    public void makeRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(linearLayoutManager);
        adapter_ing = new AdapterIng(getContext(), arrayList);
        binding.recycler.setAdapter(adapter_ing);
    }

    @Override
    public void makeHideFloating() {
        binding.btnFab.hide();
        binding.btnFab.setClickable(false);
    }

    @Override
    public void makeShowFloating() {
        binding.btnFab.setClickable(true);
        binding.btnFab.show();
    }

    @Override
    public void clickBroadcast() {
        startActivity(new Intent(getActivity(), ActivityReadyRecord.class));
    }

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
        mBroadcastIngPresenter = new broadcastIngPresenterImpl(this);

        mBroadcastIngPresenter.setRecyclerView();

       // bus.bus(binding.recycler);


        arrayList.add(new ItemIng("1",null));
        arrayList.add(new ItemIng("1",null));
        arrayList.add(new ItemIng("1",null));
        arrayList.add(new ItemIng("1",null));
        arrayList.add(new ItemIng("1",null));



        adapter_ing.notifyDataSetChanged();



        binding.recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mBroadcastIngPresenter.startFloatingAction(binding.btnFab,dx,dy);
            }
        });

        binding.btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBroadcastIngPresenter.clickedBroadcastFloating();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        bus = (FragmentBroadcastIng.bus) activity;
    }

}
