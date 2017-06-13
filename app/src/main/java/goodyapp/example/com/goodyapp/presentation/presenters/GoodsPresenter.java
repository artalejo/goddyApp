package goodyapp.example.com.goodyapp.presentation.presenters;

import java.util.List;

import goodyapp.example.com.goodyapp.presentation.models.Good;

public interface GoodsPresenter {

    interface View {
        void onGoodsRetrieved(List<Good> goods);
        void onBasketGoodsSizeRetrieved(int basketSize);
        void onGoodAdded(boolean added);
        void onGoodRemovedFromBasket(boolean removed);
    }

    void showGoods();
    void getBasketSize();
    void addGoodToBasket(Good good);
    void removeGoodFromBasket(Good good);
}
