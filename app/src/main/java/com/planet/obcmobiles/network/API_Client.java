package com.planet.obcmobiles.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class API_Client {


    //public static final String BASE_URL = "https://121.241.255.225/api/OBCSathi/"; // test live
    public static final String BASE_URL = "https://121.241.255.225:8443/api/OBCSathi/"; //live
    private static Retrofit retrofit = null;
    private static OkHttpClient client = new OkHttpClient.Builder()
/*          .sslSocketFactory(TrustKit.getInstance().getSSLSocketFactory("www.datatheorem.com"),
                  TrustKit.getInstance().getTrustManager("www.datatheorem.com"))*/
            /*   .sslSocketFactory(TrustKit.getInstance().getSSLSocketFactory("https://121.241.255.225"),
                       TrustKit.getInstance().getTrustManager("https://121.241.255.225"))*/
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(1000, TimeUnit.SECONDS)
            .build();
    //X509TrustManager trustManager;
    //SSLSocketFactory sslSocketFactory;


  /*  public API_Client() {
        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            // throw new RuntimeException(e);
        }
    }*/

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//if using RxJava only
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
