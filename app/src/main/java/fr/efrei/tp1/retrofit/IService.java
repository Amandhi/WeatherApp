package fr.efrei.tp1.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IService {

    @GET ("weather?appid=38c336434c38a692d06fc4cc10c99b87")
    Call<Example> getWeatherData(@Query("q") String name);
}
