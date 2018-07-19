package com.taller1.plano.model;

import com.github.mikephil.charting.data.Entry;

public class EntryTuberia extends Entry{

    private DetPlanoTuberia tuberia;

    public EntryTuberia(float x, float y, DetPlanoTuberia tuberia){
        super();
        this.setX(x);
        this.setY(y);
        this.tuberia = tuberia;
    }

    public DetPlanoTuberia getDetPlano(){
        return tuberia;
    }
}
