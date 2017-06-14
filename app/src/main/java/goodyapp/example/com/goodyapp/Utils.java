package goodyapp.example.com.goodyapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import goodyapp.example.com.goodyapp.presentation.models.Good;

public class Utils {

    private static DecimalFormat priceFormatter = new DecimalFormat("#0.00");

    public static String loadJSONFromAsset(Activity activity, String assetName) {
        String json = null;
        try {
            InputStream is = activity.getAssets().open(assetName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static String getPriceDescription(AppCompatActivity activity, Good good) {
        double price = good.getPrice();
        String currencyCode = getCurrencyCode(activity, good.getCurrency());
        String priceDescription = good.getPriceDescription();
        return priceFormatter.format(price) + " " + currencyCode + " " + priceDescription;
    }

    public static String getGoodPriceSummary(AppCompatActivity activity, Good good) {
        double price = good.getPrice();
        String currencyCode = getCurrencyCode(activity, good.getCurrency());
        String priceDescription = good.getPriceDescription();
        return good.getQuantity() + " x " + priceFormatter.format(price) + " " + currencyCode + " " + priceDescription;
    }

    public static String getCurrencyCode(AppCompatActivity activity, String currencyName) {
        Map<String, String> currencyCodes =
                ((AndroidApplication) activity.getApplication()).getCurrencyCodes();

        if (currencyCodes != null && currencyCodes.containsKey(currencyName))
            return currencyCodes.get(currencyName);
        return "€";
    }

    public static String getCheckoutTotalMessage(AppCompatActivity activity, List<Good> basketGoods) {
        double basketTotalPrice  = 0;
        for (Good good : basketGoods) {
            basketTotalPrice  += good.getPrice() * good.getQuantity();
        }
        String currencyCode = getCurrencyCode(activity, basketGoods.get(0).getCurrency());
        return "The total of your purchase is " + priceFormatter.format(basketTotalPrice) + " " + currencyCode;
    }
}
