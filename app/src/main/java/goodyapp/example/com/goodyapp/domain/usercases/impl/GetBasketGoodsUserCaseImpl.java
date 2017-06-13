package goodyapp.example.com.goodyapp.domain.usercases.impl;


import goodyapp.example.com.goodyapp.domain.executor.Executor;
import goodyapp.example.com.goodyapp.domain.executor.MainThread;
import goodyapp.example.com.goodyapp.domain.repository.GoodsRepository;
import goodyapp.example.com.goodyapp.domain.usercases.GetBasketGoodsUserCase;
import goodyapp.example.com.goodyapp.domain.usercases.base.AbstractUserCase;


public class GetBasketGoodsUserCaseImpl extends AbstractUserCase implements GetBasketGoodsUserCase {

    private final String currency;
    private GoodsRepository goodsRepository;
    private Callback getGoodsCallback;

    public GetBasketGoodsUserCaseImpl(Executor threadExecutor, MainThread mainThread,
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
                getGoodsCallback.onBasketGoodsRetrieved(goodsRepository.getBasketGoods(currency));
            }
        });
    }
}
