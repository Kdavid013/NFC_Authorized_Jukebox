package com.example.testdetection;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SajatZenekFragment extends Fragment {

    RecyclerView recyclerViewSajatZenek;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sajat_zenek,null);

        recyclerViewSajatZenek = root.findViewById(R.id.sajatZenekListView);

        String[] peldaErtekek = new String[]{
                "shajt","tészta","kenyér","tej"
        };
        List<String> list = new ArrayList<>(Arrays.asList(peldaErtekek));


        recyclerViewSajatZenek.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyRecycleViewAdapter adapter = new MyRecycleViewAdapter(list,getLayoutInflater(),R.id.text_view_for_list);

        recyclerViewSajatZenek.setAdapter(adapter);

        // Inflate the layout for this fragment
        return root;
    }
}