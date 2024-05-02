package Produtores;

public class Fabrica {
    public void produzir(){
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
                //Ferramentas ferramentaEsquerda = ferramentas[(i + 1) % 5];
                operarios[i] = new Operarios("Josias", ferramentaDireita,sinaleiro[i]) {
                    @Override
                    public void run() {
                        super.run(); 
                    }
                };
            }
    }
}
