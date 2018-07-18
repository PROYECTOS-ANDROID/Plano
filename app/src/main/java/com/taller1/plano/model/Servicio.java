package com.taller1.plano.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.taller1.plano.utils.Constantes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Servicio {
    //private final String HOST = "http://isyourphoto.herokuapp.com/";
    private final String HOST = "http://192.168.1.4/appweb/web/";
    private final Context context;

    public Servicio(Context context){
        this.context = context;
    }


    /**
     * Metodo que busca las viviendas
     * @param jsonListener
     * @param errorListener
     */
    public void getViviendas(Response.Listener<JSONArray> jsonListener,
                             Response.ErrorListener errorListener){

        String url = this.HOST + "sviviendas";
        HashMap<String, String> headers = new HashMap<>();
        this.getJsonArray(url, jsonListener, errorListener, headers);
    }

    /**
     * Metodo para crear una vivienda
     * @param duenio Nombre del dueño
     * @param barrio Barrio de la vivienda
     * @param ciudad nombre de la ciudad
     * @param calle nombre de la calle
     * @param nro nro de la casa
     * @param jsonObjectListener Escuchador de objetos
     * @param errorListener Escuchador de errores
     */
    public void createVivienda(
            String duenio,
            String barrio,
            String ciudad,
            String calle,
            String nro,
            Response.Listener<JSONObject> jsonObjectListener,
            Response.ErrorListener errorListener){

        HashMap<String, Object> parametros = new HashMap<>();

        parametros.put(Constantes.VIVI_DUENIO, duenio);
        parametros.put(Constantes.VIVI_BARRIO, barrio);
        parametros.put(Constantes.VIVI_CIUDAD, ciudad);
        parametros.put(Constantes.VIVI_CALLE, calle);
        parametros.put(Constantes.VIVI_NRO, nro);

        this.post(HOST+"sviviendas",
                parametros,
                jsonObjectListener,
                errorListener);
    }

    /**
     * Metodo para hacer peticiones
     * @param uri
     * @param parametros
     * @param jsonListener
     * @param errorListener
     * @param contentTypeBody
     */
    private void put(String uri,
                     HashMap<String, Object> parametros,
                     Response.Listener<JSONObject> jsonListener,
                     Response.ErrorListener errorListener,
                     final String contentTypeBody){

        JSONObject paramObjects = new JSONObject(parametros);
        Log.i(Constantes.TAG, paramObjects.toString());
        // creamos la peticion put
        JsonObjectRequest peticion = new JsonObjectRequest(
                Request.Method.PUT,
                uri,
                paramObjects,
                jsonListener,
                errorListener
        ){
            @Override
            public String getBodyContentType() {
                return contentTypeBody;

            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(peticion);
    }

    private void post(String uri,
                      HashMap<String, Object> parametros,
                      Response.Listener<JSONObject> jsonListener,
                      Response.ErrorListener errorListener){

        JSONObject paramObjects = new JSONObject(parametros);
        Log.i(Constantes.TAG, "post : " + paramObjects.toString());

        // creamos la peticion post
        JsonObjectRequest peticion = new JsonObjectRequest(
                Request.Method.POST,
                uri,
                paramObjects,
                jsonListener,
                errorListener
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String,String> headers = new HashMap<String, String>();
                //headers.put("Content-Type","application/x-www-form-urlencoded");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(peticion);
    }

    private void getJsonArray(String uri,
                     Response.Listener<JSONArray> jsonListener,
                     Response.ErrorListener errorListener,
                     final HashMap<String, String> headers){

        JsonArrayRequest peticion = new JsonArrayRequest(
                Request.Method.GET,
                uri,
                null,
                jsonListener,
                errorListener
        );


        VolleySingleton.getInstance(context).addToRequestQueue(peticion);
    }

    private void getObject(String uri,
                     Response.Listener<JSONObject> jsonListener,
                     Response.ErrorListener errorListener,
                     final HashMap<String, String> headers){

        JsonObjectRequest peticion = new JsonObjectRequest(
                uri,
                null,
                jsonListener,
                errorListener
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(peticion);
    }


}
