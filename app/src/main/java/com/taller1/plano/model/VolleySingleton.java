package com.taller1.plano.model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Libreria para consumir los servicios
 */
public class VolleySingleton {
    private static VolleySingleton singleton;
    private RequestQueue requestQueue;
    private static Context context;

    private VolleySingleton(Context contexto){
        context = contexto;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context contexto){
        if(singleton == null){
            singleton = new VolleySingleton(contexto.getApplicationContext());
        }
        return singleton;
    }

    /**
     * Añade la peticion a la cola
     * @param request Peticion a realizarse
     * @param <T> Resultado de la peticion
     */
    public <T> void addToRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }


    /**
     * Obtiene la instancia de la cola de peticiones
     * @return singleton
     */
    private RequestQueue getRequestQueue(){
        if(this.requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return this.requestQueue;
    }
}
