package com.example.carlosje.reportes_ibm;

/**
 * Created by carlosje on 1/11/2018.
 */

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class CustomAdapter extends BaseAdapter {
    Context context;
    String SerCod[],SerCodDesc[];
    int flags[];
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, String[] SerCod, String[] SerCodDesc) {
        this.context = context;
        this.SerCod = SerCod;
        this.SerCodDesc = SerCodDesc;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return SerCod.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_listview, null);
        TextView TV_SC = (TextView) view.findViewById(R.id.TV_SC);
        TextView TV_SC_Desc = (TextView) view.findViewById(R.id.TV_SC_Desc);
        TV_SC.setText(SerCod[i]);
        TV_SC_Desc.setText(SerCodDesc[i]);

        return view;
    }

}
