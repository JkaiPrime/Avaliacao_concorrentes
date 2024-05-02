package Produtores;
import java.util.concurrent.Semaphore;
public class Ferramentas {
    private Semaphore semaphore;
    public Ferramentas(){
        semaphore = new Semaphore(1);
    }
    public void Acquire() throws InterruptedException{
        semaphore.acquire();
    }
    public void Release(){
        semaphore.release();
    }
}
