package Produtores;

import java.util.Random;



public class Operarios implements Runnable{
    protected String nome;
    protected Ferramentas ferramentaDireita;
    protected Ferramentas ferramentaEsquerda;
    protected Sinaleiro sinaleiro;
    public Operarios(int nome, Ferramentas ferramentaDireita, Ferramentas ferramentaEsquerda, Sinaleiro sinaleiro){
        String nome1 = Integer.toString(nome);
        this.nome = nome1;
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
        //System.out.println("Operario "+ this.nome+" esta recebendo o pedido!");
    }
    public void work(){
        //System.out.println("Operario "+ this.nome + " comecou a produzir!");
    }
    @Override
    public void run() {
        try {
            while(true){
                preOrder();
                sinaleiro.Acquire();
                takeFerramentaDireita();
                takeFerramentaEsquerda();
                work();
                releaseFerramentaDireita();
                releaseFerramentaEsquerda();
                sinaleiro.Release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}
