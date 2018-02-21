package com.example.carlosje.reportes_ibm;



import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.String.valueOf;


public class ATMcorrectActivity extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{


    private Location mLastLocation;


    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private TextView lb_remo_suc, lb_banco,mLatitude,mLongitude,mPosicion,TV_fecha_ini,TV_fecha_fin,TV_hr_ini,TV_hr_fin,TV_falla_atm,TV_solucion_label_1,TV_solucion_label_2,TV_solucion1,TV_solucion2;
    private ProgressBar progressBarPhoto;
    private AppBarLayout app_bar;
    private NestedScrollView nestedScrollView;
    private ScrollView sv_nuevo;
    private CardView card1, card2;
    private ImageView photo_ATM_prev, fotito, im_firma,im_test;
    private Button bt_nuevo, bt_buscar, bt_fecha;
    private LinearLayout  lay_nuevo, lay_gral,ly_partes,ly_falla,ly_sol_atm;
    private FloatingActionButton fl_btn_add_photo, fl_btn_save_atm_prev;
    private String usuarioid;
    private TextView tv_user, fecha, url_photo,TV_sc,TV_ac,TV_ac_label;
    private EditText partes_solicitadas_1,partes_cantidad_1,partes_solicitadas_2,partes_cantidad_2,partes_solicitadas_3,partes_cantidad_3,partes_solicitadas_4,partes_cantidad_4,partes_solicitadas_5,partes_cantidad_5,partes_solicitadas_6,partes_cantidad_6,partes_solicitadas_7,partes_cantidad_7,partes_solicitadas_8,partes_cantidad_8;
    private EditText partes_desc_1,partes_desc_2,partes_desc_3,partes_desc_4,partes_desc_5,partes_desc_6,partes_desc_7,partes_desc_8;
    private EditText indicaciones_soporte, soporte_nombre, notas_image, reporte, id_tpv, localidad, marca, modelo, serie, inventario, cliente_final, falla, solucion,puesto, vobo, folio, comentarios;
    private RadioButton RB_soporte_resuelve_si, RB_soporte_resuelve_no, RB_soporte_si, RB_soporte_no, RB_partes_si, RB_partes_no, RB_banco_banamex, RB_banco_bancomer, RB_banco_banorte, RB_banco_bancoppel, RB_banco_santander, RB_remo_suc_remoto, RB_remo_suc_sucursal;
    Button bt_get_firma;
    private String date;
    private String usuario;
    private String ubicacion;
    private String idkey;
    private String reporte_V;
    private String id_tpv_V;
    private String localidad_V;
    private String marca_V;
    private String modelo_V;
    private String inventario_V;
    private String serie_V;
    private String cliente_final_V;

    private String falla_V;
    private String solucion_V;
    private String vobo_V;
    private String folio_V;
    private String comentarios_V;
    private String soporte_nombre_V;
    private String indicaciones_soporte_V;
    private String RB_partes_V;
    private String RB_banco_V;
    private String RB_remo_suc_V;
    private String RB_soporte_V;
    private String RB_soporte_resuelve_V;
    private String valida_fecha;
    private String valida_IDatm;
    private String valida_reporte;
    private String pais, flag_tipo;
    private String prim_child;
    private String tipo_fecha="";
    private String hr_ini_V, hr_fin_V, fecha_ini_V, fecha_fin_V, SC_V, AC_V;

    RequestQueue requestQueue;








    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atmcorrect);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestQueue = RequestQueueSingleton.getInstance(this.getApplicationContext()).getRequestQueue();



        mLatitude = (TextView) findViewById(R.id.mLatitude);
        mLongitude = (TextView) findViewById(R.id.mLongitude);
        mPosicion = (TextView) findViewById(R.id.mPosicion);

        TV_sc= (TextView) findViewById(R.id.TV_sc);
        TV_ac= (TextView) findViewById(R.id.TV_ac);
        TV_ac_label= (TextView) findViewById(R.id.TV_ac_label);

        TV_solucion_label_1= (TextView) findViewById(R.id.TV_solucion_label_1);
        TV_solucion_label_2= (TextView) findViewById(R.id.TV_solucion_label_2);
        TV_solucion1= (TextView) findViewById(R.id.TV_solucion1);
        TV_solucion2= (TextView) findViewById(R.id.TV_solucion2);




        TV_fecha_ini= (TextView) findViewById(R.id.TV_fecha_ini);
        TV_fecha_fin= (TextView) findViewById(R.id.TV_fecha_fin);
        TV_hr_fin= (TextView) findViewById(R.id.TV_hr_fin);
        TV_hr_ini= (TextView) findViewById(R.id.TV_hr_ini);
        TV_falla_atm= (TextView) findViewById(R.id.TV_falla_atm);

        bt_get_firma = (Button) findViewById(R.id.bt_get_firma);


        pais = "México";

        usuarioid = getIntent().getStringExtra("usuario");
        flag_tipo = getIntent().getStringExtra("tipo");


        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        app_bar = (AppBarLayout) findViewById(R.id.app_bar);
        fl_btn_add_photo = (FloatingActionButton) findViewById(R.id.fl_btn_add_photo);
        fl_btn_save_atm_prev = (FloatingActionButton) findViewById(R.id.fl_btn_save_atm_prev);
        photo_ATM_prev = (ImageView) findViewById(R.id.photo_ATM_prev);
        fotito = (ImageView) findViewById(R.id.fotito);

        progressBarPhoto = (ProgressBar) findViewById(R.id.progressBarPhoto);
        sv_nuevo = (ScrollView) findViewById(R.id.sv_nuevo);
        card1 = (CardView) findViewById(R.id.card1);
        card2 = (CardView) findViewById(R.id.card2);
        tv_user = (TextView) findViewById(R.id.tv_user);
        fecha = (TextView) findViewById(R.id.fecha);
        url_photo = (TextView) findViewById(R.id.url_photo);
        lay_gral = (LinearLayout) findViewById(R.id.lay_gral);
        lay_nuevo = (LinearLayout) findViewById(R.id.lay_nuevo);
        ly_partes = (LinearLayout) findViewById(R.id.ly_partes);
        bt_nuevo = (Button) findViewById(R.id.bt_nuevo);
        bt_buscar = (Button) findViewById(R.id.bt_buscar);
        bt_buscar = (Button) findViewById(R.id.bt_buscar);
        notas_image = (EditText) findViewById(R.id.notas_image);
        reporte = (EditText) findViewById(R.id.reporte);
        id_tpv = (EditText) findViewById(R.id.id_tpv);
        localidad = (EditText) findViewById(R.id.localidad);
        marca = (EditText) findViewById(R.id.marca);
        modelo = (EditText) findViewById(R.id.modelo);
        serie = (EditText) findViewById(R.id.serie);
        inventario = (EditText) findViewById(R.id.inventario);
        cliente_final = (EditText) findViewById(R.id.cliente_final);
        falla = (EditText) findViewById(R.id.falla);
        solucion = (EditText) findViewById(R.id.solucion);
        comentarios = (EditText) findViewById(R.id.comentarios);
        folio = (EditText) findViewById(R.id.folio);
        vobo = (EditText) findViewById(R.id.vobo);
        puesto= (EditText) findViewById(R.id.puesto);
        soporte_nombre = (EditText) findViewById(R.id.soporte_nombre);
        indicaciones_soporte = (EditText) findViewById(R.id.indicaciones_soporte);
        ly_falla = (LinearLayout) findViewById(R.id.ly_falla);
        ly_sol_atm= (LinearLayout) findViewById(R.id.ly_sol_atm);

        RB_partes_si = (RadioButton) findViewById(R.id.RB_partes_si);
        RB_partes_no = (RadioButton) findViewById(R.id.RB_partes_no);
        RB_soporte_si = (RadioButton) findViewById(R.id.RB_soporte_si);
        RB_soporte_no = (RadioButton) findViewById(R.id.RB_soporte_no);
        RB_banco_banamex = (RadioButton) findViewById(R.id.RB_banco_banamex);
        RB_banco_bancomer = (RadioButton) findViewById(R.id.RB_banco_bancomer);
        RB_banco_banorte = (RadioButton) findViewById(R.id.RB_banco_banorte);
        RB_banco_bancoppel = (RadioButton) findViewById(R.id.RB_banco_bancoppel);
        RB_banco_santander = (RadioButton) findViewById(R.id.RB_banco_santander);
        RB_remo_suc_remoto = (RadioButton) findViewById(R.id.RB_remo_suc_remoto);
        RB_remo_suc_sucursal = (RadioButton) findViewById(R.id.RB_remo_suc_sucursal);
        RB_soporte_resuelve_si = (RadioButton) findViewById(R.id.RB_soporte_resuelve_si);
        RB_soporte_resuelve_no = (RadioButton) findViewById(R.id.RB_soporte_resuelve_no);

        lb_remo_suc = (TextView) findViewById(R.id.lb_remo_suc);
        lb_banco = (TextView) findViewById(R.id.lb_banco);

        partes_solicitadas_1 = (EditText) findViewById(R.id.partes_solicitadas_1);
        partes_cantidad_1= (EditText) findViewById(R.id.partes_cantidad_1);
        partes_solicitadas_2 = (EditText) findViewById(R.id.partes_solicitadas_2);
        partes_cantidad_2= (EditText) findViewById(R.id.partes_cantidad_2);
        partes_solicitadas_3 = (EditText) findViewById(R.id.partes_solicitadas_3);
        partes_cantidad_3= (EditText) findViewById(R.id.partes_cantidad_3);
        partes_solicitadas_4 = (EditText) findViewById(R.id.partes_solicitadas_4);
        partes_cantidad_4= (EditText) findViewById(R.id.partes_cantidad_4);
        partes_solicitadas_5 = (EditText) findViewById(R.id.partes_solicitadas_5);
        partes_cantidad_5= (EditText) findViewById(R.id.partes_cantidad_5);
        partes_solicitadas_6 = (EditText) findViewById(R.id.partes_solicitadas_6);
        partes_cantidad_6= (EditText) findViewById(R.id.partes_cantidad_6);
        partes_solicitadas_7 = (EditText) findViewById(R.id.partes_solicitadas_7);
        partes_cantidad_7= (EditText) findViewById(R.id.partes_cantidad_7);
        partes_solicitadas_8 = (EditText) findViewById(R.id.partes_solicitadas_8);
        partes_cantidad_8= (EditText) findViewById(R.id.partes_cantidad_8);
        partes_desc_1= (EditText) findViewById(R.id.partes_desc_1);
        partes_desc_2= (EditText) findViewById(R.id.partes_desc_2);
        partes_desc_3= (EditText) findViewById(R.id.partes_desc_3);
        partes_desc_4= (EditText) findViewById(R.id.partes_desc_4);
        partes_desc_5= (EditText) findViewById(R.id.partes_desc_5);
        partes_desc_6= (EditText) findViewById(R.id.partes_desc_6);
        partes_desc_7= (EditText) findViewById(R.id.partes_desc_7);
        partes_desc_8= (EditText) findViewById(R.id.partes_desc_8);



        ly_partes.setVisibility(View.GONE);


        if (flag_tipo.equals("LOGO")) {
            prim_child = "LOGO";
        } else {
            prim_child = "ATMs";
        }


        tv_user.setText(usuarioid);

        TV_ac.setVisibility(View.GONE);
        TV_ac_label.setVisibility(View.GONE);



        TV_solucion_label_2.setVisibility(View.GONE);
        TV_solucion2.setVisibility(View.GONE);


/////////VALIDA SI ES LOGO O ATM

        if (flag_tipo.equals("LOGO")) {
            inventario.setVisibility(View.GONE);
            RB_banco_banamex.setVisibility(View.GONE);
            RB_banco_bancomer.setVisibility(View.GONE);
            RB_banco_banorte.setVisibility(View.GONE);
            RB_banco_bancoppel.setVisibility(View.GONE);
            RB_banco_santander.setVisibility(View.GONE);
            RB_remo_suc_remoto.setVisibility(View.GONE);
            RB_remo_suc_sucursal.setVisibility(View.GONE);
            RB_remo_suc_remoto.setVisibility(View.GONE);
            RB_remo_suc_sucursal.setVisibility(View.GONE);
            id_tpv.setHint("Serie");
            lb_remo_suc.setVisibility(View.GONE);
            lb_banco.setVisibility(View.GONE);
            id_tpv.setVisibility(View.GONE);
            ly_falla.setVisibility(View.GONE);
            TV_falla_atm.setVisibility(View.GONE);
            ly_sol_atm.setVisibility(View.GONE);

        } else {
            falla.setVisibility(View.GONE);


        }


        fl_btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamarIntent();

            }
        });


//////////boton salvar primero valida que esten llenos los campos

        fl_btn_save_atm_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                falla_V="";
                solucion_V="";


                if (fecha.getText().toString().equals("Fecha")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Favor seleccionar fecha de ateción";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;


                }


                if (reporte.getText().toString().equals("")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Favor documentar el reporte";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;


                }


                if (flag_tipo.equals("ATM")) {

                    if (id_tpv.getText().toString().equals("")) {

                        Context context = getApplicationContext();
                        CharSequence text = "Favor documentar el ID de ATM";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        return;


                    }

                    if (!RB_banco_banamex.isChecked() && !RB_banco_bancomer.isChecked() && !RB_banco_banorte.isChecked() && !RB_banco_bancoppel.isChecked() && !RB_banco_santander.isChecked()) {
                        int duration = Toast.LENGTH_SHORT;
                        Context context = getApplicationContext();
                        CharSequence text = "Favor seleccionar Banco";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        return;
                    }


                    if (!RB_remo_suc_remoto.isChecked() && !RB_remo_suc_sucursal.isChecked()) {
                        int duration = Toast.LENGTH_SHORT;
                        Context context = getApplicationContext();
                        CharSequence text = "Favor seleccionar Remoto / Sucursal";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        return;
                    }

                    if (inventario.getText().toString().equals("")) {

                        Context context = getApplicationContext();
                        CharSequence text = "Favor documentar número de inventario";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        return;


                    }


                } else {

                    if (serie.getText().toString().equals("")) {

                        Context context = getApplicationContext();
                        CharSequence text = "Favor documentar serie del equipo";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        return;


                    }


                }


                if (localidad.getText().toString().equals("")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Favor documentar localidad";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;


                }


                if (marca.getText().toString().equals("")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Favor documentar marca";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;


                }


                if (modelo.getText().toString().equals("")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Favor documentar modelo";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;


                }

                if (serie.getText().toString().equals("")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Favor documentar serie";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;


                }


                if (cliente_final.getText().toString().equals("")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Favor documentar cliente final";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;


                }


                if (!RB_partes_si.isChecked() && !RB_partes_no.isChecked()) {
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getApplicationContext();
                    CharSequence text = "Favor seleccionar si utilizó partes";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;
                }


                if (RB_partes_si.isChecked()) {

                    if (partes_solicitadas_1.getText().toString().equals("")) {

                        Context context = getApplicationContext();
                        CharSequence text = "Favor documentar las partes solicitadas";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        return;


                    }else{
                        if(partes_cantidad_1.getText().toString().equals("")){
                            Context context = getApplicationContext();
                            CharSequence text = "Favor documentar la cantidad de partes (parte 1)";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            return;
                        }

                        if(partes_desc_1.getText().toString().equals("")){
                            Context context = getApplicationContext();
                            CharSequence text = "Favor documentar la descripción de partes (parte 1)";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            return;
                        }
                    }


                    if(partes_solicitadas_2.getText().toString().equals("")){
                    }else {
                        if (partes_cantidad_2.getText().toString().equals("")) {
                            Context context = getApplicationContext();
                            CharSequence text = "Favor documentar la cantidad de partes (parte 2)";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            return;
                        }
                        if(partes_desc_2.getText().toString().equals("")){
                            Context context = getApplicationContext();
                            CharSequence text = "Favor documentar la descripción de partes (parte 2)";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            return;
                        }

                    }

                    if(partes_solicitadas_3.getText().toString().equals("")){
                    }else {
                        if (partes_cantidad_3.getText().toString().equals("")) {
                            Context context = getApplicationContext();
                            CharSequence text = "Favor documentar la cantidad de partes (parte 3)";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            return;
                        }

                        if(partes_desc_3.getText().toString().equals("")){
                            Context context = getApplicationContext();
                            CharSequence text = "Favor documentar la descripción de partes (parte 3)";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            return;
                        }
                    }

                    if(partes_solicitadas_4.getText().toString().equals("")){
                    }else {
                        if (partes_cantidad_4.getText().toString().equals("")) {
                            Context context = getApplicationContext();
                            CharSequence text = "Favor documentar la cantidad de partes (parte 4)";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            return;
                        }
                        if(partes_desc_4.getText().toString().equals("")){
                            Context context = getApplicationContext();
                            CharSequence text = "Favor documentar la descripción de partes (parte 4)";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            return;
                        }
                    }


                    if(partes_solicitadas_5.getText().toString().equals("")){
                    }else {
                        if (partes_cantidad_5.getText().toString().equals("")) {
                            Context context = getApplicationContext();
                            CharSequence text = "Favor documentar la cantidad de partes (parte 5)";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            return;
                        }
                        if(partes_desc_5.getText().toString().equals("")){
                            Context context = getApplicationContext();
                            CharSequence text = "Favor documentar la descripción de partes (parte 5)";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            return;
                        }
                    }

                    if(partes_solicitadas_6.getText().toString().equals("")){
                    }else {
                        if (partes_cantidad_6.getText().toString().equals("")) {
                            Context context = getApplicationContext();
                            CharSequence text = "Favor documentar la cantidad de partes (parte 6)";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            return;
                        }
                        if(partes_desc_6.getText().toString().equals("")){
                            Context context = getApplicationContext();
                            CharSequence text = "Favor documentar la descripción de partes (parte 6)";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            return;
                        }
                    }

                    if(partes_solicitadas_7.getText().toString().equals("")){
                    }else {
                        if (partes_cantidad_7.getText().toString().equals("")) {
                            Context context = getApplicationContext();
                            CharSequence text = "Favor documentar la cantidad de partes (parte 7)";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            return;
                        }
                        if(partes_desc_7.getText().toString().equals("")){
                            Context context = getApplicationContext();
                            CharSequence text = "Favor documentar la descripción de partes (parte 7)";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            return;
                        }
                    }

                    if(partes_solicitadas_8.getText().toString().equals("")){
                    }else {
                        if (partes_cantidad_8.getText().toString().equals("")) {
                            Context context = getApplicationContext();
                            CharSequence text = "Favor documentar la cantidad de partes (parte 8)";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            return;
                        }
                        if(partes_desc_8.getText().toString().equals("")){
                            Context context = getApplicationContext();
                            CharSequence text = "Favor documentar la descripción de partes (parte 8)";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            return;
                        }
                    }



                }


                if (flag_tipo.equals("LOGO")) {
                    falla_V = falla.getText().toString();


                } else {

                    falla_V = TV_falla_atm.getText().toString();


                    if (falla_V.equals("OTRA")){

                        falla_V = falla_V + ": "+ falla.getText().toString();

                    }

                }

                if (flag_tipo.equals("LOGO")) {

                    solucion_V = solucion.getText().toString();

                } else {

                    solucion_V = TV_solucion1.getText().toString();


                    if (solucion_V.equals("OTRA")){


                        solucion_V = solucion_V + ": "+ solucion.getText().toString();

                    }else {
                        if(TV_solucion2.getText().toString().equals("")){

                            Context context = getApplicationContext();
                            CharSequence text = "Favor documentar componente";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            return;

                        }else {


                        solucion_V = TV_solucion1.getText().toString()+ " " + TV_solucion2.getText().toString();
                        }
                    }

                }


                if (falla_V.equals("OTRA: ")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Favor documentar falla";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;


                }






                if (solucion_V.equals("OTRA: ")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Favor documentar solucion";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;


                }

                if (folio.getText().toString().equals("")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Favor documentar folio";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;


                }

                if (vobo.getText().toString().equals("")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Favor documentar vobo";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;


                }

                if (puesto.getText().toString().equals("")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Favor documentar puesto";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;


                }


                if (RB_soporte_si.isChecked()) {

                    if (soporte_nombre.getText().toString().equals("")) {

                        Context context = getApplicationContext();
                        CharSequence text = "Favor documentar nombre soporte 2do nivel";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        return;


                    }

                    if (indicaciones_soporte.getText().toString().equals("")) {

                        Context context = getApplicationContext();
                        CharSequence text = "Favor documentar  indicaciones de soporte";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        return;


                    }


                    if (!RB_soporte_resuelve_no.isChecked() && !RB_soporte_resuelve_si.isChecked()) {
                        int duration = Toast.LENGTH_SHORT;
                        Context context = getApplicationContext();
                        CharSequence text = "Favor seleccionar si resolvió con ayuda de soporte";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        return;
                    }


                }

                if (!RB_soporte_no.isChecked() && !RB_soporte_si.isChecked()) {
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getApplicationContext();
                    CharSequence text = "Favor seleccionar si utilizó ayuda de soporte";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;
                }



                if (TV_sc.getText().toString().equals("")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Favor seleccionar SC";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;


                }

                if (TV_ac.getText().toString().equals("")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Favor seleccionar AC";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;


                }


                if (TV_fecha_ini.getText().toString().equals("Fecha de llegada")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Favor seleccionar Fecha de llegada";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;


                }

                if (TV_fecha_fin.getText().toString().equals("Fecha de terminación")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Favor seleccionar Fecha de terminación";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;


                }


                if (TV_hr_ini.getText().toString().equals("Hora de llegada")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Favor seleccionar Hora de llegada";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;


                }

                if (TV_hr_fin.getText().toString().equals("Hora de terminación")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Favor seleccionar Hora de terminación";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;


                }



                muestraDialogo();

            }
        });


/////TODO Geolocalizacion

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .build();





    }








    ///abrir calendario

    public void showDatePickerDialog_fecha_fin(View v) {

        tipo_fecha="fecha_fin";
        DialogFragment newFragment = new DatePick();
        Bundle args = new Bundle();
        args.putInt("num", 1);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    public void showDatePickerDialog_fecha_ini(View v) {

        tipo_fecha="fecha_ini";
        DialogFragment newFragment = new DatePick();
        Bundle args = new Bundle();
        args.putInt("num", 1);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }


    public void showDatePickerDialog_Head(View v) {

        tipo_fecha="head";
        DialogFragment newFragment = new DatePick();
        Bundle args = new Bundle();
        args.putInt("num", 1);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

///recoge datos calendario
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        String m_v;
        String d_v;

        m_v = valueOf(monthOfYear + 1);
        d_v = valueOf(dayOfMonth);

        if (m_v.length() == 1) {
            m_v = "0" + m_v;
        }

        if (d_v.length() == 1) {
            d_v = "0" + d_v;
        }

        if (tipo_fecha.equals("head")){
            fecha.setText(valueOf(year)+ "/"+ m_v + "/" + d_v  );
        }

        if (tipo_fecha.equals("fecha_ini")){
            TV_fecha_ini.setText(valueOf(year)+ "/"+ m_v + "/" + d_v  );
        }

        if (tipo_fecha.equals("fecha_fin")){
            TV_fecha_fin.setText(valueOf(year)+ "/"+ m_v + "/" + d_v  );
        }



    }


    ///abrir reloj


    public void showTimePickerDialog_Head_hr_ini(View v) {



        DialogFragment newFragment = new TimePick();
        Bundle args = new Bundle();
        args.putInt("num", 1);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }

    public void showTimePickerDialog_Head_hr_fin(View v) {


        DialogFragment newFragment = new TimePick();
        Bundle args = new Bundle();
        args.putInt("num", 2);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }

    ///recoge datos hora






    ///abrir dialogo


    public void muestraDialogo() {
        AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
        myBuild.setTitle("Importante");
        myBuild.setMessage("¿Guardar Checklist? Al confirmar se cargaran los datos documentados.");
        myBuild.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveATM_corr();
            }
        });


        myBuild.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });

        AlertDialog dialog = myBuild.create();
        dialog.show();


    }


    public void muestraDialogo2() {
        AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
        myBuild.setTitle("Importante");
        myBuild.setMessage("¿Salir del formulario");
        myBuild.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();

            }
        });


        myBuild.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        AlertDialog dialog = myBuild.create();
        dialog.show();


    }






    private static final int PICK_IMAGE_ID = 234;
    private void llamarIntent() {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED){
            if (requestCode == PICK_IMAGE_ID) {
                Bitmap image = ImagePicker.getImageFromResult(this, resultCode, data);
                // TODO use bitmap
                ImageView img = (ImageView) findViewById(R.id.photo_ATM_prev);

                img.setImageBitmap(image);

                flag_foto = 1;

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            }
        }


        if (requestCode == 1) {

            if(resultCode == Activity.RESULT_OK){
                String SC=data.getStringExtra("SC_Sel");
                String AC=data.getStringExtra("AC_Sel");
                TV_sc.setText(SC);
                TV_ac.setText(AC);

                if(TV_sc.getText().equals("")){
                    TV_ac_label.setVisibility(View.GONE);
                    TV_ac.setVisibility(View.GONE);
                    TV_ac.setText("");
                }else{
                    TV_ac_label.setVisibility(View.VISIBLE);
                    TV_ac.setVisibility(View.VISIBLE);
                }


            }
            if (resultCode == Activity.RESULT_CANCELED) {

                if(TV_sc.getText().equals("")){
                    TV_ac_label.setVisibility(View.GONE);
                    TV_ac.setVisibility(View.GONE);
                    TV_ac.setText("");
                }else{
                    TV_ac_label.setVisibility(View.VISIBLE);
                    TV_ac.setVisibility(View.VISIBLE);
                }

            }
        }

        if (requestCode == 2) {

            if(resultCode == Activity.RESULT_OK) {
                falla.setText("");
                falla.setVisibility(View.GONE);
                String falla_sel = data.getStringExtra("falla_Sel");
                TV_falla_atm.setVisibility(View.VISIBLE);
                TV_falla_atm.setText(falla_sel);

                if (falla_sel.equals("OTRA")){
                    falla.setVisibility(View.VISIBLE);
                }
            }

            ////visible campo falla otro




        }


        if (requestCode == 3) {

            if(resultCode == Activity.RESULT_OK) {
                solucion.setText("");
                solucion.setVisibility(View.GONE);
                String act_sel = data.getStringExtra("Act_sel");
                TV_solucion_label_2.setVisibility(View.VISIBLE);
                TV_solucion1.setText(act_sel);

                if (act_sel.equals("OTRA")){
                    solucion.setVisibility(View.VISIBLE);
                    TV_solucion_label_2.setVisibility(View.GONE);
                    TV_solucion2.setText("");
                    TV_solucion2.setVisibility(View.GONE);

                }else {
                    TV_solucion_label_2.setVisibility(View.VISIBLE);
                    TV_solucion2.setText("");
                    TV_solucion2.setVisibility(View.VISIBLE);
                }
            }

            ////visible campo falla otro




        }

        if (requestCode == 4) {

            if(resultCode == Activity.RESULT_OK) {

                String comp_sel = data.getStringExtra("Comp_sel");

                TV_solucion2.setText(comp_sel);


            }

            ////visible campo falla otro




        }



    }





    public void Solucion_act_1(View view) {


        Intent i = new Intent(this, SCActivity.class);
        i.putExtra("tipo","act");
        i.putExtra("Act_sel",TV_solucion1.getText());
        startActivityForResult(i, 3);


    }

    public void Solucion_act_2(View view) {


        Intent i = new Intent(this, SCActivity.class);
        i.putExtra("tipo","comp");
        i.putExtra("Act_sel",TV_solucion1.getText());
        startActivityForResult(i, 4);


    }




    public void falla_act(View view) {

        Intent i = new Intent(this, SCActivity.class);
        i.putExtra("tipo","falla");
        startActivityForResult(i, 2);


    }

    public void SC_act(View view) {


        Intent i = new Intent(this, SCActivity.class);
        i.putExtra("tipo","SC");
        i.putExtra("SC_Sel",TV_sc.getText());
        startActivityForResult(i, 1);


    }

    public void AC_act(View view) {

        Intent i = new Intent(this, SCActivity.class);
        i.putExtra("tipo","AC");
        i.putExtra("SC_Sel",TV_sc.getText());
        startActivityForResult(i, 1);


    }


    private String idATM;







    private void saveATM_corr() {



        ///////////////////////////////////////////////////Asignar valores a variables


        if (flag_tipo.equals("LOGO")) {
            idATM = serie.getText().toString();

        } else {
            idATM = id_tpv.getText().toString();
        }




        reporte_V = reporte.getText().toString();
        id_tpv_V = id_tpv.getText().toString();
        localidad_V = localidad.getText().toString();
        marca_V = marca.getText().toString();
        modelo_V = modelo.getText().toString();

        serie_V = serie.getText().toString();
        cliente_final_V = cliente_final.getText().toString();
        inventario_V = inventario.getText().toString();
        soporte_nombre_V = soporte_nombre.getText().toString();

        vobo_V = vobo.getText().toString();
        folio_V = folio.getText().toString();
        comentarios_V = comentarios.getText().toString();
        indicaciones_soporte_V = indicaciones_soporte.getText().toString();

        hr_ini_V=TV_hr_ini.getText().toString();
        hr_fin_V=TV_hr_fin.getText().toString();
        fecha_ini_V=TV_fecha_ini.getText().toString();
        fecha_fin_V=TV_fecha_fin.getText().toString();
        SC_V=TV_sc.getText().toString();
        AC_V=TV_ac.getText().toString();






        if (RB_partes_si.isChecked()) {
            RB_partes_V = RB_partes_si.getText().toString();
        }
        if (RB_partes_no.isChecked()) {
            RB_partes_V = RB_partes_no.getText().toString();
        }

        if (RB_banco_banamex.isChecked()) {
            RB_banco_V = RB_banco_banamex.getText().toString();
        }
        if (RB_banco_bancomer.isChecked()) {
            RB_banco_V = RB_banco_bancomer.getText().toString();
        }
        if (RB_banco_banorte.isChecked()) {
            RB_banco_V = RB_banco_banorte.getText().toString();
        }
        if (RB_banco_bancoppel.isChecked()) {
            RB_banco_V = RB_banco_bancoppel.getText().toString();
        }
        if (RB_banco_santander.isChecked()) {
            RB_banco_V = RB_banco_santander.getText().toString();
        }
        if (RB_remo_suc_remoto.isChecked()) {
            RB_remo_suc_V = RB_remo_suc_remoto.getText().toString();
        }
        if (RB_remo_suc_sucursal.isChecked()) {
            RB_remo_suc_V = RB_remo_suc_sucursal.getText().toString();
        }

        if (RB_soporte_si.isChecked()) {
            RB_soporte_V = RB_soporte_si.getText().toString();
        }

        if (RB_soporte_no.isChecked()) {
            RB_soporte_V = RB_soporte_no.getText().toString();
        }

        if (RB_soporte_resuelve_si.isChecked()) {
            RB_soporte_resuelve_V = RB_soporte_resuelve_si.getText().toString();
        }

        if (RB_soporte_resuelve_no.isChecked()) {
            RB_soporte_resuelve_V = RB_soporte_resuelve_no.getText().toString();
        }




        ////////////////////////////valida datos obligatorios


        Post post = new Post();
        Map<String, Object> postValues = post.toMap();




        ////////////////////////////TODO////////Añade a Cloudant




       //String url = "https://6620c8ed-e3c8-49b5-8420-fa3cb622c51e-bluemix.cloudant.com/correctivos";

        String url=getResources().getString(R.string.urlCloudant)+"/correctivos";


        JsonObjectRequest jar1 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(postValues), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    REV = jsonObject.getString("rev");
                } catch (JSONException e) {


                    try {

                        /// si no hay conexcion o hay error guarda el post en file txt

                        String comas=  "\"";

                        String guardaPost = "{" +
                                comas+"id_ATM"+comas+":"+comas+idATM+comas+","+
                                comas+"Fecha"+comas+":"+comas+fecha.getText().toString()+comas+","+
                                comas+"marca"+comas+":"+comas+marca_V+comas+","+
                                comas+"modelo"+comas+":"+comas+modelo_V+comas+","+
                                comas+"serie"+comas+":"+comas+serie_V+comas+","+
                                comas+"inventario"+comas+":"+comas+inventario_V+comas+","+
                                comas+"cliente_final"+comas+":"+comas+cliente_final_V+comas+","+
                                comas+"solicita_partes"+comas+":"+comas+RB_partes_V+comas+","+
                                comas+"reporte"+comas+":"+comas+reporte_V+comas+","+
                                comas+"localidad"+comas+":"+comas+localidad_V+comas+","+
                                comas+"partes_solicitadas_1"+comas+":"+comas+partes_solicitadas_1.getText().toString()+comas+","+
                                comas+"cantidad_partes_1"+comas+":"+comas+partes_cantidad_1.getText().toString()+comas+","+
                                comas+"partes_solicitadas_2"+comas+":"+comas+partes_solicitadas_2.getText().toString()+comas+","+
                                comas+"cantidad_partes_2"+comas+":"+comas+partes_cantidad_2.getText().toString()+comas+","+
                                comas+"partes_solicitadas_3"+comas+":"+comas+partes_solicitadas_3.getText().toString()+comas+","+
                                comas+"cantidad_partes_3"+comas+":"+comas+partes_cantidad_3.getText().toString()+comas+","+
                                comas+"partes_solicitadas_4"+comas+":"+comas+partes_solicitadas_4.getText().toString()+comas+","+
                                comas+"cantidad_partes_4"+comas+":"+comas+partes_cantidad_4.getText().toString()+comas+","+
                                comas+"partes_solicitadas_5"+comas+":"+comas+partes_solicitadas_5.getText().toString()+comas+","+
                                comas+"cantidad_partes_5"+comas+":"+comas+partes_cantidad_5.getText().toString()+comas+","+
                                comas+"partes_solicitadas_6"+comas+":"+comas+partes_solicitadas_6.getText().toString()+comas+","+
                                comas+"cantidad_partes_6"+comas+":"+comas+partes_cantidad_6.getText().toString()+comas+","+
                                comas+"partes_solicitadas_7"+comas+":"+comas+partes_solicitadas_7.getText().toString()+comas+","+
                                comas+"cantidad_partes_7"+comas+":"+comas+partes_cantidad_7.getText().toString()+comas+","+
                                comas+"partes_solicitadas_8"+comas+":"+comas+partes_solicitadas_8.getText().toString()+comas+","+
                                comas+"cantidad_partes_8"+comas+":"+comas+partes_cantidad_8.getText().toString()+comas+","+
                                comas+"descripcion_partes_1"+comas+":"+comas+partes_desc_1.getText().toString()+comas+","+
                                comas+"descripcion_partes_2"+comas+":"+comas+partes_desc_2.getText().toString()+comas+","+
                                comas+"descripcion_partes_3"+comas+":"+comas+partes_desc_3.getText().toString()+comas+","+
                                comas+"descripcion_partes_4"+comas+":"+comas+partes_desc_4.getText().toString()+comas+","+
                                comas+"descripcion_partes_5"+comas+":"+comas+partes_desc_5.getText().toString()+comas+","+
                                comas+"descripcion_partes_6"+comas+":"+comas+partes_desc_6.getText().toString()+comas+","+
                                comas+"descripcion_partes_7"+comas+":"+comas+partes_desc_7.getText().toString()+comas+","+
                                comas+"descripcion_partes_8"+comas+":"+comas+partes_desc_8.getText().toString()+comas+","+
                                comas+"falla"+comas+":"+comas+falla_V+comas+","+
                                comas+"solucion"+comas+":"+comas+solucion_V+comas+","+
                                comas+"Vobo"+comas+":"+comas+vobo_V+comas+","+
                                comas+"puesto"+comas+":"+comas+puesto.getText().toString()+comas+","+
                                comas+"folio"+comas+":"+comas+folio_V+comas+","+
                                comas+"comentarios"+comas+":"+comas+comentarios_V+comas+","+
                                comas+"Usuario"+comas+":"+comas+usuarioid+comas+","+
                                comas+"banco"+comas+":"+comas+RB_banco_V+comas+","+
                                comas+"remoto_sucursal"+comas+":"+comas+RB_remo_suc_V+comas+","+
                                comas+"Recibe_ayuda_de_soporte"+comas+":"+comas+RB_soporte_V+comas+","+
                                comas+"nombre_soporte"+comas+":"+comas+soporte_nombre_V+comas+","+
                                comas+"indicaciones_de_soporte"+comas+":"+comas+indicaciones_soporte_V+comas+","+
                                comas+"Resuelve_problema_ayuda_de_soporte"+comas+":"+comas+RB_soporte_resuelve_V+comas+","+
                                comas+"pais"+comas+":"+comas+"Mexico"+comas+","+
                                comas+"equipo"+comas+":"+comas+flag_tipo+comas+","+
                                comas+"tipo_reporte"+comas+":"+comas+"Correctivo"+comas+","+
                                comas+"latitud"+comas+":"+comas+mLatitude.getText().toString()+comas+","+
                                comas+"longitud"+comas+":"+comas+mLongitude.getText().toString()+comas+","+
                                comas+"hr_ini"+comas+":"+comas+hr_ini_V+comas+","+
                                comas+"hr_fin"+comas+":"+comas+hr_fin_V+comas+","+
                                comas+"fecha_ini"+comas+":"+comas+fecha_ini_V+comas+","+
                                comas+"fecha_fin"+comas+":"+comas+fecha_fin_V+comas+","+
                                comas+"SC"+comas+":"+comas+SC_V+comas+","+
                                comas+"AC"+comas+":"+comas+AC_V+comas+


                                "}";

                        ////Genera JSON de variables




                        Long tsLong = System.currentTimeMillis()/1000;
                        String ts = tsLong.toString();

                        String filename= "CO_"+ts+"_"+reporte_V+".txt";

                        OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(filename, Activity.MODE_PRIVATE));
                        archivo.write(guardaPost);
                        archivo.flush();
                        archivo.close();


                        Context context = getApplicationContext();
                        CharSequence text = "Problema al subir file, almacenado en pendientes. " + filename;
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();



                    } catch (IOException ee) {
                    }




















                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Json Error Res: ", "" + error);
            }
        });
        requestQueue.add(jar1);

        jar1.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }





    private String key = "";



    public class Post {



        public Post() {

        }


        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();

            result.put("id_ATM", idATM);
            result.put("Fecha", fecha.getText().toString());
            result.put("marca", marca_V);
            result.put("modelo", modelo_V);
            result.put("serie", serie_V);
            result.put("inventario", inventario_V);
            result.put("cliente_final", cliente_final_V);
            result.put("solicita_partes", RB_partes_V);
            result.put("reporte", reporte_V);
            result.put("localidad", localidad_V);
            result.put("partes_solicitadas_1", partes_solicitadas_1.getText().toString());
            result.put("cantidad_partes_1", partes_cantidad_1.getText().toString());
            result.put("partes_solicitadas_2", partes_solicitadas_2.getText().toString());
            result.put("cantidad_partes_2", partes_cantidad_2.getText().toString());
            result.put("partes_solicitadas_3", partes_solicitadas_3.getText().toString());
            result.put("cantidad_partes_3", partes_cantidad_3.getText().toString());
            result.put("partes_solicitadas_4", partes_solicitadas_4.getText().toString());
            result.put("cantidad_partes_4", partes_cantidad_4.getText().toString());
            result.put("partes_solicitadas_5", partes_solicitadas_5.getText().toString());
            result.put("cantidad_partes_5", partes_cantidad_5.getText().toString());
            result.put("partes_solicitadas_6", partes_solicitadas_6.getText().toString());
            result.put("cantidad_partes_6", partes_cantidad_6.getText().toString());
            result.put("partes_solicitadas_7", partes_solicitadas_7.getText().toString());
            result.put("cantidad_partes_7", partes_cantidad_7.getText().toString());
            result.put("partes_solicitadas_8", partes_solicitadas_8.getText().toString());
            result.put("cantidad_partes_8", partes_cantidad_8.getText().toString());
            result.put("descripcion_partes_1", partes_desc_1.getText().toString());
            result.put("descripcion_partes_2", partes_desc_2.getText().toString());
            result.put("descripcion_partes_3", partes_desc_3.getText().toString());
            result.put("descripcion_partes_4", partes_desc_4.getText().toString());
            result.put("descripcion_partes_5", partes_desc_5.getText().toString());
            result.put("descripcion_partes_6", partes_desc_6.getText().toString());
            result.put("descripcion_partes_7", partes_desc_7.getText().toString());
            result.put("descripcion_partes_8", partes_desc_8.getText().toString());
            result.put("falla", falla_V);
            result.put("solucion", solucion_V);
            result.put("Vobo", vobo_V);
            result.put("puesto", puesto.getText().toString());
            result.put("folio", folio_V);
            result.put("comentarios", comentarios_V);
            result.put("Usuario", usuarioid);
            result.put("banco", RB_banco_V);
            result.put("remoto_sucursal", RB_remo_suc_V);
            result.put("Recibe_ayuda_de_soporte", RB_soporte_V);
            result.put("nombre_soporte", soporte_nombre_V);
            result.put("indicaciones_de_soporte", indicaciones_soporte_V);
            result.put("Resuelve_problema_ayuda_de_soporte", RB_soporte_resuelve_V);
            result.put("pais", "Mexico");
            result.put("equipo", flag_tipo);
            result.put("tipo_reporte", "Correctivo");
            result.put("latitud", mLatitude.getText().toString());
            result.put("longitud", mLongitude.getText().toString());
            result.put("hr_ini",hr_ini_V);
            result.put("hr_fin",hr_fin_V);
            result.put("fecha_ini",fecha_ini_V);
            result.put("fecha_fin",fecha_fin_V);
            result.put("SC",SC_V);
            result.put("AC",AC_V);





            Context context = getApplicationContext();
            CharSequence text = "Checklist Almacenado";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return result;
        }


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {

            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {

        muestraDialogo2();
    }


    private String nota_recilcer;
    private ImageView imagen_photo;
    private int i_photos;


 ///ver ocultar partes

   public void ver_partes(View view){
       ly_partes.setVisibility(View.VISIBLE);
   }

    public void ocultar_partes(View view){
        ly_partes.setVisibility(View.GONE);
        partes_solicitadas_1.setText("");
        partes_cantidad_1.setText("");
    }



    private String encodedImage;

    private String ID_atm_photos;

    public void subir_imagen(View view) {



        i_photos = i_photos + 1;


        // valida q reporte y ID existan



        if (flag_tipo.equals("LOGO")) {
            idATM = serie.getText().toString();
            ID_atm_photos = serie.getText().toString();

        } else {
            idATM = id_tpv.getText().toString();
            ID_atm_photos = id_tpv.getText().toString();
        }


        if (reporte.getText().toString().equals("")) {

            Context context = getApplicationContext();
            CharSequence text = "Favor documentar el reporte";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;


        }

        if (flag_tipo.equals("ATM")) {

            if (id_tpv.getText().toString().equals("")) {

                Context context = getApplicationContext();
                CharSequence text = "Favor documentar el ID de ATM";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                return;


            }
        } else {

            if (serie.getText().toString().equals("")) {

                Context context = getApplicationContext();
                CharSequence text = "Favor documentar serie del equipo";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                return;


            }


        }


        if (flag_foto == 0) {

            Context context = getApplicationContext();
            CharSequence text = "Favor capturar la fotografía";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;


        }

        if (notas_image.getText().toString().equals("")) {

            Context context = getApplicationContext();
            CharSequence text = "Favor documentar las Observaciones";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;


        }



        progressBarPhoto.setVisibility(View.VISIBLE);


        if (flag_tipo.equals("LOGO")) {
            idATM = serie.getText().toString();
            ID_atm_photos = serie.getText().toString();

        } else {
            idATM = id_tpv.getText().toString();
            ID_atm_photos = id_tpv.getText().toString();
        }


        notas_post = notas_image.getText().toString();

        i_photos_post = "" + i_photos;
        reporte_V = reporte.getText().toString();
        id_tpv_V = id_tpv.getText().toString();


        ATMcorrectActivity.Post_2 post_2 = new ATMcorrectActivity.Post_2();
        Map<String, Object> post_2Values = post_2.toMap();

        /////////////////////////////TODO////////Añade a Cloudant


       // String url = "https://6620c8ed-e3c8-49b5-8420-fa3cb622c51e-bluemix.cloudant.com/ima_corr";

        String url=getResources().getString(R.string.urlCloudant)+"/ima_corr";

        JsonObjectRequest jar1 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(post_2Values), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    añadir_im_recicler();
                    JSONObject jsonObject = new JSONObject(response.toString());
                    REV = jsonObject.getString("rev");
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Json Error Res: ", "" + error);


                try {

                    /// si no hay conexcion o hay error guarda el post en file txt

                    String comas=  "\"";

                    String guardaPost = "{" +
                            comas+"imagen"+comas+":"+comas+encodedImage+comas+","+
                            comas+"observaciones"+comas+":"+comas+notas_post+comas+","+
                            comas+"id_img"+comas+":"+comas+i_photos_post+comas+","+
                            comas+"id_ATM"+comas+":"+comas+id_tpv_V+comas+","+
                            comas+"serie"+comas+":"+comas+serie.getText().toString()+comas+","+
                            comas+"id_ATM"+comas+":"+comas+idATM+comas+","+
                            comas+"reporte"+comas+":"+comas+reporte.getText().toString()+comas+","+
                            comas+"equipo"+comas+":"+comas+flag_tipo+comas+","+
                            comas+"tipo_reporte"+comas+":"+comas+"Correctivo"+comas+","+
                            comas+"latitud"+comas+":"+comas+mLatitude.getText().toString()+comas+","+
                            comas+"longitud"+comas+":"+comas+mLongitude.getText().toString()+comas+

                            "}";

                    ////Genera JSON de variables




                    Long tsLong = System.currentTimeMillis()/1000;
                    String ts = tsLong.toString();

                    String filename= "IC_"+ts+"_"+reporte.getText().toString()+".txt";

                    OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(filename, Activity.MODE_PRIVATE));
                    archivo.write(guardaPost);
                    archivo.flush();
                    archivo.close();

                    añadir_im_recicler();
                    Context context = getApplicationContext();
                    CharSequence text = "Problema al subir file, almacenado en pendientes. " + filename;
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();



                } catch (IOException e) {
                }




            }
        });

        requestQueue.add(jar1);

        jar1.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));




    }


    private String i_photos_post;


    public class Post_2 {


        public Map<String, Boolean> stars = new HashMap<>();

        public Post_2() {
            // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        }



        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("imagen", encodedImage);
            result.put("observaciones", notas_post);
            result.put("id_img", i_photos_post);
            result.put("id_ATM", id_tpv_V);
            result.put("serie", serie.getText().toString());
            result.put("id_ATM", idATM);
            result.put("reporte", reporte.getText().toString());
            result.put("equipo", flag_tipo);
            result.put("tipo_reporte", "Correctivo");
            result.put("latitud", mLatitude.getText().toString());
            result.put("longitud", mLongitude.getText().toString());

            return result;
        }

    }

    private Context contexto = this;
    private int flag_foto;
    private String urlPhoto;
    private String notas_post;
    private List items = new ArrayList();

    public void añadir_im_recicler() {

        items.add(new Foto(encodedImage, notas_image.getText().toString()));


// Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

// Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

// Crear un nuevo adaptador
        adapter = new FotoAdapter(items);
        recycler.setAdapter(adapter);


        flag_foto = 0;
        notas_image.setText("");
        progressBarPhoto.setVisibility(View.GONE);
        photo_ATM_prev.setImageResource(R.drawable.ic_shutter);


    }



    public void anterior1(View view) {

        card2.setVisibility(View.GONE);
        card1.setVisibility(View.VISIBLE);
        nestedScrollView.scrollTo(0, 0);

    }


    public void siguiente2(View view) {
        card2.setVisibility(View.VISIBLE);
        card1.setVisibility(View.GONE);
        nestedScrollView.scrollTo(0, 0);

    }




    private String REV;



    public void firma(View view) {


        if (fecha.getText().toString().equals("Fecha")) {

            Context context = getApplicationContext();
            CharSequence text = "Favor seleccionar fecha de ateción";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;


        }

        if (flag_tipo.equals("LOGO")) {
            prim_child = "LOGO";
            idATM = serie.getText().toString();
            ID_atm_photos = serie.getText().toString();

            if (idATM.equals("")) {

                Context context = getApplicationContext();
                CharSequence text = "Favor documentar serie";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                return;


            }

        } else {
            prim_child = "ATMs";
            idATM = id_tpv.getText().toString();
            ID_atm_photos = id_tpv.getText().toString();

            if (idATM.equals("")) {

                Context context = getApplicationContext();
                CharSequence text = "Favor documentar ID ATM";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                return;


            }
        }


        if (reporte.getText().toString().equals("")) {

            Context context = getApplicationContext();
            CharSequence text = "Favor documentar Reporte";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;


        }


        if (vobo.getText().toString().equals("")) {

            Context context = getApplicationContext();
            CharSequence text = "Favor documentar VoBo";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;


        }

        if (puesto.getText().toString().equals("")) {

            Context context = getApplicationContext();
            CharSequence text = "Favor documentar puesto";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;


        }

        if (folio.getText().toString().equals("")) {

            Context context = getApplicationContext();
            CharSequence text = "Favor documentar folio";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;


        }


        Intent activity_signature = new Intent(getApplicationContext(), SignatureActivity.class);


        activity_signature.putExtra("usuario", usuarioid);
        activity_signature.putExtra("pais", "México");
        activity_signature.putExtra("reporte", reporte.getText().toString());
        activity_signature.putExtra("prim_child", prim_child);
        activity_signature.putExtra("id_serie", idATM);
        activity_signature.putExtra("corr_prev", "Correctivos");
        activity_signature.putExtra("Vobo", vobo.getText().toString());
        activity_signature.putExtra("puesto", puesto.getText().toString());
        activity_signature.putExtra("tipo_rep", "Correctivos");
        activity_signature.putExtra("folio", folio.getText().toString());
        activity_signature.putExtra("latitud", mLatitude.getText().toString());
        activity_signature.putExtra("longitud", mLongitude.getText().toString());
        activity_signature.putExtra("fecha", fecha.getText().toString());


        startActivity(activity_signature);




    }


    private String mJSONURLString;
    private String data;
    private String url2;






    private GoogleApiClient mGoogleApiClient;
    public static final int REQUEST_LOCATION = 1;
    public static final int REQUEST_CHECK_SETTINGS = 2;

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Aquí muestras confirmación explicativa al usuario
                // por si rechazó los permisos anteriormente
            } else {
                ActivityCompat.requestPermissions(
                        this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION);
            }
        } else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                mLatitude.setText(valueOf(mLastLocation.getLatitude()));
                mLongitude.setText(valueOf(mLastLocation.getLongitude()));
            } else {
                Toast.makeText(this, "Ubicación no encontrada, favor de Conectar GPS", Toast.LENGTH_LONG).show();
            }
        }






    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if(grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (mLastLocation != null) {
                    mLatitude.setText(valueOf(mLastLocation.getLatitude()));
                    mLongitude.setText(valueOf(mLastLocation.getLongitude()));
                } else {
                    Toast.makeText(this, "Ubicación no encontrada", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Permisos no otorgados", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }


    private void processLastLocation() {
        getLastLocation();
        if (mLastLocation != null) {
            updateLocationUI();
        }
    }
    private void getLastLocation() {
        if (isLocationPermissionGranted()) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } else {
            manageDeniedPermission();
        }
    }

    private boolean isLocationPermissionGranted() {
        int permission = ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    private void manageDeniedPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Aquí muestras confirmación explicativa al usuario
            // por si rechazó los permisos anteriormente
        } else {
            ActivityCompat.requestPermissions(
                    this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
    }
    private void updateLocationUI() {

        String errorMessage = "";

        mLatitude.setText(valueOf(mLastLocation.getLatitude()));
        mLongitude.setText(valueOf(mLastLocation.getLongitude()));
    }






}











