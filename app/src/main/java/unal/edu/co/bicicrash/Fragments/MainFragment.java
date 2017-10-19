package unal.edu.co.bicicrash.Fragments;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

import unal.edu.co.bicicrash.Activities.RegressiveTime;
import unal.edu.co.bicicrash.R;

import static android.content.Context.SENSOR_SERVICE;


public class MainFragment extends Fragment{
    private GraphView graph;
    private LineGraphSeries<DataPoint> mSeries;
    private double graphLastXValue = 5d;

    private SensorManager mSensorManager;
    private Sensor mSensorAcc;
    private SensorEventListener sensorEventListener;
    private float curX = 0, curY = 0, curZ = 0;
    private int crashLimit;
    private float sumForces;
    private float max = 0.0f;

    private View view;
    private ToggleButton toggleAcelerometerButtom;
    private TextView textView;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);
        //El grafico que muestra los resultados del acelerometro
        graph = (GraphView) view.findViewById(R.id.graph);
        mSeries = new LineGraphSeries<>();
        mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        mSensorAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        toggleAcelerometerButtom = (ToggleButton) view.findViewById(R.id.toggleAcelerometerButtom);
        crashLimit = 40;
        textView =  (TextView) view.findViewById(R.id.textView);


        graph.addSeries(mSeries);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(40);
        graph.getViewport().setMinY(-10);
        graph.getViewport().setMaxY(30);

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                                //synchronized - evita problemas de concurrencia
                synchronized (this) {
                    long current_time = sensorEvent.timestamp; //fecha/hora actual en nanosegundos
                    curX = sensorEvent.values[0];
                    curY = sensorEvent.values[1];
                    curZ = sensorEvent.values[2];

                    sumForces = Math.abs(curX) + Math.abs(curY) + Math.abs(curZ);

                    if (sumForces> max)
                        max = sumForces;
                    if( sumForces > crashLimit ){
                        Intent redirect = new Intent(getActivity(), RegressiveTime.class);
                        startActivity(redirect);
                    }else{
                        graphLastXValue += 1d;
                        mSeries.appendData(new DataPoint(graphLastXValue, sumForces), true, 40);

                        textView.setText(
                                "x: " + curX +
                                "\ny: " + curY +
                                "\nz: " + curZ +
                                "\nSuma: " + sumForces +
                                        "\nMaximo: : " + max);
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        toggleAcelerometerButtom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    mSensorManager.registerListener(sensorEventListener, mSensorAcc, SensorManager.SENSOR_DELAY_NORMAL);
                } else {
                    // The toggle is disabled
                    mSensorManager.unregisterListener(sensorEventListener);
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        mSensorManager.unregisterListener(sensorEventListener);
        super.onPause();
<<<<<<< HEAD
        mSensorManager.unregisterListener(this);
    }

    int count = 5;
    //Este metodo detecta los cambios del acelerometro
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {



        synchronized (this) {
            long current_time = sensorEvent.timestamp;

            curX = sensorEvent.values[0];
            curY = sensorEvent.values[1];
            curZ = sensorEvent.values[2];

            //textView.setText("x: " + curX + "\ny: " + curY + "\nz: " + curZ);

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

                //TODO Falta revisar el calculo del acelerometro
                //TODO Aun no se sabe cuales son los datos a graficar
                //TODO El acelerometro da mucha informacoin por segundo y la aplicacion se traba al intentar graficarla
                //TODO Metodo para graficar en tiempo de ejecucion: series.appendData(new DataPoint(count++,count++), true, 4);
                graph.addSeries(series);

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
=======
>>>>>>> miguel
    }

}
