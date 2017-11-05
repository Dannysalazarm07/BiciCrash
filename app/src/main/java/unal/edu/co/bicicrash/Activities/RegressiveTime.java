package unal.edu.co.bicicrash.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import unal.edu.co.bicicrash.R;

public class RegressiveTime extends AppCompatActivity {
    private TextView timerView;
    private FloatingActionButton aceptButton;
    private FloatingActionButton cancelButton;
    private int time;
    private String timeShared;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
