package the.winchesters.taxiii.activity.client;

import static android.content.ContentValues.TAG;
import static the.winchesters.taxiii.utils.MyMapUtils.checkLocationPermission;
import static the.winchesters.taxiii.utils.MyMapUtils.getMapBuilder;
import static the.winchesters.taxiii.utils.MyMapUtils.updateTaxiDriversLocations;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import the.winchesters.taxiii.R;
import the.winchesters.taxiii.activity.LoginFormActivity;
import the.winchesters.taxiii.activity.LoginOrSignUpActivity;
import the.winchesters.taxiii.activity.MainActivity;
import the.winchesters.taxiii.databinding.ActivityTaxiDriverMapBinding;
import the.winchesters.taxiii.model.MyLatLng;
import the.winchesters.taxiii.model.TaxiDriver;

public class ClientMapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap map;
    private ActivityTaxiDriverMapBinding binding;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private Map<String, TaxiDriver> taxiDrivers= new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTaxiDriverMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        TextView returnBack = (TextView) findViewById(R.id.return_back);
        returnBack.setOnClickListener(view -> {
            super.onBackPressed();

        });
        startTaxiDriverListener();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (!checkLocationPermission(this))
            return;
        buildClient();
        map.setMyLocationEnabled(true);
        // set to driver's mark
        // get latlng
        Bundle b = getIntent().getExtras();
        if (b == null) return;
        if (b.getDouble("lat") == 0.0 || b.getDouble("long") == 0.0) return;
        googleMap.addMarker(new MarkerOptions()
                .position(
                        new LatLng(b.getDouble("lat"),b.getDouble("long"))
                )
                .title("Taxi"));
        map.moveCamera(
                CameraUpdateFactory.newLatLng(
                        new LatLng(b.getDouble("lat"),b.getDouble("long"))
                )
        );
    }

    private synchronized void buildClient() {
        googleApiClient = getMapBuilder(this).build();
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest()
                //every second
                .setInterval(1000)
                .setFastestInterval(1000)
                // high accuracy because we need the drivers accurate location
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (!checkLocationPermission(this))
            return;
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    public void startTaxiDriverListener() {
        DatabaseReference driverIsAvailableRef = FirebaseDatabase.getInstance().getReference("driversAvailable");
        driverIsAvailableRef.addChildEventListener(new ChildEventListener() {
            String taxiDriverId;
            TaxiDriver taxiDriver = new TaxiDriver();
            MyLatLng location;

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                taxiDriverId = snapshot.getKey();
                location = snapshot.getValue(MyLatLng.class);
                DatabaseReference driverRef = FirebaseDatabase.getInstance()
                        .getReference("User")
                        .child("TaxiDriver")
                        .child(taxiDriverId);
                Log.i(TAG,"taxi driver id "+taxiDriverId);
                driverRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        taxiDriver = snapshot.getValue(TaxiDriver.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "loadPost:onCancelled", error.toException());
                    }
                });
                taxiDriver.setLocation(location);
                taxiDrivers.put(taxiDriverId, taxiDriver);
                Log.i(TAG, taxiDriver.toString());
                updateTaxiDriversLocations(taxiDrivers,map);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                taxiDriverId = snapshot.getKey();
                location = snapshot.getValue(MyLatLng.class);
                taxiDriver = taxiDrivers.get(taxiDriverId);
                assert taxiDriver != null;
                taxiDriver.setLocation(location);
                taxiDrivers.put(taxiDriverId, taxiDriver);
                Log.i(TAG, taxiDriver.toString());
                updateTaxiDriversLocations(taxiDrivers,map);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                taxiDriverId = snapshot.getKey();

                taxiDrivers.remove(taxiDriverId);
                updateTaxiDriversLocations(taxiDrivers,map);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
    }
}
