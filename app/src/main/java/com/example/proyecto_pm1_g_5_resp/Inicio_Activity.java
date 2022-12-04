package com.example.proyecto_pm1_g_5_resp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.proyecto_pm1_g_5_resp.adapters.AccesoTabsAdapter;
import com.google.android.material.tabs.TabLayout;

public class Inicio_Activity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager myPager;
    private TabLayout myTabs;
    private AccesoTabsAdapter accesoTabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        toolbar = (Toolbar) findViewById(R.id.app_main_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ProyectoPMI");
        myPager = (ViewPager) findViewById(R.id.main_tabs_pager);
        accesoTabsAdapter = new AccesoTabsAdapter(getSupportFragmentManager());
        myPager.setAdapter(accesoTabsAdapter);

        myTabs = (TabLayout) findViewById(R.id.main_tabs);
        myTabs.setupWithViewPager(myPager);
    }
}