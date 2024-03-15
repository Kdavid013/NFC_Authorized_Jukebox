package com.example.testdetection;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OsszesZeneFragment extends Fragment {

    MyRecycleViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_osszes_zene,container,false);
        RecyclerView osszesZeneRecyclerView = root.findViewById(R.id.osszes_zene_list_view);

        String[] peldaErtekek = new String[]{
                "shajt","tészta","kenyér","tej"
        };

        List<String> list = new ArrayList<>(Arrays.asList(peldaErtekek));


        osszesZeneRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyRecycleViewAdapter(list,getLayoutInflater());

        osszesZeneRecyclerView.setAdapter(adapter);
        return root;
    }
}