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
		mutex = new Semaphore(5);
		vazio = new Semaphore(tamanho);
		cheio = new Semaphore(0);

	}
	
	
	public void produzirPeças(Carro carro) throws InterruptedException {
		vazio.acquire();
		mutex.acquire();
		buffer[entrada]= carro;
		entrada = (entrada +1) % tamanho;
		System.out.println("Produzido: " + carro.GetModelo());
		mutex.release();
		cheio.release();
	}
	
	
	public Carro consumirPeças() throws InterruptedException {
	    cheio.acquire(); 
	    //int item = buffer[saida];
	    mutex.acquire();
	    saida = (saida + 1) % tamanho; 
	    System.out.println("Consumido: " + buffer[saida].GetModelo());
	    mutex.release();
	    vazio.release(); 
		//Carro carro = buffer[saida];
		//buffer[saida] = null;
		return buffer[saida];
	}

	

}

