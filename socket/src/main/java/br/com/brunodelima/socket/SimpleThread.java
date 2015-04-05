package br.com.brunodelima.socket;

/**
 * Class to simplify thread usage
 * @author Bruno de Lima
 */
public abstract class SimpleThread extends Thread{

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted())
            onLoop();
    }
    
    protected abstract void onLoop();
}
