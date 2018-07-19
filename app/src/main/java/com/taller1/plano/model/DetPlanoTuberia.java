package com.taller1.plano.model;

public class DetPlanoTuberia {
    private int idDetPlanoTuberia;
    private int idPlano;
    private int idTuberia;
    private float distancia;
    private float precioUnitario;
    private float precioTotal;

    private int index;
    private float posX;
    private float posY;

    public DetPlanoTuberia(int idDetPlanoTuberia, int idPlano, int idTuberia, float distancia, float precioUnitario, float precioTotal, int index, float posX, float posY) {
        this.idDetPlanoTuberia = idDetPlanoTuberia;
        this.idPlano = idPlano;
        this.idTuberia = idTuberia;
        this.distancia = distancia;
        this.precioUnitario = precioUnitario;
        this.precioTotal = precioTotal;

        this.index = index;
        this.posX = posX;
        this.posY = posY;

    }

    public int getIdDetPlanoTuberia() {
        return idDetPlanoTuberia;
    }

    public void setIdDetPlanoTuberia(int idDetPlanoTuberia) {
        this.idDetPlanoTuberia = idDetPlanoTuberia;
    }

    public int getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(int idPlano) {
        this.idPlano = idPlano;
    }

    public int getIdTuberia() {
        return idTuberia;
    }

    public void setIdTuberia(int idTuberia) {
        this.idTuberia = idTuberia;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    @Override
    public String toString(){
        String s="";
        s += "< idDetPlanoTuberia : " + String.valueOf(idDetPlanoTuberia) + ", ";
        s += "idPlano : " + String.valueOf(idPlano) + ", ";
        s += "idTuberia : " + String.valueOf(idTuberia) + ", ";
        s += "distancia : " + String.valueOf(distancia) + ", ";
        s += "precioUnitario : " + String.valueOf(precioUnitario) + ", ";
        s += "index : " + String.valueOf(index) + ", ";
        s += "posX : " + String.valueOf(posX) + ", ";
        s += "posY : " + String.valueOf(posY) + " >";
        return s;
    }
}