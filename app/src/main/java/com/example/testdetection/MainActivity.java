package com.example.testdetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Bundle;


import android.util.Log;
import android.view.MenuItem;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Button insertButton;

    /**
     * Az adat bázis eléréséhez való váltózó
     */
    private static final String dbAddress = "http://192.168.31.109/temp_insert.php?NFC_id=";
    private static final String TAG = "MyActivity";
    private static String tagId = "";

    /**
     * debug tag
     */
    private static Tag tag;

    //NFC-hez használatos változók
    private NfcAdapter nfcAdapter;
    private String[][] techListsArray;
    PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray;


    /**
     * A megjelenítéshez használatos változók
     */
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private ListView osszesZeneListView;

    /**
     * Példa lista a lista nézet tesztelésére
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //nfc adapter mely felelős az nfc eléhetőségének lekérésére
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        /**létrehozok egy intentet melyből ki fogom olvasni az nfc tipusát*/
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_MUTABLE);

        /**filter mely alapján kiválasztom milyen típusú nfc kártyákat olvassak be*/
        IntentFilter tech = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        intentFiltersArray = new IntentFilter[]{tech};
        techListsArray = new String[][]{new String[]{NfcF.class.getName()}};

        /**button listener mely beolvassa a regisztrációt */
        //insertButton = findViewById(R.id.dbInsertButton);
//        insertButton.setOnClickListener(v -> {
//            insertToDb();
//            //logToDebug();
//        });

        /** A navigation menü megjelenítése*/

        Toolbar toolbar = findViewById(R.id.toolbar_shajt);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open_drawer_menu, R.string.Close_drawer_menu);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }


    public void onPause() {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
        Log.d(TAG, "im on pause");
    }

    public void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);
        Log.d(TAG, "i'm on resume");
        readFromIntent(getIntent());

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

    private void logToDebug() {
        Log.d(TAG, "itt vagyok");
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
                Toast.makeText(getApplicationContext(), "sikeres regisztráció", Toast.LENGTH_SHORT).show();
                logToDebug();
            } else if (response.equals("failure")) {
                logToDebug();
                Toast.makeText(getApplicationContext(), "sikertelen", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            logToDebug();
            Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //logToDebug();
                Map<String, String> data = new HashMap<>();
                data.put("NFC_id", tagId);
                Log.d(TAG, data.toString());
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
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
        return sb.toString();
    }

    /**
     * A nav menu kiválasztását figyeli az alapján dönti el mit kell megjeleníteni
     *
     * @param item meg kapja a menu azt az itemjét amire rákattintottunk és az alapján dönti el a megjelenítendő fragmentet
     * @return boolean egy bool értékkel tér vissza miszerint meg lett nyomva vagy nem
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_osszes_zene) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OsszesZeneFragment()).commit();

        } else if (item.getItemId() == R.id.nav_sajat_adatok) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SajatAdatokFragment()).commit();

        } else if (item.getItemId() == R.id.nav_regisztracio) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegisztracioFragment()).commit();

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * a vissza gomb megnyomásával zárja be a menut ha meg van nyitva,
     * ha nincs akkor az alapértelmezet funkciót használja
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}