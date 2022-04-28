package the.winchesters.taxiii;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFormActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        mAuth = FirebaseAuth.getInstance();

        initializeComponents();
    }

    private void initializeComponents() {
        TextView emailTV = (TextView) findViewById(R.id.editTextTextEmailAddress);
        TextView passwordTV = (TextView) findViewById(R.id.editTextTextPassword);
        Button signinButton = (Button) findViewById(R.id.signinButton);
        signinButton.setOnClickListener(view -> {
            final String email = emailTV.getText().toString();
            final String password = passwordTV.getText().toString();
            logIn(email, password);
        });
    }

    private void logIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginFormActivity.this, task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(LoginFormActivity.this, "log in error", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(LoginFormActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(LoginFormActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }


}