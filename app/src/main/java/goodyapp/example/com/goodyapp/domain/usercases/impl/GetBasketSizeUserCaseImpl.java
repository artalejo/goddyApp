package goodyapp.example.com.goodyapp.domain.usercases.impl;


import goodyapp.example.com.goodyapp.domain.executor.Executor;
import goodyapp.example.com.goodyapp.domain.executor.MainThread;
import goodyapp.example.com.goodyapp.domain.repository.GoodsRepository;
import goodyapp.example.com.goodyapp.domain.usercases.GetBasketSizeUserCase;
import goodyapp.example.com.goodyapp.domain.usercases.base.AbstractUserCase;


public class GetBasketSizeUserCaseImpl extends AbstractUserCase implements GetBasketSizeUserCase {

    private final String currency;
    private GoodsRepository goodsRepository;
    private Callback getGoodsCallback;

    public GetBasketSizeUserCaseImpl(Executor threadExecutor, MainThread mainThread,
                                     GoodsRepository goodsRepository, String currency,
                                     Callback callback) {
        super(threadExecutor, mainThread);
        this.getGoodsCallback = callback;
        this.goodsRepository = goodsRepository;
        this.currency = currency;
    }


    @Override
    public void run() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                getGoodsCallback.onBasketGoodsSizeRetrieved(goodsRepository.getBasketGoods(currency).size());
            }
        });
    }
}
