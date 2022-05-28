package the.winchesters.taxiii.activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import the.winchesters.taxiii.R;
import the.winchesters.taxiii.activity.client.ClientMapActivity;
import the.winchesters.taxiii.activity.taxi_driver.TaxiDriverMapActivity;
import the.winchesters.taxiii.model.User;

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
                Toast.makeText(LoginFormActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                assert currentUser != null;
                DatabaseReference user_db = FirebaseDatabase.getInstance().getReference()
                            .child("User")
                            .child(currentUser.getUid());
                    user_db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            if(user==null) FirebaseAuth.getInstance().signOut();
                            Intent intent;
                            switch (user.getRole()){
                                case CLIENT :
                                    intent =new Intent(LoginFormActivity.this, ClientMapActivity.class);
                                    break;
                                case TAXI_DRIVER:
                                    intent =new Intent(LoginFormActivity.this, TaxiDriverMapActivity.class);
                                    break;
                                default:
                                    intent =new Intent(LoginFormActivity.this, LoginOrSignUpActivity.class);
                            }
                            startActivity(intent);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "loadPost:onCancelled", error.toException());
                        }
                    });
                }
                finish();
            }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(LoginFormActivity.this, NavigationBarActivity.class);
            startActivity(intent);
            finish();
        }
    }


}