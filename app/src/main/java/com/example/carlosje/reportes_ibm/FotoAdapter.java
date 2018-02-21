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

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by carlosje on 10/23/2017.
 */

public class FotoAdapter extends RecyclerView.Adapter<FotoAdapter.FotoViewHolder> {
    private List<Foto> items;
    private Context context;

    public static class FotoViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;


        public FotoViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.photo_ATM_reci);
            nombre = (TextView) v.findViewById(R.id.notas_image);

        }
    }

    public FotoAdapter(List<Foto> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public FotoAdapter.FotoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.photo_card, viewGroup, false);
        return new FotoAdapter.FotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FotoAdapter.FotoViewHolder viewHolder, int i) {

        byte[] decodedString = Base64.decode(items.get(i).getImagen(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        viewHolder.imagen.setImageBitmap(decodedByte);

       // Picasso.with(context).load(items.get(i).getImagen()).into(viewHolder.imagen);

        //viewHolder.imagen.setImageResource(items.get(i).getImagen());
        viewHolder.nombre.setText(items.get(i).getNombre());

    }
}
