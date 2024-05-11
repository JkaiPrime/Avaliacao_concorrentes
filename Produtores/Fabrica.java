package Produtores;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import Outros.Carro;

public class Fabrica {
    private BlockingQueue<Carro> bufferPecas;
    private workStation[] workstations;

    public Fabrica(int numWorkstations) {
        bufferPecas = new ArrayBlockingQueue<>(20);
        workstations = new workStation[numWorkstations];
        for (int i = 0; i < numWorkstations; i++) {
            workstations[i] = new workStation();
        }
    }

    public void produzir() {
        while (true) {
            Carro carro = workstations[0].produzir();
            try {
                bufferPecas.put(carro);
                System.out.println("Carro produzido: " + carro.getId() + " " + carro.getColor() + " " + carro.getTipo());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Fabrica fabrica = new Fabrica(5); // 5 workstations
        fabrica.produzir();
    }
}
