package com.example.admin.last.mainfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.last.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBroadcastEnd extends Fragment {


    public FragmentBroadcastEnd() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_broadcast_end, container, false);
        return view;
    }

}
