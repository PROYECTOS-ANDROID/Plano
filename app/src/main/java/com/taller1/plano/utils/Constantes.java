package com.taller1.plano.utils;

import com.github.mikephil.charting.utils.MPPointD;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Constantes {

    public final static String VIVI_PK = "id";
    public final static String VIVI_DUENIO = "duenio";
    public final static String VIVI_BARRIO = "barrio";
    public final static String VIVI_CIUDAD = "ciudad";
    public final static String VIVI_CALLE = "calle";
    public final static String VIVI_NRO = "nro";
    public static final String TAG = "ANDROIDE";

    /**
     * devuelve un double formateado a dos decimales
     * devuelve un double formateado a dos decimales
     * @param value
     * @return
     */
    public static double getDecimal(double value){
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Metodo que calcula la distancia entre dos puntos
     * @param p1 punto inicial
     * @param p2 punto final
     * @return
     */
    public static double calculateDistance(MPPointD p1, MPPointD p2){
        // hacer la funcion para el calculo de la distancia
        double paramX = p2.x - p1.x;
        double paramY = p2.y - p2.y;
        double distancia = Math.sqrt(paramX * paramX + paramY * paramY);

        return distancia;
    }
}
