package Produtores;

import java.util.Random;
import java.awt.Color;
import Outros.Carro;

/**
 * workStation
 */
public class workStation {
    public enum cor{
        red, green, blue;
    }
    
    private Random random = new Random();
    
    private Color randomColor() {
        int choice = random.nextInt(3);
        switch(choice) {
            case 0:
                return Color.RED;
            case 1:
                return Color.GREEN;
            case 2:
                return Color.BLUE;
            default:
                return Color.BLACK; // Default to black if something goes wrong
        }
    }
    public Carro produzir(){
        Ferramentas[] ferramentas = new Ferramentas[5];
        Sinaleiro[] sinaleiro = new Sinaleiro[4];
            
            for (int i = 0; i < 5; i++) {
                ferramentas[i] = new Ferramentas();
            }

            for (int i = 0; i < 4; i++) {
                sinaleiro[i] = new Sinaleiro();
            }
            
            Operarios[] operarios = new Operarios[5];
            for (int i = 0; i < 5; i++) {
                Ferramentas ferramentaDireita = ferramentas[i];
                Ferramentas ferramentaEsquerda = ferramentas[(i + 1) % 5];
                operarios[i] = new Operarios("Josias", ferramentaDireita,ferramentaEsquerda,sinaleiro[i]) {
                    @Override
                    public void run() {
                        super.run(); 
                    }
                    
                };
            }
            int id = random.nextInt(1000); // Gera um ID aleatório
            String tipo = random.nextBoolean() ? "sedan" : "suv"; // Escolhe aleatoriamente entre "sedan" e "suv"
            Color color = randomColor(); // Seleciona aleatoriamente uma das cores disponíveis
        
            return new Carro(id, color.toString(), tipo);
    }
    
}