import java.io.IOException;

import Consumidores.Cliente;
import Consumidores.Concessionaria;

public class ClienteServer {
    public static void main(String[] args) throws InterruptedException, IOException {
        Concessionaria concessionaria1 = new Concessionaria(1, 20);
        Concessionaria concessionaria2 = new Concessionaria(2, 10);
        Concessionaria concessionaria3 = new Concessionaria(3, 10);

        Cliente cliente1Conc1 = new Cliente(1, concessionaria1);
        Cliente cliente1Conc2 = new Cliente(3, concessionaria2);
        Cliente cliente1Conc3 = new Cliente(2, concessionaria3);

        concessionaria1.start();
        concessionaria2.start();
        concessionaria3.start();
        cliente1Conc1.start();
        cliente1Conc2.start();
        cliente1Conc3.start();
    }
}
