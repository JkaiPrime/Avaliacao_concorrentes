package Produtores;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import Outros.BufferCircular;
import Outros.Carro;
import Outros.Storage;

public class Fabrica extends Thread {
    private BufferCircular carsProduced;
    private workStation[] workstations;
    private Storage stockMaterials;
    protected Semaphore emptySlots;
    private ServerSocket serverSocket;

    public Fabrica(int numWorkstations, Storage materials) throws IOException {
        this.stockMaterials = materials;
        this.carsProduced = new BufferCircular(40);
        workstations = new workStation[numWorkstations];

        for (int i = 0; i < numWorkstations; i++) {
            workstations[i] = new workStation(i, 5);
        }
        serverSocket = new ServerSocket(4000);
    }

    private void fornecerCarros(Socket clientSocket) throws InterruptedException, IOException {
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

        try {
            while (true) {
                for (int j = 0; j < workstations.length; j++) {
                    Carro carro = workstations[j].produzir(this.stockMaterials);
                    this.carsProduced.SetBuffer(carro);
                    Carro novoCarro = this.carsProduced.GetBuffer();
                    if (novoCarro != null) {
                        out.writeObject(novoCarro);
                        out.flush();
                        System.out.println("Forneceu: " + novoCarro.GetModelo());
                        try (BufferedWriter fabricaLog = new BufferedWriter(new FileWriter("LogFabrica.txt", true))) {
                            fabricaLog.write("Modelo: " + carro.GetModelo() + "\nWorkstation: " + j);
                            fabricaLog.newLine();
                        }
                    } else {
                        System.out.println("Carro não produzido");
                    }
                }
                Thread.sleep(1000); // Simula intervalo de tempo entre produções
            }
        } catch (IOException e) {
            System.out.println("Conexão com a concessionária perdida: " + e.getMessage());
        } finally {
            out.close();
            clientSocket.close();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        fornecerCarros(clientSocket);
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
