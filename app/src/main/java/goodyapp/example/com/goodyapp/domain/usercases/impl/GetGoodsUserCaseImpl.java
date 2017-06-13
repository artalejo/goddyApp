package goodyapp.example.com.goodyapp.domain.usercases.impl;


import goodyapp.example.com.goodyapp.domain.executor.Executor;
import goodyapp.example.com.goodyapp.domain.executor.MainThread;
import goodyapp.example.com.goodyapp.domain.repository.GoodsRepository;
import goodyapp.example.com.goodyapp.domain.usercases.GetGoodsUserCase;
import goodyapp.example.com.goodyapp.domain.usercases.base.AbstractUserCase;


public class GetGoodsUserCaseImpl extends AbstractUserCase implements GetGoodsUserCase {

    private final String currency;
    private GoodsRepository goodsRepository;
    private GetGoodsUserCase.Callback getGoodsCallback;

    public GetGoodsUserCaseImpl(Executor threadExecutor, MainThread mainThread,
                                GoodsRepository goodsRepository, String currency, Callback callback) {
        super(threadExecutor, mainThread);
        this.getGoodsCallback = callback;
        this.currency = currency;
        this.goodsRepository = goodsRepository;
    }


    @Override
    public void run() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                getGoodsCallback.onGoodsRetrieved(goodsRepository.getGoods(currency));
            }
        });
    }
}
