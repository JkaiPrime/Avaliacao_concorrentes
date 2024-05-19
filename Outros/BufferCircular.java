package Outros;

import java.util.concurrent.Semaphore;

public class BufferCircular {
	private Carro[] buffer;
	private int tamanho;
	private int entrada,saida;
	
	private Semaphore mutex;
	private Semaphore vazio;
	private Semaphore cheio;
	
	
	public BufferCircular(int tamanho) {
		this.tamanho = tamanho;
		buffer= new Carro[tamanho];
		entrada =0;
		saida = 0;
		mutex = new Semaphore(1);
		vazio = new Semaphore(tamanho);
		cheio = new Semaphore(0);

	}
	// public boolean isBufferCheio() {
	// 	return cheio.availablePermits()==0;
	// }
	
	public void removerTodosCarros() {
		mutex.acquireUninterruptibly();
		entrada = 0; // Reinicia o índice de entrada
		saida = 0;   // Reinicia o índice de saída
		mutex.release();
		vazio.release(tamanho); // Libera todas as permissões do semáforo vazio
		cheio.release();        // Libera todas as permissões do semáforo cheio
	}
	
	public void SetBuffer(Carro carro) throws InterruptedException {
		vazio.acquire();
		mutex.acquire();
		buffer[entrada]= carro;
		entrada = (entrada +1) % tamanho;
		System.out.println("Produzido: " + carro.GetModelo());
		mutex.release();
		cheio.release();
	}
	
	
	public Carro GetBuffer() throws InterruptedException {
	    cheio.acquire(); 
	    //int item = buffer[saida];
	    mutex.acquire();
        Carro c = buffer[saida];
	    saida = (saida + 1) % tamanho; 
	    System.out.println("Consumido: " + c.GetModelo());
	    mutex.release();
	    vazio.release(); 
		//Carro carro = buffer[saida];
		//buffer[saida] = null;
		return c;
	}

	

}

