package goodyapp.example.com.goodyapp.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import goodyapp.example.com.goodyapp.network.models.LatestCurrencies;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class CurrencyClient {

    public static final String REST_API_URL = "http://api.fixer.io";

    private static Retrofit retrofit;
    private static CurrenciesInterface currencyApiService;

    static {

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(REST_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static CurrenciesInterface getCurrencyApiClient() {

        if (currencyApiService == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(REST_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            currencyApiService = retrofit.create(CurrenciesInterface.class);
        }

        return currencyApiService;
    }

    public interface CurrenciesInterface {

        @GET("/latest")
        Call<LatestCurrencies> getLatestCurrencies();
    }
}
