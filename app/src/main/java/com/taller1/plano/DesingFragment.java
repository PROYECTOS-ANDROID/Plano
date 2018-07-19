package com.taller1.plano;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointD;
import com.google.gson.Gson;
import com.taller1.plano.model.Empleado;
import com.taller1.plano.model.EntryTuberia;
import com.taller1.plano.model.Tuberia;
import com.taller1.plano.model.Vivienda;
import com.taller1.plano.utils.Constantes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.taller1.plano.utils.Constantes.TAG;


public class DesingFragment extends Fragment implements OnChartGestureListener, OnChartValueSelectedListener {

    private static final String ARG_VIVIENDA = "VIVIENDA";

    private DiseñoPlano negocio;

    @BindView(R.id.diseniador) LineChart diagrama;
    final String TAG = "ANDROIDE";

    private OnFragmentInteractionListener mListener;

    public DesingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String vivienda = getArguments().getString(ARG_VIVIENDA);
            String empleado = getArguments().getString("EMPLEADO");
            String tuberia = getArguments().getString("TUBERIA");

            Gson gson = new Gson();

            Log.i(Constantes.TAG, "DesingPlano empleado : " + empleado);

            negocio = new DiseñoPlano(getActivity().getApplicationContext(), this, this);

            negocio.setVivienda(gson.fromJson(vivienda, Vivienda.class));
            negocio.setEmpleado(gson.fromJson(empleado, Empleado.class));
            negocio.setTuberiaUnica(gson.fromJson(tuberia, Tuberia.class));
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        // aqui guardamos los datos del plano
        this.guardarPlano();
    }

    /**
     * Metodo que guarda el plano en el servidor
     */
    private void guardarPlano(){
        if(negocio.getPlano() == null){
            // todo es nuevo
            negocio.guardarPlano();
        }else{
            negocio.actualizarPlano();
        }
    }

    public void insertarPunto(MPPointD punto){
        try{

            if(negocio.getTuberias().size() > 0){
                Entry e1 = (Entry) negocio.getTuberias().get(0);
                if(e1.getX() == 0 && e1.getY() == 0){
                    // es el punto CERO y lo elimino
                    negocio.removeTuberia(0);
                    Log.i(TAG, "punto removido: " + negocio.getTuberias().size());
                }
            }
            float x = (float) Constantes.getDecimal(punto.x);
            float y = (float) Constantes.getDecimal(punto.y);
            negocio.setTuberia(new EntryTuberia(x, y, null));
            Log.i(TAG, "pase insertar count : " + negocio.getTuberias().size());
        }catch (Exception e){
            Log.i(TAG, "error : " + e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_desing, container, false);
        ButterKnife.bind(this, view);
        negocio.cargarDatosServidor(diagrama);
        return view;
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

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        MPPointD punto = diagrama.getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(me.getX(), me.getY());
        insertarPunto(punto);
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

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
