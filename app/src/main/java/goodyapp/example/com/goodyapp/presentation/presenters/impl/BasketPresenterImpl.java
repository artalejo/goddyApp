package goodyapp.example.com.goodyapp.presentation.presenters.impl;


import java.util.List;

import goodyapp.example.com.goodyapp.domain.executor.Executor;
import goodyapp.example.com.goodyapp.domain.executor.MainThread;
import goodyapp.example.com.goodyapp.domain.repository.GoodsRepository;
import goodyapp.example.com.goodyapp.domain.usercases.GetBasketGoodsUserCase;
import goodyapp.example.com.goodyapp.domain.usercases.RemoveGoodFromBasketUserCase;
import goodyapp.example.com.goodyapp.domain.usercases.impl.GetBasketGoodsUserCaseImpl;
import goodyapp.example.com.goodyapp.domain.usercases.impl.RemoveGoodFromBasketUserCaseImpl;
import goodyapp.example.com.goodyapp.presentation.models.Good;
import goodyapp.example.com.goodyapp.presentation.presenters.AbstractPresenter;
import goodyapp.example.com.goodyapp.presentation.presenters.BasketPresenter;

public class BasketPresenterImpl extends AbstractPresenter implements BasketPresenter,
        GetBasketGoodsUserCase.Callback, RemoveGoodFromBasketUserCase.Callback{

    private final String currency;
    private GoodsRepository goodsRepository;
    private View mView;

    public BasketPresenterImpl(Executor executor, MainThread mainThread,
                               View view, GoodsRepository goodsRepository,
                               String currency) {

        super(executor, mainThread);
        this.mView = view;
        this.goodsRepository = goodsRepository;
        this.currency = currency;
    }

    @Override
    public void showBasketGoods() {
        GetBasketGoodsUserCase getBasketGoodsUserCase =
                new GetBasketGoodsUserCaseImpl(mExecutor, mMainThread, goodsRepository,
                                               currency, this);
        getBasketGoodsUserCase.execute();
    }

    @Override
    public void removeGoodFromBasket(Good good) {
        RemoveGoodFromBasketUserCase removeGoodFromBasketUserCase =
                new RemoveGoodFromBasketUserCaseImpl(mExecutor, mMainThread,
                        goodsRepository, good, this);
        removeGoodFromBasketUserCase.execute();
    }

    @Override
    public void onGoodsRemovedToBasket(boolean removed) {
        mView.onGoodRemovedFromBasket(removed);
    }

    @Override
    public void onBasketGoodsRetrieved(List<Good> goods) {
        mView.onBasketGoodsRetrieved(goods);
    }
}
