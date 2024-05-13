package Produtores;

import java.util.concurrent.Semaphore;

public class Sinaleiro {
    private Semaphore semaphore;
    
    public Sinaleiro(int size) {
        semaphore = new Semaphore(size);
    }
    
    public void Acquire() throws InterruptedException {
         semaphore.acquire();
    }

    public void Release() {
        semaphore.release();
    }

}
