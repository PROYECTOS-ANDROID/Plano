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
import com.taller1.plano.Adapter.ViviendaAdapter;
import com.taller1.plano.model.Servicio;
import com.taller1.plano.model.Vivienda;
import com.taller1.plano.utils.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListViviendaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListViviendaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListViviendaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.recycler_viviendas)
    RecyclerView recycler_vivienda;

    private RecyclerView.Adapter adapterDatos;
    private RecyclerView.LayoutManager mLayoutManager;

    private Servicio servicio;

    public ListViviendaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListViviendaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListViviendaFragment newInstance(String param1, String param2) {
        ListViviendaFragment fragment = new ListViviendaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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

        this.cargarViviendas();
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
                    adapterDatos = new ViviendaAdapter(viviendas, getActivity());
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
