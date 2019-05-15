package com.example.admin.last.broadcastEndMvp;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.last.R;
import com.example.admin.last.databinding.FragmentBroadcastEndBinding;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBroadcastEnd extends Fragment implements BroadcastEndView{

    AdapterEnd adapterEnd;
    ArrayList<ItemEnd> arrayList;
    FragmentBroadcastEndBinding binding;

    public FragmentBroadcastEnd() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_broadcast_end, container, false);
        View view  = binding.getRoot();

        init();

        return view;
    }

    void init() {
        arrayList = new ArrayList<>();
        adapterEnd = new AdapterEnd(getActivity(),arrayList);
        binding.EndListView.setAdapter(adapterEnd);
        adapterEnd.notifyDataSetChanged();

    }

}
