package the.winchesters.taxiii;

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

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        firebaseAuthListener = firebaseAuth -> {
            FirebaseUser user =   FirebaseAuth.getInstance().getCurrentUser();
            if(user!=null){
                Intent intent = new Intent(SignUpActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        };
        signUp();
    }
    private void signUp() {
        TextView emailTV = (TextView) findViewById(R.id.emailET);
        TextView passwordTV = (TextView) findViewById(R.id.passwordET);
        Button signUpBtn = (Button) findViewById(R.id.signinButton);
        signUpBtn.setOnClickListener(view -> {
            final String email = emailTV.getText().toString();
            final String password = passwordTV.getText().toString();
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, task -> {
                if(!task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "sign up error", Toast.LENGTH_SHORT).show();
                }else {
                    String user_id = auth.getCurrentUser().getUid();
                    DatabaseReference user_db = FirebaseDatabase.getInstance().getReference()
                            .child("User")
                            .child("Client")
                            .child(user_id);

                    user_db.setValue(true);
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