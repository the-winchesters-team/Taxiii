package the.winchesters.taxiii.activity.taxi_driver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationRequest;

import java.util.List;

import the.winchesters.taxiii.R;
import the.winchesters.taxiii.activity.NavigationBarActivity;
import the.winchesters.taxiii.databinding.ActivityTaxiDriverMapBinding;

public class TaxiDriverMapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private GoogleMap map;
    private ActivityTaxiDriverMapBinding binding;
    private Location lastKnownLocation;
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
        LatLng morocco = new LatLng(33.589886, -7.603869);
        map.addMarker(new MarkerOptions().position(morocco).title("Morocco"));
        map.moveCamera(CameraUpdateFactory.newLatLng(morocco));
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
}
