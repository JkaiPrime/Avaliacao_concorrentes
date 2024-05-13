package Produtores;

import java.util.concurrent.Semaphore;

public class Operarios extends Thread {
    private int id;
    private Semaphore mySemaphore;
  
    public Operarios(int id, Semaphore semaphore) {
      this.id = id;
      this.mySemaphore = semaphore;
    }
  
    @Override
    public void run() {
      try {
        // Wait for your turn to use the tool (semaphore acquired in workstation)
        mySemaphore.acquire();
        // Simulate using the tool
        System.out.println("Operario " + id + " is using the tool.");
        Thread.sleep(500);
        // Release the semaphore for the next operario
        mySemaphore.release();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
  
