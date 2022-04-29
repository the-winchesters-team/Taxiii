package the.winchesters.taxiii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PickingRoleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pick_role);

        //navigate to Taxi driver form
        View taxiImage  = findViewById(R.id.taxi_image);
        taxiImage.setOnClickListener(view -> {
            //TODO: change
            Intent intent  = new Intent(PickingRoleActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        //navigate to client form
        View clientImage  = findViewById(R.id.client_image);
        clientImage.setOnClickListener(view -> {
            Intent intent  = new Intent(PickingRoleActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}