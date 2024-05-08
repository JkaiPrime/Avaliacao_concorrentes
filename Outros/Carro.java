package Outros;

public class Carro {
    int id;
    String color;
    String tipo;

    public Carro(int id, String color, String tipo){
        this.id = id;
        this.color = color;
        this.tipo = tipo;
    }
    public int getId() {
        return this.id;
    }
    public String getColor() {
        return this.color;
    }
    public String getTipo() {
        return this.tipo;
    }
}
