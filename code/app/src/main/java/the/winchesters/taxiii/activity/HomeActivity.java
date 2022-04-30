package the.winchesters.taxiii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import the.winchesters.taxiii.R;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        View logOutButton = (Button) findViewById(R.id.logout_button);
        logOutButton.setOnClickListener(view -> {
            mAuth.signOut();
            Toast.makeText(HomeActivity.this, "user logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, LoginOrSignUpActivity.class);
            startActivity(intent);
            finish();
        });

        Button profileButton = (Button) findViewById(R.id.home_profile_button);
        profileButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        //exit app on back pressed
        //https://stackoverflow.com/questions/21253303/exit-android-app-on-back-pressed
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}