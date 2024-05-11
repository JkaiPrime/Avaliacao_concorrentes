package Produtores;

public class Operarios implements Runnable{
    protected String nome;
    protected Ferramentas ferramentaDireita;
    protected Ferramentas ferramentaEsquerda;
    protected Sinaleiro sinaleiro;

    public Operarios(String nome, Ferramentas ferramentaDireita, Ferramentas ferramentaEsquerda, Sinaleiro sinaleiro){
        this.nome = nome;
        this.ferramentaDireita = ferramentaDireita;
        this.ferramentaEsquerda = ferramentaEsquerda;
        this.sinaleiro = sinaleiro;
        Thread operario =  new Thread(this);
        operario.start();
    }
    public void takeFerramentaDireita() throws InterruptedException{
        ferramentaDireita.Acquire();
    }
    public void releaseFerramentaDireita(){
        ferramentaDireita.Release();
    }
    public void takeFerramentaEsquerda() throws InterruptedException{
        ferramentaEsquerda.Acquire();
    }
    public void releaseFerramentaEsquerda(){
        ferramentaEsquerda.Release();
    }
    public void preOrder(){
        System.out.println("Operario "+ this.nome+" esta recebendo o pedido!");
    }
    public void work(){
        System.out.println("Operario "+ this.nome + " comecou a produzir!");
    }
    @Override
    public void run() {
        try {
            preOrder();
            sinaleiro.Acquire();
            takeFerramentaDireita();
            takeFerramentaEsquerda();
            work();
            releaseFerramentaDireita();
            releaseFerramentaEsquerda();
            sinaleiro.Release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}
