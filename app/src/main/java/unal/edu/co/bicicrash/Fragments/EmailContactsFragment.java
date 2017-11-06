package unal.edu.co.bicicrash.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

        buttonPickContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (arrayEmailContacts.size() < 5) {

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
                }else {
                    // desaparece el boton del layout
                    buttonPickContact.setVisibility(View.GONE);
                }
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

}
