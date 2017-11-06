package unal.edu.co.bicicrash.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import unal.edu.co.bicicrash.R;
import unal.edu.co.bicicrash.Utils.AdapterItem;
import unal.edu.co.bicicrash.Utils.BiciContact;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneContactsFragment extends Fragment {
    private FloatingActionButton buttonPickContact;
    private ArrayList arrayContacts;
    private ListView contactListView;
    private AdapterItem adapter;





    public PhoneContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_phone_contacts, container, false);

        arrayContacts = new ArrayList<BiciContact>();
        contactListView = (ListView)view.findViewById(R.id.listViewContact);
        adapter = new AdapterItem(getActivity(), arrayContacts);
        contactListView.setAdapter(adapter);

        buttonPickContact = (FloatingActionButton) view.findViewById(R.id.pickcontact);

        buttonPickContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, 0);
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            Uri contactUri = data.getData();
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY};
            Cursor cursor = getActivity().getContentResolver().query(contactUri, projection,
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {

                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberIndex);

                int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY);
                String name = cursor.getString(nameIndex);


                //Aagrega el contacto a la lista de contactos
                addContact(name, number);

                //Muestra la lista de contactos en pantalla
                showContacts();
            } else {

            }
        }
    }

    public void addContact(String name, String number) {
        arrayContacts.add(new BiciContact(name, number));
    }

    public void showContacts() {
        contactListView.setAdapter(adapter);
    }

}
