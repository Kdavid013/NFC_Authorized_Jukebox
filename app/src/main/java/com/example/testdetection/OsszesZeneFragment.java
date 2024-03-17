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
import android.widget.Button;
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
    MyRecycleViewAdapterWithCheckbox adapter;
    Button dalokHozzadasaGomb;

    String[] lekertOsszesZene;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_osszes_zene,container,false);
        dalokHozzadasaGomb = root.findViewById(R.id.dalok_hozzadas_gomb);

        osszesZeneLekeres();
        dalokHozzadasaGomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("DEBUG",adapter.listItemSelected());
                kivalasztottZenekHozzadasaGyujtemenyhez(adapter.listItemSelected());
            }
        });
        return root;
    }

    public void osszesZeneLekeres() {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, MainActivity.osszesZeneLekeroCim, response -> {
                //logToDebug();
                lekertOsszesZene = response.split(";");

                List<ModelClass> list = new ArrayList<ModelClass>();
                for (String e: lekertOsszesZene) {
                    String[] zeneDarabok = e.split(":");
                    list.add(new ModelClass( zeneDarabok[0],zeneDarabok[1],false));
                }
                RecyclerView osszesZeneRecyclerView = root.findViewById(R.id.osszes_zene_list_view);

                osszesZeneRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new MyRecycleViewAdapterWithCheckbox(getLayoutInflater(),list);


                osszesZeneRecyclerView.setAdapter(adapter);

            }, error -> {
                Toast.makeText(getActivity(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }) {
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);

    }
    public void kivalasztottZenekHozzadasaGyujtemenyhez(String kivalasztottZenek){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MainActivity.kivalasztottZenekHozzaadasa + MainActivity.tagId +kivalasztottZenek, response -> {

            if(response.equals("success")){
                Toast.makeText(getActivity(),"A dalok sikeresen hozzá lettek adva a Gyűjteményhez.",Toast.LENGTH_SHORT).show();
            } else
            {
                Toast.makeText(getActivity(),"Probléma történt. Kérem ellenőrizze hogy van e kártya csatlakoztatva a telefonhoz.",Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(getActivity(), error.toString().trim(), Toast.LENGTH_SHORT).show();
        }) {
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}