package Outros;

import java.util.concurrent.ArrayBlockingQueue;

import Produtores.Sinaleiro;

public class Storage {
    private Sinaleiro sinaleiro;
    private ArrayBlockingQueue<Materials> buffer;

    public Storage(int size) throws InterruptedException{
        this.buffer = new ArrayBlockingQueue<>(size);
        this.sinaleiro = new Sinaleiro(5);

        for (int i = 0; i < size; i++){
            Save(new Materials());
        }
    }
    
    public void Save (Materials material) throws InterruptedException {
        buffer.put(material);
    }

    public Materials Consume() throws InterruptedException  {
        sinaleiro.Acquire();
        Materials material = buffer.take();
        Thread.sleep(500);
        sinaleiro.Release();
        return material;
    }
}
