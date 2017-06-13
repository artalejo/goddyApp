package goodyapp.example.com.goodyapp.network.services;

import goodyapp.example.com.goodyapp.network.CurrencyClient;
import goodyapp.example.com.goodyapp.network.models.LatestCurrencies;
import retrofit2.Call;
import retrofit2.Callback;

public class GetCurrenciesService {

    public static CurrencyClient.CurrenciesInterface currencyClient;

    public GetCurrenciesService(CurrencyClient.CurrenciesInterface servicesInterface) {
        this.currencyClient = servicesInterface;
    }

    public void getLatestCurrenciesService(Callback<LatestCurrencies> getLatestCurrenciesCallback) {

        Call<LatestCurrencies> latestCurrenciesCall = this.currencyClient.getLatestCurrencies();
        latestCurrenciesCall.enqueue(getLatestCurrenciesCallback);
    }

}
