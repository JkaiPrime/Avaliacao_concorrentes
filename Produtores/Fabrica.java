package Produtores;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import Outros.BufferCircular;
import Outros.Carro;

public class Fabrica extends Thread{
    //private BlockingQueue<Carro> carsProduced;
    BufferCircular carsProduced;
    private workStation[] workstations;

    public Fabrica(int numWorkstations) {
        this.carsProduced = new BufferCircular(20);
        workstations = new workStation[numWorkstations];
        for (int i = 0; i < numWorkstations; i++) {
            workstations[i] = new workStation(i);
        }
    }


/* 
    public Boolean isCarsProducedFull(){
        return carsProduced.
    }
    */
    public Carro fornecerCarro() throws InterruptedException {
        return this.carsProduced.consumirPeças();
    }
/* 
    public boolean isCarsProducedEmpty() throws InterruptedException {
        return this.carsProduced.isEmpty();
    }
*/
    @Override
    public void run() {
            for (int j = 0; j < workstations.length; j++) {
                Carro carro = workstations[j].produzir();
                try {
                    carsProduced.produzirPeças(carro);
                    System.out.println("Produzido: " + carro.GetModelo());
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
