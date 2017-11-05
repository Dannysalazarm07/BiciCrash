package unal.edu.co.bicicrash.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;

import unal.edu.co.bicicrash.Fragments.BiciMapFragment;
import unal.edu.co.bicicrash.Fragments.MainFragment;
import unal.edu.co.bicicrash.R;
import unal.edu.co.bicicrash.Utils.SectionsPagerAdapterForMainActivity;

public class MainActivity extends AppCompatActivity implements  OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener,LocationListener {

    private ViewPager mViewPager;
    private BiciMapFragment mBiciMapFragment;
    private static final int LOCATION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private Location mCurrentLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static final float SMALLEST_DISPLACEMENT = 0.15F; //con esto obtenemos desplazamiento minimo "un cuarto de metro"

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
            case R.id.action_contacts:
                Intent contactsIntent = new Intent(getApplicationContext(), ContactsActivity.class);
                startActivity(contactsIntent);
                return true;

            case R.id.action_information:
                Intent informationIntent = new Intent(getApplicationContext(), InformationActivity.class);
                startActivity(informationIntent);
                return true;

            case R.id.action_settings:
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsIntent);
                return true;

            case R.id.action_log_out:
                logOutAction();
                return true;
                
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logOutAction(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Log.d("AUTH", "Usuario no esta logeado");
                        //getActivity().finish();
                        startActivityForResult(AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(
                                        Arrays.asList(
                                                new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                                                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build())

                                ).build(), 0);
                    }
                });
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
            buildGoogleApiClient();
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




        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
                buildGoogleApiClient();
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
                buildGoogleApiClient();
            }
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        String mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        agregarMarcador();

    }

    private void agregarMarcador() {

        LatLng currentLatLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16));



    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setSmallestDisplacement(SMALLEST_DISPLACEMENT);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
