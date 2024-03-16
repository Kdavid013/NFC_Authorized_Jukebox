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
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    static final String regisztracioDbCim = "http://192.168.1.147/regisztracio.php?ID=";
    static final String sajatAdatLekeroCim = "http://192.168.1.147/sajatAdatLekeres.php?ID=";
    static final String osszesZeneLekeroCim = "http://192.168.1.147/osszesZeneLekeres.php";

    static String tagId ="";

    private NfcAdapter nfcAdapter;
    private String[][] techListsArray;
    PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray;

    /**
     * debug tag
     */
    String TAG = "debug";


    /**
     * A megjelenítéshez használatos változók
     */
    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    public Toolbar toolbar;

    /**
     * Példa lista a lista nézet tesztelésére
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SajatZenekFragment()).commit();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open_drawer_menu, R.string.Close_drawer_menu);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        /**nfc adapter mely felelős az nfc eléhetőségének lekérésére*/
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            return;
        }

        /**létrehozok egy intentet melyből ki fogom olvasni az nfc tipusát*/
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT), PendingIntent.FLAG_MUTABLE);
        //pendingIntent

        /**filter mely alapján kiválasztom milyen típusú nfc kártyákat olvassak be*/
        IntentFilter tech = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        intentFiltersArray = new IntentFilter[]{tech};
        techListsArray = new String[][]{new String[]{NfcF.class.getName()}};
    }

    private void logToDebug() {
        Log.d(TAG, "itt vagyok");
    }

    public void onPause() {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
        Log.d(TAG, "im on pause");
    }

    public void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);
        readFromIntent(this.getIntent());
        //nfcIdTextView.setText(tagId);
    }

    /**
     * Az intentből kiszedi az adott NFC tag id-ját
     *
     * @param intent az a cselekvés amiből ki tudjuk szedni a NFC azonositó adatait
     */
    private void readFromIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(this.getIntent().getAction())) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            MainActivity.tagId = getHex(tag.getId());
            //Log.d(TAG,tagId);
        }
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
            toolbar.setTitle("Összes Zene");

        } else if (item.getItemId() == R.id.nav_sajat_adatok) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SajatAdatokFragment()).commit();
            toolbar.setTitle("Saját Adatok");

        } else if (item.getItemId() == R.id.nav_regisztracio) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegisztracioFragment()).commit();
            toolbar.setTitle("Regisztráció");

        } else if (item.getItemId() == R.id.nav_sajat_szamok) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SajatZenekFragment()).commit();
            toolbar.setTitle("Gyűjteményem");

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