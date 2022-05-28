package the.winchesters.taxiii.activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import the.winchesters.taxiii.R;
import the.winchesters.taxiii.activity.client.ClientMapActivity;
import the.winchesters.taxiii.activity.taxi_driver.TaxiDriverMapActivity;
import the.winchesters.taxiii.model.Role;
import the.winchesters.taxiii.model.User;

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
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    DatabaseReference user_db = FirebaseDatabase.getInstance().getReference()
                            .child("User")
                            .child(currentUser.getUid());
                    user_db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            assert user != null;
                            Intent intent;
                            switch (user.getRole()){
                                case CLIENT :
                                    intent =new Intent(MainActivity.this, ClientMapActivity.class);
                                    break;
                                case TAXI_DRIVER:
                                    intent =new Intent(MainActivity.this, TaxiDriverMapActivity.class);
                                    break;
                                default:
                                    intent =new Intent(MainActivity.this, LoginOrSignUpActivity.class);
                            }
                            startActivity(intent);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "loadPost:onCancelled", error.toException());
                        }
                    });
                }else {
                    Intent intent = new Intent(MainActivity.this, LoginOrSignUpActivity.class);
                    startActivity(intent);
                }

                finish();
            }
        },3000);
    }
}