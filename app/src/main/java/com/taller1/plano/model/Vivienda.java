package com.taller1.plano.model;

import java.io.Serializable;

public class Vivienda implements Serializable {
    private int id;
    private String duenio;
    private String barrio;
    private String ciudad;
    private String calle;
    private String nro;

    public Vivienda(int id, String duenio, String barrio, String ciudad, String calle, String nro){
        this.id = id;
        this.duenio = duenio;
        this.barrio = barrio;
        this.ciudad = ciudad;
        this.calle = calle;
        this.nro = nro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDuenio() {
        return duenio;
    }

    public void setDuenio(String duenio) {
        this.duenio = duenio;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }
}
