package goodyapp.example.com.goodyapp.presentation.presenters.impl;


import java.util.List;

import goodyapp.example.com.goodyapp.domain.executor.Executor;
import goodyapp.example.com.goodyapp.domain.executor.MainThread;
import goodyapp.example.com.goodyapp.domain.repository.GoodsRepository;
import goodyapp.example.com.goodyapp.domain.usercases.AddGoodToBasketUserCase;
import goodyapp.example.com.goodyapp.domain.usercases.GetBasketSizeUserCase;
import goodyapp.example.com.goodyapp.domain.usercases.GetGoodsUserCase;
import goodyapp.example.com.goodyapp.domain.usercases.RemoveGoodFromBasketUserCase;
import goodyapp.example.com.goodyapp.domain.usercases.impl.AddGoodToBasketUserCaseImpl;
import goodyapp.example.com.goodyapp.domain.usercases.impl.GetBasketSizeUserCaseImpl;
import goodyapp.example.com.goodyapp.domain.usercases.impl.GetGoodsUserCaseImpl;
import goodyapp.example.com.goodyapp.domain.usercases.impl.RemoveGoodFromBasketUserCaseImpl;
import goodyapp.example.com.goodyapp.presentation.models.Good;
import goodyapp.example.com.goodyapp.presentation.presenters.AbstractPresenter;
import goodyapp.example.com.goodyapp.presentation.presenters.GoodsPresenter;

public class GoodsPresenterImpl extends AbstractPresenter implements GoodsPresenter,
        GetGoodsUserCase.Callback, AddGoodToBasketUserCase.Callback,
        RemoveGoodFromBasketUserCase.Callback, GetBasketSizeUserCase.Callback {

    private String currency;
    private GoodsRepository goodsRepository;
    private GoodsPresenter.View mView;

    public GoodsPresenterImpl(Executor executor, MainThread mainThread,
                              GoodsPresenter.View view, GoodsRepository goodsRepository,
                              String currency) {

        super(executor, mainThread);
        this.mView = view;
        this.goodsRepository = goodsRepository;
        this.currency = currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public void showGoods() {
        GetGoodsUserCase getGoodsUserCase =
                new GetGoodsUserCaseImpl(mExecutor, mMainThread, goodsRepository, currency, this);
        getGoodsUserCase.execute();
    }

    @Override
    public void getBasketSize() {
        GetBasketSizeUserCase getBasketSizeUserCase =
                new GetBasketSizeUserCaseImpl(mExecutor, mMainThread, goodsRepository, currency, this);
        getBasketSizeUserCase.execute();
    }

    @Override
    public void addGoodToBasket(Good good) {
        AddGoodToBasketUserCase addGoodToBasketUserCase =
                new AddGoodToBasketUserCaseImpl(mExecutor, mMainThread, goodsRepository, good, this);
        addGoodToBasketUserCase.execute();
    }

    @Override
    public void removeGoodFromBasket(Good good) {
        RemoveGoodFromBasketUserCase removeGoodFromBasketUserCase =
                new RemoveGoodFromBasketUserCaseImpl(mExecutor, mMainThread,
                        goodsRepository, good, this);
        removeGoodFromBasketUserCase.execute();
    }

    @Override
    public void onGoodsRetrieved(List<Good> goods) {
        mView.onGoodsRetrieved(goods);
    }

    @Override
    public void onGoodAddedToBasket(boolean added) {
        mView.onGoodAdded(added);
    }

    @Override
    public void onGoodsRemovedToBasket(boolean removed) {
        mView.onGoodRemovedFromBasket(removed);
    }

    @Override
    public void onBasketGoodsSizeRetrieved(int basketSize) {
        mView.onBasketGoodsSizeRetrieved(basketSize);
    }
}
