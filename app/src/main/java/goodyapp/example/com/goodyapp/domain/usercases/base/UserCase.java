package goodyapp.example.com.goodyapp.domain.usercases.base;

public interface UserCase {

    /**
     * This is the main method that starts an user case. It will make sure that the interactor operation is done on a
     * background thread.
     */
    void execute();
}
