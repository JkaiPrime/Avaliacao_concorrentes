package Produtores;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import Outros.Carro;
import Outros.Storage;

public class Fabrica extends Thread{
    private BlockingQueue<Carro> carsProduced;
    private workStation[] workstations;
    private Storage stockMaterials;

    public Fabrica(int numWorkstations, Storage materials) {
        this.carsProduced = new ArrayBlockingQueue<>(20);
        this.stockMaterials = materials;
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
                try {
                    if (this.stockMaterials.GetCountMaterials() > 0) {
                        this.stockMaterials.Consume();
                        Carro carro = workstations[j].produzir();
                        if (carro != null) {
                            System.out.println("Fabrica:"+carsProduced.remainingCapacity());
                            carsProduced.put(carro);
                            System.out.println("Produzido: " + carro.GetModelo());
                            Thread.sleep(250);
                        } 
                    } else {
                        System.out.println("O material acabou!");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } 
        }
    }
}