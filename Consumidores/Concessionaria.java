package Consumidores;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import Outros.Carro;
import Produtores.Fabrica;

public class Concessionaria extends Thread {
    private int id;
    private BlockingQueue<Carro> carStock;
    protected Fabrica fabrica;
    public Concessionaria(int id, int capacidade,Fabrica fabrica) {
        this.id = id;
        carStock = new ArrayBlockingQueue<>(capacidade);
        this.fabrica = fabrica;
    }

    public void adicionarCarro(Carro carro) throws InterruptedException {
        carStock.put(carro);
        System.out.println("Carro adicionado na concessionária: \n" + carro.GetModelo());
        //sleep(500);
    }

    public Carro venderCarro() throws InterruptedException {
        Carro carro = carStock.take();
        System.out.println("Carro vendido da concessionária: \n" + carro.GetModelo());
        //sleep(500);
        return carro;
    }

    @Override
    public void run(){
        if (carStockIsFull()) {
            System.out.println("Concessionaria está cheia");
        }else{
            try {
                adicionarCarro(fabrica.fornecerCarro());
            } catch (Exception e) {
                
            }
        }
    }
    // Não sei se vai usar
    public int getQuantidadeCarros() {
        return carStock.size();
    }
    public Boolean carStockIsFull(){
        return carStock.remainingCapacity() == 0;
    }
    public int GetId() {
        return this.id;
    }


}