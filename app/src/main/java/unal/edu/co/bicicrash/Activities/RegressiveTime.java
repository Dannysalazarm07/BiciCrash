package unal.edu.co.bicicrash.Activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import unal.edu.co.bicicrash.R;

public class RegressiveTime extends AppCompatActivity {
    TextView timerView;
    FloatingActionButton aceptButton;
    FloatingActionButton cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regressive_time);

        timerView = (TextView) findViewById(R.id.timerView);
        aceptButton =  (FloatingActionButton) findViewById(R.id.floatingActionButtonAcept);
        cancelButton =  (FloatingActionButton) findViewById(R.id.floatingActionButtonClose);

        //Objeto que permite la cuenta regresiva
        new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerView.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerView.setText("done!");
                // TODO redirecciona a una actividad con la informacion del usuario
                // TODO envia mensajes a todos los contactos
            }
        }.start();

        aceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO redirecciona a una actividad con la informacion del usuario
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
