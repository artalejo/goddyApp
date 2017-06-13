package goodyapp.example.com.goodyapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

import java.util.Map;


public class AndroidApplication extends Application {

    private Map<String, String> currencyCodes;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }

    public Map<String, String> getCurrencyCodes() {
        return currencyCodes;
    }

    public void setCurrencyCodes(Map<String, String> currencyCodes) {
        this.currencyCodes = currencyCodes;
    }
}
