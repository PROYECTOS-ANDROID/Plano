package com.taller1.plano.utils;

import android.graphics.Point;

import com.github.mikephil.charting.utils.MPPointD;
import com.taller1.plano.model.Punto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class Constantes {

    public final static String VIVI_PK = "id";
    public final static String VIVI_DUENIO = "duenio";
    public final static String VIVI_BARRIO = "barrio";
    public final static String VIVI_CIUDAD = "ciudad";
    public final static String VIVI_CALLE = "calle";
    public final static String VIVI_NRO = "nro";
    public final static String TAG = "ANDROIDE";

    public final static String EMPL_PK = "idEmpleado";
    public final static String EMPL_NOMBRE = "nombre";
    public final static String EMPL_APELLIDO = "apellido";
    public final static String EMPL_CI = "ci";
    public final static String EMPL_TELEFONO = "telefono";
    public final static String EMPL_EMAIL = "email";
    public final static String EMPL_PASSWORD = "password";

    public final static String TUBE_PK = "idTuberia";
    public final static String TUBE_MEDIDA = "medida";
    public final static String TUBE_DESCRIPCION = "descripcion";
    public final static String TUBE_PRECIO = "precio";

    public final static String PLAN_PK = "idPlano";
    public final static String PLAN_IDEMPLEADO = "idEmpleado";
    public final static String PLAN_IDVIVIENDA = "idVivienda";
    public final static String PLAN_FECHA = "fecha";

    public final static String DETP_PK = "idDetPlanoTuberia";
    public final static String DETP_IDPLANO = "idPlano";
    public final static String DETP_IDTUBERIA = "idTuberia";
    public final static String DETP_DISTANCIA = "distancia";
    public final static String DETP_PRECIO_UNI = "precioUnitario";
    public final static String DETP_PRECIOTOT = "precioTotal";
    public final static String DETP_INDEX = "index";
    public final static String DETP_POSX = "posX";
    public final static String DETP_POSY = "posY";


    public final static String TIPO_COTIZACION = "cotizacion";
    public final static String TIPO_PLANO = "plano";

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
    public static double calculateDistance(Punto p1, Punto p2){
        // hacer la funcion para el calculo de la distancia
        double paramX = p2.x - p1.x;
        double paramY = p2.y - p2.y;
        double distancia = Math.sqrt(paramX * paramX + paramY * paramY);

        return distancia;
    }
}
