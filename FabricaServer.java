import java.io.IOException;

import Outros.Storage;
import Produtores.Fabrica;

public class FabricaServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Storage storage = new Storage(500);
        Fabrica fabrica = new Fabrica(5, storage);

        fabrica.start();
    }
}
