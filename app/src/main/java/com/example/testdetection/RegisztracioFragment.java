package com.example.testdetection;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisztracioFragment extends Fragment {

    Button regisztracioButton;
    TextView nfcIdTextView;

    private NfcAdapter nfcAdapter;
    private String[][] techListsArray;
    PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray;

    private static Tag tag;
    private static final String TAG = "MyActivity";

    public static String tagId = "";


    EditText felhasznaloNevET;
    String csatlakozasIdeje;

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_regisztracio,null);

        regisztracioButton = root.findViewById(R.id.regisztracioGomb);
        nfcIdTextView = root.findViewById(R.id.unipass_kartya_azonosito_adat);
        felhasznaloNevET = root.findViewById(R.id.regisztracio_felhasznalo_nev_adat);

        nfcIdTextView.setText(MainActivity.tagId);

        regisztracioButton.setOnClickListener(v -> {

            if (!(tagId.equals("") || felhasznaloNevET.getText().toString().equals(""))) {
                insertToDb();
                nfcIdTextView.setText("");
                felhasznaloNevET.setText("");
            }
            else {
                Toast.makeText(getActivity(),"Hiányzó információ! Ellenőrizze a megadott adatokat!",Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }

    /**
     * Adat bázisba való adat felvitel
     * megkapja az nfc kártya adatait és azt hozzá adja az adatbázishoz
     * majd modosítani kell hogy egy felhasználó nevet is vigyen fel vele
     */

    public void insertToDb() {
            StringRequest stringRequest = new StringRequest(Request.Method.GET,MainActivity.regisztracioDbCim + MainActivity.tagId + "&fn=" + felhasznaloNevET.getText() + "&csi=" + df.format(new Date()), response -> {
                //logToDebug();
                Log.d(TAG, response);
                if (response.equals("success")) {
                    Toast.makeText(getActivity(), "sikeres regisztráció", Toast.LENGTH_SHORT).show();
                } else if (response.equals("failure")) {

                    Toast.makeText(getActivity(), "sikertelen", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getActivity(), "A kártya már regisztrálva van", Toast.LENGTH_SHORT).show();
                }
            }, error -> {
                Toast.makeText(getActivity(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }) {
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);

    }

}