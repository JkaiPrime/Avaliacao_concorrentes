
import java.awt.font.TextHitInfo;

import Consumidores.Cliente;
import Consumidores.Concessionaria;
import Outros.Carro;
import Produtores.Fabrica;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Fabrica fabrica = new Fabrica(5); // 5 workstations
        Concessionaria concessionaria1 = new Concessionaria(1, 20,fabrica);
        Concessionaria concessionaria2 = new Concessionaria(2, 10,fabrica);
        Cliente cliente1Conc1 = new Cliente(1, concessionaria1);
        Cliente cliente2Conc1 = new Cliente(2, concessionaria1);
        Cliente cliente1Conc2 = new Cliente(3, concessionaria2);
        Cliente cliente2Conc2 = new Cliente(4, concessionaria2);
        fabrica.start();
        concessionaria1.start();
        concessionaria2.start();
        cliente1Conc1.start();
        //cliente2Conc1.start();
        cliente1Conc2.start();
        //cliente2Conc2.start();
        while (true) {
            try {
                if(fabrica.isCarsProducedFull()){
                    if (concessionaria1.carStockIsFull()) {
                        System.out.println("Sexo!");
                        continue;
                    }
                    Carro carro = fabrica.fornecerCarro();
                    Carro carro2 = fabrica.fornecerCarro();
                    concessionaria1.adicionarCarro(carro);
                    concessionaria2.adicionarCarro(carro2);
                }else{
                    Carro carro = fabrica.fornecerCarro();
                    Carro carro2 = fabrica.fornecerCarro();
                    concessionaria1.adicionarCarro(carro);
                    concessionaria2.adicionarCarro(carro2);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
