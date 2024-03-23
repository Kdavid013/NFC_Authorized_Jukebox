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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SajatZenekFragment extends Fragment {

    MyRecycleViewAdapter adapter;
    String[] gyujtemeny;
    View root;
    TextView sajatDalokSzamaTV;

    static int sajatDalokSzama;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         root = inflater.inflate(R.layout.fragment_sajat_zenek,container,false);

        if (MainActivity.tagId == ""){
            Toast.makeText(getActivity(),"Érintse kártyájáz a telefonhoz a gyüjteménye megtekintéséhez.",Toast.LENGTH_LONG).show();
        }
        else {
            gyujtemenyLekerese();
        }
        sajatDalokSzamaTV = root.findViewById(R.id.sajat_dalok_szama);
        sajatDalokSzamaTV.setText(String.valueOf(sajatDalokSzama));

        // Inflate the layout for this fragment
        return root;

    }

    public void gyujtemenyLekerese() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MainActivity.gyujtemenyLekeres + MainActivity.tagId, response -> {
            //logToDebug();
            gyujtemeny = response.split(";");

            sajatDalokSzama = gyujtemeny.length;

            List<String> list = new ArrayList<String>();
            list.addAll(Arrays.asList(gyujtemeny));

            RecyclerView gyujtemenyRecyclerView = root.findViewById(R.id.sajatZenekListView);

            gyujtemenyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new MyRecycleViewAdapter(list,getLayoutInflater());


            gyujtemenyRecyclerView.setAdapter(adapter);

        }, error -> {
            Toast.makeText(getActivity(), error.toString().trim(), Toast.LENGTH_SHORT).show();
        }) {
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
}