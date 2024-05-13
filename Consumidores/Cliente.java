package Consumidores;

import java.util.Random;

import Outros.Carro;

public class Cliente extends Thread {
    private int id;
    private Concessionaria concessionaria;

    public Cliente(int id, Concessionaria concessionaria) {
        this.id = id;
        this.concessionaria = concessionaria;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(new Random().nextInt(5000));
                if (concessionaria.getQuantidadeCarros() != 0) {
                    Carro carro = concessionaria.venderCarro();
                    System.out.println("Client: " + id + " comprou o carro: " + carro.GetModelo() +
                    "\nConcessionária: " + concessionaria.GetId());       
                    Thread.sleep(new Random().nextInt(500));
                } else {
                    System.out.println(id + " não encontrou carros na concessionária, vai verificar mais tarde.");
                    Thread.sleep(10000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}