package the.winchesters.taxiii.activity;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import the.winchesters.taxiii.R;
import the.winchesters.taxiii.model.TaxiDriver;

public class ListTaxiActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Map<String, TaxiDriver> taxiDrivers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_taxi);
        mAuth = FirebaseAuth.getInstance();
        startTaxiDriverListener();
    }

    public void startTaxiDriverListener() {
        DatabaseReference driverIsAvailableRef = FirebaseDatabase.getInstance().getReference("DriverIsAvailable");
        driverIsAvailableRef.addChildEventListener(new ChildEventListener() {
            String taxiDriverId;
            TaxiDriver taxiDriver;
            LatLng location;

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                taxiDriverId = snapshot.getKey();
                location = snapshot.getValue(LatLng.class);
                DatabaseReference driverRef = FirebaseDatabase.getInstance()
                        .getReference("User")
                        .child("TaxiDriver")
                        .child(taxiDriverId);
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
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                taxiDriverId = snapshot.getKey();
                location = snapshot.getValue(LatLng.class);
                taxiDriver = taxiDrivers.get(taxiDriverId);
                assert taxiDriver != null;
                taxiDriver.setLocation(location);
                taxiDrivers.put(taxiDriverId, taxiDriver);
                Log.i(TAG, taxiDriver.toString());

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                taxiDriverId = snapshot.getKey();
                taxiDrivers.remove(taxiDriverId);
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