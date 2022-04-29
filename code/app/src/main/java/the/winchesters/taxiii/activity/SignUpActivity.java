package the.winchesters.taxiii;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import the.winchesters.taxiii.activity.TaxiDriverMapActivity;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        initializeComponents();
    }

    private void initializeComponents() {
        TextView emailTV = (TextView) findViewById(R.id.sign_up_email);
        TextView passwordTV = (TextView) findViewById(R.id.sign_up_password);
        Button signUpBtn = (Button) findViewById(R.id.sign_up_sign_up_button);

        signUpBtn.setOnClickListener(view -> {
            final String email = emailTV.getText().toString();
            final String password = passwordTV.getText().toString();
            signUp(email, password);
        });
    }

    private void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, task -> {
            if (!task.isSuccessful()) {
                Objects.requireNonNull(task.getException()).printStackTrace();
                Toast.makeText(SignUpActivity.this, "sign up error", Toast.LENGTH_SHORT).show();
            } else {
                String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                Toast.makeText(SignUpActivity.this, String.format("user %s registered", user_id), Toast.LENGTH_SHORT).show();
                DatabaseReference user_db = FirebaseDatabase.getInstance().getReference()
                        .child("User")
                        .child("Client")
                        .child(user_id);

                user_db.setValue(true);

                Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        user.sendEmailVerification()
                .addOnCompleteListener(this, task -> {
                    // Email sent
                });
        // [END send_email_verification]
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }


}