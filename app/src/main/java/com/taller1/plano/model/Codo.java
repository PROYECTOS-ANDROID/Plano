package com.taller1.plano.model;

public class Codo {
    private int idCodo;
    private String medida;
    private String descripcion;
    private float precio;

    public Codo(int idCodo, String medida, String descripcion, float precio) {
        this.idCodo = idCodo;
        this.medida = medida;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public int getIdCodo() {
        return idCodo;
    }

    public void setIdCodo(int idCodo) {
        this.idCodo = idCodo;
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
