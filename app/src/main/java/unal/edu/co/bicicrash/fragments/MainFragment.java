package unal.edu.co.bicicrash.fragments;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import unal.edu.co.bicicrash.Activities.RegressiveTime;
import unal.edu.co.bicicrash.R;

import static android.R.attr.data;
import static android.content.Context.SENSOR_SERVICE;


public class MainFragment extends Fragment implements SensorEventListener{

    private long last_update = 0, last_movement = 0;
    private float prevX = 0, prevY = 0, prevZ = 0;
    private float curX = 0, curY = 0, curZ = 0;

    View view;
    ToggleButton toggleAcelerometerButtom;
    Button crashButton;

    SensorManager mSensorManager;
    Sensor mSensorAcc;

    TextView textView;


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);

        textView =  (TextView) view.findViewById(R.id.textView);
        mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        mSensorAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        crashButton = (Button) view.findViewById(R.id.buttonTest);
        toggleAcelerometerButtom = (ToggleButton) view.findViewById(R.id.toggleAcelerometerButtom);

        toggleAcelerometerButtom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Toast.makeText(getContext(), "Boton habilitado ", Toast.LENGTH_SHORT).show();
                    //TODO Activar acelerometro
                } else {
                    // The toggle is disabled
                    Toast.makeText(getContext(), "Boton Deshabilitado ", Toast.LENGTH_SHORT).show();
                    //TODO Desactivar acelerometro


                }
            }
        });


        crashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirect = new Intent(getActivity(), RegressiveTime.class);
                startActivity(redirect);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorAcc, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    //Este metodo detecta los cambios del acelerometro
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x,y,z;
        x =  sensorEvent.values[0];
        y =  sensorEvent.values[1];
        z =  sensorEvent.values[2];
        textView.setText("x: " + x + "\ny: " + y + "\nz: " + z);

        synchronized (this) {
            long current_time = sensorEvent.timestamp;

            curX = sensorEvent.values[0];
            curY = sensorEvent.values[1];
            curZ = sensorEvent.values[2];

            if (prevX == 0 && prevY == 0 && prevZ == 0) {
                last_update = current_time;
                last_movement = current_time;
                prevX = curX;
                prevY = curY;
                prevZ = curZ;
            }

            long time_difference = current_time - last_update;
            if (time_difference > 0) {
                float movement = Math.abs((curX + curY + curZ) - (prevX - prevY - prevZ)) / time_difference;
                int limit = 1500;
                float min_movement = 1E-6f;
                if (movement > min_movement) {
                    if (current_time - last_movement >= limit) {
                        Toast.makeText(view.getContext(), "Hay movimiento de " + movement, Toast.LENGTH_SHORT).show();
                    }
                    last_movement = current_time;
                }
                prevX = curX;
                prevY = curY;
                prevZ = curZ;
                last_update = current_time;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
