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
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SajatZenekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SajatZenekFragment extends Fragment {

    ListView listViewSajatZenek;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SajatZenekFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SajatZenekFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SajatZenekFragment newInstance(String param1, String param2) {
        SajatZenekFragment fragment = new SajatZenekFragment();
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
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sajat_zenek,null);

        listViewSajatZenek = root.findViewById(R.id.sajatZenekListView);

        String[] peldaErtekek = new String[]{
                "shajt","tészta","kenyér","tej"
        };
        List<String> list = new ArrayList<>(Arrays.asList(peldaErtekek));

        ListAdapter sajatZeneAdapter = new ArrayAdapter<String>(getActivity(), R.layout.text_view_for_list_item,R.id.text_view_for_list_item,list);


        listViewSajatZenek.setAdapter(sajatZeneAdapter);

        // Inflate the layout for this fragment
        return root;
    }
}