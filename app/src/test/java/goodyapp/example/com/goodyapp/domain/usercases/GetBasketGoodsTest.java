package goodyapp.example.com.goodyapp.domain.usercases;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import goodyapp.example.com.goodyapp.domain.executor.Executor;
import goodyapp.example.com.goodyapp.domain.executor.MainThread;
import goodyapp.example.com.goodyapp.domain.repository.GoodsRepository;
import goodyapp.example.com.goodyapp.domain.usercases.impl.GetBasketGoodsUserCaseImpl;
import goodyapp.example.com.goodyapp.presentation.models.Good;
import goodyapp.example.com.goodyapp.storage.database.GoodsDb;
import goodyapp.example.com.goodyapp.threading.TestMainThread;


public class GetBasketGoodsTest {

    private MainThread mMainThread;
    @Mock private Executor mExecutor;
    @Mock private GetBasketGoodsUserCase.Callback mockedCallback;
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
    public void getBasketGoodsEmptyListTest() throws Exception {
        GetBasketGoodsUserCaseImpl userCase =
                new GetBasketGoodsUserCaseImpl(mExecutor, mMainThread, mockedGoodRepo,
                        currencySelected ,mockedCallback);

        userCase.run();

        Mockito.verify(mockedCallback).onBasketGoodsRetrieved(Matchers.anyListOf(Good.class));
        Mockito.verifyNoMoreInteractions(mockedCallback);
    }
}
