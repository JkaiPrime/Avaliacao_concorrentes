package Consumidores;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.Random;

import Outros.Carro;

public class Cliente extends Thread {

    private int id;
    private Concessionaria concessionaria;
    private List<Carro> garagem;

    public Cliente(int id, Concessionaria concessionaria) {
        this.id = id;
        this.concessionaria = concessionaria;
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
                Thread.sleep(new Random().nextInt(5000));
                if (concessionaria.getQuantidadeCarros() != 0) {
                    try {
                        Carro carro = concessionaria.venderCarro();
                        garagem.add(carro);
                        BufferedWriter workstationsLog = new BufferedWriter(new FileWriter("ClientLog.txt", true));
                        workstationsLog.write("Client:"+ this.id+"Comprou o carro"+ carro.GetModelo() + "\n" + garagem.size()+ "\nConcessionária: " + concessionaria.GetId());
                        workstationsLog.newLine();
                        workstationsLog.flush();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }      
                    Thread.sleep(new Random().nextInt(500));
                } else {
                    System.out.println(id + " não encontrou carros na concessionária, vai verificar mais tarde.");
                    Thread.sleep(15000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}