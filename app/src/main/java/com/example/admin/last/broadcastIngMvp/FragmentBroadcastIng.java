package com.example.admin.last.broadcastIngMvp;


import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.last.SharedPreferenceUtil;
import com.example.admin.last.recordMvp.ActivityReadyRecord;
import com.example.admin.last.R;
import com.example.admin.last.databinding.FragmentBroadcastIngBinding;
import com.example.admin.last.recordMvp.RecordData;
import com.example.admin.last.retrofit.ApiClient;
import com.example.admin.last.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBroadcastIng extends Fragment implements broadcastIngView, SwipeRefreshLayout.OnRefreshListener {

    LinearLayoutManager linearLayoutManager;
    AdapterIng adapter_ing;
    FragmentBroadcastIngBinding binding;
    broadcastIngPresenter mBroadcastIngPresenter;
    // ArrayList<ItemIng> arrayList = new ArrayList<>();
    IngViewModel ingViewModel;
    bus bus;

    @Override
    public void makeRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(linearLayoutManager);

        ingViewModel = ViewModelProviders.of(getActivity()).get(IngViewModel.class);
        adapter_ing = new AdapterIng(getContext());

        ingViewModel.ingPagedList.observe(getActivity(), new Observer<PagedList<ItemIng>>() {
            @Override
            public void onChanged(@Nullable final PagedList<ItemIng> itemIngs) {
                adapter_ing.submitList(itemIngs);
                binding.refresh.setRefreshing(false);
                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (itemIngs.size() != 0) {
                            hideTxt();
                        } else {
                            showTxt();
                        }
                    }
                };
                handler.postDelayed(runnable, 1000);

            }
        });
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

    @Override
    public void setRefresh() {
        binding.refresh.setOnRefreshListener(this);
    }

    @Override
    public void showTxt() {
        binding.RelWithTxt.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTxt() {
        binding.RelWithTxt.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
//        mBroadcastIngPresenter.setAllStreamingRoom(arrayList, adapter_ing);
//        if(arrayList.size() != 0){
//            binding.RelWithTxt.setVisibility(View.GONE);
//        }else{
//            binding.RelWithTxt.setVisibility(View.VISIBLE);
//        }
        ingViewModel.refresh();
        binding.refresh.setRefreshing(false);
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_broadcast_ing, container, false);
        View view = binding.getRoot();


        mBroadcastIngPresenter = new broadcastIngPresenterImpl(this);

        mBroadcastIngPresenter.setRefreshListner();

        mBroadcastIngPresenter.setRecyclerView();

        mBroadcastIngPresenter.getProfile(getActivity());


        // bus.bus(binding.recycler);

        binding.recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mBroadcastIngPresenter.startFloatingAction(binding.btnFab, dx, dy);
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
    public void onResume() {
        super.onResume();
        //  mBroadcastIngPresenter.setAllStreamingRoom(arrayList, adapter_ing);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        bus = (FragmentBroadcastIng.bus) activity;
    }

}
