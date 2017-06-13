package goodyapp.example.com.goodyapp.presentation.presenters;

import java.util.List;

import goodyapp.example.com.goodyapp.presentation.models.Good;

public interface BasketPresenter {

    interface View {
        void onBasketGoodsRetrieved(List<Good> goods);
        void onGoodRemovedFromBasket(boolean removed);
    }

    void showBasketGoods();
    void removeGoodFromBasket(Good good);
}
