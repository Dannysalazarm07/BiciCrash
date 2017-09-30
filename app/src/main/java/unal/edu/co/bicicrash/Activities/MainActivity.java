package unal.edu.co.bicicrash.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import unal.edu.co.bicicrash.R;
import unal.edu.co.bicicrash.Utils.SectionsPagerAdapterForMainActivity;
import unal.edu.co.bicicrash.fragments.MainFragment;
import unal.edu.co.bicicrash.fragments.BiciMapFragment;
import unal.edu.co.bicicrash.Utils.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ViewPager mViewPager;
    private BiciMapFragment mBiciMapFragment;
    private static final int LOCATION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private Location mCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setear adaptador al viewpager.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(mViewPager);

        // Preparar las pestañas
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);

        //Bloque la orientacion. En caso de un choque el telefono no cambiará su orientacion
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent ListSong = new Intent(getApplicationContext(), ToolsActivity.class);
                startActivity(ListSong);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //En este metodo se agregan los Fragmentos a la activity
    private void setupViewPager(ViewPager viewPager) {
        //El adaptador enlaza las biñetas del ViewPager con cada Fragment.
        // Administra la forma de mostar las Fragments
        //Nota, este adaptador es configurado unicamente para los gragmentos incrustados en el mainActivity, debido a que manjea tipos diferentes de Fragments
        SectionsPagerAdapterForMainActivity adapter = new SectionsPagerAdapterForMainActivity(getSupportFragmentManager());
        //Se agregaa el Fragment pricipal que muestra las señales del acelerometro
        adapter.addFragment(new MainFragment(), getString(R.string.title_section1));

        mBiciMapFragment = BiciMapFragment.newInstance();
        // Registrar escucha onMapReadyCallback
        mBiciMapFragment.getMapAsync(this);

        //Se agregaa el Fragment pricipal que muestra el mapa de accidentes
        adapter.addBiciMapFragment(mBiciMapFragment, getString(R.string.title_section2));

        viewPager.setAdapter(adapter);
    }


    //Controla la respuseta de GoogleMaps. Podemos manipular el mapa con este metodo
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Controles UI
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

            //TODO encontrar ubicacion actual
            //TODO agregar marcador de la ubicacion actual
            //TODO mover la camara a la ubicacion actual
            //TODO graficar los compañeros a 1 km de distancia

            //// OJO: Se grafican los siguinte puntos en el mapa a manera de ejemplo//////
            LatLng unal = new LatLng(4.6381938, -74.08404639999998);
            LatLng friend1 = new LatLng(4.6371948, -74.08404639999998);
            LatLng friend2 = new LatLng(4.6391938, -74.08404639999998);
            LatLng friend3 = new LatLng(4.6381938, -74.08414539999998);
            LatLng friend4 = new LatLng(4.6381938, -74.08424439999998);

            googleMap.addMarker(new MarkerOptions()
                    .position(unal)
                    .title("Universidad Nacional")
                    .snippet("Mi ubicacion actual")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

            googleMap.addMarker(new MarkerOptions()
                    .position(friend1)
                    .title("Amigo 1")
                    .snippet("A 100 mtrs")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

            googleMap.addMarker(new MarkerOptions()
                    .position(friend2)
                    .title("Amigo 2")
                    .snippet("A 100 mtrs")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

            googleMap.addMarker(new MarkerOptions()
                    .position(friend3)
                    .title("Amigo 3")
                    .snippet("A 100 mtrs")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

            googleMap.addMarker(new MarkerOptions()
                    .position(friend4)
                    .title("Amigo 4")
                    .snippet("Accidente a A 100 mtrs")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(unal)
                    .zoom(15)
                    .build();

            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);


    }



}
