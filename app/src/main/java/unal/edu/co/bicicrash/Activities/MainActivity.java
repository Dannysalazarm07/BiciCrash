package unal.edu.co.bicicrash.Activities;

import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import unal.edu.co.bicicrash.R;
import unal.edu.co.bicicrash.fragments.MainFragment;
import unal.edu.co.bicicrash.fragments.MapFragment;
import unal.edu.co.bicicrash.Utils.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity{

    ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setear adaptador al viewpager.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(mViewPager);

        // Preparar las pestañas
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent ListSong = new Intent(getApplicationContext(), ToolsActivity.class );
                startActivity(ListSong);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //En este metodo se agregan los Fragmentos a la activity
    private void setupViewPager(ViewPager viewPager) {
        //El adaptador enlaza las biñetas del ViewPager con cada Fragment.
        // Administra la forma de mostar las Fragments
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        //Se agregaa el Fragment pricipal que muestra las señales del acelerometro
        adapter.addFragment(new MainFragment(), getString(R.string.title_section1));
        //Se agregaa el Fragment pricipal que muestra el mapa de accidentes
        adapter.addFragment(new MapFragment(), getString(R.string.title_section2));

        viewPager.setAdapter(adapter);

    }


}
