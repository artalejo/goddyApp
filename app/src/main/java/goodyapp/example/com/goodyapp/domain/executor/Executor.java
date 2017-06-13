package goodyapp.example.com.goodyapp.domain.executor;


import goodyapp.example.com.goodyapp.domain.usercases.base.AbstractUserCase;

/**
 * This executor is responsible for running user cases on background threads.
 * <p/>
 */
public interface Executor {

    /**
     * This method should call the user cases's run method and thus start the user case.
     * This should be called on a background thread as user cases might do lengthy operations.
     *
     * @param userCase The userCase to run.
     */
    void execute(final AbstractUserCase userCase);
}
