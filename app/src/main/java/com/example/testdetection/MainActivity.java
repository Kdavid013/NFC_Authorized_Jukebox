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

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Button insertButton;

    private TextView nfcIdTextViewRegisztracio;

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
    public Toolbar toolbar;

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    /**
     * Példa lista a lista nézet tesztelésére
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SajatZenekFragment()).commit();

        //nfc adapter mely felelős az nfc eléhetőségének lekérésére
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            return;
        }


        /** A navigation menü megjelenítése*/

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open_drawer_menu, R.string.Close_drawer_menu);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    private void logToDebug() {
        Log.d(TAG, "itt vagyok");
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