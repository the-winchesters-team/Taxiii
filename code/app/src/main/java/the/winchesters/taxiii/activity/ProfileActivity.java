package the.winchesters.taxiii.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import the.winchesters.taxiii.R;
import the.winchesters.taxiii.model.Client;
import the.winchesters.taxiii.model.User;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        initializeComponents();
        changePassword();
    }

    private void changePassword() {
        TextView changePasswordView = (TextView) findViewById(R.id.profile_change_password);
        changePasswordView.setOnClickListener(view -> {
            //TODO
        });
    }

    public void initializeComponents(){

        TextView usernameView = (TextView)findViewById(R.id.profile_username);
        TextView fullNameView = (TextView)findViewById(R.id.profile_name);
        TextView emailView = (TextView)findViewById(R.id.profile_email);
        TextView genderView = (TextView)findViewById(R.id.profile_gender);
        TextView dateOfBirthView = (TextView)findViewById(R.id.profile_date_of_birth);
        TextView numberView = (TextView)findViewById(R.id.profile_phone_number);

        String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        DatabaseReference userDbRef = FirebaseDatabase.getInstance().getReference(String.format("User/Client/%s",userId));
        userDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                usernameView.setText(user.getUsername());
                fullNameView.setText(String.format("%s %s",user.getFirstName(),user.getLastName()));
                emailView.setText(mAuth.getCurrentUser().getEmail());
                numberView.setText(user.getPhoneNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
    }
}