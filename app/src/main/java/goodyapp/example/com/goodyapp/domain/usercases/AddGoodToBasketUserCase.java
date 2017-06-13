package goodyapp.example.com.goodyapp.domain.usercases;

import goodyapp.example.com.goodyapp.domain.usercases.base.UserCase;

public interface AddGoodToBasketUserCase extends UserCase {

    interface Callback {
        void onGoodAddedToBasket(boolean added);
    }
}
