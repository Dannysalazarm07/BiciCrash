package unal.edu.co.bicicrash.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.android.gms.maps.MapFragment;

import java.util.ArrayList;
import java.util.List;

import unal.edu.co.bicicrash.fragments.BiciMapFragment;

/**
 * Un {@link FragmentPagerAdapter} que gestiona las secciones, fragmentos y
 * títulos de las pestañas
 */
public class SectionsPagerAdapterForMainActivity extends FragmentPagerAdapter {
    Fragment mFragment;
    String mFragmentTitle;
    BiciMapFragment  mBiciMapFragment;
    String mBiciMapFragmentTitle;

    public SectionsPagerAdapterForMainActivity(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return mFragment;
            case 1:
                return mBiciMapFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public void addFragment(Fragment fragment, String title) {
        mFragment = fragment;
        mFragmentTitle = title;
    }

    public void addBiciMapFragment(BiciMapFragment biciMapFragment, String title) {
        mBiciMapFragment = biciMapFragment;
        mBiciMapFragmentTitle = title;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mFragmentTitle;
            case 1:
                return mBiciMapFragmentTitle;
            default:
                return null;
        }
    }
}