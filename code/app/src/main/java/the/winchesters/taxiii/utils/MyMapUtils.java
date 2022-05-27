package the.winchesters.taxiii.utils;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

import the.winchesters.taxiii.R;
import the.winchesters.taxiii.activity.client.ClientMapActivity;
import the.winchesters.taxiii.databinding.ActivityTaxiDriverMapBinding;
import the.winchesters.taxiii.model.TaxiDriver;

public class MyMapUtils {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public static boolean checkLocationPermission(FragmentActivity fragmentActivity) {
        if (ActivityCompat.checkSelfPermission(fragmentActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(fragmentActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(fragmentActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(fragmentActivity)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, (dialogInterface, i) -> ActivityCompat.requestPermissions(fragmentActivity,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION))
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(fragmentActivity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    public static GoogleApiClient.Builder getMapBuilder(ClientMapActivity clientMapActivity){
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(clientMapActivity);
        builder.addApi(LocationServices.API);
        builder.addConnectionCallbacks(clientMapActivity);
        return builder.addOnConnectionFailedListener(clientMapActivity);
    }
    public static void updateTaxiDriversLocations(Map<String, TaxiDriver> taxiDrivers, GoogleMap map){
        for (TaxiDriver taxiDriver : taxiDrivers.values()){
            map.addMarker(new MarkerOptions()
                    .position(
                            taxiDriver.getLocation()
                    )
                    .title("Taxi : "+taxiDriver.getFirstName() + " " + taxiDriver.getLastName())
            );
        }
    }
}
