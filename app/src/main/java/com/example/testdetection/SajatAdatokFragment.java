package com.example.testdetection;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SajatAdatokFragment extends Fragment {

     TextView felhasznaloNevTV;
    TextView uniPassKartyaAzonositoTV;
    TextView csatlakozasDatumaTV;

    TextView sajatDalokSzamaTV;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sajat_adatok,null);

        csatlakozasDatumaTV = root.findViewById(R.id.textViewCsatlakozasIdejeAdat);
        felhasznaloNevTV = root.findViewById(R.id.text_View_Felhasznalo_Nev_Adat);
        uniPassKartyaAzonositoTV = root.findViewById(R.id.textViewNfcIdAdat);
        sajatDalokSzamaTV = root.findViewById(R.id.textViewZenekSzamaAdat);

        csatlakozasDatumaTV.setText("");
        felhasznaloNevTV.setText("");
        uniPassKartyaAzonositoTV.setText("");
        sajatDalokSzamaTV.setText("");


        // Inflate the layout for this fragment
        return root;
    }
}