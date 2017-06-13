package goodyapp.example.com.goodyapp.storage;

import android.content.Context;

import java.util.HashMap;
import java.util.List;

import goodyapp.example.com.goodyapp.domain.repository.GoodsRepository;
import goodyapp.example.com.goodyapp.presentation.models.Good;
import goodyapp.example.com.goodyapp.storage.database.GoodsDb;

public class GoodsRepositoryImpl implements GoodsRepository {


    private final GoodsDb db;

    public GoodsRepositoryImpl(Context context) {
        db = GoodsDb.getInstance(context);
    }

    @Override
    public boolean addGoodToBasket(Good good) {
        return db.addGoodToBasket(good);
    }

    @Override
    public boolean removeGoodFromBasket(Good good) {
        return db.removeGoodFromBasket(good);
    }

    @Override
    public List<Good> getGoods(String currencySelected) {
        return db.getGoods(currencySelected);
    }

    @Override
    public List<Good> getBasketGoods(String currencySelected) {
        return db.getBasketGoods(currencySelected);
    }

    @Override
    public void insertCurrencies(HashMap<String, Double> currencies) {
        db.insertCurrencies(currencies);
    }
}
