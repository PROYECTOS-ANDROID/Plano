package com.taller1.plano;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.LineChart;
import com.taller1.plano.model.Servicio;
import com.taller1.plano.utils.Constantes;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViviendaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViviendaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViviendaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.duenio)
    TextInputEditText txtDuenio;

    @BindView(R.id.barrio)
    TextInputEditText txtBarrio;

    @BindView(R.id.calle)
    TextInputEditText txtCalle;

    @BindView(R.id.ciudad)
    TextInputEditText txtCiudad;

    @BindView(R.id.nro_casa)
    TextInputEditText txtNroCasa;

    @BindView(R.id.create_vivienda_button)
    Button btnGuardar;

    Servicio servicio;

    private OnFragmentInteractionListener mListener;

    public ViviendaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViviendaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViviendaFragment newInstance(String param1, String param2) {
        ViviendaFragment fragment = new ViviendaFragment();
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

        View view = inflater.inflate(R.layout.fragment_vivienda, container, false);
        ButterKnife.bind(this, view);
        this.inicializar();
        return view;
    }
    private void inicializar(){

        servicio = new Servicio(getContext());

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarVivienda();
            }
        });

    }
    private void guardarVivienda(){

        try{
            validar();
            servicio.createVivienda(
                    txtDuenio.getText().toString(),
                    txtBarrio.getText().toString(),
                    txtCiudad.getText().toString(),
                    txtCalle.getText().toString(),
                    txtNroCasa.getText().toString(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i(Constantes.TAG, "vivienda save : " + response.toString());
                            nuevaVivienda();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i(Constantes.TAG, "error : " + error.getMessage());
                            Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void validar() throws Exception {
        String msg = "";

        if(txtDuenio.getText().toString().length()<= 0 ){
            msg += "Nombre de Dueño no definido \n";
        }
        if(txtBarrio.getText().toString().length()<= 0 ){
            msg += "Nombre de Barrio no definido \n";
        }
        if(txtCiudad.getText().toString().length()<= 0 ){
            msg += "Nombre de Ciudad no definido \n";
        }
        if(txtCalle.getText().toString().length()<= 0 ){
            msg += "Nombre de Calle no definido \n";
        }
        if(txtNroCasa.getText().toString().length()<= 0 ){
            msg += "Nro. de casa no definido \n";
        }

        if(msg.length()>0){
            throw new Exception(msg);
        }
    }
    private void nuevaVivienda(){
        txtDuenio.setText("");
        txtBarrio.setText("");
        txtCiudad.setText("");
        txtCalle.setText("");
        txtNroCasa.setText("");
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
