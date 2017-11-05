package unal.edu.co.bicicrash.Activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.net.URI;
import java.util.regex.Pattern;

import unal.edu.co.bicicrash.R;

public class InformationActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
    private ImageButton imageButton;
    private EditText name;
    private String nameConfig;
    private EditText identification;
    private String identificationConfig;
    private EditText phone;
    private String phoneConfig;
    private EditText email;
    private String emailConfig;
    private EditText gender;
    private String genderConfig;
    private EditText rh;
    private String rhConfig;
    private EditText eps;
    private String epsConfig;
    private EditText secure;
    private String secureConfig;

    private final int PHOTO_SELECTED = 0;
    private Uri imageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);


        imageButton = findViewById(R.id.imageButton);
        name = (EditText) findViewById(R.id.campo_nombre);
        identification = (EditText) findViewById(R.id.campo_cedula);
        phone = (EditText) findViewById(R.id.campo_telefono);
        email = (EditText) findViewById(R.id.campo_correo);
        gender = (EditText) findViewById(R.id.campo_genero);
        rh = (EditText) findViewById(R.id.campo_rh);
        eps = (EditText) findViewById(R.id.campo_eps);
        secure = (EditText) findViewById(R.id.campo_seguro);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);


        imageUri = Uri.parse("android.resource://"+getPackageName()+"/drawable/bibicrash_icon.png");
        //imageUri = Uri.parse("@drawable/bicicrash_icon.png");
        imageButton.setImageURI(imageUri);
        //imageButton.setImageResource(R.drawable.bicicrash_icon);
        //imageButton.setImageURI(Uri.parse(sharedPref.getString("imageConfig", "android.resource://drawable//account_settings.xml")));
        name.setText(sharedPref.getString("nameConfig", ""));
        identification.setText(sharedPref.getString("identificationConfig", ""));
        phone.setText(sharedPref.getString("phoneConfig", ""));
        email.setText(sharedPref.getString("emailConfig", ""));
        gender.setText(sharedPref.getString("genderConfig", ""));
        rh.setText(sharedPref.getString("rhConfig", ""));
        eps.setText(sharedPref.getString("epsConfig", ""));
        secure.setText(sharedPref.getString("secureConfig", ""));

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    public void openGallery(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_SELECTED);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == RESULT_OK && requestCode == PHOTO_SELECTED){
            imageUri = data.getData();
            imageButton.setImageURI(imageUri);
            Log.d("####### Una Uri:", imageUri.toString());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editPref = sharedPref.edit();

        editPref.putString("nameConfig", name.getText().toString());
        editPref.putString("identificationConfig", identification.getText().toString());
        editPref.putString("phoneConfig", phone.getText().toString());
        editPref.putString("emailConfig", email.getText().toString());
        editPref.putString("genderConfig", gender.getText().toString());
        editPref.putString("rhConfig", rh.getText().toString());
        editPref.putString("epsConfig", eps.getText().toString());
        editPref.putString("secureConfig", secure.getText().toString());
        //editPref.putString("imageConfig", imageUri.toString());

        editPref.commit();
    }
}


