package com.example.carlosje.reportes_ibm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by carlosje on 10/2/2017.
 */

public class BusquedaAdapter extends RecyclerView.Adapter<BusquedaAdapter.BusquedaViewHolder>{
    private List<Busqueda> items;
    private Context context;



    public static class BusquedaViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item

        public TextView reporte;
        public TextView falla;
        public TextView solucion;
        public TextView partes1;
        public TextView partes2;
        public TextView partes3;
        public TextView partes4;
        public TextView partes5;
        public TextView partes6;
        public TextView partes7;
        public TextView partes8;
        public TextView soporte;
        public TextView fecha;
        public TextView usuario;
        private TextView comentarios;





        public BusquedaViewHolder(View v) {
            super(v);


            reporte = (TextView) v.findViewById(R.id.reporte);
            falla = (TextView) v.findViewById(R.id.falla);
            solucion = (TextView) v.findViewById(R.id.solucion);
            partes1 = (TextView) v.findViewById(R.id.partes1);
            partes2 = (TextView) v.findViewById(R.id.partes2);
            partes3 = (TextView) v.findViewById(R.id.partes3);
            partes4 = (TextView) v.findViewById(R.id.partes4);
            partes5 = (TextView) v.findViewById(R.id.partes5);
            partes6 = (TextView) v.findViewById(R.id.partes6);
            partes7 = (TextView) v.findViewById(R.id.partes7);
            partes8 = (TextView) v.findViewById(R.id.partes8);
            soporte = (TextView) v.findViewById(R.id.soporte);
            fecha = (TextView) v.findViewById(R.id.fecha);
            usuario = (TextView) v.findViewById(R.id.usuario);
            comentarios = (TextView) v.findViewById(R.id.comentarios);


        }
    }

    public BusquedaAdapter(List<Busqueda> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public BusquedaAdapter.BusquedaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.busqueda_card, viewGroup, false);
        return new BusquedaAdapter.BusquedaViewHolder(v);
    }


    @Override
    public void onBindViewHolder(BusquedaAdapter.BusquedaViewHolder viewHolder, int i) {



        viewHolder.reporte.setText(items.get(i).getReporte());
        viewHolder.falla.setText(items.get(i).getFalla());
        viewHolder.solucion.setText(items.get(i).getSolucion());
        viewHolder.partes1.setText(items.get(i).getPartes1());
        viewHolder.partes2.setText(items.get(i).getPartes2());
        viewHolder.partes3.setText(items.get(i).getPartes3());
        viewHolder.partes4.setText(items.get(i).getPartes4());
        viewHolder.partes5.setText(items.get(i).getPartes5());
        viewHolder.partes6.setText(items.get(i).getPartes6());
        viewHolder.partes7.setText(items.get(i).getPartes7());
        viewHolder.partes8.setText(items.get(i).getPartes8());
        viewHolder.soporte.setText(items.get(i).getSoporte());
        viewHolder.fecha.setText(items.get(i).getFecha());
        viewHolder.usuario.setText(items.get(i).getUsuario());
        viewHolder.comentarios.setText(items.get(i).getComentarios());


    }











}
