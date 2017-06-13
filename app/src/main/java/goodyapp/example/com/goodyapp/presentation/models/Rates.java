package goodyapp.example.com.goodyapp.presentation.models;

public enum Rates{
    AUD("AUD - $"),
    BGN("BGN - лв"),
    BRL("BRL - R$"),
    CAD("CAD - $"),
    CHF("CHF - CHF"),
    CNY("CNY - ¥"),
    CZK("CZK - Kč"),
    DKK("DKK - kr"),
    EUR("EUR - €"),
    GBP("GBP - £"),
    HKD("HKD - $"),
    HRK("HRK - kn"),
    HUF("HUF - Ft"),
    IDR("IDR- Rp"),
    ILS("ILS - ₪"),
    INR("INR - Rp"),
    JPY("JPY - ¥"),
    KRW("KRW - ₩"),
    MXN("MXN - $"),
    MYR("MYR - RM"),
    NOK("NOK - kr"),
    NZD("NZD - $"),
    PHP("PHP - ₱"),
    PLN("PLN - zł"),
    RON("RON - lei"),
    RUB("RUN - RUB"),
    SEK("SEK - kr"),
    SGD("SGD - $"),
    THB("THB - ฿"),
    TRY("TRY - TRY"),
    USD("USD - $"),
    ZAR("ZAR - R");

    private String friendlyName;

    Rates(String friendlyName){
        this.friendlyName = friendlyName;
    }

    @Override public String toString(){
        return friendlyName;
    }

}
