package com.taller1.plano.model;

public class Plano {
    private int idPlano;
    private int idEmpleado;
    private int idVivienda;

    public Plano(int idPlano, int idEmpleado, int idVivienda) {
        this.idPlano = idPlano;
        this.idEmpleado = idEmpleado;
        this.idVivienda = idVivienda;
    }

    public int getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(int idPlano) {
        this.idPlano = idPlano;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdVivienda() {
        return idVivienda;
    }

    public void setIdVivienda(int idVivienda) {
        this.idVivienda = idVivienda;
    }
}
