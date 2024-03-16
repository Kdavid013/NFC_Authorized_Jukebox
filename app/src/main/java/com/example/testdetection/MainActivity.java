package com.example.testdetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;


import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


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