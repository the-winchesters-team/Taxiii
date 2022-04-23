package the.winchesters.taxiii;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import the.winchesters.taxiii.dto.LoginFormData;
import the.winchesters.taxiii.dto.SignUpFormDto;
import the.winchesters.taxiii.retrofit.ApiEndPoints;
import the.winchesters.taxiii.retrofit.RetrofitService;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUp();
    }

    private void signUp() {
        Button signUpButton = (Button) findViewById(R.id.appCompatButton);

        RetrofitService retrofitService = new RetrofitService();
        ApiEndPoints apiEndPoints = retrofitService.getRetrofit().create(ApiEndPoints.class);

        signUpButton.setOnClickListener(view -> {
            String username = String.valueOf(((TextView) findViewById(R.id.usernameET)).getText());
            String password = String.valueOf(((TextView) findViewById(R.id.password)).getText());
            String confirmPassword = String.valueOf(((TextView) findViewById(R.id.confirmPasswordET)).getText());
            String email = String.valueOf(((TextView) findViewById(R.id.emailET)).getText());
            String number = String.valueOf(((TextView) findViewById(R.id.numberET)).getText());

            if(!password.equals(confirmPassword)){
                Toast.makeText(this, "confirm password doesn't match password", Toast.LENGTH_SHORT).show();
                return;
            }
            SignUpFormDto signUpForm = new SignUpFormDto();
            signUpForm.setUsername(username);
            signUpForm.setPassword(password);
            signUpForm.setPasswordConfirmation(confirmPassword);
            signUpForm.setEmail(email);
            signUpForm.setRole("CLIENT");

            apiEndPoints.signup(signUpForm)
                    .enqueue(new Callback() {
                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) {
                            if (response.code()==200){
                                Toast.makeText(SignUpActivity.this, String.format("%s registered successfully",response.code()), Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(SignUpActivity.this, String.format("error. code : %s",response.code()), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                            Toast.makeText(SignUpActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(LoginFormActivity.class.getName()).log(Level.SEVERE, "Error occured");
                        }
                    });


        });
    }

}