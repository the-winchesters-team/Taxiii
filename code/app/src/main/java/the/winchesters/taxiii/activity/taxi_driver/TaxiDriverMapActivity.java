package the.winchesters.taxiii.activity.taxi_driver;

import static android.content.ContentValues.TAG;
import static the.winchesters.taxiii.utils.MyMapUtils.checkLocationPermission;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.location.LocationRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import the.winchesters.taxiii.R;
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

        if (!checkLocationPermission(this))
            return;
        buildClient();
        map.setMyLocationEnabled(true);

    }

    private synchronized void buildClient() {
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(LocationServices.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        googleApiClient = builder.build();
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
//        showRequestPopUp(findViewById(R.id.taxi_driver_logout),"this a request");
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
        map.animateCamera(CameraUpdateFactory.zoomTo(10));
        String currentUser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference refAvailable = FirebaseDatabase.getInstance().getReference("driversAvailable");
        DatabaseReference ref = refAvailable.child(currentUser);
        ref.setValue(new LatLng(
                lastKnownLocation.getLatitude(),
                lastKnownLocation.getLongitude()
            )
        );
//        GeoFire geoFireAvailable = new GeoFire(refAvailable);
//        GeoLocation updatedLocation = new GeoLocation(location.getLatitude(),location.getLongitude());
//        geoFireAvailable.setLocation(currentUser,updatedLocation,(key,err)->{});
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
    @Override
    protected void onStop() {
        super.onStop();
//        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("driversAvailable");
//
//        GeoFire geoFire = new GeoFire(ref);
//        geoFire.removeLocation(currentUser , (key,err)->{});
    }

    public void startRequestListener() {
        String userId = FirebaseAuth.getInstance().getUid();
        assert userId != null;
        DatabaseReference requestsRef = FirebaseDatabase.getInstance()
                .getReference("User")
                .child(userId)
                .child("request")
                ;

        requestsRef.addChildEventListener(new ChildEventListener() {

            View view = findViewById(R.id.taxi_driver_logout);

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                showRequestPopUp(view,"this a request");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

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

    public void showRequestPopUp(View view,String username,LatLng latLng) {



        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.request_popup, null);

        TextView textView = popupView.findViewById(R.id.popup_text);
        textView.setText(String.format("You have a request from %s",username));

        Button getRequestLocalisationBtn = (Button)popupView.findViewById(R.id.get_request_location);

        getRequestLocalisationBtn.setOnClickListener(v->{

        });

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view,Gravity.CENTER, 0, 0);
        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
