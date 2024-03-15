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

    static String felhasznaloNev;
    static String uniPassKartyaAzonosito;
    static DateFormat sdf = DateFormat.getDateInstance();

    EditText felhasznalóNevET;
    TextView uniPassKartyaAzonositoTV;
    TextView csatlakozasDatumaTV;
    private static String csatlakozasDatuma;

    static public void setCsatlakozasDatuma(Date datum) {
        String newcsatlakozasDatuma = sdf.format(datum);
        csatlakozasDatuma = newcsatlakozasDatuma;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sajat_adatok,null);

        csatlakozasDatumaTV = root.findViewById(R.id.textViewCsatlakozasIdejeAdat);
        felhasznalóNevET = root.findViewById(R.id.text_View_Felhasznalo_Nev_Adat);
        uniPassKartyaAzonositoTV = root.findViewById(R.id.textViewNfcIdAdat);

        csatlakozasDatumaTV.setText(csatlakozasDatuma);


        // Inflate the layout for this fragment
        return root;
    }
}