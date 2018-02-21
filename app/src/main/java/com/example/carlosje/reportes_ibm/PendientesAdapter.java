package com.example.carlosje.reportes_ibm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by carlosje on 2/7/2018.
 */

public class PendientesAdapter extends RecyclerView.Adapter<PendientesAdapter.PendientesViewHolder> implements View.OnClickListener {
    private List<Pendientes> items;
    private Context context;
    private View.OnClickListener listener;

    public static class PendientesViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item

        public TextView nombre;


        public PendientesViewHolder(View v) {
            super(v);

            nombre = (TextView) v.findViewById(R.id.tv_pendiente);

        }
    }

    public PendientesAdapter(List<Pendientes> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public PendientesAdapter.PendientesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.pendientes_card, viewGroup, false);
        v.setOnClickListener(this);
        return new PendientesAdapter.PendientesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PendientesAdapter.PendientesViewHolder viewHolder, int i) {

        viewHolder.nombre.setText(items.get(i).getNombre());

    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;

    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }
}