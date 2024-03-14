package com.example.testdetection;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OsszesZeneFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_osszes_zene,container,false);
        ListView osszesZeneListView = root.findViewById(R.id.osszes_zene_list_view);

        String[] peldaErtekek = new String[]{
                "shajt","tészta","kenyér","tej"
        };

        List<String> list = new ArrayList<>(Arrays.asList(peldaErtekek));

        ListAdapter osszesZeneAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice,list);

        osszesZeneListView.setAdapter(osszesZeneAdapter);
        return root;
    }
}