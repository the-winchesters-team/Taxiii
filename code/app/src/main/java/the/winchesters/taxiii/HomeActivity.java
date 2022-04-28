package the.winchesters.taxiii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        View logOutButton = (Button)findViewById(R.id.logout_button);
        logOutButton.setOnClickListener(view -> {
            mAuth.signOut();
            Toast.makeText(HomeActivity.this,"user logged out",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this,LoginOrSignUpActivity.class);
            startActivity(intent);
            finish();
        });

    }
}