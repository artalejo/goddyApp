package goodyapp.example.com.goodyapp.domain.usercases.impl;


import goodyapp.example.com.goodyapp.domain.executor.Executor;
import goodyapp.example.com.goodyapp.domain.executor.MainThread;
import goodyapp.example.com.goodyapp.domain.repository.GoodsRepository;
import goodyapp.example.com.goodyapp.domain.usercases.RemoveGoodFromBasketUserCase;
import goodyapp.example.com.goodyapp.domain.usercases.base.AbstractUserCase;
import goodyapp.example.com.goodyapp.presentation.models.Good;


public class RemoveGoodFromBasketUserCaseImpl extends AbstractUserCase implements RemoveGoodFromBasketUserCase {

    private final Good goodToRemove;
    private GoodsRepository goodsRepository;
    private Callback removeGoodFromBasketCallback;

    public RemoveGoodFromBasketUserCaseImpl(Executor threadExecutor, MainThread mainThread,
                                            GoodsRepository goodsRepository, Good goodToRemove,
                                            Callback callback) {

        super(threadExecutor, mainThread);
        this.goodsRepository = goodsRepository;
        this.goodToRemove = goodToRemove;
        this.removeGoodFromBasketCallback = callback;
    }


    @Override
    public void run() {
        final boolean goodRemoved = goodsRepository.removeGoodFromBasket(goodToRemove);
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                removeGoodFromBasketCallback.onGoodsRemovedToBasket(goodRemoved);
            }
        });
    }
}
