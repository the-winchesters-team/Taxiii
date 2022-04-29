package the.winchesters.taxiii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class LoginOrSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_signup);

        //sign up
        TextView signUp = (TextView) findViewById(R.id.sign_up_clickable);
        signUp.setOnClickListener(view -> {
            Intent intent  = new Intent(LoginOrSignUpActivity.this, PickingRoleActivity.class);
            startActivity(intent);
        });

        //login
        TextView login = (TextView) findViewById(R.id.login_clickable);
        login.setOnClickListener(view -> {
            Intent intent  = new Intent(LoginOrSignUpActivity.this, LoginFormActivity.class);
            startActivity(intent);
        });
    }
}