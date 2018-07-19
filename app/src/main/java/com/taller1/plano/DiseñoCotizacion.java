package com.taller1.plano;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.itextpdf.text.DocumentException;
import com.taller1.plano.model.DetPlanoTuberia;
import com.taller1.plano.model.Empleado;
import com.taller1.plano.model.EntryTuberia;
import com.taller1.plano.model.Plano;
import com.taller1.plano.model.Servicio;
import com.taller1.plano.model.Tuberia;
import com.taller1.plano.model.Vivienda;
import com.taller1.plano.utils.Constantes;
import com.taller1.plano.utils.Cotizacion;
import com.taller1.plano.utils.CotizacionDetail;
import com.taller1.plano.utils.PdfManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.taller1.plano.utils.Constantes.TAG;

public class DiseñoCotizacion {
    Cotizacion cotizacion = new Cotizacion();
    Servicio servicio ;
    Context context;
    Vivienda vivienda;
    Empleado usuario;
    Tuberia tuberia;
    //Declaramos la clase PdfManager
    private PdfManager pdfManager = null;

    public DiseñoCotizacion(Context context, Vivienda vivienda, Empleado usuario, Tuberia tuberia){
        try {
            //Instanciamos la clase PdfManager
            pdfManager = new PdfManager(context);
            this.context = context;
            this.servicio = new Servicio(context);
            this.vivienda = vivienda;
            this.usuario = usuario;
            this.tuberia = tuberia;
        } catch (IOException e) {
            Log.i(TAG, "Error constructo : " + e.getMessage());
        } catch (DocumentException e) {
            Log.i(TAG, "Error constructo Documen : " + e.getMessage());
        }
    }

    /**
     * metodo que genera la cotizacion de la vivienda
     */
    public void generarCotizacion(){
     // hacer las peticiones al servidor
        Log.i(TAG, "generarCotizacion id vivienda: " + vivienda.getId());
        servicio.getPlano(vivienda.getId(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        cotizacion = new Cotizacion();


                        String resultado = null;
                        try {
                            resultado = response.getString("resultado");
                            if(resultado.equals("ok")){
                                JSONObject plan =  response.getJSONObject("data");
                                // int idEmpleado, int idVivienda, Date fecha
                                cotizacion.nro = String.valueOf(plan.getInt(Constantes.PLAN_PK));
                                cotizacion.companyName = "PATO GAS S.R.L";
                                cotizacion.companyDireccion = "Avenida San Silvestre calle numero dos , # 3 ";
                                cotizacion.companyCiudad = "Santa cruz - Bolivia";

                                cotizacion.clienteNombre = vivienda.getDuenio();
                                cotizacion.clienteDireccion = "calle : " + vivienda.getCalle() + "nro : " + vivienda.getNro();
                                cotizacion.clienteTelefono = "76258712";
                                cotizacion.clienteCiudad = vivienda.getCiudad();

                                cotizacion.usuarioNombre = usuario.getNombre();
                                cotizacion.usuarioCi = usuario.getCi();
                                cotizacion.total = 0; // se cambia cuando se genera los detalles
                                cotizacion.cotizacionDetailList = new ArrayList<CotizacionDetail>();

                                generarDetalleCotizacion(plan.getInt(Constantes.PLAN_PK));
                                Log.i(TAG, "cotizacion.generarCotizacion (plano encontrado)!!!");

                            }

                        } catch (JSONException e) {
                            Log.i(TAG, "error : " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "cotizacion.generarCotizacion Error : " + error.getMessage());
                    }
                }
        );
    }

    private void generarDetalleCotizacion(int idplano){

        servicio.getDetalle(idplano,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String resultado = null;
                        float totalAc = 0;
                        try {
                            resultado = response.getString("resultado");
                            if(resultado.equals("ok")){
                                JSONArray detalle = response.getJSONArray("data");
                                for (int i = 0; i < detalle.length(); i++) {
                                    JSONObject item = detalle.getJSONObject(i);

                                    CotizacionDetail cotdet = new CotizacionDetail();

                                    cotdet.item = String.valueOf(item.getInt(Constantes.DETP_INDEX));
                                    cotdet.nombre = tuberia.getDescripcion();
                                    cotdet.medida = tuberia.getMedida();
                                    cotdet.distancia = (float) item.getDouble(Constantes.DETP_DISTANCIA);
                                    cotdet.precioUnitario = (float) item.getDouble(Constantes.DETP_PRECIO_UNI);
                                    cotdet.precioTotal = (float) item.getDouble(Constantes.DETP_PRECIOTOT);

                                    cotizacion.cotizacionDetailList.add(cotdet);

                                    totalAc = totalAc + cotdet.precioTotal;
                                }

                                cotizacion.total = totalAc;
                                Log.i(TAG, "Detalle generado correctamente");
                                // llamar al creador de pdf
                                crearCotizacion();
                                mostrarEnConsola();
                            }

                        } catch (JSONException e) {
                            Log.i(TAG, "Error json cargarDetalle : " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "cotizacion.generarDetalleCotizacion Error : " + error.getMessage());
                    }
                }
        );
    }
    private void mostrarEnConsola(){
        Log.i(TAG, "COTIZA : nro: " + cotizacion.nro + ", nombreUsuario: " + cotizacion.usuarioNombre);
        for (int i = 0; i < cotizacion.cotizacionDetailList.size(); i++) {
            CotizacionDetail detail = cotizacion.cotizacionDetailList.get(i);
            Log.i(TAG, "DETAIL : item: " + detail.item + ", nombew: " + detail.nombre);
        }
    }
    private void crearCotizacion(){
        assert pdfManager != null;

        pdfManager.createPdfDocument(cotizacion);

        this.mostrarCotizacion();
    }
    private void mostrarCotizacion(){
        assert pdfManager != null;
        pdfManager.showPdfFile(cotizacion.nro,context);
    }
}
