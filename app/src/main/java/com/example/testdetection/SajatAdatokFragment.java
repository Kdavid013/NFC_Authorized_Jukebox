package com.example.testdetection;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

        sajatAdatKeres();


        // Inflate the layout for this fragment
        return root;
    }


    public void sajatAdatKeres() {

        if (!MainActivity.tagId.equals("")) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, MainActivity.sajatAdatLekeroCim + MainActivity.tagId, response -> {
                //logToDebug();
                Log.d("Debug", response);
                if(response.equals("Nem regisztrált.")){
                    Toast.makeText(getActivity(),"A kártya még nem regisztrált.",Toast.LENGTH_SHORT).show();
                }
                else {
                    String[] data = response.split(";");
                    felhasznaloNevTV.setText(data[1]);
                    uniPassKartyaAzonositoTV.setText(data[0]);
                    csatlakozasDatumaTV.setText(data[2]);
                    sajatDalokSzamaTV.setText(String.valueOf(SajatZenekFragment.sajatDalokSzama));
                }

            }, error -> {
                Toast.makeText(getActivity(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }) {
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }
        else
            Toast.makeText(getActivity(),"Érints egy kártyát a telefonhoz az adatok megtekintéséhez",Toast.LENGTH_LONG).show();

    }
}