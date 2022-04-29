package the.winchesters.taxiii.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import the.winchesters.taxiii.R;
import the.winchesters.taxiii.activity.client.ClientSignUpActivity;
import the.winchesters.taxiii.activity.taxi_driver.TaxiDriverSignUpActivity;

public class PickingRoleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pick_role);

        //navigate to Taxi driver form
        View taxiImage  = findViewById(R.id.taxi_image);
        taxiImage.setOnClickListener(view -> {
            //TODO: change
            Intent intent  = new Intent(PickingRoleActivity.this, TaxiDriverSignUpActivity.class);
            startActivity(intent);
        });

        //navigate to client form
        View clientImage  = findViewById(R.id.client_image);
        clientImage.setOnClickListener(view -> {
            Intent intent  = new Intent(PickingRoleActivity.this, ClientSignUpActivity.class);
            startActivity(intent);
        });
    }
}