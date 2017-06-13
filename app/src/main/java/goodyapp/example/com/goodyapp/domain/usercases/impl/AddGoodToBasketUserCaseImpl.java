package goodyapp.example.com.goodyapp.domain.usercases.impl;


import goodyapp.example.com.goodyapp.domain.executor.Executor;
import goodyapp.example.com.goodyapp.domain.executor.MainThread;
import goodyapp.example.com.goodyapp.domain.repository.GoodsRepository;
import goodyapp.example.com.goodyapp.domain.usercases.AddGoodToBasketUserCase;
import goodyapp.example.com.goodyapp.domain.usercases.base.AbstractUserCase;
import goodyapp.example.com.goodyapp.presentation.models.Good;


public class AddGoodToBasketUserCaseImpl extends AbstractUserCase implements AddGoodToBasketUserCase {

    private final Good goodToAdd;
    private GoodsRepository goodsRepository;
    private Callback addGoodToBasketCallback;

    public AddGoodToBasketUserCaseImpl(Executor threadExecutor, MainThread mainThread,
                                       GoodsRepository goodsRepository, Good goodToAdd,
                                       Callback callback) {

        super(threadExecutor, mainThread);
        this.goodsRepository = goodsRepository;
        this.goodToAdd = goodToAdd;
        this.addGoodToBasketCallback = callback;
    }


    @Override
    public void run() {
        final boolean goodAdded = goodsRepository.addGoodToBasket(goodToAdd);
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                addGoodToBasketCallback.onGoodAddedToBasket(goodAdded);
            }
        });
    }
}
