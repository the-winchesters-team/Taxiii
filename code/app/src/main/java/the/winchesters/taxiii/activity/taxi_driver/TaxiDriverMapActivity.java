package the.winchesters.taxiii.activity.taxi_driver;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import the.winchesters.taxiii.R;
import the.winchesters.taxiii.activity.LoginOrSignUpActivity;
import the.winchesters.taxiii.activity.ProfileActivity;
import the.winchesters.taxiii.databinding.ActivityTaxiDriverMapBinding;

public class TaxiDriverMapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth mAuth;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private GoogleMap map;
    private ActivityTaxiDriverMapBinding binding;
    private Location lastKnownLocation;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        binding = ActivityTaxiDriverMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        signOut();
        userSettings();
    }

    private void signOut() {
        View logOutButton = findViewById(R.id.map_logout);
        logOutButton.setOnClickListener(view -> {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser!=null){
                removeAvailableDriver(currentUser.getUid());
                mAuth.signOut();
            }
            Toast.makeText(TaxiDriverMapActivity.this, "user logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(TaxiDriverMapActivity.this, LoginOrSignUpActivity.class);
            startActivity(intent);
            finish();
        });
    }
    private void userSettings(){
        View userSettingsView = findViewById(R.id.map_user_settings);
        userSettingsView.setOnClickListener(view -> {
            Intent intent = new Intent(TaxiDriverMapActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (!checkLocationPermission())
            return;
        buildClient();
        map.setMyLocationEnabled(true);

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        lastKnownLocation = location;
        Log.d("debug", location.toString());
        map.moveCamera(
                CameraUpdateFactory.newLatLng(
                        new LatLng(
                                lastKnownLocation.getLatitude(),
                                lastKnownLocation.getLongitude()
                        )
                )
        );
        map.animateCamera(CameraUpdateFactory.zoomBy(15));
        // get current user's id
        String currentUser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        // get the reference to the "DriverIsAvailable" db
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("DriverIsAvailable");
        GeoLocation geoLocation = new GeoLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        GeoFire geoFire = new GeoFire(dbRef);
        geoFire.setLocation(currentUser, geoLocation, (key, error) -> Log.e(TAG, "GeoFire Complete"));
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest()
                //every second
                .setInterval(1000)
                // high accuracy because we need the drivers accurate location
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        ;
        if (!checkLocationPermission())
            return;
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, (dialogInterface, i) -> ActivityCompat.requestPermissions(TaxiDriverMapActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION))
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    private synchronized void buildClient() {
        googleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        // when no longer tracked
        super.onStop();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            removeAvailableDriver(currentUser.getUid());
        }

    }

    void removeAvailableDriver(String currentUser){
        // get current user's id
        // get the reference to the "DriverIsAvailable" db
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("DriverIsAvailable");
        GeoFire geoFire = new GeoFire(dbRef);
        geoFire.removeLocation(currentUser);
    }

}