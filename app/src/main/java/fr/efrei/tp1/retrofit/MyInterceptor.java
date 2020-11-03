package fr.efrei.tp1.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

public class MyInterceptor implements Interceptor
{
    @Override
    public okhttp3.Response intercept (Chain chain)
            throws IOException
    {
        final Request request = chain
                .request()
                .newBuilder()
                .addHeader( "Custom-Hader" , "XXXX-XXXX-XXXX" )
                .build() ;
        return chain.proceed(request) ;
    }
}
