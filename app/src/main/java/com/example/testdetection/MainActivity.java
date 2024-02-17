package com.example.testdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private Button insertButton;

    private static final String dbAddress = "http://192.168.31.109/temp_insert.php?NFC_id=";

    private static final String TAG = "MyActivity";
    private static String tagId = "";
    private static Tag tag;
    private Tag tagFromIntent;
    private NfcAdapter nfcAdapter;
    private String[][] techListsArray;
    PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_MUTABLE);
        IntentFilter tech = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        intentFiltersArray = new IntentFilter[] {tech};
        techListsArray = new String[][] { new String[] { NfcF.class.getName() } };
        insertButton = findViewById(R.id.dbInsertButton);
        insertButton.setOnClickListener(v -> {
            insertToDb();
            //logToDebug();
        });

    }


    public void onPause() {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
        Log.d(TAG,"im on pause");
    }

    public void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);
        Log.d(TAG,"i'm on resume");
        readFromIntent(getIntent());

    }
    /**
     *  Az intentből kiszedi az adott NFC tag id-ját
     * @param intent az a cselekvés amiből ki tudjuk szedni a NFC azonositó adatait
     * */
    private void readFromIntent(Intent intent){
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)){
           tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
           tagId = getHex(tag.getId());
           //Log.d(TAG,tagId);
        }
    }

    private void logToDebug(){
        Log.d(TAG,"itt vagyok");
    }

    public void insertToDb() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, dbAddress + tagId, response -> {
            //logToDebug();
            Log.d(TAG, response);
            if (response.equals("success")) {
                Toast.makeText(getApplicationContext(), "sikeres regisztráció", Toast.LENGTH_SHORT).show();
                logToDebug();
            } else if (response.equals("failure")) {
                logToDebug();
                Toast.makeText(getApplicationContext(), "sikertelen", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            logToDebug();
            Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //logToDebug();
                Map<String, String> data = new HashMap<>();
                data.put("NFC_id", tagId);
                Log.d(TAG,data.toString());
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        Log.d(TAG,stringRequest.toString() + requestQueue.toString());
    }

    /**
     *  Át alakítja a kapott bytes tömböb egy stringé
     * @param bytes byte tömb ami az NFC tag id-ját tartalmazza
     * */
    private String getHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
            if (i >= 0) {
                sb.append("");
            }
        }
        //Log.d(TAG,sb.toString());
        return sb.toString();
    }
}