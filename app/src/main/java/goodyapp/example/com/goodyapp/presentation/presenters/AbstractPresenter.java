package goodyapp.example.com.goodyapp.presentation.presenters;

import goodyapp.example.com.goodyapp.domain.executor.Executor;
import goodyapp.example.com.goodyapp.domain.executor.MainThread;

public abstract class AbstractPresenter {

    protected Executor mExecutor;
    protected MainThread mMainThread;

    public AbstractPresenter(Executor executor, MainThread mainThread) {
        mExecutor = executor;
        mMainThread = mainThread;
    }
}
