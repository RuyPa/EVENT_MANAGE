package api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseAPI {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://mobile-app-server-2byn.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
