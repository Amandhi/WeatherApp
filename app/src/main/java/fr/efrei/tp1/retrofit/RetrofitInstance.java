package fr.efrei.tp1.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit = null;



    public static Retrofit getInstance() {
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor() ;
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level. BODY);

        final OkHttpClient okHttp = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor( new MyInterceptor())
                .build() ;

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(MoshiConverterFactory. create())
                    .client(okHttp)
                    .build() ;
        }

        return retrofit;
    }

}

