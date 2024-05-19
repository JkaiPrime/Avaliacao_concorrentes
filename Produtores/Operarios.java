package Produtores;

public class Operarios implements Runnable{
  protected int nome;
  protected Ferramentas ferramentaDireita;
  protected Ferramentas ferramentaEsquerda;
  protected Sinaleiro sinaleiro;

    public Operarios(int nome, Ferramentas ferramentaDireita, Ferramentas ferramentaEsquerda, Sinaleiro sinaleiro){
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
  public int getId(){
    return this.nome;
  }
  public void preOrder(){
      System.out.println("Operario "+ this.nome+" esta recebendo o pedido!");
  }
  public void run() {
        try {
            preOrder();
            sinaleiro.Acquire();
            takeFerramentaEsquerda();
            takeFerramentaDireita();
            work();
            releaseFerramentaEsquerda();
            releaseFerramentaDireita();
            sinaleiro.Release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
  private void work() throws InterruptedException {
    System.out.println("Trabalhando");
    Thread.sleep(1000);
  }
  
}
  
