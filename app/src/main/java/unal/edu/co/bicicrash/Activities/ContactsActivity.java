package unal.edu.co.bicicrash.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import unal.edu.co.bicicrash.R;
import unal.edu.co.bicicrash.Utils.AdapterItem;
import unal.edu.co.bicicrash.Utils.BiciContact;


public class ContactsActivity extends AppCompatActivity {

    private TextView contactNumber;
    private FloatingActionButton buttonPickContact;
    private ListView contactListView;
    private ArrayList arrayContacts;
    private AdapterItem adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        buttonPickContact = (FloatingActionButton) findViewById(R.id.pickcontact);

        arrayContacts = new ArrayList<BiciContact>();
        contactListView = (ListView)findViewById(R.id.listViewContact);
        adapter = new AdapterItem(this, arrayContacts);
        contactListView.setAdapter(adapter);


        buttonPickContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 0 );
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0  && resultCode == RESULT_OK) {

            Uri contactUri = data.getData();
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY};
            Cursor cursor = getContentResolver().query(contactUri, projection,
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
            }
            else{
                contactNumber.setText("Estoy afuera del cursor");
            }
        }
    }

    public void addContact(String name, String number){
        arrayContacts.add(new BiciContact(name,number));
    }

    public void showContacts(){
        contactListView.setAdapter(adapter);
    }

}
