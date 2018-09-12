package com.example.carlosje.reportes_ibm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by carlosje on 4/25/2018.
 */

public class InventarioAdapter extends RecyclerView.Adapter<InventarioAdapter.InventarioViewHolder> {
    private List<Inventario> items;
    private Context context;

    public static class InventarioViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView reporte;
        public TextView deter;
        public TextView marca;
        public TextView modelo;
        public TextView serie;
        public TextView description;
        public TextView tipo_equipo;





        public InventarioViewHolder(View v) {
            super(v);




            reporte = (TextView) v.findViewById(R.id.tv_reporte);
            deter = (TextView) v.findViewById(R.id.tv_determinante);
            marca = (TextView) v.findViewById(R.id.tv_marca);
            modelo = (TextView) v.findViewById(R.id.tv_modelo);
            serie = (TextView) v.findViewById(R.id.tv_serie);
            description= (TextView) v.findViewById(R.id.tv_description);
            tipo_equipo= (TextView) v.findViewById(R.id.tv_tipo_equipo);

        }
    }

    public InventarioAdapter(List<Inventario> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public InventarioAdapter.InventarioViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.inventario_card, viewGroup, false);
        return new InventarioAdapter.InventarioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(InventarioAdapter.InventarioViewHolder viewHolder, int i) {

        viewHolder.reporte.setText(items.get(i).getReporte());
        viewHolder.deter.setText(items.get(i).getDeter());
        viewHolder.marca.setText(items.get(i).getMarca());
        viewHolder.modelo.setText(items.get(i).getModelo());
        viewHolder.serie.setText(items.get(i).getSerie());
        viewHolder.description.setText(items.get(i).getDescription());
        viewHolder.tipo_equipo.setText(items.get(i).getTipo_equipo());




    }
}