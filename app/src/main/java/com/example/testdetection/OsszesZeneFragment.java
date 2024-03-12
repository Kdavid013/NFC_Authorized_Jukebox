package com.example.testdetection;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OsszesZeneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OsszesZeneFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OsszesZeneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OsszesZeneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OsszesZeneFragment newInstance(String param1, String param2) {
        OsszesZeneFragment fragment = new OsszesZeneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contentView = inflater.inflate(R.layout.fragment_osszes_zene,container,false);
        ListView osszesZeneListView = contentView.findViewById(R.id.osszes_zene_list_view);


        String[] peldaErtekek = new String[]{
                "shajt","tészta","kenyér","tej"
        };
        List<String> list = new ArrayList<>();

        for(int i = 0; i < peldaErtekek.length;i++){
            list.add(peldaErtekek[i]);
        }

        CustomAdapterOsszesZene listAdapter = new CustomAdapterOsszesZene(list);
        osszesZeneListView.setAdapter(listAdapter);

        return contentView;
    }
}