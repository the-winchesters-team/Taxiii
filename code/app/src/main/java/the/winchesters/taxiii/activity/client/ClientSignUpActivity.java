package the.winchesters.taxiii.activity.client;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import the.winchesters.taxiii.R;
import the.winchesters.taxiii.activity.NavigationBarActivity;
import the.winchesters.taxiii.model.User;

public class ClientSignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        initializeComponents();
    }

    private void initializeComponents() {
        TextView usernameView = (TextView) findViewById(R.id.sign_up_username);
        TextView emailView = (TextView) findViewById(R.id.sign_up_email);
        TextView firstNameView = (TextView) findViewById(R.id.sign_up_first_name);
        TextView lastNameView = (TextView) findViewById(R.id.sign_up_last_name);
        TextView numberView = (TextView) findViewById(R.id.sign_up_number);
        TextView confirmPasswordView = (TextView) findViewById(R.id.sign_up_confirm_password);
        TextView passwordView = (TextView) findViewById(R.id.sign_up_password);
        Button signUpBtn = (Button) findViewById(R.id.sign_up_sign_up_button);


        signUpBtn.setOnClickListener(view -> {
            final String email = emailView.getText().toString();
            final String password = passwordView.getText().toString();
            final String firstName= firstNameView.getText().toString();
            final String lastName= lastNameView.getText().toString();
            final String username= usernameView.getText().toString();
            final String number= numberView.getText().toString();
            final String confirmPassword= confirmPasswordView.getText().toString();
            if(password.equals(confirmPassword)){
                signUp(email, password,username,firstName,lastName,number);
            }else{
                Toast.makeText(ClientSignUpActivity.this,"confirm password doesn't match password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signUp(String email, String password,String username,String firstName,String lastName,String number) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(ClientSignUpActivity.this, task -> {
            if (!task.isSuccessful()) {
                Objects.requireNonNull(task.getException()).printStackTrace();
                Toast.makeText(ClientSignUpActivity.this, "sign up error", Toast.LENGTH_SHORT).show();
            } else {
                String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                Toast.makeText(ClientSignUpActivity.this, String.format("user %s registered", user_id), Toast.LENGTH_SHORT).show();
                DatabaseReference user_db = FirebaseDatabase.getInstance().getReference()
                        .child("User")
                        .child("Client")
                        .child(user_id);

                User user = new User(username,firstName,lastName,number);
                user_db.setValue(user);

                Intent intent = new Intent(ClientSignUpActivity.this, NavigationBarActivity.class);
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
            Intent intent = new Intent(ClientSignUpActivity.this, NavigationBarActivity.class);
            startActivity(intent);
            finish();
        }
    }


}