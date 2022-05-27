package the.winchesters.taxiii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

import the.winchesters.taxiii.R;
import the.winchesters.taxiii.activity.client.ClientMapActivity;
import the.winchesters.taxiii.activity.taxi_driver.TaxiDriverMapActivity;

public class MainActivity extends AppCompatActivity {
    Timer timer;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
//                FirebaseAuth.getInstance().signOut();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                Intent intent;
                if (currentUser != null) {
                    intent = new Intent(MainActivity.this, ClientMapActivity.class);
                }else {

                    intent = new Intent(MainActivity.this, LoginOrSignUpActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },3000);
    }
}