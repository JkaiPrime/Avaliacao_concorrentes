package Produtores;

public class Fabrica {
    Ferramentas[] ferramentas = new Ferramentas[5];
    Sinaleiro[] sinaleiro = new Sinaleiro[4];
        
        for (int i = 0; i < 5; i++) {
            ferramentas[i] = new Ferramentas();
        }

        for (int j = 0; i < 4; i++) {
            sinaleiro[j] = new Sinaleiro();
        }
        
        Operarios[] operarios = new Operarios[5];
        for (int k = 0; i < 5; i++) {
            Ferramentas ferramentaDireita = ferramentas[k];
            Ferramentas ferramentaEsquerda = ferramentas[(k + 1) % 5];
            operarios[k] = new Operarios("Josias", ferramentaDireita,sinaleiro[k]) {
                @Override
                public void run() {
                    super.run(); 
                }
            };
        }

}
