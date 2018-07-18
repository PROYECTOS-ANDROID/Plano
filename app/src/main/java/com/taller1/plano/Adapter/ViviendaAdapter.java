package com.taller1.plano.Adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.taller1.plano.DesingFragment;
import com.taller1.plano.R;
import com.taller1.plano.model.Vivienda;

import java.util.ArrayList;

import static com.taller1.plano.utils.Constantes.TAG;

public class ViviendaAdapter extends RecyclerView.Adapter<ViviendaAdapter.ViewHolder>{
    private ArrayList<Vivienda> viviendas;
    private FragmentActivity actividadFragment;

    public ViviendaAdapter(ArrayList<Vivienda>  viviendas, FragmentActivity actividadFragment) {
        this.viviendas= viviendas;
        this.actividadFragment = actividadFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.asignarDatos(viviendas.get(position), this.actividadFragment);
    }

    @Override
    public int getItemCount() {
        return viviendas.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtItemDuenio;
        public TextView txtItemCalle;
        public Vivienda vivienda;

        public ViewHolder(View itemView) {
            super(itemView);
            txtItemDuenio = (TextView) itemView.findViewById(R.id.iditem_duenio);
            txtItemCalle = (TextView) itemView.findViewById(R.id.iditem_calle);
        }

        public void asignarDatos(final Vivienda vivienda, final FragmentActivity actividadFragment){
            this.vivienda = vivienda;
            txtItemDuenio.setText("DUEÑO : " + vivienda.getDuenio());
            txtItemCalle.setText("DIRECCION : " + vivienda.getCalle() + " nro: " + vivienda.getNro());

            View.OnClickListener escuchador = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DesingFragment fragmento = new DesingFragment();
                    Bundle bundle = new Bundle();

                    Gson gson = new Gson();
                    bundle.putString("VIVIENDA", gson.toJson(vivienda));
                    Log.i(TAG, "onClick: pase pasar varable");
                    fragmento.setArguments(bundle);

                    actividadFragment.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contenedor, fragmento)
                            .addToBackStack(null)
                            .commit();

                    Log.i(TAG, "onClick: " + vivienda.getDuenio());
                }
            };

            txtItemDuenio.setOnClickListener(escuchador);
            txtItemCalle.setOnClickListener(escuchador);

        }
    }
}
