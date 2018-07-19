package com.taller1.plano;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.taller1.plano.Adapter.ViviendaAdapter;
import com.taller1.plano.model.Empleado;
import com.taller1.plano.model.EntryTuberia;
import com.taller1.plano.model.Plano;
import com.taller1.plano.model.Servicio;
import com.taller1.plano.model.Tuberia;
import com.taller1.plano.model.Vivienda;
import com.taller1.plano.utils.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListViviendaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ListViviendaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_EMPLEADO = "EMPLEADO";
    private static final String ARG_TUBERIA = "TUBERIA";
    private static final String ARG_TIPO = "TIPO_LISTA";

    private Empleado usuario = null;
    private Tuberia tuberia = null;
    private OnFragmentInteractionListener mListener;

    private String TIPO_LISTA = "";

    @BindView(R.id.recycler_viviendas)
    RecyclerView recycler_vivienda;

    private RecyclerView.Adapter adapterDatos;
    private RecyclerView.LayoutManager mLayoutManager;

    private Servicio servicio;

    public ListViviendaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String strUsuario = getArguments().getString(ARG_EMPLEADO);
            String strTuberia = getArguments().getString(ARG_TUBERIA);
            TIPO_LISTA = getArguments().getString(ARG_TIPO);
            Gson gson = new Gson();
            usuario = gson.fromJson(strUsuario, Empleado.class);
            tuberia = gson.fromJson(strTuberia, Tuberia.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_vivienda, container, false);
        ButterKnife.bind(this, view);
        inicializar();
        return view;
    }
    private void inicializar(){
        servicio = new Servicio(getActivity().getApplicationContext());
        recycler_vivienda.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recycler_vivienda.setLayoutManager(mLayoutManager);

        this.cargarDatos();
    }
    private void cargarViviendas(){
        servicio.getViviendas(new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{

                    ArrayList<Vivienda> viviendas = new ArrayList<Vivienda>();

                    for (int i = 0; i< response.length(); i++){
                        JSONObject vivienda = response.getJSONObject(i);
                        Log.i(Constantes.TAG, "item : " + vivienda.getString(Constantes.VIVI_DUENIO));

                        viviendas.add(new Vivienda( vivienda.getInt(Constantes.VIVI_PK),
                                vivienda.getString(Constantes.VIVI_DUENIO),
                                vivienda.getString(Constantes.VIVI_BARRIO),
                                vivienda.getString(Constantes.VIVI_CIUDAD),
                                vivienda.getString(Constantes.VIVI_CALLE),
                                vivienda.getString(Constantes.VIVI_NRO)
                        ));
                    }
                    adapterDatos = new ViviendaAdapter(viviendas, getActivity(), usuario, tuberia, TIPO_LISTA);
                    recycler_vivienda.setAdapter(adapterDatos);
                }catch(JSONException e){
                    Log.i(Constantes.TAG, "erro : " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(Constantes.TAG, "getviviendas error : " + error.getMessage());
            }
        });
    }

    private void cargarViviendaPlanos(){

        servicio.getViviendas_plano(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    ArrayList<Vivienda> viviendas = new ArrayList<Vivienda>();

                    String resultado =  response.getString("resultado");

                    if(resultado.equals("ok")){
                        JSONArray listado =  response.getJSONArray("data");
                        for (int i = 0; i < listado.length(); i++) {
                            JSONObject objeto = listado.getJSONObject(i);

                            viviendas.add(new Vivienda(
                                    objeto.getInt(Constantes.VIVI_PK),
                                    objeto.getString(Constantes.VIVI_DUENIO),
                                    objeto.getString(Constantes.VIVI_BARRIO),
                                    objeto.getString(Constantes.VIVI_CIUDAD),
                                    objeto.getString(Constantes.VIVI_CALLE),
                                    objeto.getString(Constantes.VIVI_NRO)
                            ));
                            Log.i(Constantes.TAG, "dueño vivienda : " + objeto.getString("duenio"));
                        }

                        Log.i(Constantes.TAG, "getViviendas_plano (planos encontrado)!!!");
                    }

                    adapterDatos = new ViviendaAdapter(viviendas, getActivity(), usuario, tuberia, TIPO_LISTA);
                    recycler_vivienda.setAdapter(adapterDatos);
                }catch(JSONException e){
                    Log.i(Constantes.TAG, "erro : " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(Constantes.TAG, "getviviendasPlan error : " + error.getMessage());
            }
        });
    }


    private void cargarDatos(){

        if(TIPO_LISTA.equals(Constantes.TIPO_COTIZACION)){
            // cargar viviendas con planos
            cargarViviendaPlanos();
        }else{
            // cargar viviendas
            cargarViviendas();
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
