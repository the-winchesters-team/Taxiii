package the.winchesters.taxiii;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import the.winchesters.taxiii.dto.LoginFormData;
import the.winchesters.taxiii.retrofit.ApiEndPoints;
import the.winchesters.taxiii.retrofit.RetrofitService;
import the.winchesters.taxiii.utils.TokenUtils;

public class LoginFormActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        initialiseComponents();
    }

    private void initialiseComponents() {
        TextView emailTV = (TextView) findViewById(R.id.editTextTextEmailAddress);
        TextView passwordTV = (TextView) findViewById(R.id.editTextTextPassword);
        Button signinButton = (Button) findViewById(R.id.signinButton);

        RetrofitService retrofitService = new RetrofitService();
        ApiEndPoints apiEndPoints = retrofitService.getRetrofit().create(ApiEndPoints.class);

        signinButton.setOnClickListener(view -> {
            String username = String.valueOf(emailTV.getText());
            String password = String.valueOf(passwordTV.getText());

            LoginFormData loginFormData = new LoginFormData();
            loginFormData.setUsername(username);
            loginFormData.setPassword(password);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();

            apiEndPoints.login(loginFormData)
                    .enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if (response.code()==200){
                                //Save token in shared preferences
                                TokenUtils.saveJwtToken(response.headers().get("Authorization"),LoginFormActivity.this);
                                Toast.makeText(LoginFormActivity.this, "success", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(LoginFormActivity.this, String.format("error. code : %s",response.code()), Toast.LENGTH_SHORT).show();
                            }

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