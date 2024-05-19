package Produtores;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
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
    private Socket socket;
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

    public void fornecerCarro(Socket clienSocket) throws InterruptedException, IOException {
        ObjectOutputStream out = new ObjectOutputStream(clienSocket.getOutputStream()); // problema aqui
        try {
            for (int j = 0; j < workstations.length; j++) {
                Carro carro = workstations[j].produzir(this.stockMaterials);
                this.carsProduced.SetBuffer(carro);
                Carro novoCarro = this.carsProduced.GetBuffer();
                if (novoCarro != null) {
                    out.writeObject(novoCarro);
                    System.out.println("Forneceu: " + novoCarro.GetModelo());

                    try (BufferedWriter fabricaLog = new BufferedWriter(new FileWriter("LogFabrica.txt", true))) {
                        fabricaLog.write("Modelo: " + carro.GetModelo() + "\nWorkstation: " + j);
                        fabricaLog.newLine();
                    }
                } else {
                    System.out.println("Carro não produzido");
                    return;
                }
            }
        } finally {
            out.close();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                socket = serverSocket.accept();
                if (this.stockMaterials.GetCountMaterials() > 0) {
                    try {
                        System.out.println("tentando fornecer");
                        fornecerCarro(socket);
                        socket.close();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                } else {
                    System.out.println("O material acabou!");
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
