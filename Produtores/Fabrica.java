package Produtores;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

import Outros.BufferCircular;
import Outros.Carro;
import Outros.Storage;

public class Fabrica extends Thread {
    // private BlockingQueue<Carro> carsProduced;
    BufferCircular carsProduced;
    private workStation[] workstations;
    private Storage stockMaterials;
    protected Semaphore emptySlots;

    public Fabrica(int numWorkstations, Storage materials) {
        this.stockMaterials = materials;
        this.carsProduced = new BufferCircular(40);
        workstations = new workStation[numWorkstations];
        for (int i = 0; i < numWorkstations; i++) {
            workstations[i] = new workStation(i, 5);
        }

    }

    public Carro fornecerCarro() throws InterruptedException {
        return this.carsProduced.consumirPeças();
    }

    @Override
    public void run() {
        while (true) {
            for (int j = 0; j < workstations.length; j++) {
                try {
                    if (this.stockMaterials.GetCountMaterials() > 0) {
                       
                        Carro carro = workstations[j].produzir(this.stockMaterials);
                        try {
                            BufferedWriter fabricaLog = new BufferedWriter(new FileWriter("LogFabrica.txt", true));
                            fabricaLog.write(carro.GetModelo()+"\n"+"Workstation:"+workstations.length+"Operario"+ "");
                            fabricaLog.newLine();
                            fabricaLog.flush();
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        if (carro != null) {
                            carsProduced.produzirPeças(carro);
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
