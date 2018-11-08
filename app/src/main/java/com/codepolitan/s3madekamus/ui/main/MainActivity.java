package com.codepolitan.s3madekamus.ui.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.codepolitan.s3madekamus.adapter.SearchKamusAdapter;
import com.codepolitan.s3madekamus.helper.KamusHelper;
import com.codepolitan.s3madekamus.helper.PrefManager;
import com.codepolitan.s3madekamus.model.KamusModel;
import com.codepolitan.s3madekamus.ui.mainFragment.MainFragment;
import com.codepolitan.s3madekamus.R;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        prefManager = new PrefManager(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.frame_container, new MainFragment())
                .commit();

        loadLocale();

        navigationView.setNavigationItemSelectedListener(this);
    }

    void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Configuration config = new Configuration();

        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        if (prefManager.getFirstRun()) {
            prefManager.setFirstRun(false);
            recreate();
        }
    }

    void loadLocale() {
        String lang = prefManager.getLangSelected();

        switch (lang) {
            case "en":
                setLocale("en");
                break;
            case "id":
                setLocale("id");
                break;
            default:
                setLocale("en");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_en_in) {
            prefManager.setIsEnglish(true);
            prefManager.setLangSelected("en");
            setLangRecreate("en");
        } else if (id == R.id.nav_in_en) {
            prefManager.setIsEnglish(false);
            prefManager.setLangSelected("id");
            setLangRecreate("id");
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setLangRecreate(String langval) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        Locale locale = new Locale(langval);
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }

}
