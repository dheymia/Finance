package senac.finance.models;

import senac.finance.R;

public class Finance {

    private int id;
    private String dia;
    private String tipo;
    private Double valor;
    int foto;

    public Finance() {
    }

    public Finance(int id, String dia, String tipo, Double valor) {
        this.id = id;
        this.dia = dia;
        this.tipo = tipo;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public String getDia() {
        return dia;
    }

    public String getTipo() {
        return tipo;
    }

    public Double getValor() {
        return valor;
    }

    public int getFoto() {
        switch (tipo){
            case "Receita":
                foto = R.drawable.receita;
                break;
            case "Despesa":
                foto = R.drawable.despesa;
                break;
        }
        return foto;
    }

}

