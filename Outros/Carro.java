package Outros;

import java.awt.Color;

public class Carro {
    int id;
    Color color;
    String tipo;

    public Carro(int id, Color color2, String tipo){
        this.id = id;
        this.color = color2;
        this.tipo = tipo;
    }
    public int getId() {
        return this.id;
    }
    public Color getColor() {
        return this.color;
    }
    public String getTipo() {
        return this.tipo;
    }
}
