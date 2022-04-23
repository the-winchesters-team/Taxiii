package the.winchesters.taxiii;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import the.winchesters.taxiii.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        Toolbar toolbar = findViewById(R.id.);
//        setSupportActionBar(toolbar);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng morocco = new LatLng(33.589886, 	-7.603869);
        mMap.addMarker(new MarkerOptions().position(morocco).title("Morocco"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(morocco));
    }
}