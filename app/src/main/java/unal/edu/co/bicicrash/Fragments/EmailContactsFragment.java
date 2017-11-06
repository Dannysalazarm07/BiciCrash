package unal.edu.co.bicicrash.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import unal.edu.co.bicicrash.R;
import unal.edu.co.bicicrash.Utils.AdapterItem;
import unal.edu.co.bicicrash.Utils.BiciContact;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmailContactsFragment extends Fragment {

    private FloatingActionButton buttonPickContact;
    private ArrayList arrayEmailContacts;
    private ListView contactListView;
    private AdapterItem adapter;
    private EditText inputName;
    private EditText inputEmail;
    private SharedPreferences sharedPref;

    public EmailContactsFragment() {

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_email_contacts, container, false);

        arrayEmailContacts = new ArrayList<BiciContact>();
        contactListView = (ListView) view.findViewById(R.id.listViewEmailContacts);
        adapter = new AdapterItem(getActivity(), arrayEmailContacts);
        contactListView.setAdapter(adapter);
        buttonPickContact = (FloatingActionButton) view.findViewById(R.id.pickEmailContact);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(view.getContext());

        for(int i=0; i<5; i++) {
            String name = sharedPref.getString("emailName" + String.valueOf(i), "");
            String number = sharedPref.getString("emailNumber" + String.valueOf(i), "");
            if(!name.equals("")){
                arrayEmailContacts.add(new BiciContact(name, number));
            }
        }

        if (arrayEmailContacts.size() >= 5) {
            buttonPickContact.setVisibility(View.GONE);
        }

        showContacts();

        buttonPickContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                alertDialog.setTitle(R.string.fui_provider_name_email);
                alertDialog.setMessage(R.string.message_input_email);
                View dialogView = inflater.inflate(R.layout.dialog_email, null);
                alertDialog.setView(dialogView);
                alertDialog.setIcon(R.drawable.email);

                inputName = (EditText) dialogView.findViewById(R.id.username_dialog);
                inputEmail = (EditText) dialogView.findViewById(R.id.email_dialog);

                alertDialog.setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                String name = inputName.getText().toString();
                                String email = inputEmail.getText().toString();
                                addEmailContact(name, email);
                                if (arrayEmailContacts.size() >= 5) {
                                    buttonPickContact.setVisibility(View.GONE);
                                }
                                showContacts();
                            }
                        });

                alertDialog.setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                    alertDialog.show();

            }
        });
        return view;
    }

    public void addEmailContact(String name, String email) {
        arrayEmailContacts.add(new BiciContact(name, email));
    }

    public void showContacts() {
        contactListView.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences.Editor editPref = sharedPref.edit();

        for(int i=0; i<arrayEmailContacts.size(); i++){
            BiciContact biciContact = (BiciContact) arrayEmailContacts.get(i);
            editPref.putString("emailName"+String.valueOf(i), biciContact.getName());
            editPref.putString("emailNumber"+String.valueOf(i), biciContact.getNumber());
        }

        editPref.commit();
    }
}
