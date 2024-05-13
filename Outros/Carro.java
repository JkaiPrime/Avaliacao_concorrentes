package Outros;
public class Carro {
    private int id;
    private String color;
    private String tipo;

    public Carro(int id, String color2, String tipo){
        this.id = id;
        this.color = color2;
        this.tipo = tipo;
    }

    public String GetModelo() {
        String carro = "Carro: " + this.id + " Cor: " + this.color + " Tipo: " + this.tipo;
        return carro;
    }
}
