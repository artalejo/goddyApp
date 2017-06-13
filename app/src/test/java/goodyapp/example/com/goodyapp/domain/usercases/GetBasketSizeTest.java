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
import goodyapp.example.com.goodyapp.domain.usercases.impl.GetBasketSizeUserCaseImpl;
import goodyapp.example.com.goodyapp.presentation.models.Good;
import goodyapp.example.com.goodyapp.storage.database.GoodsDb;
import goodyapp.example.com.goodyapp.threading.TestMainThread;


public class GetBasketSizeTest {

    private MainThread mMainThread;
    @Mock private Executor mExecutor;
    @Mock private GetBasketSizeUserCaseImpl.Callback mockedCallback;
    @Mock private Good mockedGood;
    @Mock private Context mockedContext;
    @Mock private GoodsRepository mockedGoodRepo;
    @Mock private GoodsDb mockedDb;
    private String currencySelected;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mMainThread = new TestMainThread();
        currencySelected = "EUR";
    }

    @Test
    public void getBasketSizeTest() throws Exception {
        GetBasketSizeUserCaseImpl userCase =
                new GetBasketSizeUserCaseImpl(mExecutor, mMainThread, mockedGoodRepo,
                                              currencySelected ,mockedCallback);

        userCase.run();

        Mockito.verify(mockedCallback).onBasketGoodsSizeRetrieved(0);
        Mockito.verifyNoMoreInteractions(mockedCallback);
    }
}
