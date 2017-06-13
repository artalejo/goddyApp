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
import goodyapp.example.com.goodyapp.domain.usercases.impl.AddGoodToBasketUserCaseImpl;
import goodyapp.example.com.goodyapp.presentation.models.Good;
import goodyapp.example.com.goodyapp.storage.database.GoodsDb;
import goodyapp.example.com.goodyapp.threading.TestMainThread;

import static org.mockito.Mockito.times;


public class AddGoodToBasketTest {

    private MainThread mMainThread;
    @Mock private Executor mExecutor;
    @Mock private AddGoodToBasketUserCase.Callback mockedCallback;
    @Mock private Good mockedGood;
    @Mock private Context mockedContext;
    @Mock private GoodsRepository mockedGoodRepository;
    @Mock private GoodsDb mockedDb;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mMainThread = new TestMainThread();
    }

    @Test
    public void notAddingGoodWhenGoodIsNullTest() throws Exception {
        AddGoodToBasketUserCaseImpl userCase =
                new AddGoodToBasketUserCaseImpl(mExecutor, mMainThread, mockedGoodRepository,
                        null, mockedCallback);
        userCase.run();

        Mockito.verify(mockedCallback).onGoodAddedToBasket(false);
        Mockito.verifyNoMoreInteractions(mockedCallback);
    }

    @Test
    public void addingGoodWhenGoodNotNullTest() throws Exception {
        AddGoodToBasketUserCaseImpl userCase =
                new AddGoodToBasketUserCaseImpl(mExecutor, mMainThread, mockedGoodRepository,
                        mockedGood, mockedCallback);
        userCase.run();

        Mockito.verify(mockedGoodRepository).addGoodToBasket(mockedGood);
        Mockito.verifyNoMoreInteractions(mockedGoodRepository);
        Mockito.verify(mockedCallback, times(1)).onGoodAddedToBasket(Mockito.anyBoolean());
    }
}
