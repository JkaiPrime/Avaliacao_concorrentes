import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Consumidores.Cliente;
import Consumidores.Concessionaria;
import Outros.Carro;
import Outros.Storage;
import Produtores.Fabrica;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        Storage storage = new Storage(500);
        Fabrica fabrica = new Fabrica(5, storage); // 5 workstations
        
        Concessionaria concessionaria1 = new Concessionaria(1, 20,fabrica);
        Concessionaria concessionaria2 = new Concessionaria(2, 10,fabrica);
        Concessionaria concessionaria3 = new Concessionaria(3, 10, fabrica);

        Cliente cliente1Conc1 = new Cliente(1, concessionaria1);
        Cliente cliente1Conc2 = new Cliente(3, concessionaria2);
        Cliente cliente1Conc3 = new Cliente(2, concessionaria3);

        fabrica.start();
        concessionaria1.start();
        concessionaria2.start();
        concessionaria3.start();
        cliente1Conc1.start();
        cliente1Conc2.start();
        cliente1Conc3.start();

    }
}
