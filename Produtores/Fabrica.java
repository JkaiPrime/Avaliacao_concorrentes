package Produtores;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import Outros.Carro;

public class Fabrica extends Thread{
    private BlockingQueue<Carro> carsProduced;
    private workStation[] workstations;

    public Fabrica(int numWorkstations) {
        this.carsProduced = new ArrayBlockingQueue<>(20);
        workstations = new workStation[numWorkstations];
        for (int i = 0; i < numWorkstations; i++) {
            workstations[i] = new workStation(i);
        }
    }

    public Boolean isCarsProducedFull(){
        return carsProduced.remainingCapacity() == 0;
    }

    public Carro fornecerCarro() throws InterruptedException {
        return this.carsProduced.take();
    }

    public boolean isCarsProducedEmpty() throws InterruptedException {
        return this.carsProduced.isEmpty();
    }

    @Override
    public void run() {
        if (carsProduced.remainingCapacity()==0) {
            System.out.println("Fabrica Cheia");
        }else{
            for (int j = 0; j < workstations.length; j++) {
                Carro carro = workstations[j].produzir();
                try {
                    System.out.println("Fabrica:"+carsProduced.remainingCapacity());
                    carsProduced.put(carro);
                    System.out.println("Produzido: " + carro.GetModelo());
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } 
        }
    }
}