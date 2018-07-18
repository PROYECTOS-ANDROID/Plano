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
import com.taller1.plano.model.Vivienda;
import com.taller1.plano.utils.Constantes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.taller1.plano.utils.Constantes.TAG;


public class DesingFragment extends Fragment implements OnChartGestureListener, OnChartValueSelectedListener {

    private static final String ARG_VIVIENDA = "VIVIENDA";

    private Vivienda vivienda;


    @BindView(R.id.diseniador) LineChart diagrama;
    final String TAG = "ANDROIDE";
    List<Entry> listaPuntos = new ArrayList<Entry>();

    private OnFragmentInteractionListener mListener;

    public DesingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String vivienda = getArguments().getString(ARG_VIVIENDA);

            Gson gson = new Gson();
            this.vivienda = gson.fromJson(vivienda, Vivienda.class);
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

    }
    private void inicializar(){
        try{
            listaPuntos.add(new Entry(0,0));
            Log.i(TAG, "fpase list ");
            LineDataSet dataSet = new LineDataSet(listaPuntos, "");
            dataSet.setFillAlpha(110);
            dataSet.setColor(Color.BLUE);
            dataSet.setDrawValues(false);

            ArrayList<ILineDataSet> dsInterface = new ArrayList<ILineDataSet>();
            dsInterface.add(dataSet);

            LineData data = new LineData(dsInterface);

            diagrama.setDragEnabled(true);
            diagrama.setScaleEnabled(true);
            diagrama.setOnChartValueSelectedListener(this);
            diagrama.setOnChartGestureListener(this);

            Description description = new Description();
            description.setText("");
            diagrama.setDescription(description);

            diagrama.getLegend().setEnabled(false);


            diagrama.setTouchEnabled(true);
            diagrama.setData(data);
            diagrama.invalidate(); // refresh the diagrama
        }catch(Exception e){
            Log.i(TAG, "error : " + e.getMessage());
        }

    }
    public void insertarPunto(MPPointD punto){
        try{

            if(listaPuntos.size() > 0){
                Entry e1 = (Entry) listaPuntos.get(0);
                if(e1.getX() == 0 && e1.getY() == 0){
                    // es el punto CERO y lo elimino
                    listaPuntos.remove(0);
                    Log.i(TAG, "punto removido: " + listaPuntos.size());
                }
            }
            Log.i(TAG, "pase inserrta1: ");
            float x = (float) Constantes.getDecimal(punto.x);
            float y = (float) Constantes.getDecimal(punto.y);
            Entry entry = new Entry(x, y);
            listaPuntos.add(entry);
            diagrama.invalidate();
        }catch (Exception e){
            Log.i(TAG, "error : " + e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_desing, container, false);
        ButterKnife.bind(this, view);
        inicializar();
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
