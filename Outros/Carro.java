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

    public String GetModelo() {
        String carro = "Carro: " + this.id + " Cor: " + this.color + " Tipo: " + this.tipo;
        return carro;
    }
}
