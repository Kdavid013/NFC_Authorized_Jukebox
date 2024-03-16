package com.example.testdetection;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OsszesZeneFragment extends Fragment {

    View root;

    MyRecycleViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_osszes_zene,container,false);
        return root;
    }

    public void osszesZeneLekeres() {

            StringRequest stringRequest = new StringRequest(Request.Method.GET, MainActivity.osszesZeneLekeroCim, response -> {
                //logToDebug();
                String[] data = response.split(";");

                List<String> list = new ArrayList<>(Arrays.asList((data)));
                RecyclerView osszesZeneRecyclerView = root.findViewById(R.id.osszes_zene_list_view);

                osszesZeneRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new MyRecycleViewAdapter(list,getLayoutInflater(), R.id.text_view_for_list);

                osszesZeneRecyclerView.setAdapter(adapter);

            }, error -> {
                Toast.makeText(getActivity(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }) {
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
    }
}