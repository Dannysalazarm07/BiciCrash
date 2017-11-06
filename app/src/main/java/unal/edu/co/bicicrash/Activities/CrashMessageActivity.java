package unal.edu.co.bicicrash.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import unal.edu.co.bicicrash.R;

public class CrashMessageActivity extends AppCompatActivity {

    ImageButton shareFbButton;

    private SharedPreferences sharedPref;
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
    private EditText messageWarning;
    private String messageWarningConfig;

    Intent myShareFacebook;

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_message);

        myShareFacebook = new Intent(CrashMessageActivity.this, ShareOnFb.class);

        name = (EditText) findViewById(R.id.crash_campo_nombre);
        identification = (EditText) findViewById(R.id.crash_campo_cedula);
        phone = (EditText) findViewById(R.id.crash_campo_telefono);
        email = (EditText) findViewById(R.id.crash_campo_correo);
        gender = (EditText) findViewById(R.id.crash_campo_genero);
        rh = (EditText) findViewById(R.id.crash_campo_rh);
        eps = (EditText) findViewById(R.id.crash_campo_eps);
        secure = (EditText) findViewById(R.id.crash_campo_seguro);
        messageWarning = (EditText) findViewById(R.id.crash_campo_message_warning);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        name.setText(sharedPref.getString("nameConfig", ""));
        identification.setText(sharedPref.getString("identificationConfig", ""));
        phone.setText(sharedPref.getString("phoneConfig", ""));
        email.setText(sharedPref.getString("emailConfig", ""));
        gender.setText(sharedPref.getString("genderConfig", ""));
        rh.setText(sharedPref.getString("rhConfig", ""));
        eps.setText(sharedPref.getString("epsConfig", ""));
        secure.setText(sharedPref.getString("secureConfig", ""));

        messageWarningConfig = sharedPref.getString("messageConfig", "Mensaje por defecto");


        name.setText(sharedPref.getString("nameConfig", ""));
        name.setEnabled(false);
        identification.setText(sharedPref.getString("identificationConfig", ""));
        identification.setEnabled(false);
        phone.setText(sharedPref.getString("phoneConfig", ""));
        phone.setEnabled(false);
        email.setText(sharedPref.getString("emailConfig", ""));
        email.setEnabled(false);
        gender.setText(sharedPref.getString("genderConfig", ""));
        gender.setEnabled(false);
        rh.setText(sharedPref.getString("rhConfig", ""));
        rh.setEnabled(false);
        eps.setText(sharedPref.getString("epsConfig", ""));
        eps.setEnabled(false);
        secure.setText(sharedPref.getString("secureConfig", ""));
        secure.setEnabled(false);
        messageWarning.setText(messageWarningConfig);
        messageWarning.setEnabled(false);

        shareFbButton = (ImageButton)findViewById(R.id.fbB);

        shareFbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(myShareFacebook);
            }
        });


    }
}
