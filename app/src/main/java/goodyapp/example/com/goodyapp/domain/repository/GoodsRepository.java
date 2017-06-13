package goodyapp.example.com.goodyapp.domain.repository;

import java.util.HashMap;
import java.util.List;

import goodyapp.example.com.goodyapp.presentation.models.Good;

public interface GoodsRepository {

    boolean addGoodToBasket(Good good);
    boolean removeGoodFromBasket(Good good);
    List<Good> getGoods(String currency);
    List<Good> getBasketGoods(String currency);
    void insertCurrencies(HashMap<String, Double> currencies);
    double getCurrencyValue(String currencyName);

}
