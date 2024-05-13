package Outros;

import java.util.concurrent.Semaphore;

public class BufferCircular {
	private int[] buffer;
	private int tamanho;
	private int entrada,saida;
	
	private Semaphore mutex;
	private Semaphore vazio;
	private Semaphore cheio;
	
	
	public BufferCircular(int tamanho) {
		this.tamanho = tamanho;
		buffer= new int[tamanho];
		entrada =0;
		saida = 0;
		mutex = new Semaphore(5);
		vazio = new Semaphore(tamanho);
		cheio = new Semaphore(0);

	}
	
	
	public void produzirPeças(int item) throws InterruptedException {
		vazio.acquire();
		mutex.acquire();
		buffer[entrada] = item;
		entrada = (entrada +1) % tamanho;
		System.out.println("Produzido: " + item);
		mutex.release();
		cheio.release();
	}
	
	
	public void consumirPeças() throws InterruptedException {
	    cheio.acquire(); 
	    int item = buffer[saida];
	    mutex.acquire();
	    saida = (saida + 1) % tamanho; 
	    System.out.println("Consumido: " + item);
	    mutex.release();
	    vazio.release(); 
	}

	

}

class Produtor extends Thread {
	private BufferCircular buffer;
	
	public Produtor(BufferCircular buffer) {
		this.buffer = buffer;
	}
	
	@Override
	public void run() {
		try {
			for(int i = 0; i < 10; i++) {
				buffer.produzirPeças(i);
				sleep(1000);
			}
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Consumidor extends Thread {
	private BufferCircular buffer;
	
	public Consumidor(BufferCircular buffer) {
		this.buffer = buffer;
	}
	
	@Override
	public void run() {
		try {
			for(int i = 0; i < 10; i++) {
				buffer.consumirPeças();
				sleep(1500);
			}
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

