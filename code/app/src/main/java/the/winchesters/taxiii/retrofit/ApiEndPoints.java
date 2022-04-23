package the.winchesters.taxiii.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import the.winchesters.taxiii.dto.LoginFormData;
import the.winchesters.taxiii.dto.SignUpFormDto;
import the.winchesters.taxiii.dto.User;

public interface ApiEndPoints {
    @POST("/login")
    Call<Void> login(@Body LoginFormData loginFormData);

    @POST("/signup")
    Call<User> signup(@Body SignUpFormDto signUpForm);

}

