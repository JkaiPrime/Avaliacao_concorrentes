package Produtores;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.awt.Color;
import Outros.Carro;
import Outros.Materials;

/**
 * workStation
 */
public class workStation {
    private Random random = new Random();
    protected int id;
    private Semaphore[] toolSemaphores; // Array of semaphores representing tools
  
    public workStation(int id, int numTools) {
      this.id = id;
      this.toolSemaphores = new Semaphore[numTools];
      for (int i = 0; i < numTools; i++) {
        toolSemaphores[i] = new Semaphore(1);
      }
    }
  
    public synchronized Carro produzir() throws InterruptedException {
      int id = random.nextInt(1000); // Gera um ID aleatório
      String tipo = random.nextBoolean() ? "sedan" : "suv"; // Escolhe aleatoriamente entre "sedan" e "suv"
      String r = Integer.toString(random.nextInt(0, 255));
      String g = Integer.toString(random.nextInt(0, 255));
      String b = Integer.toString(random.nextInt(0, 255));
      String color = "R:" + r + " G:" + g + " B:" + b;
  
      for (int i = 0; i < toolSemaphores.length; i++) {
        toolSemaphores[i].acquire(); // Acquire the semaphore for the current tool
        Thread.sleep(500);
        toolSemaphores[i].release(); // Release the semaphore for the next operario
      }
  
      Carro carro = new Carro(id, color, tipo);
      return carro;
    }
  
    public int getId() {
      return this.id;
    }
  }
  