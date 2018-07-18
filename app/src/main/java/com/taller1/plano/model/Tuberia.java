package com.taller1.plano.model;

public class Tuberia {

    private int idTuberia;
    private String medida;
    private String descripcion;
    private float precio;

    public Tuberia(int idTuberia, String medida, String descripcion, float precio) {
        this.idTuberia = idTuberia;
        this.medida = medida;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public int getIdTuberia() {
        return idTuberia;
    }

    public void setIdTuberia(int idTuberia) {
        this.idTuberia = idTuberia;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}
