package the.winchesters.taxiii.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import the.winchesters.taxiii.R;
import the.winchesters.taxiii.model.User;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
    }

    public void initializeComponents(){

        TextView usernameView = (TextView)findViewById(R.id.profile_username);
        TextView fullNameView = (TextView)findViewById(R.id.profile_name);
        TextView emailView = (TextView)findViewById(R.id.profile_email);
        TextView genderView = (TextView)findViewById(R.id.profile_gender);
        TextView dateOfBirthView = (TextView)findViewById(R.id.profile_date_of_birth);

        String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        DatabaseReference user_db = FirebaseDatabase.getInstance().getReference(String.format("User/Client/%s",userId));

    }
}