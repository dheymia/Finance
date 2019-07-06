package senac.finance.models;

import android.content.Context;

import java.time.LocalDate;

public class Finance {

    private LocalDate dia;
    private String tipo;
    private Double valor;
    private FinanceDB financeDB;

    public Finance(Context context) {
        this.financeDB = new FinanceDB(context);
    }

    public Finance(LocalDate dia, String tipo, Double valor) {
        this.dia = dia;
        this.tipo = tipo;
        this.valor = valor;
    }

    public boolean inserir() {
        return financeDB.insert(this);
    }

    public LocalDate getDia() {
        return dia;
    }

    public String getTipo() {
        return tipo;
    }

    public Double getValor() {
        return valor;
    }
}
