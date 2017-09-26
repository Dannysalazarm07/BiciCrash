package unal.edu.co.bicicrash.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import unal.edu.co.bicicrash.R;

import static android.R.attr.data;


public class MainFragment extends Fragment {

    ToggleButton toggleAcelerometerButtom;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        toggleAcelerometerButtom = (ToggleButton) view.findViewById(R.id.toggleAcelerometerButtom);
        toggleAcelerometerButtom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Toast.makeText(getContext(), "Boton habilitado ", Toast.LENGTH_SHORT).show();
                    //TODO Activar acelerometro
                } else {
                    // The toggle is disabled
                    Toast.makeText(getContext(), "Boton Deshabilitado ", Toast.LENGTH_SHORT).show();
                    //TODO DesActivar acelerometro
                }
            }
        });
        return view;
    }







}
