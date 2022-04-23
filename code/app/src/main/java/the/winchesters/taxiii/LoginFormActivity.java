package the.winchesters.taxiii;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;


import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import the.winchesters.taxiii.dto.LoginFormData;
import the.winchesters.taxiii.retrofit.ApiEndPoints;
import the.winchesters.taxiii.retrofit.RetrofitService;

public class LoginFormActivity extends AppCompatActivity {

    private SignInButton googleAuthButton;
    private GoogleSignInClient googleSignInClient;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        System.out.println(data);
                    }
                });

        setContentView(R.layout.activity_login_form);
        initialiseComponents();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleAuthButton =  (SignInButton) findViewById(R.id.fab_google);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null)
            updateUI(account);
        googleAuthButton.setOnClickListener(view -> {
            signInThroughGoogle();
        });

    }

    private void updateUI(GoogleSignInAccount account) {
        //TODO
        Logger.getGlobal().log(Level.SEVERE, "updateUI not yet handled");
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
                                //https://stackoverflow.com/questions/3624280/how-to-use-sharedpreferences-in-android-to-store-fetch-and-edit-values
                                //Save token in shared preferences
                                String saveToken = response.headers().get("Authorization");
                                editor.putString("Authorization", saveToken);
                                Log.i("Login", saveToken);
                                editor.apply();
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
    private void signInThroughGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        someActivityResultLauncher.launch(signInIntent);
    }

}