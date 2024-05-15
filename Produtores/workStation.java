package Produtores;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;

import Outros.Carro;
import Outros.Materials;
import Outros.Storage;

/**
 * workStation
 */
public class workStation {
    private Random random = new Random();
    protected int id;
    private Sinaleiro[] toolSemaphores; // Array of semaphores representing tools
    private Operarios[] operarios;
    Ferramentas[] ferramentas = new Ferramentas[5];

    public workStation(int id, int numTools) {
      this.id = id;
      this.toolSemaphores = new Sinaleiro[numTools];
      this.operarios = new Operarios[numTools];
      for (int i = 0; i < numTools; i++) {
        toolSemaphores[i] = new Sinaleiro(1);
        this.ferramentas[i] = new Ferramentas();
      }
    }
  
    public synchronized Carro produzir(Storage storage) throws InterruptedException {
      int id = random.nextInt(1000); // Gera um ID aleatório
      storage.Consume();
      System.out.println(storage.GetCountMaterials());
      String tipo = random.nextBoolean() ? "sedan" : "suv"; // Escolhe aleatoriamente entre "sedan" e "suv"
      String r = Integer.toString(random.nextInt(0, 255));
      String g = Integer.toString(random.nextInt(0, 255));
      String b = Integer.toString(random.nextInt(0, 255));
      String color = "R:" + r + " G:" + g + " B:" + b;
      long idOperario = 0;
      for (int i = 0; i < toolSemaphores.length; i++) {
        Ferramentas ferramentaEsquerdo = ferramentas[i];
        Ferramentas ferramentaDireito = ferramentas[(i + 1) % 5];
        operarios[i] = new Operarios(i, ferramentaDireito, ferramentaEsquerdo,toolSemaphores[i]);;
        toolSemaphores[i].Acquire();
        idOperario = operarios[i].getId();
      }
      
    Carro carro = new Carro(id, color, tipo);
      for (int i = 0; i < toolSemaphores.length; i++) {
        toolSemaphores[i].Release();
      }
      try {
        BufferedWriter workstationsLog = new BufferedWriter(new FileWriter("WorkstationLog.txt", true));
        workstationsLog.write("Workstation:"+ this.id+" & Operario:"+ idOperario +"Produziu:"+ carro.GetModelo());
        workstationsLog.newLine();
        workstationsLog.flush();
      } catch (Exception e) {
        // TODO: handle exception
      }
      return carro;
    }
  
    public int getIds() {
      return this.id;
    }
  }
  