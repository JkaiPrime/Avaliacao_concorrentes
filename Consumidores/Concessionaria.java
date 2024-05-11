package Consumidores;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import Outros.Carro;

public class Concessionaria extends Thread {
    private int id;
    private BlockingQueue<Carro> carStock;

    public Concessionaria(int id, int capacidade) {
        this.id = id;
        carStock = new ArrayBlockingQueue<>(capacidade);
    }

    public void adicionarCarro(Carro carro) throws InterruptedException {
        carStock.put(carro);
        System.out.println("Carro adicionado na concessionária: \n" + carro.GetModelo());
        sleep(500);
    }

    public Carro venderCarro() throws InterruptedException {
        Carro carro = carStock.take();
        System.out.println("Carro vendido da concessionária: \n" + carro.GetModelo());
        sleep(500);
        return carro;
    }

    // Não sei se vai usar
    public int getQuantidadeCarros() {
        return carStock.size();
    }

    public int GetId() {
        return this.id;
    }


}