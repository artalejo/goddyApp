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

    public HashMap<String, Double> getRates() {
        return this.rates;
    }

}