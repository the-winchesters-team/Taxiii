package the.winchesters.taxiii.activity;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;

import the.winchesters.taxiii.R;
import the.winchesters.taxiii.activity.taxi_driver.TaxiDriverSignUpActivity;
import the.winchesters.taxiii.model.User;

public class ProfileActivity extends NavigationBarActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        initializeComponents();
        changePassword();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_profile;
    }

    private void changePassword() {
        TextView profileChangePassword = (TextView) findViewById(R.id.profile_change_password);
        profileChangePassword.setOnClickListener(v -> {
             final EditText resetPassword = new EditText(v.getContext()) ;
             final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
             passwordResetDialog.setTitle("Change password");
             passwordResetDialog.setMessage("Enter new password : minimum 6 characters");
             passwordResetDialog.setView(resetPassword);

             passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     String newPassword = resetPassword.getText().toString();
                     FirebaseUser user = mAuth.getCurrentUser();
                     user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                         @Override
                         public void onSuccess(Void unused) {
                             Toast.makeText(ProfileActivity.this,"Password reset succesfulyy",Toast.LENGTH_SHORT).show();
                         }

                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Toast.makeText(ProfileActivity.this,"Password reset failure",Toast.LENGTH_SHORT).show();
                         }
                     });

                 }
             });
             passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                   //TODO : close the dialag
                 }
             });
             passwordResetDialog.create().show();


        });
    }

    public void initializeComponents() {

        TextView usernameView = (TextView) findViewById(R.id.profile_username);
        TextView fullNameView = (TextView) findViewById(R.id.profile_name);
        TextView emailView = (TextView) findViewById(R.id.profile_email);
        TextView genderView = (TextView) findViewById(R.id.profile_gender);
        TextView dateOfBirthView = (TextView) findViewById(R.id.profile_date_of_birth);
        TextView numberView = (TextView) findViewById(R.id.profile_phone_number);
        TextView profileChangePassword = (TextView) findViewById(R.id.profile_change_password);


        String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        DatabaseReference userDbRef = FirebaseDatabase.getInstance().getReference(String.format("User/%s", userId));
        userDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                usernameView.setText(user.getUsername());
                fullNameView.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
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