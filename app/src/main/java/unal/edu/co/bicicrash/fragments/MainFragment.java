package unal.edu.co.bicicrash.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import unal.edu.co.bicicrash.Activities.RegressiveTime;
import unal.edu.co.bicicrash.R;

import static android.R.attr.data;


public class MainFragment extends Fragment {

    ToggleButton toggleAcelerometerButtom;
    Button crashButton;

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

        crashButton = (Button) view.findViewById(R.id.buttonTest);
        crashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirect = new Intent(getActivity(), RegressiveTime.class);
                startActivity(redirect);
            }
        });
        return view;
    }







}
