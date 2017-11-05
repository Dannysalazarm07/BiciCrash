package unal.edu.co.bicicrash.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import unal.edu.co.bicicrash.Activities.SettingsActivity;
import unal.edu.co.bicicrash.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean syncConnPref = sharedPref.getBoolean("facebookPublic",false);
        String mensaje = sharedPref.getString("message", "Ayuda, me he esrellado");
        String time = sharedPref.getString("TimeForWait","20");
        Toast toast = Toast.makeText(getActivity(), String.valueOf(time), Toast.LENGTH_LONG);
        toast.show();

    }

}
