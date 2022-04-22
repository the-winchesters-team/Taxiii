package the.winchesters.taxiii.retrofit;

import android.content.res.Resources;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import the.winchesters.taxiii.R;

public class RetrofitService {
    private Retrofit retrofit;

    public RetrofitService() {
        initialiseRetrofit();
    }

    private void initialiseRetrofit() {
//        String backendUrl = Resources.getSystem().getString(R.string.backendUrl);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.29:8080")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }
    public Retrofit getRetrofit(){
        return retrofit;
    }
}
