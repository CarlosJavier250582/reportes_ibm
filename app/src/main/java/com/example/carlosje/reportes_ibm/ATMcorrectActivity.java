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
import android.provider.SyncStateContract;
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
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;


import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.String.valueOf;


public class ATMcorrectActivity extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    private String REV;
    private String mJSONURLString;
    private int flag_foto;
    private String notas_post;
    private List items = new ArrayList();
    private String i_photos_post;
    private String encodedImage;
    private Location mLastLocation;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private TextView lb_remo_suc, lb_banco,mLatitude,mLongitude,mPosicion,TV_fecha_ini,TV_fecha_fin,TV_hr_ini,TV_hr_fin,TV_falla_atm,TV_solucion_label_1,TV_solucion_label_2,TV_solucion1,TV_solucion2;
    private ProgressBar progressBarPhoto;
    private NestedScrollView nestedScrollView;
    private ScrollView sv_nuevo;
    private CardView card1, card2;
    private ImageView photo_ATM_prev, fotito;
    private Button bt_nuevo, bt_buscar, bt_fecha;
    private LinearLayout  lay_nuevo, lay_gral,ly_partes,ly_falla,ly_sol_atm;
    private FloatingActionButton fl_btn_add_photo, fl_btn_save_atm_prev,fl_btn_respaldo;
    private String usuarioid;
    private TextView tv_user, fecha, url_photo,TV_sc,TV_ac,TV_ac_label, lb_cliente;
    private EditText partes_solicitadas_1,partes_cantidad_1,partes_solicitadas_2,partes_cantidad_2,partes_solicitadas_3,partes_cantidad_3,partes_solicitadas_4,partes_cantidad_4,partes_solicitadas_5,partes_cantidad_5,partes_solicitadas_6,partes_cantidad_6,partes_solicitadas_7,partes_cantidad_7,partes_solicitadas_8,partes_cantidad_8;
    private EditText partes_desc_1,partes_desc_2,partes_desc_3,partes_desc_4,partes_desc_5,partes_desc_6,partes_desc_7,partes_desc_8;
    private EditText indicaciones_soporte, soporte_nombre, notas_image, reporte, id_tpv, localidad, marca, modelo, serie, inventario, cliente_final, falla, solucion,puesto, vobo, folio, comentarios;
    private RadioButton RB_soporte_resuelve_si, RB_soporte_resuelve_no, RB_soporte_si, RB_soporte_no, RB_partes_si, RB_partes_no, RB_banco_banamex, RB_banco_bancomer, RB_banco_banorte, RB_banco_bancoppel, RB_banco_santander, RB_remo_suc_remoto, RB_remo_suc_sucursal;
    private RadioButton RB_cliente_ALSEA,RB_cliente_Banorte_Suc,RB_cliente_Banorte_Nod,RB_cliente_CCK,RB_cliente_otro;
    private String cliente;
    private EditText cliente_otro;
    private String falla_V;
    private String solucion_V;
    private String RB_partes_V;
    private String RB_banco_V;
    private String RB_remo_suc_V;
    private String RB_soporte_V;
    private String RB_soporte_resuelve_V;
    private RadioGroup op_banco,op_cliente,op_remo_suc;
    private String pais, flag_tipo;
    private String tipo_fecha="";
    private Boolean flag_cliente;
    private String cual_campo;
    private String idATM;
    private String comas=  "\"";
    private int i_photos;

    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atmcorrect);
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
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        fl_btn_add_photo = (FloatingActionButton) findViewById(R.id.fl_btn_add_photo);
        fl_btn_save_atm_prev = (FloatingActionButton) findViewById(R.id.fl_btn_save_atm_prev);
        fl_btn_respaldo= (FloatingActionButton) findViewById(R.id.fl_btn_respaldo);
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
        RB_cliente_ALSEA = (RadioButton) findViewById(R.id.RB_cliente_ALSEA);
        RB_cliente_Banorte_Suc = (RadioButton) findViewById(R.id.RB_cliente_Banorte_Suc);
        RB_cliente_Banorte_Nod = (RadioButton) findViewById(R.id.RB_cliente_Banorte_Nod);
        RB_cliente_CCK = (RadioButton) findViewById(R.id.RB_cliente_CCK);
        RB_cliente_otro = (RadioButton) findViewById(R.id.RB_cliente_otro);
        op_banco=(RadioGroup) findViewById(R.id.op_banco);
        op_remo_suc=(RadioGroup) findViewById(R.id.op_remo_suc);
        op_cliente=(RadioGroup) findViewById(R.id.op_cliente);
        lb_cliente=(TextView) findViewById(R.id.lb_cliente);
        cliente_otro= (EditText) findViewById(R.id.cliente_otro);
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


        pais = "México";
        flag_cliente= false;
        cliente="";
        usuarioid = getIntent().getStringExtra("usuario");
        flag_tipo = getIntent().getStringExtra("tipo");
        tv_user.setText(usuarioid);

/* --------------------------------------------------
	oculta los items
-------------------------------------------------- */
        fl_btn_save_atm_prev.setVisibility(View.VISIBLE);
        ly_partes.setVisibility(View.GONE);
        TV_ac.setVisibility(View.GONE);
        TV_ac_label.setVisibility(View.GONE);
        TV_solucion_label_2.setVisibility(View.GONE);
        TV_solucion2.setVisibility(View.GONE);

 /* --------------------------------------------------
	valida si es logo o atm
-------------------------------------------------- */

        if (flag_tipo.equals("LOGO")) {
            inventario.setVisibility(View.GONE);
            op_banco.setVisibility(View.GONE);
            id_tpv.setHint("Serie");
            lb_remo_suc.setVisibility(View.GONE);
            lb_banco.setVisibility(View.GONE);
            id_tpv.setVisibility(View.GONE);
            ly_falla.setVisibility(View.GONE);
            TV_falla_atm.setVisibility(View.GONE);
            ly_sol_atm.setVisibility(View.GONE);
            op_remo_suc.setVisibility(View.GONE);

        } else {
            falla.setVisibility(View.GONE);
            op_cliente.setVisibility(View.GONE);
            lb_cliente.setVisibility(View.GONE);
        }


/* ------------------------------------------------------------------
	busca si hay respaldos y si si los carga // crea folio digital
--------------------------------------------------------------------*/

        lee_respaldo_c();
        crea_folio();
/* ------------------------------------------------------------------
	llama a la camara
--------------------------------------------------------------------*/
        fl_btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamarIntent();

            }
        });

/* --------------------------------------------------
api geolocalización
-------------------------------------------------- */
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .build();
    }


/* --------------------------------------------------
 mensaje de que falta llenar campo
-------------------------------------------------- */
    public void mensaje_valida(){
        Context context = getApplicationContext();
        CharSequence text = "Favor documentar " +cual_campo;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
/* --------------------------------------------------
     valida que esten llenos los campos
-------------------------------------------------- */
    public void valida_campos(View view) {

        if (reporte.getText().toString().equals("")) {
            cual_campo="Reporte";
            mensaje_valida();
            return;
        }
        if (reporte.getText().toString().length()!=7) {
            cual_campo="correctamente reporte IBM (7 caracteres)";
            mensaje_valida();
            return;
        }

        if (reporte.getText().toString().substring(0,1).equals("P")) {}
            else{
            cual_campo="correctamente reporte IBM ";
            mensaje_valida();
            return;
        }

        if (fecha.getText().toString().equals("Fecha")) {
            cual_campo="Fecha de Atención";
            mensaje_valida();
            return;
        }
        falla_V="";
        if (flag_tipo.equals("ATM")) {

            idATM = id_tpv.getText().toString();
             if (idATM.equals("")) {
                cual_campo="ID ATM";
                mensaje_valida();
                return;
            }
            falla_V = TV_falla_atm.getText().toString();
            if (falla_V.equals("")){
                cual_campo="falla";
                mensaje_valida();
                return;
            }
            if (falla_V.equals("OTRA")){
                falla_V = falla_V + ": "+ falla.getText().toString();
            }
            if (TV_solucion1.getText().toString().equals("")){
                cual_campo="actividad";
                mensaje_valida();
                return;
            }
            solucion_V = TV_solucion1.getText().toString();

            if (solucion_V.equals("OTRA")){
                solucion_V = solucion_V + ": "+ solucion.getText().toString()+"..."+solucion.getText().toString();
            }else {
                if(TV_solucion2.getText().toString().equals("")){
                    cual_campo="componente";
                    mensaje_valida();
                    return;
                }else {
                    solucion_V = TV_solucion1.getText().toString()+ " " + TV_solucion2.getText().toString()+"..."+solucion.getText().toString();
                }
            }
            if (id_tpv.getText().toString().equals("")) {
                cual_campo="el ID de ATM";
                mensaje_valida();
                return;
            }
            if (!RB_banco_banamex.isChecked() && !RB_banco_bancomer.isChecked() && !RB_banco_banorte.isChecked() && !RB_banco_bancoppel.isChecked() && !RB_banco_santander.isChecked()) {
                cual_campo="Banco";
                mensaje_valida();
                return;
            }
            if (!RB_remo_suc_remoto.isChecked() && !RB_remo_suc_sucursal.isChecked()) {
                cual_campo="Remoto / Sucursal";
                mensaje_valida();
                return;
            }
            if (inventario.getText().toString().equals("")) {
                cual_campo="inventario";
                mensaje_valida();
                return;
            }
        } else {
            ///logo

            idATM = serie.getText().toString();
            if (solucion.getText().toString().equals("")) {
                cual_campo="Solución";
                mensaje_valida();
                return;
            }

            falla_V = falla.getText().toString();
            solucion_V = solucion.getText().toString();
            idATM = serie.getText().toString();
            if (RB_cliente_ALSEA.isChecked() ) {
                cliente=RB_cliente_ALSEA.getText().toString();
            }
            if (RB_cliente_Banorte_Suc.isChecked() ) {
                cliente=RB_cliente_Banorte_Suc.getText().toString();
            }
            if (RB_cliente_Banorte_Nod.isChecked() ) {
                cliente=RB_cliente_Banorte_Nod.getText().toString();
            }
            if (RB_cliente_CCK.isChecked() ) {
                cliente=RB_cliente_CCK.getText().toString();
            }
            if (RB_cliente_otro.isChecked() ) {
                cliente=cliente_otro.getText().toString();
            }
            if (idATM.equals("")) {
                cual_campo="Serie";
                mensaje_valida();
                return;
            }

            if(cliente.equals("")){
                cual_campo="Cliente";
                mensaje_valida();
            }

            if (serie.getText().toString().equals("")) {
                cual_campo="serie del equipo";
                mensaje_valida();
                return;
            }
            if (!RB_cliente_ALSEA.isChecked() && !RB_cliente_Banorte_Suc.isChecked() && !RB_cliente_Banorte_Nod.isChecked() && !RB_cliente_CCK.isChecked() && !RB_cliente_otro.isChecked()) {
                cual_campo="cliente";
                mensaje_valida();
                return;
            }
            if(flag_cliente==true && cliente_otro.getText().toString().equals("") ){
                cual_campo="cliente (otro)";
                mensaje_valida();
                return;
             }

             ////asigna valir a cliente
            if (RB_cliente_ALSEA.isChecked() ) {
                cliente=RB_cliente_ALSEA.getText().toString();
            }
            if (RB_cliente_Banorte_Suc.isChecked() ) {
                cliente=RB_cliente_Banorte_Suc.getText().toString();
            }
            if (RB_cliente_Banorte_Nod.isChecked() ) {
                cliente=RB_cliente_Banorte_Nod.getText().toString();
            }
            if (RB_cliente_CCK.isChecked() ) {
                cliente=RB_cliente_CCK.getText().toString();
            }
            if (RB_cliente_otro.isChecked() ) {
                cliente=cliente_otro.getText().toString();
            }

            if (falla_V.equals("")) {
                cual_campo="falla";
                mensaje_valida();
                return;
            }

        }
        if (localidad.getText().toString().equals("")) {
            cual_campo="localidad";
            mensaje_valida();
            return;
        }
        if (marca.getText().toString().equals("")) {
            cual_campo="marca";
            mensaje_valida();
            return;
        }
        if (modelo.getText().toString().equals("")) {
            cual_campo="modelo";
            mensaje_valida();
            return;
        }
        if (serie.getText().toString().equals("")) {
            cual_campo="serie";
            mensaje_valida();
            return;
        }
        if (cliente_final.getText().toString().equals("")) {
            cual_campo="cliente final";
            mensaje_valida();
            return;
        }
        if (!RB_partes_si.isChecked() && !RB_partes_no.isChecked()) {
            cual_campo="si utilizó partes";
            mensaje_valida();
            return;
        }
        if (RB_partes_si.isChecked()) {

            if (partes_solicitadas_1.getText().toString().equals("")) {
                cual_campo="las partes solicitadas";
                mensaje_valida();
                return;
            }else{
                if(partes_cantidad_1.getText().toString().equals("")){
                    cual_campo="la cantidad de partes (parte 1)";
                    mensaje_valida();
                    return;
                }
                if(partes_desc_1.getText().toString().equals("")){
                    cual_campo="la descripción de partes (parte 1)";
                    mensaje_valida();
                    return;
                }
            }
            if(partes_solicitadas_2.getText().toString().equals("")){
            }else {
                if (partes_cantidad_2.getText().toString().equals("")) {
                    cual_campo="la cantidad de partes (parte 2)";
                    mensaje_valida();
                    return;
                }
                if(partes_desc_2.getText().toString().equals("")){
                    cual_campo="la descripción de partes (parte 2)";
                    mensaje_valida();
                    return;
                }
            }
            if(partes_solicitadas_3.getText().toString().equals("")){
            }else {
                if (partes_cantidad_3.getText().toString().equals("")) {
                    cual_campo="la cantidad de partes (parte 3)";
                    mensaje_valida();
                    return;
                }
                if(partes_desc_3.getText().toString().equals("")){
                    cual_campo="la descripción de partes (parte 3)";
                    mensaje_valida();
                    return;
                }
            }
            if(partes_solicitadas_4.getText().toString().equals("")){
            }else {
                if (partes_cantidad_4.getText().toString().equals("")) {
                    cual_campo="la cantidad de partes (parte 4)";
                    mensaje_valida();
                    return;
                }
                if(partes_desc_4.getText().toString().equals("")){
                    cual_campo="la descripción de partes (parte 4)";
                    mensaje_valida();
                    return;
                }
            }
            if(partes_solicitadas_5.getText().toString().equals("")){
            }else {
                if (partes_cantidad_5.getText().toString().equals("")) {
                    cual_campo="la cantidad de partes (parte 5)";
                    mensaje_valida();
                    return;
                }
                if(partes_desc_5.getText().toString().equals("")){
                    cual_campo="la descripción de partes (parte 5)";
                    mensaje_valida();
                    return;
                }
            }
            if(partes_solicitadas_6.getText().toString().equals("")){
            }else {
                if (partes_cantidad_6.getText().toString().equals("")) {
                    cual_campo="la cantidad de partes (parte 6)";
                    mensaje_valida();
                    return;
                }
                if(partes_desc_6.getText().toString().equals("")){
                    cual_campo="la descripción de partes (parte 6)";
                    mensaje_valida();
                    return;
                }
            }

            if(partes_solicitadas_7.getText().toString().equals("")){
            }else {
                if (partes_cantidad_7.getText().toString().equals("")) {
                    cual_campo="la cantidad de partes (parte 7)";
                    mensaje_valida();
                    return;
                }
                if(partes_desc_7.getText().toString().equals("")){
                    cual_campo="la descripción de partes (parte 7)";
                    mensaje_valida();
                    return;
                }
            }
            if(partes_solicitadas_8.getText().toString().equals("")){
            }else {
                if (partes_cantidad_8.getText().toString().equals("")) {
                    cual_campo="la cantidad de partes (parte 8)";
                    mensaje_valida();
                    return;
                }
                if(partes_desc_8.getText().toString().equals("")){
                    cual_campo="la descripción de partes (parte 8)";
                    mensaje_valida();
                    return;
                }
            }
        }

        if (falla_V.equals("OTRA: ")) {
            cual_campo="falla";
            mensaje_valida();
            return;
        }
        if (solucion_V.equals("OTRA: ")) {
            cual_campo="solucion";
            mensaje_valida();
            return;
        }
        if (vobo.getText().toString().equals("")) {
            cual_campo="vobo";
            mensaje_valida();
            return;
        }
        if (puesto.getText().toString().equals("")) {
            cual_campo="puesto";
            mensaje_valida();
            return;
        }
        if (RB_soporte_si.isChecked()) {
            if (soporte_nombre.getText().toString().equals("")) {
                cual_campo="nombre soporte 2do nivel";
                mensaje_valida();
                return;
            }
            if (indicaciones_soporte.getText().toString().equals("")) {
                cual_campo="indicaciones de soporte";
                mensaje_valida();
                return;
            }
            if (!RB_soporte_resuelve_no.isChecked() && !RB_soporte_resuelve_si.isChecked()) {
                cual_campo="si resolvió con ayuda de soporte";
                mensaje_valida();
                return;
            }
        }
        if (!RB_soporte_no.isChecked() && !RB_soporte_si.isChecked()) {
            cual_campo="si utilizó ayuda de soporte";
            mensaje_valida();
            return;
        }
        if (TV_sc.getText().toString().equals("")) {
            cual_campo="SC";
            mensaje_valida();
            return;
        }
        if (TV_ac.getText().toString().equals("")) {
            cual_campo="AC";
            mensaje_valida();
            return;
        }
        if (TV_fecha_ini.getText().toString().equals("Fecha de llegada")) {
            cual_campo="Fecha de llegada";
            mensaje_valida();
            return;
        }
        if (TV_fecha_fin.getText().toString().equals("Fecha de terminación")) {
            cual_campo="Fecha de terminación";
            mensaje_valida();
            return;
        }
        if (TV_hr_ini.getText().toString().equals("Hora de llegada")) {
            cual_campo="Hora de llegada";
            mensaje_valida();
            return;
        }
        if (TV_hr_fin.getText().toString().equals("Hora de terminación")) {
            cual_campo="Hora de terminación";
            mensaje_valida();
            return;
        }


        if (folio.getText().toString().equals("")) {
            cual_campo="Folio";
            mensaje_valida();
            return;
        }
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
        muestraDialogo();

    }
/* --------------------------------------------------
     muestra dialogo de confirmacion de guardado
-------------------------------------------------- */


    public void muestraDialogo() {
        AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
        myBuild.setTitle("Importante");
        myBuild.setMessage("¿Guardar Checklist? Al confirmar se cargaran los datos documentados.");
        myBuild.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                save_corr();
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


/* --------------------------------------------------
     salva los datos
-------------------------------------------------- */
    private void save_corr() {


        Post post = new Post();
        Map<String, Object> postValues = post.toMap();
        /* --------------------------------------------------
             añade a Cloudant
        -------------------------------------------------- */

        String url=getResources().getString(R.string.urlCloudant)+"/correctivos";

        JsonObjectRequest jar1 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(postValues), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    REV = jsonObject.getString("rev");

                    String validaRespuesta=response.toString().substring(0, 10);

                    if(validaRespuesta.equals("{" +comas + "ok" +comas+ ":true")){

                        Context context = getApplicationContext();
                        CharSequence text = "Checklist Almacenado Correctamente. " ;
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        fl_btn_save_atm_prev.setVisibility(View.GONE);
                        firma();

                    }
                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Json Error Res: ", "" + error);


                try {
                    /* --------------------------------------------------
                   si no hay conexcion o hay error guarda el post en file txt
                   -------------------------------------------------- */

                    String comas=  "\"";
                    String guardaPost = "{" +
                            comas+"id_ATM"+comas+":"+comas+idATM+comas+","+
                            comas+"Fecha"+comas+":"+comas+fecha.getText().toString()+comas+","+
                            comas+"marca"+comas+":"+comas+marca.getText().toString()+comas+","+
                            comas+"modelo"+comas+":"+comas+modelo.getText().toString()+comas+","+
                            comas+"serie"+comas+":"+comas+serie.getText().toString()+comas+","+
                            comas+"inventario"+comas+":"+comas+inventario.getText().toString()+comas+","+
                            comas+"cliente_final"+comas+":"+comas+cliente_final.getText().toString()+comas+","+
                            comas+"solicita_partes"+comas+":"+comas+RB_partes_V+comas+","+
                            comas+"reporte"+comas+":"+comas+reporte.getText().toString()+comas+","+
                            comas+"localidad"+comas+":"+comas+localidad.getText().toString()+comas+","+
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
                            comas+"Vobo"+comas+":"+comas+vobo.getText().toString()+comas+","+
                            comas+"puesto"+comas+":"+comas+puesto.getText().toString()+comas+","+
                            comas+"folio"+comas+":"+comas+folio.getText().toString()+comas+","+
                            comas+"comentarios"+comas+":"+comas+comentarios.getText().toString()+comas+","+
                            comas+"Usuario"+comas+":"+comas+usuarioid+comas+","+
                            comas+"banco"+comas+":"+comas+RB_banco_V+comas+","+
                            comas+"remoto_sucursal"+comas+":"+comas+RB_remo_suc_V+comas+","+
                            comas+"Recibe_ayuda_de_soporte"+comas+":"+comas+RB_soporte_V+comas+","+
                            comas+"nombre_soporte"+comas+":"+comas+soporte_nombre.getText().toString()+comas+","+
                            comas+"indicaciones_de_soporte"+comas+":"+comas+indicaciones_soporte.getText().toString()+comas+","+
                            comas+"Resuelve_problema_ayuda_de_soporte"+comas+":"+comas+RB_soporte_resuelve_V+comas+","+
                            comas+"pais"+comas+":"+comas+"Mexico"+comas+","+
                            comas+"equipo"+comas+":"+comas+flag_tipo+comas+","+
                            comas+"tipo_reporte"+comas+":"+comas+"Correctivo"+comas+","+
                            comas+"latitud"+comas+":"+comas+mLatitude.getText().toString()+comas+","+
                            comas+"longitud"+comas+":"+comas+mLongitude.getText().toString()+comas+","+
                            comas+"hr_ini"+comas+":"+comas+TV_hr_ini.getText().toString()+comas+","+
                            comas+"hr_fin"+comas+":"+comas+TV_hr_fin.getText().toString()+comas+","+
                            comas+"fecha_ini"+comas+":"+comas+TV_fecha_ini.getText().toString()+comas+","+
                            comas+"fecha_fin"+comas+":"+comas+TV_fecha_fin.getText().toString()+comas+","+
                            comas+"SC"+comas+":"+comas+TV_sc.getText().toString()+comas+","+
                            comas+"cliente"+comas+":"+comas+cliente+comas+","+
                            comas+"AC"+comas+":"+comas+TV_ac.getText().toString()+comas+
                            "}";

                    Long tsLong = System.currentTimeMillis()/1000;
                    String ts = tsLong.toString();
                    String filename= "CO_"+ts+"_"+reporte.getText().toString()+".txt";
                    OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(filename, Activity.MODE_PRIVATE));
                    archivo.write(guardaPost);
                    archivo.flush();
                    archivo.close();
                    Context context = getApplicationContext();
                    CharSequence text = "Problema al subir file, almacenado en pendientes. " + filename;
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    /* --------------------------------------------------
                  abre intet para encuesta y firma
                   -------------------------------------------------- */
                    firma();
                } catch (IOException ee) {
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = getResources().getString(R.string.user_cloudant) + ":" + getResources().getString(R.string.pass_cloudant);
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic " + base64EncodedCredentials);
                return headers;
            }
        };
        requestQueue.add(jar1);
        jar1.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
/* --------------------------------------------------
Post mapa a cloudant
-------------------------------------------------- */


    public class Post {
        public Post() {

        }
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("id_ATM", idATM);
            result.put("Fecha", fecha.getText().toString());
            result.put("marca", marca.getText().toString());
            result.put("modelo", modelo.getText().toString());
            result.put("serie", serie.getText().toString());
            result.put("inventario", inventario.getText().toString());
            result.put("cliente_final", cliente_final.getText().toString());
            result.put("solicita_partes", RB_partes_V);
            result.put("reporte", reporte.getText().toString());
            result.put("localidad", localidad.getText().toString());
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
            result.put("Vobo", vobo.getText().toString());
            result.put("puesto", puesto.getText().toString());
            result.put("folio", folio.getText().toString());
            result.put("comentarios", comentarios.getText().toString());
            result.put("Usuario", usuarioid);
            result.put("banco", RB_banco_V);
            result.put("remoto_sucursal", RB_remo_suc_V);
            result.put("Recibe_ayuda_de_soporte", RB_soporte_V);
            result.put("nombre_soporte", soporte_nombre.getText().toString());
            result.put("indicaciones_de_soporte", indicaciones_soporte.getText().toString());
            result.put("Resuelve_problema_ayuda_de_soporte", RB_soporte_resuelve_V);
            result.put("pais", "Mexico");
            result.put("equipo", flag_tipo);
            result.put("tipo_reporte", "Correctivo");
            result.put("latitud", mLatitude.getText().toString());
            result.put("longitud", mLongitude.getText().toString());
            result.put("hr_ini",TV_hr_ini.getText().toString());
            result.put("hr_fin",TV_hr_fin.getText().toString());
            result.put("fecha_ini",TV_fecha_ini.getText().toString());
            result.put("fecha_fin",TV_fecha_fin.getText().toString());
            result.put("SC",TV_sc.getText().toString());
            result.put("AC",TV_ac.getText().toString());
            result.put("cliente", cliente);
            return result;
        }
    }

/* --------------------------------------------------
abre intent para encuesta y firma
-------------------------------------------------- */
    public void firma() {
        Intent activity_signature = new Intent(getApplicationContext(), SignatureActivity.class);

        activity_signature.putExtra("usuario", usuarioid);
        activity_signature.putExtra("pais", "México");
        activity_signature.putExtra("reporte", reporte.getText().toString());
        activity_signature.putExtra("id_serie", idATM);
        activity_signature.putExtra("Vobo", vobo.getText().toString());
        activity_signature.putExtra("puesto", puesto.getText().toString());
        activity_signature.putExtra("tipo_rep", "Correctivo");
        activity_signature.putExtra("corr_prev", "Correctivo");
        activity_signature.putExtra("folio", folio.getText().toString());
        activity_signature.putExtra("latitud", mLatitude.getText().toString());
        activity_signature.putExtra("longitud", mLongitude.getText().toString());
        activity_signature.putExtra("fecha", fecha.getText().toString());
        activity_signature.putExtra("serie", serie.getText().toString());
        activity_signature.putExtra("cliente", cliente);
        startActivity(activity_signature);
    }

/* --------------------------------------------------
muestra y ocultatextbox cliente otro
-------------------------------------------------- */
    public void verotro(View view){
        cliente_otro.setVisibility(View.VISIBLE);
        flag_cliente=true;
    }
    public void ocultarotro(View view){
        cliente_otro.setVisibility(View.GONE);
        flag_cliente=false;
    }

    /* --------------------------------------------------
ver ocultar partes
-------------------------------------------------- */
    public void ver_partes(View view){
        ly_partes.setVisibility(View.VISIBLE);
    }

    public void ocultar_partes(View view){
        ly_partes.setVisibility(View.GONE);

    }


/* --------------------------------------------------
abre calendario
-------------------------------------------------- */

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
/* --------------------------------------------------
recoge datos calendario
-------------------------------------------------- */
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

/* --------------------------------------------------
abrir reloj
-------------------------------------------------- */
    public void showTimePickerDialog_Head_hr_ini(View v) {
        DialogFragment newFragment = new TimePick();
        Bundle args = new Bundle();
        args.putInt("num", 1);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    /* --------------------------------------------------
    recoge datos hora
    -------------------------------------------------- */
    public void showTimePickerDialog_Head_hr_fin(View v) {
        DialogFragment newFragment = new TimePick();
        Bundle args = new Bundle();
        args.putInt("num", 2);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

/* --------------------------------------------------
Dialogo confirmar salir
-------------------------------------------------- */
    public void muestraDialogo_salir() {
        AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
        myBuild.setTitle("Importante");
        myBuild.setMessage("¿Salir del formulario?");
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
/* --------------------------------------------------
abre camara
-------------------------------------------------- */

    private static final int PICK_IMAGE_ID = 234;
    private void llamarIntent() {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }
/* --------------------------------------------------
resultados de la camara y listas
-------------------------------------------------- */

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
            }            ////visible campo falla otro
        }
        if (requestCode == 3) {
            if(resultCode == Activity.RESULT_OK) {
                //solucion.setText("");
               // solucion.setVisibility(View.GONE);
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
/* --------------------------------------------------
abre listas
-------------------------------------------------- */

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

 /* --------------------------------------------------
precionar atras en la aplicación
-------------------------------------------------- */

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
        muestraDialogo_salir();
    }
 /* --------------------------------------------------
sube imagen a cloudant
-------------------------------------------------- */

    public void subir_imagen(View view) {
        i_photos = i_photos + 1;
        // valida q reporte y ID existan
        if (flag_tipo.equals("LOGO")) {
            idATM = serie.getText().toString();
        } else {
            idATM = id_tpv.getText().toString();
        }

        if (reporte.getText().toString().equals("")) {
            cual_campo="reporte";
            mensaje_valida();
            return;
        }
        if (flag_tipo.equals("ATM")) {
            if (id_tpv.getText().toString().equals("")) {
                cual_campo="ID de ATM";
                mensaje_valida();
                return;
            }
        } else {
            if (serie.getText().toString().equals("")) {
                cual_campo="serie del equipo";
                mensaje_valida();
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
            cual_campo="serie del Observaciones";
            mensaje_valida();
            return;
        }
        progressBarPhoto.setVisibility(View.VISIBLE);
        notas_post = notas_image.getText().toString();
        i_photos_post = "" + i_photos;

        ATMcorrectActivity.Post_2 post_2 = new ATMcorrectActivity.Post_2();
        Map<String, Object> post_2Values = post_2.toMap();
        /////////////////////////////TODO////////Añade a Cloudant
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
                    /// si no hay conexcion o hay error guarda el post en file tx
                    String comas=  "\"";

                    String guardaPost = "{" +
                            comas+"imagen"+comas+":"+comas+encodedImage+comas+","+
                            comas+"observaciones"+comas+":"+comas+notas_post+comas+","+
                            comas+"id_img"+comas+":"+comas+i_photos_post+comas+","+
                            comas+"id_ATM"+comas+":"+comas+idATM+comas+","+
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
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = getResources().getString(R.string.user_cloudant) + ":" + getResources().getString(R.string.pass_cloudant);
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic " + base64EncodedCredentials);
                return headers;
            }
        };

        requestQueue.add(jar1);
        jar1.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


  /* --------------------------------------------------
mapa de para subir imagen
-------------------------------------------------- */

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

/* --------------------------------------------------
añade imagen a recicler view
-------------------------------------------------- */

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
/* --------------------------------------------------
cambia card view
-------------------------------------------------- */
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


    /* --------------------------------------------------
google api geolocalizavcion
-------------------------------------------------- */
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
                        REQUEST_LOCATION);            }
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

/* --------------------------------------------------
Al detener la plicación se genera respaldo
-------------------------------------------------- */

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
        if(reporte.getText().toString().equals("")){
        }else {
            crea_respaldo();
        }
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
    /* --------------------------------------------------
       //TODO   busca todos los num de folio y añade el mayor +1 y lo crea asi la prox busqueda ya no lo duplicará
    -------------------------------------------------- */

private Integer a ,   array_len_folios;
private String folio_b;
    private void crea_folio() {

        folio_b="";
        a=0;

        array_len_folios=0;
            mJSONURLString = getResources().getString(R.string.urlCloudant) + "/folio_consecutivo/_all_docs";
            JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, mJSONURLString, null,
                    new Response.Listener<JSONObject>() {

                        // Takes the response from the JSON request
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject obj = response;
                                int array_len_folios=obj.getInt("total_rows");
                                a=array_len_folios+1;
                                folio_b=""+a;
                                cuenta_flag_folio();
                            }
                            catch (JSONException e) { }
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                           public void onErrorResponse(VolleyError error) {
                            Log.e("Volley", "Error");
                        }
                    }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    String credentials = getResources().getString(R.string.user_cloudant) + ":" + getResources().getString(R.string.pass_cloudant);
                    String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Basic " + base64EncodedCredentials);
                    return headers;
                }
            };

        requestQueue.add(obreq);
    }
/* --------------------------------------------------
 //añade folio en el campo
 -------------------------------------------------- */

    private void  cuenta_flag_folio(){


            if(folio_b.length()==1){
                folio_b="DA00000"+folio_b;
            }
            if(folio_b.length()==2){
                folio_b="DA0000"+folio_b;
            }
            if(folio_b.length()==3){
                folio_b="DA000"+folio_b;
            }
            if(folio_b.length()==4){
                folio_b="DA00"+folio_b;
            }
            if(folio_b.length()==5){
                folio_b="DA0"+folio_b;
            }
            if(folio_b.length()==6){
                folio_b="DA"+folio_b;
            }

            añade_folio_consec();

    }
/* --------------------------------------------------
 //añade el siguiente folio para proteger el que se creó
 -------------------------------------------------- */

    private void añade_folio_consec(){

            ATMcorrectActivity.Post_folio_consecutivo post_folio_consecutivo = new ATMcorrectActivity.Post_folio_consecutivo();
            Map<String, Object> post_folio_consecutivo_Values = post_folio_consecutivo.toMap();
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url=getResources().getString(R.string.urlCloudant)+"/folio_consecutivo";

            JsonObjectRequest jar1 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(post_folio_consecutivo_Values), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());
                        REV = jsonObject.getString("rev");
                        folio.setText(folio_b);
                        folio.setEnabled(false);
                    } catch (JSONException e) {
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Json Error Res: ", "" + error);
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    String credentials = getResources().getString(R.string.user_cloudant) + ":" + getResources().getString(R.string.pass_cloudant);
                    String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Basic " + base64EncodedCredentials);
                    return headers;
                }
            };
            requestQueue.add(jar1);
            jar1.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }
    /* --------------------------------------------------
     //mapa envio folio
     -------------------------------------------------- */
    public class Post_folio_consecutivo{

        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("f", a);
            return result;
        }
    }

    public void accion_respaldo(View view) {
        crea_respaldo();
        Context context = getApplicationContext();
        CharSequence text = "Respaldo creado";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /* --------------------------------------------------
 //busca si hay respaldos
 -------------------------------------------------- */

    public void   lee_respaldo_c(){    ////lee al iniciar automáticamente
       String archivoBusca="respaldo_c.txt";
        String[] archivos = fileList();
        for (int f = 0; f < archivos.length; f++){
            if (archivoBusca.equals(archivos[f])){
                muestraDialogo_confirmar(archivoBusca);  //si encuentra file de respaldo solicita confirmacion para escribir
            }
        }
    }
    /* --------------------------------------------------
     //alertas de respaldo
     -------------------------------------------------- */
    public void muestraDialogo_confirmar(String archivoBusca) {
        final String archivoBuscaOk= archivoBusca;
        AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
        myBuild.setTitle("Importante");
        myBuild.setMessage("¿Desea continuar con la última seción?");

        myBuild.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Carga_datos_respaldo_c(archivoBuscaOk);
            }
        });

        myBuild.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                muestraDialogo_eliminar_respaldo(archivoBuscaOk);
            }
        });
        AlertDialog dialog = myBuild.create();
        dialog.show();
    }
    public void muestraDialogo_eliminar_respaldo(String archivoBusca) {
        final String archivoBuscaOk = archivoBusca;
        AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
        myBuild.setTitle("Importante");
        myBuild.setMessage("¿Deseas eliminar el respaldo?");

        myBuild.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                borraRespaldo(archivoBuscaOk);
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

    public void borraRespaldo(String archivoBuscaOk){
        Boolean flagBorra=false;
        while (flagBorra == false) {
         try {
                this.deleteFile(archivoBuscaOk);
                flagBorra = true;
         } catch (Exception e) {
         }
         }
    }
    /* --------------------------------------------------
     //crea respaldo
     -------------------------------------------------- */
    public void crea_respaldo(){

        String archivoBusca="respaldo_c.txt";
        String[] archivos = fileList();

        for (int f = 0; f < archivos.length; f++){
            if (archivoBusca.equals(archivos[f])){
                Boolean flagBorra=false;
                while (flagBorra == false) {
                    try {
                        this.deleteFile(archivoBusca);
                        flagBorra = true;
                    } catch (Exception e) {
                    }
                }
            }
        }

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

        String comas=  "\"";

        String guardaPost = "{" +
                comas+"id_ATM"+comas+":"+comas+idATM+comas+","+
                comas+"Fecha"+comas+":"+comas+fecha.getText().toString()+comas+","+
                comas+"marca"+comas+":"+comas+marca.getText().toString()+comas+","+
                comas+"modelo"+comas+":"+comas+modelo.getText().toString()+comas+","+
                comas+"serie"+comas+":"+comas+serie.getText().toString()+comas+","+
                comas+"inventario"+comas+":"+comas+inventario.getText().toString()+comas+","+
                comas+"cliente_final"+comas+":"+comas+cliente_final.getText().toString()+comas+","+
                comas+"solicita_partes"+comas+":"+comas+RB_partes_V+comas+","+
                comas+"reporte"+comas+":"+comas+reporte.getText().toString()+comas+","+
                comas+"localidad"+comas+":"+comas+localidad.getText().toString()+comas+","+
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
                comas+"Vobo"+comas+":"+comas+vobo.getText().toString()+comas+","+
                comas+"puesto"+comas+":"+comas+puesto.getText().toString()+comas+","+
                comas+"folio"+comas+":"+comas+folio.getText().toString()+comas+","+
                comas+"comentarios"+comas+":"+comas+comentarios.getText().toString()+comas+","+
                comas+"Usuario"+comas+":"+comas+usuarioid+comas+","+
                comas+"banco"+comas+":"+comas+RB_banco_V+comas+","+
                comas+"remoto_sucursal"+comas+":"+comas+RB_remo_suc_V+comas+","+
                comas+"Recibe_ayuda_de_soporte"+comas+":"+comas+RB_soporte_V+comas+","+
                comas+"nombre_soporte"+comas+":"+comas+soporte_nombre.getText().toString()+comas+","+
                comas+"indicaciones_de_soporte"+comas+":"+comas+indicaciones_soporte.getText().toString()+comas+","+
                comas+"Resuelve_problema_ayuda_de_soporte"+comas+":"+comas+RB_soporte_resuelve_V+comas+","+
                comas+"pais"+comas+":"+comas+"Mexico"+comas+","+
                comas+"equipo"+comas+":"+comas+flag_tipo+comas+","+
                comas+"tipo_reporte"+comas+":"+comas+"Correctivo"+comas+","+
                comas+"latitud"+comas+":"+comas+mLatitude.getText().toString()+comas+","+
                comas+"longitud"+comas+":"+comas+mLongitude.getText().toString()+comas+","+
                comas+"hr_ini"+comas+":"+comas+TV_hr_ini.getText().toString()+comas+","+
                comas+"hr_fin"+comas+":"+comas+TV_hr_fin.getText().toString()+comas+","+
                comas+"fecha_ini"+comas+":"+comas+TV_fecha_ini.getText().toString()+comas+","+
                comas+"fecha_fin"+comas+":"+comas+TV_fecha_fin.getText().toString()+comas+","+
                comas+"SC"+comas+":"+comas+TV_sc.getText().toString()+comas+","+
                comas+"cliente"+comas+":"+comas+cliente+comas+","+
                comas+"AC"+comas+":"+comas+TV_ac.getText().toString()+comas+
                "}";

        ////Genera JSON de variables
        try {
            String filename = archivoBusca;

            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(filename, Activity.MODE_PRIVATE));
            archivo.write(guardaPost);
            archivo.flush();
            archivo.close();
        } catch (IOException e) {
        }
    }

/* --------------------------------------------------
 //carga el respaldo
 -------------------------------------------------- */

    public void Carga_datos_respaldo_c(String archivoBusca){
        try {////////////llena el post
            InputStreamReader archivo = new InputStreamReader(openFileInput(archivoBusca));
            BufferedReader br = new BufferedReader(archivo);
            String linea = br.readLine();
            String F_todo = "";
            while (linea != null) {
                F_todo = F_todo + linea;
                linea = br.readLine();
            }
            br.close();
            archivo.close();
            try {
                String myJsonString= F_todo;
                JSONObject todoJson = new JSONObject(myJsonString);
                cliente_final.setText(todoJson.getString("cliente_final"));
                comentarios.setText(todoJson.getString("comentarios"));
                //falla.setText(todoJson.getString("falla"));
                fecha.setText(todoJson.getString("Fecha"));
               // folio.setText(todoJson.getString("folio"));
                id_tpv.setText(todoJson.getString("id_ATM"));
                indicaciones_soporte.setText(todoJson.getString("indicaciones_de_soporte"));
                inventario.setText(todoJson.getString("inventario"));
                localidad.setText(todoJson.getString("localidad"));
                marca.setText(todoJson.getString("marca"));
                mLatitude.setText(todoJson.getString("latitud"));
                mLongitude.setText(todoJson.getString("longitud"));
                modelo.setText(todoJson.getString("modelo"));
                partes_cantidad_1.setText(todoJson.getString("cantidad_partes_1"));
                partes_cantidad_2.setText(todoJson.getString("cantidad_partes_2"));
                partes_cantidad_3.setText(todoJson.getString("cantidad_partes_3"));
                partes_cantidad_4.setText(todoJson.getString("cantidad_partes_4"));
                partes_cantidad_5.setText(todoJson.getString("cantidad_partes_5"));
                partes_cantidad_6.setText(todoJson.getString("cantidad_partes_6"));
                partes_cantidad_7.setText(todoJson.getString("cantidad_partes_7"));
                partes_cantidad_8.setText(todoJson.getString("cantidad_partes_8"));
                partes_desc_1.setText(todoJson.getString("descripcion_partes_1"));
                partes_desc_2.setText(todoJson.getString("descripcion_partes_2"));
                partes_desc_3.setText(todoJson.getString("descripcion_partes_3"));
                partes_desc_4.setText(todoJson.getString("descripcion_partes_4"));
                partes_desc_5.setText(todoJson.getString("descripcion_partes_5"));
                partes_desc_6.setText(todoJson.getString("descripcion_partes_6"));
                partes_desc_7.setText(todoJson.getString("descripcion_partes_7"));
                partes_desc_8.setText(todoJson.getString("descripcion_partes_8"));
                partes_solicitadas_1.setText(todoJson.getString("partes_solicitadas_1"));
                partes_solicitadas_2.setText(todoJson.getString("partes_solicitadas_2"));
                partes_solicitadas_3.setText(todoJson.getString("partes_solicitadas_3"));
                partes_solicitadas_4.setText(todoJson.getString("partes_solicitadas_4"));
                partes_solicitadas_5.setText(todoJson.getString("partes_solicitadas_5"));
                partes_solicitadas_6.setText(todoJson.getString("partes_solicitadas_6"));
                partes_solicitadas_7.setText(todoJson.getString("partes_solicitadas_7"));
                partes_solicitadas_8.setText(todoJson.getString("partes_solicitadas_8"));
                puesto.setText(todoJson.getString("puesto"));
                reporte.setText(todoJson.getString("reporte"));
                serie.setText(todoJson.getString("serie"));
                //solucion.setText(todoJson.getString("solucion"));
                soporte_nombre.setText(todoJson.getString("nombre_soporte"));
                TV_ac.setText(todoJson.getString("AC"));
                TV_fecha_fin.setText(todoJson.getString("fecha_fin"));
                TV_fecha_ini.setText(todoJson.getString("fecha_ini"));
                TV_hr_fin.setText(todoJson.getString("hr_fin"));
                TV_hr_ini.setText(todoJson.getString("hr_ini"));
                TV_sc.setText(todoJson.getString("SC"));
                vobo.setText(todoJson.getString("Vobo"));
                if(todoJson.getString("banco").equals("Banamex")){
                    RB_banco_banamex.setChecked(true);
                }
                if(todoJson.getString("banco").equals("Bancomer")){
                    RB_banco_bancomer.setChecked(true);
                }
                if(todoJson.getString("banco").equals("Banorte")){
                    RB_banco_banorte.setChecked(true);
                }
                if(todoJson.getString("banco").equals("Bancoppel")){
                    RB_banco_bancoppel.setChecked(true);
                }
                if(todoJson.getString("banco").equals("Santander")){
                    RB_banco_santander.setChecked(true);
                }
                if(todoJson.getString("solicita_partes").equals("Si")){
                    RB_partes_si.setChecked(true);
                }
                if(todoJson.getString("solicita_partes").equals("No")){
                    RB_partes_no.setChecked(true);
                }
                if(todoJson.getString("Recibe_ayuda_de_soporte").equals("Si")){
                    RB_soporte_si.setChecked(true);
                }
                if(todoJson.getString("Recibe_ayuda_de_soporte").equals("No")){
                    RB_soporte_no.setChecked(true);
                }
                if(todoJson.getString("Resuelve_problema_ayuda_de_soporte").equals("Si")){
                    RB_soporte_resuelve_si.setChecked(true);
                }
                if(todoJson.getString("Resuelve_problema_ayuda_de_soporte").equals("No")){
                    RB_soporte_resuelve_no.setChecked(true);
                }
                if(todoJson.getString("remoto_sucursal").equals("Remoto")){
                    RB_remo_suc_remoto.setChecked(true);
                }
                if(todoJson.getString("remoto_sucursal").equals("Sucursal")){
                    RB_remo_suc_sucursal.setChecked(true);
                }
                Integer i_otros=0;
                if(todoJson.getString("cliente").equals("ALSEA")){
                    RB_cliente_ALSEA.setChecked(true);
                    i_otros=1;
                }
                if(todoJson.getString("cliente").equals("Banorte Sucursales")){
                    RB_cliente_Banorte_Suc.setChecked(true);
                    i_otros=1;
                }
                if(todoJson.getString("cliente").equals("Banorte Nodos")){
                    RB_cliente_Banorte_Nod.setChecked(true);
                    i_otros=1;
                }
                if(todoJson.getString("cliente").equals("CCK")){
                    RB_cliente_CCK.setChecked(true);
                    i_otros=1;
                }
                if(i_otros==0){
                    RB_cliente_otro.setChecked(true);
                    cliente_otro.setText(todoJson.getString("cliente"));
                    cliente_otro.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
            }
        } catch (IOException e) {

            Context context = getApplicationContext();
            CharSequence text = "Error al leer archivo: " +e;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}











