package ma.ensias.winchesters.utils;

import org.springframework.http.HttpMethod;

import java.lang.reflect.Method;

public class Format {
    public static String formatRequest(HttpMethod Request, String method, String message){
        return Request + " Request : "+method + " -- "+message;
    }
    public static String formatRequest(HttpMethod Request, String method){
        return Request + " Request : "+method ;
    }
}
