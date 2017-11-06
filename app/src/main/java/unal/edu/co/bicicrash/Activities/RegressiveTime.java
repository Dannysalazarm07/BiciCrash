package unal.edu.co.bicicrash.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import unal.edu.co.bicicrash.R;
import unal.edu.co.bicicrash.Utils.BiciContact;
import unal.edu.co.bicicrash.business.MenssagerBusiness;

public class RegressiveTime extends AppCompatActivity {
    TextView timerView;
    FloatingActionButton aceptButton;
    FloatingActionButton cancelButton;
    Intent myInformation;
    Intent myIntentMail;
    private int time;
    private String timeShared;
    private SharedPreferences sharedPref;
    private ArrayList arrayEmailContacts;
    private ArrayList arrayPhoneContacts;

    private MenssagerBusiness menssagerBusiness;
    private String messageWarning;
    private String name;
    private String number;
    private boolean flag;

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        arrayEmailContacts = new ArrayList<BiciContact>();
        arrayPhoneContacts = new ArrayList<BiciContact>();
        myInformation = new Intent(RegressiveTime.this, CrashMessageActivity.class);
        myIntentMail = new Intent(RegressiveTime.this, ShareOnFb.class);
        menssagerBusiness = new MenssagerBusiness();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regressive_time);

        flag = true;

        timerView = (TextView) findViewById(R.id.timerView);
        aceptButton = (FloatingActionButton) findViewById(R.id.floatingActionButtonAcept);
        cancelButton = (FloatingActionButton) findViewById(R.id.floatingActionButtonClose);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);


        for (int i = 0; i < 5; i++) {
            name = sharedPref.getString("emailName" + String.valueOf(i), "");
            number = sharedPref.getString("emailNumber" + String.valueOf(i), "");
            if (!name.equals("")) {
                arrayEmailContacts.add(new BiciContact(name, number));
            }
        }

        for (int i = 0; i < 5; i++) {
            name = sharedPref.getString("phoneName" + String.valueOf(i), "");
            number = sharedPref.getString("phoneNumber" + String.valueOf(i), "");
            if (!name.equals("")) {
                arrayPhoneContacts.add(new BiciContact(name, number));
            }
        }

        messageWarning = sharedPref.getString("messageConfig", "Mensaje por defecto");

        timeShared = sharedPref.getString("TimeForWait", "10");
        time = Integer.valueOf(timeShared) * 1000;


        //Objeto que permite la cuenta regresiva
        new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                timerView.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerView.setText("done!");
                if(flag){
                    sendAllMessage();
                    startActivity(myInformation);
                    //startActivity(myIntentMail);
                }

            }
        }.start();

        aceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = false;
                sendAllMessage();
                startActivity(myInformation);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegressiveTime.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    public void sendAllMessage() {
        if (arrayPhoneContacts.size() > 0) {

            try {

                //verificamos si tenemos los permisos concedidos para enviar un SMS
                int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "No se tiene Permiso para enviar SMS", Toast.LENGTH_LONG).show();
                    //Solicita los permisos.
                    Log.i("Mensaje", "No tiene permiso para enviar SMS!");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 225);

                } else {
                    Toast.makeText(getApplicationContext(), "Si se tiene Permiso para enviar SMS", Toast.LENGTH_LONG).show();
                    Log.d("Mensaje", "Se tiene permiso para enviar SMS!");
                    Boolean isSentMail = new MenssagerBusiness.msnTask(arrayPhoneContacts, messageWarning, getApplicationContext()).execute().get();
                    Toast.makeText(getApplicationContext(), " Envío de msm exitoso!", Toast.LENGTH_SHORT).show();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), " No hay números registrados", Toast.LENGTH_SHORT).show();
        }


        try {
            if (arrayEmailContacts.size() > 0) {
                Boolean isSentMail = new MenssagerBusiness.mailerTask(arrayEmailContacts, messageWarning).execute().get();
                Toast.makeText(getApplicationContext(), " Envío de correos exitoso!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), " No hay correos registrados", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
