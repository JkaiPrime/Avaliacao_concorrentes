package Consumidores;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import Outros.Carro;
import Produtores.Fabrica;

public class Concessionaria extends Thread {
    private int id;
    private BlockingQueue<Carro> carStock;
    protected Fabrica fabrica;

    public Concessionaria(int id, int capacidade, Fabrica fabrica) throws UnknownHostException, IOException {
        this.id = id;
        carStock = new ArrayBlockingQueue<>(capacidade);
        this.fabrica = fabrica;

    }

    public void adicionarCarro(Carro carro) throws InterruptedException {
        Carro carro1 = fabrica.fornecerCarro();
        carStock.put(carro);
        carStock.put(carro1);
        try {
            BufferedWriter ConcessionariaLogCompras = new BufferedWriter(new FileWriter("ConcessionariaComprasLog.txt", true));
            ConcessionariaLogCompras.write("Concessionaria:"+ id+ "\n"+carro.GetModelo());
            ConcessionariaLogCompras.write("Concessionaria:"+ id+ "\n"+carro1.GetModelo());
            ConcessionariaLogCompras.newLine();
            ConcessionariaLogCompras.flush();
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println("Carro adicionado na concessionária: \n" + carro.GetModelo() + "\n Posição na esteira:" + getQuantidadeCarros());
        System.out.println("Carro adicionado na concessionária: \n" + carro1.GetModelo() + "\n Posição na esteira:" + getQuantidadeCarros());
    }

    public Carro venderCarro() throws InterruptedException {
        Carro carro = carStock.take();
        try {
            BufferedWriter ConcessionariaLogVendas = new BufferedWriter(new FileWriter("ConcessionariaVendasLog.txt", true));
            ConcessionariaLogVendas.write("Concessionaria:"+ this.id+ "Vendeu:\n"+carro.GetModelo()+"para Cliente");
            ConcessionariaLogVendas.newLine();
            ConcessionariaLogVendas.flush();
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println("Carro vendido da concessionária: \n" + carro.GetModelo());
        return carro;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                adicionarCarro(fabrica.fornecerCarro());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public int getQuantidadeCarros() {
        return carStock.size();
    }

    public int GetId() {
        return this.id;
    }

}