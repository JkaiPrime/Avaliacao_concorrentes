package Produtores;

import java.util.Random;
import java.awt.Color;
import Outros.Carro;
import Outros.Materials;

/**
 * workStation
 */
public class workStation {
    private Random random = new Random();
    protected int id;
    public workStation(int id){
        this.id = id;
    }
    public synchronized Carro produzir(){
        Ferramentas[] ferramentas = new Ferramentas[5];
        Sinaleiro[] sinaleiro = new Sinaleiro[5];
            
        for (int i = 0; i < 5; i++) {
            ferramentas[i] = new Ferramentas();
        }

        for (int i = 0; i < 5; i++) {
            sinaleiro[i] = new Sinaleiro(1);
        }

        Operarios[] operarios = new Operarios[5];
        for (int i = 0; i < 5; i++) {
            Ferramentas ferramentaDireita = ferramentas[i];
            Ferramentas ferramentaEsquerda = ferramentas[(i + 1) % 5];
            operarios[i] = new Operarios(i, ferramentaDireita,ferramentaEsquerda,sinaleiro[i]);
        }
        int id = random.nextInt(1000); // Gera um ID aleatório
        String tipo = random.nextBoolean() ? "sedan" : "suv"; // Escolhe aleatoriamente entre "sedan" e "suv"
        //String color = randomColor().toString(); // Seleciona aleatoriamente uma das cores disponíveis
        String r = Integer.toString(random.nextInt(0,255));
        String g = Integer.toString(random.nextInt(0,255));
        String b = Integer.toString(random.nextInt(0,255));
            
        String color = "R:"+r+" G:"+g+" B:"+b;
        Carro carro = new Carro(id, color, tipo);
        return carro;
    }
    public int getId(){
        return this.id;
    }
    
}