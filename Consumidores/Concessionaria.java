package Consumidores;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import Outros.Carro;

public class Concessionaria extends Thread {
    private int id;
    private BlockingQueue<Carro> carStock;
    private Socket socket;

    public Concessionaria(int id, int capacidade) throws UnknownHostException, IOException {
        this.id = id;
        carStock = new ArrayBlockingQueue<>(capacidade);
        socket = new Socket("localhost", 4000);
    }

    public void adicionarCarro() throws InterruptedException, IOException, ClassNotFoundException {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); // problema aqui
            Carro carro = (Carro) in.readObject();
            if (carro == null) {
                System.out.println("Carro Null"); return;
            } else {
                carStock.put(carro);
                logConcessionariaCompra(carro);
                System.out.println("Adicionou carro a concessionaria: " + this.id);
            }
        } catch (SocketException e) {
            System.err.println("Conexão com o servidor foi resetada: " + e.getMessage());
            reconectar();
        } catch (EOFException e) {
            System.err.println("Fim do fluxo de entrada alcançado prematuramente: " + e.getMessage());
            reconectar();
        }
    }

    private void logConcessionariaCompra(Carro carro) {
        try {
            BufferedWriter ConcessionariaLogCompras = new BufferedWriter(new FileWriter("ConcessionariaComprasLog.txt", true));
            ConcessionariaLogCompras.write("Concessionaria:"+ id+ "\n"+ carro.GetModelo());
            ConcessionariaLogCompras.newLine();
            ConcessionariaLogCompras.flush();
        } catch (Exception e) {
        }
    }

    private void logConcessionariaVenda(Carro carro) {
        try {
            BufferedWriter ConcessionariaLogVendas = new BufferedWriter(new FileWriter("ConcessionariaVendasLog.txt", true));
            ConcessionariaLogVendas.write("Concessionaria:"+ this.id+ "Vendeu:\n"+carro.GetModelo()+"para Cliente");
            ConcessionariaLogVendas.newLine();
            ConcessionariaLogVendas.flush();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public Carro venderCarro() throws InterruptedException {
        if (!carStock.isEmpty()) {
            Carro carro = carStock.take();
            logConcessionariaVenda(carro);
            return carro;
        } else {
            System.out.println("Concessionária esperando um carro \n");
            sleep(1000);
        }
        return null;
    }

    private void reconectar() throws InterruptedException {
        try {
            sleep(1000);
            this.socket.close();
            this.socket = new Socket("localhost", 4000);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Reconectado ao servidor.");
        } catch (IOException e) {
            System.err.println("Falha ao reconectar: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while (true) {
            try {
                adicionarCarro();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
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