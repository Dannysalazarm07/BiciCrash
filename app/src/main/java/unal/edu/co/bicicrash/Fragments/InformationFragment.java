package unal.edu.co.bicicrash.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.regex.Pattern;

import unal.edu.co.bicicrash.R;

public class InformationFragment extends Fragment {


    public InformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        return view;
    }

    private boolean validarNombre(String nombre){
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        return patron.matcher(nombre).matches() || nombre.length() > 30;


    }

    private boolean validadTelefono(String telefono){
        return Patterns.PHONE.matcher(telefono).matches();
    }
    private boolean esCorreoValido(String correo){
        return Patterns.EMAIL_ADDRESS.matcher(correo).matches();
    }


}
