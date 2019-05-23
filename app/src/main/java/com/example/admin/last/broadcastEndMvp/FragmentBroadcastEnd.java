package com.example.admin.last.broadcastEndMvp;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.admin.last.R;
import com.example.admin.last.databinding.FragmentBroadcastEndBinding;
import com.example.admin.last.watchVodMvp.WatchVod;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBroadcastEnd extends Fragment implements BroadcastEndView{

    AdapterEnd adapterEnd;
    ArrayList<ItemEnd> arrayList;
    ArrayList<ItemEnd> copyArrayList;
    FragmentBroadcastEndBinding binding;
    BroadcastEndPresenter mBroadcastEndPresenter;

    public FragmentBroadcastEnd() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_broadcast_end, container, false);
        View view  = binding.getRoot();

        mBroadcastEndPresenter = new BroadcastEndPresenterImpl(this);

        mBroadcastEndPresenter.setListView();

        mBroadcastEndPresenter.setVodList(arrayList,adapterEnd,copyArrayList);

        binding.findVOD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mBroadcastEndPresenter.startSearchVOD(arrayList,copyArrayList,adapterEnd,binding.findVOD.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        binding.EndListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mBroadcastEndPresenter.clickVodListView(arrayList,position);
            }
        });

        return view;
    }


    @Override
    public void makeListView() {
        arrayList = new ArrayList<>();
        copyArrayList = new ArrayList<>();
        adapterEnd = new AdapterEnd(getActivity(),arrayList);
        binding.EndListView.setAdapter(adapterEnd);
    }



    @Override
    public void goToWatchVod(ArrayList<ItemEnd> arrayList,int position) {
        Intent intent = new Intent(getActivity(), WatchVod.class);
        intent.putExtra("vodUrl", arrayList.get(position).getAddress());
        startActivity(intent);
    }

    @Override
    public void showTxt() {
        binding.RelWithTxtEnd.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTxt() {
        binding.RelWithTxtEnd.setVisibility(View.GONE);
    }
}
