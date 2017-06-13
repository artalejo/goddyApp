package goodyapp.example.com.goodyapp.domain.usercases;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import goodyapp.example.com.goodyapp.domain.executor.Executor;
import goodyapp.example.com.goodyapp.domain.executor.MainThread;
import goodyapp.example.com.goodyapp.domain.repository.GoodsRepository;
import goodyapp.example.com.goodyapp.domain.usercases.impl.RemoveGoodFromBasketUserCaseImpl;
import goodyapp.example.com.goodyapp.presentation.models.Good;
import goodyapp.example.com.goodyapp.threading.TestMainThread;

import static org.mockito.Mockito.times;


public class RemoveGoodFromBasketTest {

    private MainThread mMainThread;
    @Mock private Executor mExecutor;
    @Mock private RemoveGoodFromBasketUserCase.Callback mockedCallback;
    @Mock private Good mockedGood;
    @Mock private Context mockedContext;
    @Mock private GoodsRepository mockedGoodsRepo;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mMainThread = new TestMainThread();
    }

    @Test
    public void notRemovingFavoriteTweetWhenTweetIsNullTest() throws Exception {
        RemoveGoodFromBasketUserCaseImpl userCase =
                new RemoveGoodFromBasketUserCaseImpl(mExecutor, mMainThread, mockedGoodsRepo,
                                                    null, mockedCallback);
        userCase.run();

        Mockito.verify(mockedCallback).onGoodsRemovedToBasket(false);
        Mockito.verifyNoMoreInteractions(mockedCallback);
    }

    @Test
    public void removingFavoriteTweetWhenTweetNotNullTest() throws Exception {
        RemoveGoodFromBasketUserCaseImpl userCase =
                new RemoveGoodFromBasketUserCaseImpl(mExecutor, mMainThread, mockedGoodsRepo,
                                                     mockedGood, mockedCallback);
        userCase.run();

        Mockito.verify(mockedGoodsRepo).removeGoodFromBasket(mockedGood);
        Mockito.verifyNoMoreInteractions(mockedGoodsRepo);
        Mockito.verify(mockedCallback, times(1)).onGoodsRemovedToBasket(Mockito.anyBoolean());
    }
}
