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


    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        auth = FirebaseAuth.getInstance();
        firebaseAuthListener = firebaseAuth -> {
            FirebaseUser user =   FirebaseAuth.getInstance().getCurrentUser();
            if(user!=null){
                Intent intent = new Intent(LoginFormActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        };
        logIn();
    }

    private void logIn() {
        TextView emailTV = (TextView) findViewById(R.id.editTextTextEmailAddress);
        TextView passwordTV = (TextView) findViewById(R.id.editTextTextPassword);
        Button signinButton = (Button) findViewById(R.id.signinButton);
        signinButton.setOnClickListener(view -> {
            final String email = emailTV.getText().toString();
            final String password = passwordTV.getText().toString();
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginFormActivity.this, task -> {
                if(!task.isSuccessful()) {
                    Toast.makeText(LoginFormActivity.this, "log in error", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(LoginFormActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(firebaseAuthListener);
    }
}