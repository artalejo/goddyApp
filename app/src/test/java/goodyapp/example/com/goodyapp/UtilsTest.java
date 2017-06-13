package goodyapp.example.com.goodyapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import goodyapp.example.com.goodyapp.presentation.models.Good;

import static junit.framework.Assert.assertEquals;

public class UtilsTest {

    @Mock private AppCompatActivity mockedActivity;
    @Mock private AndroidApplication mockedAndroidApp;
    @Mock private Context mockedContext;

    private HashMap<String, String> currencyCodes;
    private Good good;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        currencyCodes = new HashMap<>();
        currencyCodes.put("GBP", "£");
        good = new Good(1, "test", 22.22, "testing", "resource", 2, "GBP");

        Mockito.when(mockedActivity.getApplication()).thenReturn(mockedAndroidApp);
        Mockito.when(mockedAndroidApp.getCurrencyCodes()).thenReturn(currencyCodes);
    }

    @Test
    public void getPriceDescriptionTest() throws Exception {
        String priceDescription = Utils.getPriceDescription(mockedActivity, good);

        String expectedPriceDescription = "22.22 £ testing";
        assertEquals(priceDescription, expectedPriceDescription);
    }

    @Test
    public void getGoodPriceSummaryTest() throws Exception {
        String priceSummary = Utils.getGoodPriceSummary(mockedActivity, good);

        String expectedPriceSummary = "2 x 22.22 £ testing";
        assertEquals(priceSummary, expectedPriceSummary);
    }

    @Test
    public void getCheckoutTotalMessageTest() throws Exception {
        List<Good> goods = new ArrayList<>();
        goods.add(good);
        String checkoutMsg = Utils.getCheckoutTotalMessage(mockedActivity, goods);

        String expectedPriceSummary = "The total of your purchase is 44.44 £";
        assertEquals(checkoutMsg, expectedPriceSummary);
    }

    @Test
    public void getCurrencyCodeWhenCurrencyIsWrongTest() throws Exception {
        String currencyCode = Utils.getCurrencyCode(mockedActivity, "TEST");
        // retrieves € by default
        String expectedPriceSummary = "€";
        assertEquals(currencyCode, expectedPriceSummary);
    }

    @Test
    public void getCurrencyCodeWhenCurrencyExistsTest() throws Exception {
        String currencyCode = Utils.getCurrencyCode(mockedActivity, "GBP");
        // retrieves € by default
        String expectedPriceSummary = "£";
        assertEquals(currencyCode, expectedPriceSummary);
    }


}
