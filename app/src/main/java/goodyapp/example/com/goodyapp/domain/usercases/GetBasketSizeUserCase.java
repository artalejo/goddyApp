package goodyapp.example.com.goodyapp.domain.usercases;

import goodyapp.example.com.goodyapp.domain.usercases.base.UserCase;

public interface GetBasketSizeUserCase extends UserCase {

    interface Callback {
        void onBasketGoodsSizeRetrieved(int basketSize);
    }
}
