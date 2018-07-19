package com.taller1.plano.model;

import java.util.Date;

public class Plano {
    private int idPlano;
    private int idEmpleado;
    private int idVivienda;
    private Date fecha;

    public Plano(int idPlano, int idEmpleado, int idVivienda, Date fecha) {
        this.idPlano = idPlano;
        this.idEmpleado = idEmpleado;
        this.idVivienda = idVivienda;
        this.fecha = fecha;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
