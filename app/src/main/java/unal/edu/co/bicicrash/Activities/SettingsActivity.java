package unal.edu.co.bicicrash.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import unal.edu.co.bicicrash.Fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }
}
