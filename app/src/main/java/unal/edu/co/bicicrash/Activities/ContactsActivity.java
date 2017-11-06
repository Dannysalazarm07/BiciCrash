package unal.edu.co.bicicrash.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import unal.edu.co.bicicrash.Fragments.BiciMapFragment;
import unal.edu.co.bicicrash.Fragments.EmailContactsFragment;
import unal.edu.co.bicicrash.Fragments.MainFragment;
import unal.edu.co.bicicrash.Fragments.PhoneContactsFragment;
import unal.edu.co.bicicrash.R;
import unal.edu.co.bicicrash.Utils.AdapterItem;
import unal.edu.co.bicicrash.Utils.BiciContact;
import unal.edu.co.bicicrash.Utils.SectionsPagerAdapter;
import unal.edu.co.bicicrash.Utils.SectionsPagerAdapterForMainActivity;


public class ContactsActivity extends AppCompatActivity {

    private ArrayList arrayContacts;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // Setear adaptador al viewpager.
        mViewPager = (ViewPager) findViewById(R.id.contacts_pager);
        setupViewPager(mViewPager);

        // Preparar las pestañas
        TabLayout tabs = (TabLayout) findViewById(R.id.contact_tabs);
        tabs.setupWithViewPager(mViewPager);

    }

    //En este metodo se agregan los Fragmentos a la activity
    private void setupViewPager(ViewPager viewPager) {
        //El adaptador enlaza las biñetas del ViewPager con cada Fragment.
        // Administra la forma de mostar las Fragments
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new PhoneContactsFragment(), "Phone");
        adapter.addFragment(new EmailContactsFragment(), "Correo");

        viewPager.setAdapter(adapter);
    }



}
