package the.winchesters.taxiii.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

//https://stackoverflow.com/questions/3624280/how-to-use-sharedpreferences-in-android-to-store-fetch-and-edit-values
public class TokenUtils {

    public static void saveJwtToken(String token, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Authorization", token);
        Log.i("Login", token);
        editor.apply();
    }
    public static String getJwtToken(Context context){
        //returns token if it exists in sharedPreferences , else it returns empty string ""
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("Authorization", "");
    }

}
