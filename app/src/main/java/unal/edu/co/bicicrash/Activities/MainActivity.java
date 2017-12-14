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
import android.widget.ArrayAdapter;

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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import unal.edu.co.bicicrash.Fragments.BiciMapFragment;
import unal.edu.co.bicicrash.Fragments.MainFragment;
import unal.edu.co.bicicrash.Models.PositionLL;
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

    //FirebaseObjects
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private List<PositionLL> listPessoa = new ArrayList<PositionLL>();
    private ArrayAdapter<PositionLL> arrayAdapterPessoa;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private String uuidUser;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //8Umwz8HKYTY9OI42eR30hJmIpui1

        initiarFirebase();
        eventoDatabase();
        getUserFireBase();

        Log.d("UUIDDD", "UID "+uuidUser);

        // Setear adaptador al viewpager.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(mViewPager);

        // Preparar las pestañas
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);

        //Bloque la orientacion. En caso de un choque el telefono no cambiará su orientacion
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        positionUser();


    }

    private void getUserFireBase() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("FI777", "onAuthStateChanged:signed_in:" + user.getUid());
                    uuidUser =  user.getUid();
                    Log.d("FI888", "onAuthStateChanged:signed_in:" + uuidUser);
                } else {
                    // User is signed out
                    Log.d("Nfb", "onAuthStateChanged:signed_out");
                }
                // ...
            }

        };
    }

    //Initiar firebase object for this project
    private void initiarFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

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

    private void eventoDatabase() {
        databaseReference.child("Ubication").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listPessoa.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    PositionLL p = objSnapshot.getValue(PositionLL.class);
                    Log.i("Positiona ",p.getUbication());
                    listPessoa.add(p);
                    Log.i("TODOo ", listPessoa.get(0).getUbication());


                }
//                arrayAdapterPessoa = new ArrayAdapter<PositionLL>(MainActivity.this,
//                        android.R.layout.simple_list_item_1,listPessoa);
//                listV_dados.setAdapter(arrayAdapterPessoa);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

            LatLng friendOcana = new LatLng(8.2374666,-73.3542888);
            LatLng friendOcana2 = new LatLng(8.2373966, -73.3547888);


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
                    .position(friendOcana)
                    .title("Amigo oca1")
                    .snippet("A 100 mtrs")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

            googleMap.addMarker(new MarkerOptions()
                    .position(friendOcana2)
                    .title("Amigo oca2")
                    .snippet("A 100 mtrs")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

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


    /////ACA SE ACTUALIZA LA LOCALIZACION
    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        String mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        final LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        agregarMarcador();

        //Subir a firebase la ubicacion

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("FI777", "onAuthStateChanged:signed_in:" + user.getUid());
                    uuidUser =  user.getUid();

                    //////////

                    PositionLL p = new PositionLL();
                    p.setUid(uuidUser);
                    //Se debe cambiar a arreglo.
                    p.setUbication(latLng.toString());

                    databaseReference.child("Ubication").child(p.getUid()).setValue(p);
                    /////////

                    Log.d("FI888", "onAuthStateChanged:signed_in:" + uuidUser);

                } else {
                    // User is signed out
                    Log.d("Nfb", "onAuthStateChanged:signed_out");
                }
                // ...
            }

        };

        Log.d("mLastUpdateTime",mLastUpdateTime);
        Log.d("mlatLng", String.valueOf(latLng));
    }

    public void positionUser( ) {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.d("BDFIREBASE", "base de datos:" + databaseReference.getDatabase());
                DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference();
                Log.d("ubicationDB", "base de datos db:" + UserRef);

                UserRef.keepSynced(true);
                UserRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
                        Log.d("daSnaDB", "db:" + dataSnapshot.getValue(PositionLL.class).getUid());

                        List<PositionLL> fcmUsers = new ArrayList<>();
                        while (dataSnapshots.hasNext()) {
                            DataSnapshot dataSnapshotChild = dataSnapshots.next();
                            PositionLL fcmUser = dataSnapshotChild.getValue(PositionLL.class);
                            fcmUsers.add(fcmUser);
                            Log.d("LADBuser", "db:" + fcmUser.getUbication());

                        }

                        // Check your arraylist size and pass to list view like
                        if(fcmUsers.size()>0)
                        {
                            Log.d("LADB", "db:" + fcmUsers.get(0).getUbication());


                        }else
                        {
                            // Display dialog for there is no user available.
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // for handling database error
                    }
                });

            }
        };
    }
      private void agregarMarcador() {

        LatLng currentLatLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        Log.d("currentLatLng", String.valueOf(currentLatLng));
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