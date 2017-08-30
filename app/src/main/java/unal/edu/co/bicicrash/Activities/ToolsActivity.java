package unal.edu.co.bicicrash.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import unal.edu.co.bicicrash.Utils.SectionsPagerAdapter;


import java.util.ArrayList;
import java.util.List;

import unal.edu.co.bicicrash.R;
import unal.edu.co.bicicrash.fragments.ContactsFragment;
import unal.edu.co.bicicrash.fragments.InformationFragment;
import unal.edu.co.bicicrash.fragments.MainFragment;
import unal.edu.co.bicicrash.fragments.MapFragment;
import unal.edu.co.bicicrash.fragments.SettingsFragment;

public class ToolsActivity extends AppCompatActivity {
    ViewPager mViewPagerTool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setear adaptador al viewpager.
        mViewPagerTool = (ViewPager) findViewById(R.id.pagerTool);
        setupViewPager(mViewPagerTool);

        // Preparar las pestañas
        TabLayout tabs = (TabLayout) findViewById(R.id.tabsTool);
        tabs.setupWithViewPager(mViewPagerTool);
    }


    //En este metodo se agregan los Fragmentos a la activity
    private void setupViewPager(ViewPager viewPager) {
        //El adaptador enlaza las binetas del ViewPager con cada Fragment.
        // Administra la forma de mostar las Fragments
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        //Se agregaa el Fragment pricipal que muestra los contactos de emergencia
        adapter.addFragment(new ContactsFragment(), getString(R.string.title_section3));
        //Se agregaa el Fragment que muestra la información de emergencia
        adapter.addFragment(new InformationFragment(), getString(R.string.title_section4));
        //Se agregaa el Fragment pricipal que muestra las configuraciones de la APP
        adapter.addFragment(new SettingsFragment(), getString(R.string.title_section5));

        viewPager.setAdapter(adapter);

    }



}
