package Produtores;

public class Operarios implements Runnable{
    protected String nome;
    protected Ferramentas ferramenta;
    protected Sinaleiro sinaleiro;

    public Operarios(String nome, Ferramentas ferramenta, Sinaleiro sinaleiro){
        this.nome = nome;
        this.ferramenta = ferramenta;
        this.sinaleiro = sinaleiro;
        Thread operario =  new Thread(this);
        operario.start();
    }
    public void takeFerramenta() throws InterruptedException{
        ferramenta.Acquire();
    }
    public void releaseFerramenta(){
        ferramenta.Release();
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
            takeFerramenta();
            work();
            releaseFerramenta();
            sinaleiro.Release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
}
