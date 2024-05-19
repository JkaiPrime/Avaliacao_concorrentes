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
    private ObjectInputStream in;

    public Concessionaria(int id, int capacidade) throws UnknownHostException, IOException {
        this.id = id;
        carStock = new ArrayBlockingQueue<>(capacidade);
        conectar();
    }

    private void conectar() throws IOException {
        this.socket = new Socket("localhost", 4000);
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    public void adicionarCarro() throws InterruptedException, IOException, ClassNotFoundException {
        while (true) {
            try {
                Carro carro = (Carro) in.readObject();
                if (carro != null) {
                    carStock.put(carro);
                    logConcessionariaCompra(carro);
                    System.out.println("Adicionou carro a concessionaria: " + this.id);
                }
            } catch (SocketException | EOFException e) {
                System.err.println("Erro de conexão: " + e.getMessage());
                reconectar();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void logConcessionariaCompra(Carro carro) {
        try (BufferedWriter ConcessionariaLogCompras = new BufferedWriter(new FileWriter("ConcessionariaComprasLog.txt", true))) {
            ConcessionariaLogCompras.write("Concessionaria:" + id + "\n" + carro.GetModelo());
            ConcessionariaLogCompras.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void logConcessionariaVenda(Carro carro) {
        try (BufferedWriter ConcessionariaLogVendas = new BufferedWriter(new FileWriter("ConcessionariaVendasLog.txt", true))) {
            ConcessionariaLogVendas.write("Concessionaria:" + this.id + " Vendeu:\n" + carro.GetModelo() + " para Cliente");
            ConcessionariaLogVendas.newLine();
        } catch (Exception e) {
            e.printStackTrace();
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
        while (true) {
            try {
                Thread.sleep(1000);
                if (socket != null) {
                    socket.close();
                }
                conectar();
                System.out.println("Reconectado ao servidor.");
                break; // Saia do loop se reconectado com sucesso
            } catch (IOException e) {
                System.err.println("Falha ao reconectar: " + e.getMessage());
                // Continuar no loop até conseguir reconectar
            } catch (InterruptedException e) {
                System.err.println("Reconexão interrompida: " + e.getMessage());
                Thread.currentThread().interrupt();
                break; // Se a thread for interrompida, saia do loop
            }
        }
    }

    @Override
    public void run() {
        try {
            adicionarCarro();
        } catch (InterruptedException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public int getQuantidadeCarros() {
        return carStock.size();
    }

    public int GetId() {
        return this.id;
    }

}
