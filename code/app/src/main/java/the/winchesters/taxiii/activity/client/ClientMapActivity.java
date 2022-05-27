package the.winchesters.taxiii.activity.client;

import static the.winchesters.taxiii.utils.MyMapUtils.checkLocationPermission;
import static the.winchesters.taxiii.utils.MyMapUtils.getMapBuilder;

import android.location.Location;
import android.os.Bundle;

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

import the.winchesters.taxiii.R;
import the.winchesters.taxiii.databinding.ActivityTaxiDriverMapBinding;

public class ClientMapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap map;
    private ActivityTaxiDriverMapBinding binding;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTaxiDriverMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

}
