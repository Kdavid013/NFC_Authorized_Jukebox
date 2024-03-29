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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisztracioFragment extends Fragment {

    Button regisztracioButton;
    TextView nfcIdTextView;

    private NfcAdapter nfcAdapter;
    private String[][] techListsArray;
    PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray;

    private static Tag tag;
    private static final String TAG = "MyActivity";

    private static String tagId = "";

    private static final String dbAddress = "http://192.168.1.147/regisztracio.php?NFC_id=";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**nfc adapter mely felelős az nfc eléhetőségének lekérésére*/
        nfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        if (nfcAdapter == null) {
            Toast.makeText(getActivity(), "NFC is not available", Toast.LENGTH_LONG).show();
            return;
        }

        /**létrehozok egy intentet melyből ki fogom olvasni az nfc tipusát*/
        pendingIntent = PendingIntent.getActivity(getActivity(), 0, new Intent(getActivity(), getClass()).addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT), PendingIntent.FLAG_MUTABLE);
        //pendingIntent

        /**filter mely alapján kiválasztom milyen típusú nfc kártyákat olvassak be*/
        IntentFilter tech = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        intentFiltersArray = new IntentFilter[]{tech};
        techListsArray = new String[][]{new String[]{NfcF.class.getName()}};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_regisztracio,null);

        regisztracioButton = (Button) root.findViewById(R.id.regisztracioGomb);
        nfcIdTextView = root.findViewById(R.id.unipass_kartya_azonosito_adat);

        regisztracioButton.setOnClickListener(v -> {
            insertToDb();
            //logToDebug();
        });
        return root;

    }

    public void onPause() {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(getActivity());
        Log.d(TAG, "im on pause");
    }

    public void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(getActivity(), pendingIntent, intentFiltersArray, techListsArray);
        Log.d(TAG, "i'm on resume");
        readFromIntent(getActivity().getIntent());
        nfcIdTextView.setText(tagId);
    }


    /**
     * Az intentből kiszedi az adott NFC tag id-ját
     *
     * @param intent az a cselekvés amiből ki tudjuk szedni a NFC azonositó adatait
     */
    private void readFromIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            tagId = getHex(tag.getId());

            //Log.d(TAG,tagId);
        }
    }

    /**
     * Adat bázisba való adat felvitel
     * megkapja az nfc kártya adatait és azt hozzá adja az adatbázishoz
     * majd modosítani kell hogy egy felhasználó nevet is vigyen fel vele
     */

    public void insertToDb() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, dbAddress + tagId, response -> {
            //logToDebug();
            Log.d(TAG, response);
            if (response.equals("success")) {
                Toast.makeText(getActivity(), "sikeres regisztráció", Toast.LENGTH_SHORT).show();
            } else if (response.equals("failure")) {

                Toast.makeText(getActivity(), "sikertelen", Toast.LENGTH_SHORT).show();            }
        }, error -> {
            Toast.makeText(getActivity(), error.toString().trim(), Toast.LENGTH_SHORT).show();     }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //logToDebug();
                Map<String, String> data = new HashMap<>();
                data.put("NFC_id", tagId);
                Log.d(TAG, data.toString());
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

        Log.d(TAG, stringRequest.toString() + requestQueue.toString());
    }

    /**
     * Át alakítja a kapott bytes tömböb egy stringé
     *
     * @param bytes byte tömb ami az NFC tag id-ját tartalmazza
     */
    private String getHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int b = bytes[i] & 0xff;
            if (b < 0x10) sb.append('0');
            sb.append(Integer.toHexString(b));
            if (i >= 0) {
                sb.append("");
            }
        }
        //Log.d(TAG,sb.toString());
        return sb.toString().toUpperCase();
    }
}