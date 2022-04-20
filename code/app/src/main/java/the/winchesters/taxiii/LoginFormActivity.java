package the.winchesters.taxiii;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import the.winchesters.taxiii.dto.LoginFormData;
import the.winchesters.taxiii.retrofit.ApiEndPoints;
import the.winchesters.taxiii.retrofit.RetrofitService;

public class LoginFormActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        initialiseComponents();
    }

    private void initialiseComponents() {
        TextView emailTV = (TextView) findViewById(R.id.editTextTextEmailAddress);
        TextView passwordTV =(TextView)  findViewById(R.id.editTextTextPassword);
        Button signinButton = (Button) findViewById(R.id.signinButton);

        RetrofitService retrofitService = new RetrofitService();
        ApiEndPoints apiEndPoints = retrofitService.getRetrofit().create(ApiEndPoints.class);

        signinButton.setOnClickListener(view -> {
            String username = String.valueOf(emailTV.getText());
            String password = String.valueOf(passwordTV.getText());

            LoginFormData loginFormData = new LoginFormData();
            loginFormData.setUsername(username);
            loginFormData.setPassword(password);

            apiEndPoints.login(loginFormData)
                    .enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            String msg = response.headers().get("Authorization");
                            Toast.makeText(LoginFormActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(LoginFormActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(LoginFormActivity.class.getName()).log(Level.SEVERE, "Error occured");
                        }
                    });


        });


    }
}