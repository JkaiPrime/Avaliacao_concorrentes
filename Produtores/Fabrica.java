package Produtores;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import Outros.Carro;

public class Fabrica {
    private BlockingQueue<Carro> carsProduced;
    private workStation[] workstations;

    public Fabrica(int numWorkstations) {
        this.carsProduced = new ArrayBlockingQueue<>(20);
        workstations = new workStation[numWorkstations];
        for (int i = 0; i < numWorkstations; i++) {
            workstations[i] = new workStation();
        }
    }

    public void produzir() {
        while (true) {
            Carro carro = workstations[0].produzir();
            try {
                carsProduced.put(carro);
                System.out.println("Produzido: " + carro.GetModelo());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public Carro fornecerCarro() throws InterruptedException {
        return this.carsProduced.take();
    }

    public boolean isCarsProducedEmpty() throws InterruptedException {
        return this.carsProduced.isEmpty();
    }

}