package com.taller1.plano;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;

import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.taller1.plano.model.Codo;
import com.taller1.plano.model.DetPlanoTuberia;
import com.taller1.plano.model.Empleado;
import com.taller1.plano.model.EntryTuberia;
import com.taller1.plano.model.Plano;
import com.taller1.plano.model.Punto;
import com.taller1.plano.model.Servicio;
import com.taller1.plano.model.Tuberia;
import com.taller1.plano.model.Vivienda;
import com.taller1.plano.utils.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * clase que contine el negocio de diseñador de tuberias
 */
public class DiseñoPlano {

    private List<Entry> tuberias = new ArrayList<Entry>();

    private Vivienda vivienda;
    private Empleado empleado;

    private Servicio servicio;

    /**
     * Es el listado de las tuberias que tenemos para escoger
     */
    private Tuberia tuberiaUnica = null;

    private ArrayList<Codo> codos = null;

    private Plano plano = null;

    // VARIABLES DE LA INTERFACE
    private LineChart diagrama = null;
    private OnChartGestureListener listenerGesture;
    private OnChartValueSelectedListener listenerSelect;

    public DiseñoPlano(
            Context context,
            OnChartGestureListener listenerGesture,
            OnChartValueSelectedListener listenerSelect
        ) {
        this.servicio = new Servicio(context);
        this.listenerSelect = listenerSelect;
        this.listenerGesture = listenerGesture;
    }

    public Vivienda getVivienda() {
        return vivienda;
    }

    public void setVivienda(Vivienda vivienda) {
        this.vivienda = vivienda;
    }
    public ArrayList<Codo> getCodos() {
        return codos;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public List<Entry> getTuberias() {
        return tuberias;
    }

    public void setTuberia(EntryTuberia entry){
        this.tuberias.add(entry);
        representar();
    }
    public void removeTuberia(int index){
        this.tuberias.remove(index);
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public void setTuberiaUnica(Tuberia tuberia){
        this.tuberiaUnica= tuberia;
    }

    public void guardarPlano(){
        // guardo el plano solo si hay minimo 2 puntos

        if(tuberias.size() >= 2){

            plano = new Plano(0, empleado.getIdEmpleado(), vivienda.getId(), new Date());

            servicio.createPlano(plano.getIdEmpleado(),
                    plano.getIdVivienda(),
                    plano.getFecha(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.i(Constantes.TAG, "servicio.createPlano : " + response.toString());
                                plano.setIdPlano(response.getInt(Constantes.PLAN_PK));
                                guardarDetallePlano();
                            } catch (JSONException e) {
                                Log.i(Constantes.TAG, "servicio.createPlano ErrorJSON: " + e.getMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i(Constantes.TAG, "ERROR servicio.createPlano : " + error.getMessage());
                        }
                    }
            );
        }else{
            Log.i(Constantes.TAG, "numero de punto minimos : " + String.valueOf(tuberias.size()));
        }
    }
    public void guardarDetallePlano(){

        ArrayList<DetPlanoTuberia> tuberiasDet = new ArrayList<>();

        EntryTuberia punto;

        DetPlanoTuberia tuberiaAnterior = null;
        DetPlanoTuberia tuberia = null;
        for (int i = 0; i < tuberias.size(); i++) {
            punto = (EntryTuberia) tuberias.get(i);
            tuberia = punto.getDetPlano();
            if(tuberia == null){
                tuberia = new DetPlanoTuberia(0,
                        plano.getIdPlano(),
                        tuberiaUnica.getIdTuberia(),
                        0,
                        tuberiaUnica.getPrecio(),
                        0,
                        i,
                        punto.getX(),
                        punto.getY()
                );
            }
            Log.i(Constantes.TAG, "antes de distancias");
            double distancia = 0;
            if(tuberiaAnterior != null){
                distancia = Constantes.calculateDistance(new Punto(tuberiaAnterior.getPosX(), tuberiaAnterior.getPosY()), new Punto(tuberia.getPosX(),tuberia.getPosY()));
                distancia = Constantes.getDecimal(distancia);
                tuberia.setDistancia((float) distancia);
                tuberia.setPrecioTotal((float) (distancia * tuberia.getPrecioUnitario()));
            }

            tuberiaAnterior = tuberia;
            if(tuberia.getIdDetPlanoTuberia() == 0){
                tuberiasDet.add(tuberia);
            }
            Log.i(Constantes.TAG, "tuberia : " + tuberia.toString());
        }

        for (int i=0; i < tuberiasDet.size(); i++){
            DetPlanoTuberia t = tuberiasDet.get(i);
            servicio.createDetPlanoTuberia(
                                    t.getIdPlano(),
                                    t.getIdTuberia(),
                                    t.getDistancia(),
                                    t.getPrecioUnitario(),
                                    t.getPrecioTotal(),
                                    t.getIndex(),
                                    t.getPosX(),
                                    t.getPosY(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i(Constantes.TAG, "det : " + response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i(Constantes.TAG, "ERROR det : " + error.getMessage());
                        }
                    }
                    );
        }
    }
    public void actualizarPlano(){
        // solo guardamos los detalles nuevos
        guardarDetallePlano();
    }

    public void cargarDatosServidor(LineChart diagrama){
        // buscamos el plano de la vivienda
        this.diagrama = diagrama;

        servicio.getPlano(
                vivienda.getId(),
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String resultado =  response.getString("resultado");
                    if(resultado.equals("ok")){
                        JSONObject plan =  response.getJSONObject("data");
                        // int idEmpleado, int idVivienda, Date fecha
                        plano = new Plano(
                                plan.getInt(Constantes.PLAN_PK),
                                plan.getInt(Constantes.PLAN_IDEMPLEADO),
                                plan.getInt(Constantes.PLAN_IDVIVIENDA),
                                new Date()
                        );
                        CargarDetalle();
                        Log.i(Constantes.TAG, "DiseñoPlano.cargarDatosServidor (plano encontrado)!!!");
                    }else{
                        // cuando no existe un plano en el servidor
                        setTuberia(new EntryTuberia(0,0, null));
                        //representar();
                    }
                } catch (JSONException e) {
                    Log.i(Constantes.TAG, "jsonERrror :" + e.getMessage());
                }
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(Constantes.TAG, "Error DiseñoPlano.cargarDatosServidor : " + error.getMessage());
            }
        });
    }
    /**
     * metodo que cargaDetalle
     **/
    private void CargarDetalle(){

        servicio.getDetalle(plano.getIdPlano(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String resultado = null;
                        try {
                            resultado = response.getString("resultado");
                            if(resultado.equals("ok")){
                                JSONArray detalle = response.getJSONArray("data");
                                for (int i = 0; i < detalle.length(); i++) {
                                    JSONObject item = detalle.getJSONObject(i);

                                    DetPlanoTuberia tuberia = new DetPlanoTuberia(
                                            item.getInt(Constantes.DETP_PK),
                                            item.getInt(Constantes.DETP_IDPLANO),
                                            item.getInt(Constantes.DETP_IDTUBERIA),
                                            (float) item.getDouble(Constantes.DETP_DISTANCIA),
                                            (float) item.getDouble(Constantes.DETP_PRECIO_UNI),
                                            (float) item.getDouble(Constantes.DETP_PRECIOTOT),
                                            item.getInt(Constantes.DETP_INDEX),
                                            (float) item.getDouble(Constantes.DETP_POSX),
                                            (float) item.getDouble(Constantes.DETP_POSY)
                                        );
                                    setTuberia( new EntryTuberia(tuberia.getPosX(), tuberia.getPosY(), tuberia));
                                    Log.i(Constantes.TAG, "tuberia : " + tuberia.toString());
                                }
                                //representar();
                            }
                        } catch (JSONException e) {
                            Log.i(Constantes.TAG, "Error json cargarDetalle : " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(Constantes.TAG, "Error cargarDetalle : " + error.getMessage());
                    }
                }
        );
    }

    private void representar(){
        try{
            LineDataSet dataSet = new LineDataSet( getTuberias(), "");
            dataSet.setFillAlpha(110);
            dataSet.setColor(Color.BLUE);
            dataSet.setDrawValues(false);

            ArrayList<ILineDataSet> dsInterface = new ArrayList<ILineDataSet>();
            dsInterface.add(dataSet);

            LineData data = new LineData(dsInterface);

            diagrama.setDragEnabled(true);
            diagrama.setScaleEnabled(true);

            //diagrama.setScaleMinima(0f, 0f);
            //diagrama.fitScreen();

            diagrama.setOnChartValueSelectedListener(listenerSelect);
            diagrama.setOnChartGestureListener(listenerGesture);

            Description description = new Description();
            description.setText("hola");
            diagrama.setDescription(description);

            diagrama.getLegend().setEnabled(false);


            diagrama.setTouchEnabled(true);
            diagrama.setData(data);
            diagrama.notifyDataSetChanged();
            diagrama.invalidate(); // refresh the diagrama
        }catch(Exception e){
            Log.i(Constantes.TAG, "error : " + e.getMessage());
        }

    }


}
