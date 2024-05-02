package Produtores;

import java.util.concurrent.Semaphore;

public class Sinaleiro {
    private Semaphore semaphore;
    
    public Sinaleiro() {
        semaphore = new Semaphore(1);
    }
    

    
    public void Acquire() throws InterruptedException {
         semaphore.acquire();
    }

    public void Release() {
        semaphore.release();
    }

}
