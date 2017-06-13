package goodyapp.example.com.goodyapp.domain.usercases;

import goodyapp.example.com.goodyapp.domain.usercases.base.UserCase;

public interface RemoveGoodFromBasketUserCase extends UserCase {

    interface Callback {
        void onGoodsRemovedToBasket(boolean removed);
    }
}
