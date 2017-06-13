package goodyapp.example.com.goodyapp.network.models;

import java.util.HashMap;

public class LatestCurrencies {

    private String base;
    private String date;
    private HashMap<String, Double> rates;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HashMap<String, Double> getRates() {
        return rates;
    }

    public double getRate(String currencyCode) {
        if (rates.containsKey(currencyCode))
            return rates.get(currencyCode);
        return -1;
    }

}