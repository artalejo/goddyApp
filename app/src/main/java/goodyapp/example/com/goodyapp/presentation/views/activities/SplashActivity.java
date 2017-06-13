package goodyapp.example.com.goodyapp.presentation.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import goodyapp.example.com.goodyapp.AndroidApplication;
import goodyapp.example.com.goodyapp.R;
import goodyapp.example.com.goodyapp.Utils;
import goodyapp.example.com.goodyapp.network.CurrencyClient;
import goodyapp.example.com.goodyapp.network.models.LatestCurrencies;
import goodyapp.example.com.goodyapp.network.services.GetCurrenciesService;
import goodyapp.example.com.goodyapp.storage.GoodsRepositoryImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivity extends AppCompatActivity {

    private CurrencyClient.CurrenciesInterface currencyApiClient;
    private GetCurrenciesService currenciesService;
    private Callback<LatestCurrencies> latestCurrenciesCallBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadCurrencyCodes();

        // Initializing network services and getting the latest currency values
        currencyApiClient = CurrencyClient.getCurrencyApiClient();
        currenciesService = new GetCurrenciesService(currencyApiClient);
        latestCurrenciesCallBack = getLatestCurrenciesCallBack();
        currenciesService.getLatestCurrenciesService(latestCurrenciesCallBack);

        startActivity(new Intent(SplashActivity.this, GoodsActivity.class));
    }

    private void loadCurrencyCodes() {
        // Loading currency symbols from assets and saving globally for the whole app.
        Gson gson = new Gson();
        String json = Utils.loadJSONFromAsset(this, "currency_symbols.json");
        Map<String, String> currencyCodes = new HashMap<>();
        currencyCodes = (Map<String, String>) gson.fromJson(json, currencyCodes.getClass());
        ((AndroidApplication) this.getApplication()).setCurrencyCodes(currencyCodes);
    }

    private Callback<LatestCurrencies> getLatestCurrenciesCallBack() {
        return new Callback<LatestCurrencies>() {
            @Override
            public void onResponse(Call<LatestCurrencies> call, Response<LatestCurrencies> response) {
                if (response.isSuccessful()) {
                    LatestCurrencies latestCurrencies = response.body();
                    // Adding the currencies to the repository
                    GoodsRepositoryImpl goodsRepository = new GoodsRepositoryImpl(SplashActivity.this);
                    goodsRepository.insertCurrencies(latestCurrencies.getRates());
                }
                else {
                    Toast.makeText(SplashActivity.this, R.string.error_retrieving_currencies_msg,
                                    Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LatestCurrencies> call, Throwable t) {
                Toast.makeText(SplashActivity.this, R.string.error_retrieving_currencies_msg,
                        Toast.LENGTH_SHORT).show();
            }
        };
    }
}

