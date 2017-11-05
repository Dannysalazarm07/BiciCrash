package unal.edu.co.bicicrash.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.share.widget.ShareDialog;
import java.util.concurrent.ExecutionException;
import unal.edu.co.bicicrash.R;
import unal.edu.co.bicicrash.business.MenssagerBusiness;

public class RegressiveTime extends AppCompatActivity {
    TextView timerView;
    FloatingActionButton aceptButton;
    FloatingActionButton cancelButton;
    Intent myIntent;
    Intent myIntentMail;
    private int time;
    private String timeShared;
    private SharedPreferences sharedPref;

    MenssagerBusiness menssagerBusiness;



    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        myIntent = new Intent(RegressiveTime.this, MailSenderActivity.class);
        myIntentMail = new Intent(RegressiveTime.this, ShareOnFb.class);
        menssagerBusiness= new MenssagerBusiness();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regressive_time);

        timerView = (TextView) findViewById(R.id.timerView);
        aceptButton =  (FloatingActionButton) findViewById(R.id.floatingActionButtonAcept);
        cancelButton =  (FloatingActionButton) findViewById(R.id.floatingActionButtonClose);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        timeShared = sharedPref.getString("TimeForWait","10");
        time = Integer.valueOf(timeShared)*1000;


        //Objeto que permite la cuenta regresiva
        new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                timerView.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerView.setText("done!");


                new MenssagerBusiness.msnTask().execute();
                try {
                    Boolean isSentMail = new MenssagerBusiness.mailerTask().execute().get();
                    Toast.makeText(getApplicationContext()," Envio de correos exitoso!",Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                //ShareDialog shareDialo= new ShareDialog();


                startActivity(myIntent);

                startActivity(myIntentMail);


                // TODO redirecciona a una actividad con la informacion del usuario
                // TODO envia mensajes a todos los contactos
            }
        }.start();

        aceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent crashMessageIntent2 = new Intent(getApplicationContext(), CrashMessageActivity.class);
                startActivity(crashMessageIntent2);

                // TODO envia mensajes a todos los contactos
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
}
