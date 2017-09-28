package com.example.carlosje.reportes_ibm;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;


import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;


public class ATMprevActivity extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    private ProgressBar progressBarPhoto;




    private ScrollView sv_nuevo;

    private CardView card1, card2, card3, card4, card5, card6;

    private ImageView photo_ATM_prev,fotito;


    private Button bt_nuevo, bt_buscar, bt_fecha;
    private LinearLayout lay_buscar, lay_nuevo, lay_gral;

    private FloatingActionButton fl_btn_add_photo, fl_btn_save_atm_prev;

    private String usuarioid;
    private int dia, mes, ano;
    private TextView tv_user, fecha,url_photo;

    private EditText notas_image, reporte, id_tpv, localidad, marca, modelo, serie, carga, inventario, cliente_final, sistema_operativo, version_multivendor, version_checker, puntos_anclaje_otro;
    private EditText carcaza, temperatura, UPS_kva, regulador_kva, fase_neutro_v_pared, fase_neutro_v_regulado, fase_tierra_v_pared, fase_tierra_v_regulado, tierra_neutro_v_pared, tierra_neutro_v_regulado;
    private EditText notas_seguridad, notas_inst_elect, notas_comunic;
    private EditText ob_edo_impresora, ob_edo_lectora, ob_edo_teclados, ob_edo_CPU, ob_edo_monitor, ob_edo_dispensador;
    private EditText marca_router, longitud_cable, pruebas_ping_result;

    private RadioButton RB_publico, RB_Personal, RB_cerradura_si, RB_cerradura_no, RB_puntos_anclaje_6, RB_puntos_anclaje_4, RB_puntos_anclaje_otro, RB_tipo_anclaje_RM16, RB_tipo_anclaje_tradicional;
    private RadioButton RB_base_anclaje_no, RB_base_anclaje_si, RB_placa_seguridad_si, RB_placa_seguridad_no, RB_perno_apertura_si, RB_perno_apertura_no;
    private RadioButton RB_placa_seg_sitio_si, RB_placa_seg_sitio_no, RB_postes_ext_si, RB_postes_ext_no, RB_carcaza_si, RB_carcaza_no, RB_aire_si, RB_aire_no, RB_limpieza_buena, RB_limpieza_mala, RB_limpieza_regular;
    private RadioButton RB_cesto_si, RB_cesto_no, RB_polvo_si, RB_polvo_no, RB_sol_si, RB_sol_no, RB_iluminacion_si, RB_iluminacion_no, RB_UPS_no, RB_UPS_si;
    private RadioButton RB_supresor_si, RB_supresor_no, RB_polarizacion_correcta_si, RB_polarizacion_correcta_no;
    private RadioButton RB_volt_regulado_sum_UPS, RB_volt_regulado_sum_regulador,  RB_tipo_regulado_Twist_lock, RB_tipo_regulado_normal;
    private RadioButton RB_logo_si, RB_logo_no, RB_logo_deteriorada, RB_senal_lectora_si, RB_senal_lectora_no, RB_senal_lectora_deteriorada, RB_senal_impresora_si, RB_senal_impresora_no, RB_senal_impresora_deteriorada;
    private RadioButton RB_senal_salida_efectivo_si, RB_senal_salida_efectivo_no, RB_senal_salida_efectivo_deteriorada, RB_senal_carcaza_si, RB_senal_carcaza_no, RB_senal_carcaza_deteriorada;
    private RadioButton RB_edo_impresora_ok, RB_edo_impresora_danado, RB_edo_lectora_ok, RB_edo_lectora_danado, RB_edo_teclados_ok, RB_edo_teclados_danado;
    private RadioButton RB_edo_CPU_ok, RB_edo_CPU_danado, RB_edo_monitor_ok, RB_edo_monitor_danado, RB_edo_dispensador_ok, RB_edo_dispensador_danado;
    private RadioButton RB_satelital, RB_DIAL,  RB_edo_cableado_maltratado, RB_edo_cableado_bueno, RB_edo_cableado_a_la_vista;
    private RadioButton RB_conf_cableado_conex_directa, RB_conf_cableado_conex_cruzado, RB_equipo_con_alte_si, RB_equipo_con_alte_no;
    private RadioButton  RB_pruebas_ping_si, RB_pruebas_ping_no, RB_tipo_dial_mecanico, RB_tipo_dial_electrico, RB_tipo_dial_randomico;
    private RadioButton RB_empotrado_si, RB_empotrado_no, RB_banco_banamex, RB_banco_bancomer ,RB_banco_banorte, RB_banco_bancoppel, RB_banco_santander, RB_remo_suc_remoto, RB_remo_suc_sucursal ;
    private RadioButton RB_ADSL, RB_3_4_G;



    private String pais;
    private String date;
    private String usuario;
    private String ubicacion;
    private String idkey;


    private String reporte_V;
    private String id_tpv_V;
    private String localidad_V;
    private String marca_V;
    private String modelo_V;
    private String carga_V;
    private String serie_V;
    private String inventario_V;
    private String cliente_final_V;
    private String sistema_operativo_V;
    private String version_checker_V;
    private String version_multivendor_V;
    private String puntos_anclaje_otro_V;
    private String carcaza_V;
    private String temperatura_V;
    private String UPS_kva_V;
    private String regulador_kva_V;
    private String fase_neutro_v_pared_V;
    private String fase_neutro_v_regulado_V;
    private String fase_tierra_v_pared_V;
    private String fase_tierra_v_regulado_V;
    private String tierra_neutro_v_pared_V;
    private String tierra_neutro_v_regulado_V;
    private String notas_seguridad_V;
    private String notas_inst_elect_V;
    private String notas_comunic_V;
    private String ob_edo_impresora_V;
    private String ob_edo_lectora_V;
    private String ob_edo_teclados_V;
    private String ob_edo_CPU_V;
    private String ob_edo_monitor_V;
    private String ob_edo_dispensador_V;
    private String marca_router_V;
    private String longitud_cable_V;
    private String pruebas_ping_result_V;


    private String RB_servicio_V;

    private String RB_tipo_dial_V;

    private String RB_cerradura_V;
    private String RB_puntos_anclaje_V;

    private String RB_tipo_anclaje_V;
    private String RB_base_anclaje_V;
    private String RB_placa_seguridad_V;
    private String RB_perapertura_V;
    private String RB_placa_seg_sitio_V;
    private String RB_empotrado_V;
    private String RB_postes_ext_V;
    private String RB_carcaza_V;
    private String RB_aire_V;
    private String RB_limpieza_V;

    private String RB_cesto_V;
    private String RB_polvo_V;
    private String RB_sol_V;
    private String RB_iluminacion_V;
    private String RB_UPS_V;
    private String RB_supresor_V;

    private String RB_polarizacion_correcta_V;
    private String RB_volt_regulado_sum_V;
    private String RB_tipo_regulado_V;
    private String RB_logo_V;
    private String RB_senal_lectora_V;
    private String RB_senal_impresora_V;
    private String RB_senal_salida_efectivo_V;
    private String RB_senal_carcaza_V;
    private String RB_edo_impresora_V;
    private String RB_edo_lectora_V;
    private String RB_edo_teclados_V;
    private String RB_edo_CPU_V;
    private String RB_edo_monitor_V;
    private String RB_edo_dispensador_V;
    private String RB_tipo_comunicacion_V;
    private String RB_edo_cableado_V;
    private String RB_conf_cableado_conex_V;
    private String RB_equipo_con_alte_V;

    private String RB_pruebas_ping_V;

    private String RB_banco_V;
    private String RB_remo_suc_V;




















    private String CB_CSDS_V;
    private String CB_CHECKER_V;
    private String CB_RKL_V;
    private String CB_FIX_WIN_V;
    private String sp_tipo_dial_V;


    private String valida_fecha;
    private String valida_IDatm;
    private String valida_reporte;


    private CheckBox CB_CSDS, CB_CHECKER, CB_RKL, CB_FIX_WIN;

    private Spinner sp_tipo_dial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atmprev);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        pais="México";

        usuarioid = getIntent().getStringExtra("usuario");

        fl_btn_add_photo = (FloatingActionButton) findViewById(R.id.fl_btn_add_photo);
        fl_btn_save_atm_prev = (FloatingActionButton) findViewById(R.id.fl_btn_save_atm_prev);

        photo_ATM_prev = (ImageView) findViewById(R.id.photo_ATM_prev);

        fotito= (ImageView) findViewById(R.id.fotito);


        progressBarPhoto=(ProgressBar) findViewById(R.id.progressBarPhoto);

        sv_nuevo = (ScrollView) findViewById(R.id.sv_nuevo);

        card1 = (CardView) findViewById(R.id.card1);
        card2 = (CardView) findViewById(R.id.card2);
        card3 = (CardView) findViewById(R.id.card3);
        card4 = (CardView) findViewById(R.id.card4);
        card5 = (CardView) findViewById(R.id.card5);
        card6 = (CardView) findViewById(R.id.card6);

        tv_user = (TextView) findViewById(R.id.tv_user);

        fecha = (TextView) findViewById(R.id.fecha);

        url_photo= (TextView) findViewById(R.id.url_photo);

        lay_gral = (LinearLayout) findViewById(R.id.lay_gral);

        lay_nuevo = (LinearLayout) findViewById(R.id.lay_nuevo);


        bt_nuevo = (Button) findViewById(R.id.bt_nuevo);
        bt_buscar = (Button) findViewById(R.id.bt_buscar);
        bt_buscar = (Button) findViewById(R.id.bt_buscar);


        notas_image = (EditText) findViewById(R.id.notas_image);

        reporte = (EditText) findViewById(R.id.reporte);
        id_tpv = (EditText) findViewById(R.id.id_tpv);
        localidad = (EditText) findViewById(R.id.localidad);
        marca = (EditText) findViewById(R.id.marca);
        modelo = (EditText) findViewById(R.id.modelo);
        carga = (EditText) findViewById(R.id.carga);
        serie = (EditText) findViewById(R.id.serie);
        inventario = (EditText) findViewById(R.id.inventario);
        cliente_final = (EditText) findViewById(R.id.cliente_final);
        sistema_operativo = (EditText) findViewById(R.id.sistema_operativo);
        version_multivendor = (EditText) findViewById(R.id.version_multivendor);
        version_checker = (EditText) findViewById(R.id.version_checker);
        puntos_anclaje_otro = (EditText) findViewById(R.id.puntos_anclaje_otro);
        carcaza = (EditText) findViewById(R.id.carcaza);
        temperatura = (EditText) findViewById(R.id.temperatura);
        UPS_kva = (EditText) findViewById(R.id.UPS_kva);
        regulador_kva = (EditText) findViewById(R.id.regulador_kva);
        fase_neutro_v_pared = (EditText) findViewById(R.id.fase_neutro_v_pared);
        fase_neutro_v_regulado = (EditText) findViewById(R.id.fase_neutro_v_regulado);
        fase_tierra_v_pared = (EditText) findViewById(R.id.fase_tierra_v_pared);
        fase_tierra_v_regulado = (EditText) findViewById(R.id.fase_tierra_v_regulado);
        tierra_neutro_v_pared = (EditText) findViewById(R.id.tierra_neutro_v_pared);
        tierra_neutro_v_regulado = (EditText) findViewById(R.id.tierra_neutro_v_regulado);
        notas_seguridad = (EditText) findViewById(R.id.notas_seguridad);
        notas_inst_elect = (EditText) findViewById(R.id.notas_inst_elect);
        notas_comunic = (EditText) findViewById(R.id.notas_comunic);


        ob_edo_impresora = (EditText) findViewById(R.id.ob_edo_impresora);
        ob_edo_lectora = (EditText) findViewById(R.id.ob_edo_lectora);
        ob_edo_teclados = (EditText) findViewById(R.id.ob_edo_teclados);
        ob_edo_CPU = (EditText) findViewById(R.id.ob_edo_CPU);
        ob_edo_monitor = (EditText) findViewById(R.id.ob_edo_monitor);
        ob_edo_dispensador = (EditText) findViewById(R.id.ob_edo_dispensador);
        marca_router = (EditText) findViewById(R.id.marca_router);
        longitud_cable = (EditText) findViewById(R.id.longitud_cable);
        pruebas_ping_result = (EditText) findViewById(R.id.pruebas_ping_result);



        RB_publico = (RadioButton) findViewById(R.id.RB_publico);
        RB_Personal = (RadioButton) findViewById(R.id.RB_Personal);
        RB_cerradura_si = (RadioButton) findViewById(R.id.RB_cerradura_si);
        RB_cerradura_no = (RadioButton) findViewById(R.id.RB_cerradura_no);
        RB_puntos_anclaje_6 = (RadioButton) findViewById(R.id.RB_puntos_anclaje_6);
        RB_puntos_anclaje_4 = (RadioButton) findViewById(R.id.RB_puntos_anclaje_4);
        RB_puntos_anclaje_otro = (RadioButton) findViewById(R.id.RB_puntos_anclaje_otro);
        RB_tipo_anclaje_RM16 = (RadioButton) findViewById(R.id.RB_tipo_anclaje_RM16);
        RB_tipo_anclaje_tradicional = (RadioButton) findViewById(R.id.RB_tipo_anclaje_tradicional);
        RB_base_anclaje_no = (RadioButton) findViewById(R.id.RB_base_anclaje_no);
        RB_base_anclaje_si = (RadioButton) findViewById(R.id.RB_base_anclaje_si);
        RB_placa_seguridad_si = (RadioButton) findViewById(R.id.RB_placa_seguridad_si);
        RB_placa_seguridad_no = (RadioButton) findViewById(R.id.RB_placa_seguridad_no);
        RB_perno_apertura_si = (RadioButton) findViewById(R.id.RB_perno_apertura_si);
        RB_perno_apertura_no = (RadioButton) findViewById(R.id.RB_perno_apertura_no);
        RB_placa_seg_sitio_si = (RadioButton) findViewById(R.id.RB_placa_seg_sitio_si);
        RB_placa_seg_sitio_no = (RadioButton) findViewById(R.id.RB_placa_seg_sitio_no);
        RB_placa_seg_sitio_no = (RadioButton) findViewById(R.id.RB_placa_seg_sitio_no);
        RB_postes_ext_si = (RadioButton) findViewById(R.id.RB_postes_ext_si);
        RB_postes_ext_no = (RadioButton) findViewById(R.id.RB_postes_ext_no);
        RB_carcaza_si = (RadioButton) findViewById(R.id.RB_carcaza_si);
        RB_carcaza_no = (RadioButton) findViewById(R.id.RB_carcaza_no);
        RB_aire_si = (RadioButton) findViewById(R.id.RB_aire_si);
        RB_aire_no = (RadioButton) findViewById(R.id.RB_aire_no);
        RB_limpieza_buena = (RadioButton) findViewById(R.id.RB_limpieza_buena);
        RB_limpieza_mala = (RadioButton) findViewById(R.id.RB_limpieza_mala);
        RB_limpieza_regular = (RadioButton) findViewById(R.id.RB_limpieza_regular);
        RB_cesto_si = (RadioButton) findViewById(R.id.RB_cesto_si);
        RB_cesto_no = (RadioButton) findViewById(R.id.RB_cesto_no);
        RB_polvo_si = (RadioButton) findViewById(R.id.RB_polvo_si);
        RB_polvo_no = (RadioButton) findViewById(R.id.RB_polvo_no);
        RB_sol_si = (RadioButton) findViewById(R.id.RB_sol_si);
        RB_sol_no = (RadioButton) findViewById(R.id.RB_sol_no);
        RB_iluminacion_si = (RadioButton) findViewById(R.id.RB_iluminacion_si);
        RB_iluminacion_no = (RadioButton) findViewById(R.id.RB_iluminacion_no);
        RB_UPS_si = (RadioButton) findViewById(R.id.RB_UPS_si);
        RB_UPS_no = (RadioButton) findViewById(R.id.RB_UPS_no);
        RB_supresor_si = (RadioButton) findViewById(R.id.RB_supresor_si);
        RB_supresor_no = (RadioButton) findViewById(R.id.RB_supresor_no);

        RB_polarizacion_correcta_si = (RadioButton) findViewById(R.id.RB_polarizacion_correcta_si);
        RB_polarizacion_correcta_no = (RadioButton) findViewById(R.id.RB_polarizacion_correcta_no);
        RB_volt_regulado_sum_UPS = (RadioButton) findViewById(R.id.RB_volt_regulado_sum_UPS);
        RB_volt_regulado_sum_regulador = (RadioButton) findViewById(R.id.RB_volt_regulado_sum_regulador);

        RB_tipo_regulado_Twist_lock = (RadioButton) findViewById(R.id.RB_tipo_regulado_Twist_lock);
        RB_tipo_regulado_normal = (RadioButton) findViewById(R.id.RB_tipo_regulado_normal);
        RB_logo_si = (RadioButton) findViewById(R.id.RB_logo_si);
        RB_logo_no = (RadioButton) findViewById(R.id.RB_logo_no);
        RB_logo_deteriorada = (RadioButton) findViewById(R.id.RB_logo_deteriorada);
        RB_senal_lectora_si = (RadioButton) findViewById(R.id.RB_senal_lectora_si);
        RB_senal_lectora_no = (RadioButton) findViewById(R.id.RB_senal_lectora_no);
        RB_senal_lectora_deteriorada = (RadioButton) findViewById(R.id.RB_senal_lectora_deteriorada);
        RB_senal_impresora_si = (RadioButton) findViewById(R.id.RB_senal_impresora_si);
        RB_senal_impresora_no = (RadioButton) findViewById(R.id.RB_senal_impresora_no);
        RB_senal_impresora_deteriorada = (RadioButton) findViewById(R.id.RB_senal_impresora_deteriorada);
        RB_senal_salida_efectivo_si = (RadioButton) findViewById(R.id.RB_senal_salida_efectivo_si);
        RB_senal_salida_efectivo_no = (RadioButton) findViewById(R.id.RB_senal_salida_efectivo_no);
        RB_senal_salida_efectivo_deteriorada = (RadioButton) findViewById(R.id.RB_senal_salida_efectivo_deteriorada);
        RB_senal_carcaza_si = (RadioButton) findViewById(R.id.RB_senal_carcaza_si);
        RB_senal_carcaza_no = (RadioButton) findViewById(R.id.RB_senal_carcaza_no);
        RB_senal_carcaza_deteriorada = (RadioButton) findViewById(R.id.RB_senal_carcaza_deteriorada);
        RB_edo_impresora_ok = (RadioButton) findViewById(R.id.RB_edo_impresora_ok);
        RB_edo_impresora_danado = (RadioButton) findViewById(R.id.RB_edo_impresora_danado);
        RB_edo_lectora_ok = (RadioButton) findViewById(R.id.RB_edo_lectora_ok);
        RB_edo_lectora_danado = (RadioButton) findViewById(R.id.RB_edo_lectora_danado);
        RB_edo_teclados_ok = (RadioButton) findViewById(R.id.RB_edo_teclados_ok);
        RB_edo_teclados_danado = (RadioButton) findViewById(R.id.RB_edo_teclados_danado);
        RB_edo_CPU_ok = (RadioButton) findViewById(R.id.RB_edo_CPU_ok);
        RB_edo_CPU_danado = (RadioButton) findViewById(R.id.RB_edo_CPU_danado);
        RB_edo_monitor_ok = (RadioButton) findViewById(R.id.RB_edo_monitor_ok);
        RB_edo_monitor_danado = (RadioButton) findViewById(R.id.RB_edo_monitor_danado);
        RB_edo_dispensador_ok = (RadioButton) findViewById(R.id.RB_edo_dispensador_ok);
        RB_edo_dispensador_danado = (RadioButton) findViewById(R.id.RB_edo_dispensador_danado);
        RB_satelital = (RadioButton) findViewById(R.id.RB_satelital);
        RB_DIAL = (RadioButton) findViewById(R.id.RB_DIAL);

        RB_edo_cableado_maltratado = (RadioButton) findViewById(R.id.RB_edo_cableado_maltratado);
        RB_edo_cableado_bueno = (RadioButton) findViewById(R.id.RB_edo_cableado_bueno);
        RB_edo_cableado_a_la_vista = (RadioButton) findViewById(R.id.RB_edo_cableado_a_la_vista);
        RB_conf_cableado_conex_directa = (RadioButton) findViewById(R.id.RB_conf_cableado_conex_directa);
        RB_conf_cableado_conex_cruzado = (RadioButton) findViewById(R.id.RB_conf_cableado_conex_cruzado);
        RB_equipo_con_alte_si = (RadioButton) findViewById(R.id.RB_equipo_con_alte_si);
        RB_equipo_con_alte_no = (RadioButton) findViewById(R.id.RB_equipo_con_alte_no);

        RB_pruebas_ping_si = (RadioButton) findViewById(R.id.RB_pruebas_ping_si);
        RB_pruebas_ping_no = (RadioButton) findViewById(R.id.RB_pruebas_ping_no);
        RB_tipo_dial_mecanico = (RadioButton) findViewById(R.id.RB_tipo_dial_mecanico);
        RB_tipo_dial_electrico = (RadioButton) findViewById(R.id.RB_tipo_dial_electrico);
        RB_tipo_dial_randomico = (RadioButton) findViewById(R.id.RB_tipo_dial_randomico);
        RB_empotrado_si = (RadioButton) findViewById(R.id.RB_empotrado_si);
        RB_empotrado_no = (RadioButton) findViewById(R.id.RB_empotrado_no);

        RB_banco_banamex = (RadioButton) findViewById(R.id.RB_banco_banamex);
        RB_banco_bancomer = (RadioButton) findViewById(R.id.RB_banco_bancomer);
        RB_banco_banorte = (RadioButton) findViewById(R.id.RB_banco_banorte);
        RB_banco_bancoppel = (RadioButton) findViewById(R.id.RB_banco_bancoppel);
        RB_banco_santander = (RadioButton) findViewById(R.id.RB_banco_santander);
        RB_remo_suc_remoto = (RadioButton) findViewById(R.id.RB_remo_suc_remoto);
        RB_remo_suc_sucursal = (RadioButton) findViewById(R.id.RB_remo_suc_sucursal);

        RB_ADSL = (RadioButton) findViewById(R.id.RB_ADSL);
        RB_3_4_G = (RadioButton) findViewById(R.id.RB_3_4_G);








        CB_CSDS = (CheckBox) findViewById(R.id.CB_CSDS);
        CB_CHECKER = (CheckBox) findViewById(R.id.CB_CHECKER);
        CB_RKL = (CheckBox) findViewById(R.id.CB_RKL);
        CB_FIX_WIN = (CheckBox) findViewById(R.id.CB_FIX_WIN);


        sp_tipo_dial = (Spinner) findViewById(R.id.sp_tipo_dial);
        String[] tipo_dial = {"Seleccionar", "Mecánico", "Electrónico", "Randómico"};
        sp_tipo_dial.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo_dial));


        valida_fecha = fecha.getText().toString();
        valida_IDatm = id_tpv.getText().toString();
        valida_reporte = reporte.getText().toString();


        tv_user.setText(usuarioid);


        //// Invisible a cards //


        card2.setVisibility(View.GONE);
        card3.setVisibility(View.GONE);
        card4.setVisibility(View.GONE);
        card5.setVisibility(View.GONE);
        card6.setVisibility(View.GONE);


        fl_btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamarIntent();

            }
        });

        ///////////////dialogo confirmar salvar


        fl_btn_save_atm_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String flag_fecha = fecha.getText().toString();
                String flag_reporte = reporte.getText().toString();
                String flag_idATM = id_tpv.getText().toString();

                if (fecha.getText().toString().equals("Fecha de atención")) {

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

                if (id_tpv.getText().toString().equals("")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Favor documentar el ID de ATM";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;


                }


                muestraDialogo();

            }
        });


    }


    ///abrir calendario
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


        //fecha.setText( valueOf(dayOfMonth)  + "/ " + valueOf(monthOfYear + 1) + "/ " + valueOf(year) );

        fecha.setText(m_v + "/" + d_v + "/" + valueOf(year));

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePick();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }


    ///abrir dialogo


    public void muestraDialogo() {
        AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
        myBuild.setTitle("Importante");
        myBuild.setMessage("¿Guardar Checklist? Al confirmar se cargaran los datos documentados y se reiniciará el formulario");
        myBuild.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                saveATM_prev();

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


    public void anterior1(View view) {
        card2.setVisibility(View.GONE);
        card1.setVisibility(View.VISIBLE);

    }


    public void anterior2(View view) {
        card2.setVisibility(View.VISIBLE);
        card3.setVisibility(View.GONE);

    }


    public void anterior3(View view) {
        card3.setVisibility(View.VISIBLE);
        card4.setVisibility(View.GONE);

    }

    public void anterior4(View view) {
        card4.setVisibility(View.VISIBLE);
        card5.setVisibility(View.GONE);

    }


    public void anterior5(View view) {
        card5.setVisibility(View.VISIBLE);
        card6.setVisibility(View.GONE);

    }


    public void siguiente2(View view) {
        card2.setVisibility(View.VISIBLE);
        card1.setVisibility(View.GONE);

    }


    public void siguiente3(View view) {
        card2.setVisibility(View.GONE);
        card3.setVisibility(View.VISIBLE);

    }

    public void siguiente4(View view) {
        card3.setVisibility(View.GONE);
        card4.setVisibility(View.VISIBLE);

    }


    public void siguiente5(View view) {
        card4.setVisibility(View.GONE);
        card5.setVisibility(View.VISIBLE);

    }


    public void siguiente6(View view) {
        card5.setVisibility(View.GONE);
        card6.setVisibility(View.VISIBLE);

    }


    public void siguiente7(View view) {
        card6.setVisibility(View.GONE);

    }


    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void llamarIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            photo_ATM_prev.setDrawingCacheEnabled(true);
            photo_ATM_prev.buildDrawingCache(false);

            if(photo_ATM_prev.getDrawingCache() != null) {
                Bitmap  bitmap = Bitmap.createBitmap(photo_ATM_prev.getDrawingCache());
                photo_ATM_prev.setDrawingCacheEnabled(false);

            }




            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            photo_ATM_prev.setImageBitmap(imageBitmap);
            flag_foto=1;
        }
    }


    private DatabaseReference mDatabase;
    private String idATM;


    ////////////////////declaracion de variables


//TODO agregar radio grup pais  MEXICO y mandarlo por default escondido


    // TODO  id y serie, tipo, modelo , reporte, enviarlas a mayuscula spara conjuntarlas ///////////////////////////////////////////////////////////////////////////////////////////////


    private void saveATM_prev() {


        ///////////////////////////////////////////////////Asignar valores a variables


        idATM = id_tpv.getText().toString();


        reporte_V = reporte.getText().toString();
        id_tpv_V = id_tpv.getText().toString();
        localidad_V = localidad.getText().toString();
        marca_V = marca.getText().toString();
        modelo_V = modelo.getText().toString();
        carga_V = carga.getText().toString();
        serie_V = serie.getText().toString();
        inventario_V = inventario.getText().toString();
        cliente_final_V = cliente_final.getText().toString();
        sistema_operativo_V = sistema_operativo.getText().toString();
        version_checker_V = version_checker.getText().toString();
        version_multivendor_V = version_multivendor.getText().toString();
        puntos_anclaje_otro_V = puntos_anclaje_otro.getText().toString();
        carcaza_V = carcaza.getText().toString();
        temperatura_V = temperatura.getText().toString();
        UPS_kva_V = UPS_kva.getText().toString();
        regulador_kva_V = regulador_kva.getText().toString();
        fase_neutro_v_pared_V = fase_neutro_v_pared.getText().toString();
        fase_neutro_v_regulado_V = fase_neutro_v_regulado.getText().toString();
        fase_tierra_v_pared_V = fase_tierra_v_pared.getText().toString();
        fase_tierra_v_regulado_V = fase_tierra_v_regulado.getText().toString();
        tierra_neutro_v_pared_V = tierra_neutro_v_pared.getText().toString();
        tierra_neutro_v_regulado_V = tierra_neutro_v_regulado.getText().toString();
        notas_seguridad_V = notas_seguridad.getText().toString();
        notas_inst_elect_V = notas_inst_elect.getText().toString();
        notas_comunic_V = notas_comunic.getText().toString();
        ob_edo_impresora_V = ob_edo_impresora.getText().toString();
        ob_edo_lectora_V = ob_edo_lectora.getText().toString();
        ob_edo_teclados_V = ob_edo_teclados.getText().toString();
        ob_edo_CPU_V = ob_edo_CPU.getText().toString();
        ob_edo_monitor_V = ob_edo_monitor.getText().toString();
        ob_edo_dispensador_V = ob_edo_dispensador.getText().toString();
        marca_router_V = marca_router.getText().toString();
        longitud_cable_V = longitud_cable.getText().toString();
        pruebas_ping_result_V = pruebas_ping_result.getText().toString();



        if (RB_publico.isChecked()) {
            RB_servicio_V = RB_publico.getText().toString();
        }
        if (RB_Personal.isChecked()) {
            RB_servicio_V = RB_Personal.getText().toString();
        }
        if (RB_tipo_dial_mecanico.isChecked()) {
            RB_tipo_dial_V = RB_tipo_dial_mecanico.getText().toString();
        }
        if (RB_tipo_dial_electrico.isChecked()) {
            RB_tipo_dial_V = RB_tipo_dial_electrico.getText().toString();
        }
        if (RB_tipo_dial_randomico.isChecked()) {
            RB_tipo_dial_V = RB_tipo_dial_randomico.getText().toString();
        }

        if (RB_cerradura_si.isChecked()) {
            RB_cerradura_V = RB_cerradura_si.getText().toString();
        }
        if (RB_cerradura_no.isChecked()) {
            RB_cerradura_V = RB_cerradura_no.getText().toString();
        }

        if (RB_puntos_anclaje_6.isChecked()) {
            RB_puntos_anclaje_V = RB_puntos_anclaje_6.getText().toString();
        }
        if (RB_puntos_anclaje_4.isChecked()) {
            RB_puntos_anclaje_V = RB_puntos_anclaje_4.getText().toString();
        }
        if (RB_puntos_anclaje_otro.isChecked()) {
            RB_puntos_anclaje_V = RB_puntos_anclaje_otro.getText().toString();
        }
        if (RB_tipo_anclaje_RM16.isChecked()) {
            RB_tipo_anclaje_V = RB_tipo_anclaje_RM16.getText().toString();
        }
        if (RB_tipo_anclaje_tradicional.isChecked()) {
            RB_tipo_anclaje_V = RB_tipo_anclaje_tradicional.getText().toString();
        }
        if (RB_base_anclaje_no.isChecked()) {
            RB_base_anclaje_V = RB_base_anclaje_no.getText().toString();
        }
        if (RB_base_anclaje_si.isChecked()) {
            RB_base_anclaje_V = RB_base_anclaje_si.getText().toString();
        }
        if (RB_placa_seguridad_si.isChecked()) {
            RB_placa_seguridad_V = RB_placa_seguridad_si.getText().toString();
        }
        if (RB_placa_seguridad_no.isChecked()) {
            RB_placa_seguridad_V = RB_placa_seguridad_no.getText().toString();
        }
        if (RB_perno_apertura_si.isChecked()) {
            RB_perapertura_V = RB_perno_apertura_si.getText().toString();
        }
        if (RB_perno_apertura_no.isChecked()) {
            RB_perapertura_V = RB_perno_apertura_no.getText().toString();
        }
        if (RB_placa_seg_sitio_si.isChecked()) {
            RB_placa_seg_sitio_V = RB_placa_seg_sitio_si.getText().toString();
        }
        if (RB_placa_seg_sitio_no.isChecked()) {
            RB_placa_seg_sitio_V = RB_placa_seg_sitio_no.getText().toString();
        }
        if (RB_empotrado_si.isChecked()) {
            RB_empotrado_V = RB_empotrado_si.getText().toString();
        }
        if (RB_empotrado_no.isChecked()) {
            RB_empotrado_V = RB_empotrado_no.getText().toString();
        }
        if (RB_postes_ext_si.isChecked()) {
            RB_postes_ext_V = RB_postes_ext_si.getText().toString();
        }
        if (RB_postes_ext_no.isChecked()) {
            RB_postes_ext_V = RB_postes_ext_no.getText().toString();
        }
        if (RB_carcaza_si.isChecked()) {
            RB_carcaza_V = RB_carcaza_si.getText().toString();
        }
        if (RB_carcaza_no.isChecked()) {
            RB_carcaza_V = RB_carcaza_no.getText().toString();
        }
        if (RB_aire_si.isChecked()) {
            RB_aire_V = RB_aire_si.getText().toString();
        }
        if (RB_aire_no.isChecked()) {
            RB_aire_V = RB_aire_no.getText().toString();
        }
        if (RB_limpieza_buena.isChecked()) {
            RB_limpieza_V = RB_limpieza_buena.getText().toString();
        }
        if (RB_limpieza_mala.isChecked()) {
            RB_limpieza_V = RB_limpieza_mala.getText().toString();
        }
        if (RB_limpieza_regular.isChecked()) {
            RB_limpieza_V = RB_limpieza_regular.getText().toString();
        }
        if (RB_cesto_si.isChecked()) {
            RB_cesto_V = RB_cesto_si.getText().toString();
        }
        if (RB_cesto_no.isChecked()) {
            RB_cesto_V = RB_cesto_no.getText().toString();
        }
        if (RB_polvo_si.isChecked()) {
            RB_polvo_V = RB_polvo_si.getText().toString();
        }
        if (RB_polvo_no.isChecked()) {
            RB_polvo_V = RB_polvo_no.getText().toString();
        }
        if (RB_sol_si.isChecked()) {
            RB_sol_V = RB_sol_si.getText().toString();
        }
        if (RB_sol_no.isChecked()) {
            RB_sol_V = RB_sol_no.getText().toString();
        }
        if (RB_iluminacion_si.isChecked()) {
            RB_iluminacion_V = RB_iluminacion_si.getText().toString();
        }
        if (RB_iluminacion_no.isChecked()) {
            RB_iluminacion_V = RB_iluminacion_no.getText().toString();
        }
        if (RB_UPS_si.isChecked()) {
            RB_UPS_V = RB_UPS_si.getText().toString();
        }
        if (RB_UPS_no.isChecked()) {
            RB_UPS_V = RB_UPS_no.getText().toString();
        }
        if (RB_supresor_si.isChecked()) {
            RB_supresor_V = RB_supresor_si.getText().toString();
        }
        if (RB_supresor_no.isChecked()) {
            RB_supresor_V = RB_supresor_no.getText().toString();
        }

        if (RB_polarizacion_correcta_si.isChecked()) {
            RB_polarizacion_correcta_V = RB_polarizacion_correcta_si.getText().toString();
        }
        if (RB_polarizacion_correcta_no.isChecked()) {
            RB_polarizacion_correcta_V = RB_polarizacion_correcta_no.getText().toString();
        }
        if (RB_volt_regulado_sum_UPS.isChecked()) {
            RB_volt_regulado_sum_V = RB_volt_regulado_sum_UPS.getText().toString();
        }
        if (RB_volt_regulado_sum_regulador.isChecked()) {
            RB_volt_regulado_sum_V = RB_volt_regulado_sum_regulador.getText().toString();
        }

        if (RB_tipo_regulado_Twist_lock.isChecked()) {
            RB_tipo_regulado_V = RB_tipo_regulado_Twist_lock.getText().toString();
        }
        if (RB_tipo_regulado_normal.isChecked()) {
            RB_tipo_regulado_V = RB_tipo_regulado_normal.getText().toString();
        }
        if (RB_logo_si.isChecked()) {
            RB_logo_V = RB_logo_si.getText().toString();
        }
        if (RB_logo_no.isChecked()) {
            RB_logo_V = RB_logo_no.getText().toString();
        }
        if (RB_logo_deteriorada.isChecked()) {
            RB_logo_V = RB_logo_deteriorada.getText().toString();
        }
        if (RB_senal_lectora_si.isChecked()) {
            RB_senal_lectora_V = RB_senal_lectora_si.getText().toString();
        }
        if (RB_senal_lectora_no.isChecked()) {
            RB_senal_lectora_V = RB_senal_lectora_no.getText().toString();
        }
        if (RB_senal_lectora_deteriorada.isChecked()) {
            RB_senal_lectora_V = RB_senal_lectora_deteriorada.getText().toString();
        }
        if (RB_senal_impresora_si.isChecked()) {
            RB_senal_impresora_V = RB_senal_impresora_si.getText().toString();
        }
        if (RB_senal_impresora_no.isChecked()) {
            RB_senal_impresora_V = RB_senal_impresora_no.getText().toString();
        }
        if (RB_senal_impresora_deteriorada.isChecked()) {
            RB_senal_impresora_V = RB_senal_impresora_deteriorada.getText().toString();
        }
        if (RB_senal_salida_efectivo_si.isChecked()) {
            RB_senal_salida_efectivo_V = RB_senal_salida_efectivo_si.getText().toString();
        }
        if (RB_senal_salida_efectivo_no.isChecked()) {
            RB_senal_salida_efectivo_V = RB_senal_salida_efectivo_no.getText().toString();
        }
        if (RB_senal_salida_efectivo_deteriorada.isChecked()) {
            RB_senal_salida_efectivo_V = RB_senal_salida_efectivo_deteriorada.getText().toString();
        }
        if (RB_senal_carcaza_si.isChecked()) {
            RB_senal_carcaza_V = RB_senal_carcaza_si.getText().toString();
        }
        if (RB_senal_carcaza_no.isChecked()) {
            RB_senal_carcaza_V = RB_senal_carcaza_no.getText().toString();
        }
        if (RB_senal_carcaza_deteriorada.isChecked()) {
            RB_senal_carcaza_V = RB_senal_carcaza_deteriorada.getText().toString();
        }
        if (RB_edo_impresora_ok.isChecked()) {
            RB_edo_impresora_V = RB_edo_impresora_ok.getText().toString();
        }
        if (RB_edo_impresora_danado.isChecked()) {
            RB_edo_impresora_V = RB_edo_impresora_danado.getText().toString();
        }
        if (RB_edo_lectora_ok.isChecked()) {
            RB_edo_lectora_V = RB_edo_lectora_ok.getText().toString();
        }
        if (RB_edo_lectora_danado.isChecked()) {
            RB_edo_lectora_V = RB_edo_lectora_danado.getText().toString();
        }
        if (RB_edo_teclados_ok.isChecked()) {
            RB_edo_teclados_V = RB_edo_teclados_ok.getText().toString();
        }
        if (RB_edo_teclados_danado.isChecked()) {
            RB_edo_teclados_V = RB_edo_teclados_danado.getText().toString();
        }
        if (RB_edo_CPU_ok.isChecked()) {
            RB_edo_CPU_V = RB_edo_CPU_ok.getText().toString();
        }
        if (RB_edo_CPU_danado.isChecked()) {
            RB_edo_CPU_V = RB_edo_CPU_danado.getText().toString();
        }
        if (RB_edo_monitor_ok.isChecked()) {
            RB_edo_monitor_V = RB_edo_monitor_ok.getText().toString();
        }
        if (RB_edo_monitor_danado.isChecked()) {
            RB_edo_monitor_V = RB_edo_monitor_danado.getText().toString();
        }
        if (RB_edo_dispensador_ok.isChecked()) {
            RB_edo_dispensador_V = RB_edo_dispensador_ok.getText().toString();
        }
        if (RB_edo_dispensador_danado.isChecked()) {
            RB_edo_dispensador_V = RB_edo_dispensador_danado.getText().toString();
        }
        if (RB_satelital.isChecked()) {
            RB_tipo_comunicacion_V = RB_satelital.getText().toString();
        }
        if (RB_DIAL.isChecked()) {
            RB_tipo_comunicacion_V = RB_DIAL.getText().toString();
        }
        if (RB_ADSL.isChecked()) {
            RB_tipo_comunicacion_V = RB_ADSL.getText().toString();
        }

        if (RB_3_4_G.isChecked()) {
            RB_tipo_comunicacion_V = RB_3_4_G.getText().toString();
        }

        if (RB_edo_cableado_maltratado.isChecked()) {
            RB_edo_cableado_V = RB_edo_cableado_maltratado.getText().toString();
        }
        if (RB_edo_cableado_bueno.isChecked()) {
            RB_edo_cableado_V = RB_edo_cableado_bueno.getText().toString();
        }
        if (RB_edo_cableado_a_la_vista.isChecked()) {
            RB_edo_cableado_V = RB_edo_cableado_a_la_vista.getText().toString();
        }
        if (RB_conf_cableado_conex_directa.isChecked()) {
            RB_conf_cableado_conex_V = RB_conf_cableado_conex_directa.getText().toString();
        }
        if (RB_conf_cableado_conex_cruzado.isChecked()) {
            RB_conf_cableado_conex_V = RB_conf_cableado_conex_cruzado.getText().toString();
        }
        if (RB_equipo_con_alte_si.isChecked()) {
            RB_equipo_con_alte_V = RB_equipo_con_alte_si.getText().toString();
        }
        if (RB_equipo_con_alte_no.isChecked()) {
            RB_equipo_con_alte_V = RB_equipo_con_alte_no.getText().toString();
        }

        if (RB_pruebas_ping_si.isChecked()) {
            RB_pruebas_ping_V = RB_pruebas_ping_si.getText().toString();
        }
        if (RB_pruebas_ping_no.isChecked()) {
            RB_pruebas_ping_V = RB_pruebas_ping_no.getText().toString();
        }

        if (CB_CSDS.isChecked()) {
            CB_CSDS_V = CB_CSDS.getText().toString();
        }
        if (CB_CHECKER.isChecked()) {
            CB_CHECKER_V = CB_CHECKER.getText().toString();
        }
        if (CB_RKL.isChecked()) {
            CB_RKL_V = CB_RKL.getText().toString();
        }
        if (CB_FIX_WIN.isChecked()) {
            CB_FIX_WIN_V = CB_FIX_WIN.getText().toString();
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


















        final String reporte_F = reporte_V;
        final String id_tpv_F = id_tpv_V;
        final String localidad_F = localidad_V;
        final String marca_F = marca_V;
        final String modelo_F = modelo_V;
        final String carga_F = carga_V;
        final String serie_F = serie_V;
        final String inventario_F = inventario_V;
        final String cliente_final_F = cliente_final_V;
        final String sistema_operativo_F = sistema_operativo_V;
        final String version_checker_F = version_checker_V;
        final String version_multivendor_F = version_multivendor_V;
        final String puntos_anclaje_otro_F = puntos_anclaje_otro_V;
        final String carcaza_F = carcaza_V;
        final String temperatura_F = temperatura_V;
        final String UPS_kva_F = UPS_kva_V;
        final String regulador_kva_F = regulador_kva_V;
        final String fase_neutro_pared_F = fase_neutro_v_pared_V;
        final String fase_neutro_regulado_F = fase_neutro_v_regulado_V;
        final String fase_tierra_pared_F = fase_tierra_v_pared_V;
        final String fase_tierra_regulado_F = fase_tierra_v_regulado_V;
        final String tierra_neutro_pared_F = tierra_neutro_v_pared_V;
        final String tierra_neutro_regulado_F = tierra_neutro_v_regulado_V;
        final String notas_seguridad_F = notas_seguridad_V;
        final String notas_inst_elect_F = notas_inst_elect_V;
        final String notas_comunic_F = notas_comunic_V;
        final String ob_edo_impresora_F = ob_edo_impresora_V;
        final String ob_edo_lectora_F = ob_edo_lectora_V;
        final String ob_edo_teclados_F = ob_edo_teclados_V;
        final String ob_edo_CPU_F = ob_edo_CPU_V;
        final String ob_edo_monitor_F = ob_edo_monitor_V;
        final String ob_edo_dispensador_F = ob_edo_dispensador_V;
        final String marca_router_F = marca_router_V;
        final String longitud_cable_F = longitud_cable_V;
        final String pruebas_ping_result_F = pruebas_ping_result_V;


        final String RB_servicio_F = RB_servicio_V;

        final String RB_tipo_dial__F = RB_tipo_dial_V;

        final String RB_cerradura_F = RB_cerradura_V;
        final String RB_puntos_anclaje_F = RB_puntos_anclaje_V;

        final String RB_tipo_anclaje_F = RB_tipo_anclaje_V;
        final String RB_base_anclaje_F = RB_base_anclaje_V;
        final String RB_placa_seguridad_F = RB_placa_seguridad_V;
        final String RB_perapertura_F = RB_perapertura_V;
        final String RB_placa_seg_sitio_F = RB_placa_seg_sitio_V;
        final String RB_empotrado_F = RB_empotrado_V;
        final String RB_postes_ext_F = RB_postes_ext_V;
        final String RB_carcaza_F = RB_carcaza_V;
        final String RB_aire_F = RB_aire_V;
        final String RB_limpieza_F = RB_limpieza_V;

        final String RB_cesto_F = RB_cesto_V;
        final String RB_polvo_F = RB_polvo_V;
        final String RB_sol_F = RB_sol_V;
        final String RB_iluminacion_F = RB_iluminacion_V;
        final String RB_UPS_F = RB_UPS_V;
        final String RB_supresor_F = RB_supresor_V;

        final String RB_polarizacion_correcta_F = RB_polarizacion_correcta_V;
        final String RB_volt_regulado_sum_F = RB_volt_regulado_sum_V;
        final String RB_tipo_regulado_F = RB_tipo_regulado_V;
        final String RB_logo_F = RB_logo_V;
        final String RB_senal_lectora_F = RB_senal_lectora_V;
        final String RB_senal_impresora_F = RB_senal_impresora_V;
        final String RB_senal_salida_efectivo_F = RB_senal_salida_efectivo_V;
        final String RB_senal_carcaza_F = RB_senal_carcaza_V;
        final String RB_edo_impresora_F = RB_edo_impresora_V;
        final String RB_edo_lectora_F = RB_edo_lectora_V;
        final String RB_edo_teclados_F = RB_edo_teclados_V;
        final String RB_edo_CPU_F = RB_edo_CPU_V;
        final String RB_edo_monitor_F = RB_edo_monitor_V;
        final String RB_edo_dispensador_F = RB_edo_dispensador_V;
        final String RB_tipo_comunicacion_F = RB_tipo_comunicacion_V;
        final String RB_edo_cableado_F = RB_edo_cableado_V;
        final String RB_conf_cableado_conex_F = RB_conf_cableado_conex_V;
        final String RB_equipo_con_alte_F = RB_equipo_con_alte_V;

        final String RB_pruebas_ping_F = RB_pruebas_ping_V;
        final String CB_CSDS_F = CB_CSDS_V;
        final String CB_CHECKER_F = CB_CHECKER_V;
        final String CB_RKL_F = CB_RKL_V;
        final String CB_FIX_WIN_F = CB_FIX_WIN_V;


        final String RB_banco_F = RB_banco_V;
        final String RB_remo_suc_F = RB_remo_suc_V;




        final String sp_tipo_dial_F = sp_tipo_dial_V;


        final String date = fecha.getText().toString();
        final String usuario = usuarioid;
        final String ubicacion = "mexico2";




        ////////////////////////////valida datos obligatorios




            key = database.child(pais).child(idATM).child("preventivos").push().getKey();






        reporte_child=reporte.getText().toString();


        Post post = new Post(RB_banco_F, RB_remo_suc_F, usuario, idkey, date, CB_FIX_WIN_F, CB_RKL_F, CB_CHECKER_F, CB_CSDS_F, RB_pruebas_ping_F,  RB_equipo_con_alte_F, RB_conf_cableado_conex_F, RB_edo_cableado_F, RB_tipo_comunicacion_F, RB_edo_dispensador_F, RB_edo_monitor_F, RB_edo_CPU_F, RB_edo_teclados_F, RB_edo_lectora_F, RB_edo_impresora_F, RB_senal_carcaza_F, RB_senal_salida_efectivo_F, RB_senal_impresora_F, RB_senal_lectora_F, RB_logo_F, RB_tipo_regulado_F, RB_volt_regulado_sum_F, RB_polarizacion_correcta_F,  RB_supresor_F, RB_UPS_F, RB_iluminacion_F, RB_sol_F, RB_polvo_F, RB_cesto_F, RB_limpieza_F, RB_aire_F, RB_carcaza_F, RB_postes_ext_F, RB_empotrado_F, RB_placa_seg_sitio_F, RB_perapertura_F, RB_placa_seguridad_F, RB_base_anclaje_F, RB_tipo_anclaje_F, RB_puntos_anclaje_F, RB_cerradura_F, RB_tipo_dial__F, RB_servicio_F,  pruebas_ping_result_F, longitud_cable_F, marca_router_F, ob_edo_dispensador_F, ob_edo_monitor_F, ob_edo_CPU_F, ob_edo_teclados_F, ob_edo_lectora_F, ob_edo_impresora_F, notas_comunic_F, notas_inst_elect_F, notas_seguridad_F, tierra_neutro_regulado_F, tierra_neutro_pared_F, fase_tierra_regulado_F, fase_tierra_pared_F, fase_neutro_regulado_F, fase_neutro_pared_F, regulador_kva_F, UPS_kva_F, temperatura_F, carcaza_F, puntos_anclaje_otro_F, version_multivendor_F, version_checker_F, sistema_operativo_F, cliente_final_F, inventario_F, serie_F, carga_F, modelo_F, marca_F, localidad_F, id_tpv_F, reporte_F);


        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        //childUpdates.put(codigo + key, postValues);


        childUpdates.put("checklist", postValues);

        database.child(pais).child("ATMs").child(idATM).child("preventivos").child(reporte_child).updateChildren(childUpdates);


    }
private String reporte_child;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private ValueEventListener eventListener;

    private String key="";


    @IgnoreExtraProperties
    public class Post {

        public String date;
        public String usuario;
        public String ubicacion;
        public String idkey;


        public String reporte_F;
        public String id_tpv_F;
        public String localidad_F;
        public String marca_F;
        public String modelo_F;
        public String carga_F;
        public String serie_F;
        public String inventario_F;
        public String cliente_final_F;
        public String sistema_operativo_F;
        public String version_checker_F;
        public String version_multivendor_F;
        public String puntos_anclaje_otro_F;
        public String carcaza_F;
        public String temperatura_F;
        public String UPS_kva_F;
        public String regulador_kva_F;
        public String fase_neutro_pared_F;
        public String fase_neutro_regulado_F;
        public String fase_tierra_pared_F;
        public String fase_tierra_regulado_F;
        public String tierra_neutro_pared_F;
        public String tierra_neutro_regulado_F;
        public String notas_seguridad_F;
        public String notas_inst_elect_F;
        public String notas_comunic_F;
        public String ob_edo_impresora_F;
        public String ob_edo_lectora_F;
        public String ob_edo_teclados_F;
        public String ob_edo_CPU_F;
        public String ob_edo_monitor_F;
        public String ob_edo_dispensador_F;
        public String marca_router_F;
        public String longitud_cable_F;
        public String pruebas_ping_result_F;


        public String RB_servicio_F;
        public String RB_Personal_F;
        public String RB_tipo_dial__F;

        public String RB_cerradura_F;
        public String RB_puntos_anclaje_F;

        public String RB_tipo_anclaje_F;
        public String RB_base_anclaje_F;
        public String RB_placa_seguridad_F;
        public String RB_perapertura_F;
        public String RB_placa_seg_sitio_F;
        public String RB_empotrado_F;
        public String RB_postes_ext_F;
        public String RB_carcaza_F;
        public String RB_aire_F;
        public String RB_limpieza_F;

        public String RB_cesto_F;
        public String RB_polvo_F;
        public String RB_sol_F;
        public String RB_iluminacion_F;
        public String RB_UPS_F;
        public String RB_supresor_F;

        public String RB_polarizacion_correcta_F;
        public String RB_Folt_regulado_sum_F;
        public String RB_tipo_regulado_F;
        public String RB_logo_F;
        public String RB_senal_lectora_F;
        public String RB_senal_impresora_F;
        public String RB_senal_salida_efectivo_F;
        public String RB_senal_carcaza_F;
        public String RB_edo_impresora_F;
        public String RB_edo_lectora_F;
        public String RB_edo_teclados_F;
        public String RB_edo_CPU_F;
        public String RB_edo_monitor_F;
        public String RB_edo_dispensador_F;
        public String RB_tipo_comunicacion_F;
        public String RB_edo_cableado_F;
        public String RB_conf_cableado_conex_F;
        public String RB_equipo_con_alte_F;

        public String RB_pruebas_ping_F;

        public String RB_banco_F;
        public String RB_remo_suc_F;



        public String CB_CSDS_F;
        public String CB_CHECKER_F;
        public String CB_RKL_F;
        public String CB_FIX_WIN_F;
        public String sp_tipo_dial_F;


        public Post() {
            // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        }

        public Post(String RB_banco_F, String RB_remo_suc_F, String usuario, String idkey, String date, String CB_FIX_WIN_F, String CB_RKL_F, String CB_CHECKER_F, String CB_CSDS_F, String RB_pruebas_ping_F,  String RB_equipo_con_alte_F, String RB_conf_cableado_conex_F, String RB_edo_cableado_F, String RB_tipo_comunicacion_F, String RB_edo_dispensador_F, String RB_edo_monitor_F, String RB_edo_CPU_F, String RB_edo_teclados_F, String RB_edo_lectora_F, String RB_edo_impresora_F, String RB_senal_carcaza_F, String RB_senal_salida_efectivo_F, String RB_senal_impresora_F, String RB_senal_lectora_F, String RB_logo_F, String RB_tipo_regulado_F, String RB_Folt_regulado_sum_F, String RB_polarizacion_correcta_F,  String RB_supresor_F, String RB_UPS_F, String RB_iluminacion_F, String RB_sol_F, String RB_polvo_F, String RB_cesto_F, String RB_limpieza_F, String RB_aire_F, String RB_carcaza_F, String RB_postes_ext_F, String RB_empotrado_F, String RB_placa_seg_sitio_F, String RB_perapertura_F, String RB_placa_seguridad_F, String RB_base_anclaje_F, String RB_tipo_anclaje_F, String RB_puntos_anclaje_F, String RB_cerradura_F, String RB_tipo_dial__F, String RB_servicio_F,  String pruebas_ping_result_F, String longitud_cable_F, String marca_router_F, String ob_edo_dispensador_F, String ob_edo_monitor_F, String ob_edo_CPU_F, String ob_edo_teclados_F, String ob_edo_lectora_F, String ob_edo_impresora_F, String notas_comunic_F, String notas_inst_elect_F, String notas_seguridad_F, String tierra_neutro_regulado_F, String tierra_neutro_pared_F, String fase_tierra_regulado_F, String fase_tierra_pared_F, String fase_neutro_regulado_F, String fase_neutro_pared_F, String regulador_kva_F, String UPS_kva_F, String temperatura_F, String carcaza_F, String puntos_anclaje_otro_F, String version_multivendor_F, String version_checker_F, String sistema_operativo_F, String cliente_final_F, String inventario_F, String serie_F, String carga_F, String modelo_F, String marca_F, String localidad_F, String id_tpv_F, String reporte_F) {
            //////////////////////////////////////////////aqui poner variables
            this.date = date;
            this.usuario = usuario;
            this.ubicacion = ubicacion;
            this.idkey = idkey;

            this.reporte_F = reporte_F;
            this.id_tpv_F = id_tpv_F;
            this.localidad_F = localidad_F;
            this.marca_F = marca_F;
            this.modelo_F = modelo_F;
            this.carga_F = carga_F;
            this.serie_F = serie_F;
            this.inventario_F = inventario_F;
            this.cliente_final_F = cliente_final_F;
            this.sistema_operativo_F = sistema_operativo_F;
            this.version_checker_F = version_checker_F;
            this.version_multivendor_F = version_multivendor_F;
            this.puntos_anclaje_otro_F = puntos_anclaje_otro_F;
            this.carcaza_F = carcaza_F;
            this.temperatura_F = temperatura_F;
            this.UPS_kva_F = UPS_kva_F;
            this.regulador_kva_F = regulador_kva_F;
            this.fase_neutro_pared_F = fase_neutro_pared_F;
            this.fase_neutro_regulado_F = fase_neutro_regulado_F;
            this.fase_tierra_pared_F = fase_tierra_pared_F;
            this.fase_tierra_regulado_F = fase_tierra_regulado_F;
            this.tierra_neutro_pared_F = tierra_neutro_pared_F;
            this.tierra_neutro_regulado_F = tierra_neutro_regulado_F;
            this.notas_seguridad_F = notas_seguridad_F;
            this.notas_inst_elect_F = notas_inst_elect_F;
            this.notas_comunic_F = notas_comunic_F;
            this.ob_edo_impresora_F = ob_edo_impresora_F;
            this.ob_edo_lectora_F = ob_edo_lectora_F;
            this.ob_edo_teclados_F = ob_edo_teclados_F;
            this.ob_edo_CPU_F = ob_edo_CPU_F;
            this.ob_edo_monitor_F = ob_edo_monitor_F;
            this.ob_edo_dispensador_F = ob_edo_dispensador_F;
            this.marca_router_F = marca_router_F;
            this.longitud_cable_F = longitud_cable_F;
            this.pruebas_ping_result_F = pruebas_ping_result_F;



            this.RB_tipo_dial__F = RB_tipo_dial__F;

            this.RB_servicio_F = RB_servicio_F;
            this.RB_cerradura_F = RB_cerradura_F;
            this.RB_puntos_anclaje_F = RB_puntos_anclaje_F;
            this.RB_tipo_anclaje_F = RB_tipo_anclaje_F;
            this.RB_base_anclaje_F = RB_base_anclaje_F;
            this.RB_placa_seguridad_F = RB_placa_seguridad_F;
            this.RB_perapertura_F = RB_perapertura_F;
            this.RB_placa_seg_sitio_F = RB_placa_seg_sitio_F;
            this.RB_empotrado_F = RB_empotrado_F;
            this.RB_postes_ext_F = RB_postes_ext_F;
            this.RB_carcaza_F = RB_carcaza_F;
            this.RB_aire_F = RB_aire_F;
            this.RB_limpieza_F = RB_limpieza_F;
            this.RB_cesto_F = RB_cesto_F;
            this.RB_polvo_F = RB_polvo_F;
            this.RB_sol_F = RB_sol_F;
            this.RB_iluminacion_F = RB_iluminacion_F;
            this.RB_UPS_F = RB_UPS_F;
            this.RB_supresor_F = RB_supresor_F;

            this.RB_polarizacion_correcta_F = RB_polarizacion_correcta_F;
            this.RB_Folt_regulado_sum_F = RB_Folt_regulado_sum_F;
            this.RB_tipo_regulado_F = RB_tipo_regulado_F;
            this.RB_logo_F = RB_logo_F;
            this.RB_senal_lectora_F = RB_senal_lectora_F;
            this.RB_senal_impresora_F = RB_senal_impresora_F;
            this.RB_senal_salida_efectivo_F = RB_senal_salida_efectivo_F;
            this.RB_senal_carcaza_F = RB_senal_carcaza_F;
            this.RB_edo_impresora_F = RB_edo_impresora_F;
            this.RB_edo_lectora_F = RB_edo_lectora_F;
            this.RB_edo_teclados_F = RB_edo_teclados_F;
            this.RB_edo_CPU_F = RB_edo_CPU_F;
            this.RB_edo_monitor_F = RB_edo_monitor_F;
            this.RB_edo_dispensador_F = RB_edo_dispensador_F;
            this.RB_tipo_comunicacion_F = RB_tipo_comunicacion_F;
            this.RB_edo_cableado_F = RB_edo_cableado_F;
            this.RB_conf_cableado_conex_F = RB_conf_cableado_conex_F;
            this.RB_equipo_con_alte_F = RB_equipo_con_alte_F;

            this.RB_pruebas_ping_F = RB_pruebas_ping_F;


            this.RB_banco_F = RB_banco_F;
            this.RB_remo_suc_F = RB_remo_suc_F;





            this.CB_CSDS_F = CB_CSDS_F;
            this.CB_CHECKER_F = CB_CHECKER_F;
            this.CB_RKL_F = CB_RKL_F;
            this.CB_FIX_WIN_F = CB_FIX_WIN_F;
            this.sp_tipo_dial_F = sp_tipo_dial_F;


        }

        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("IDkey", idkey);
            result.put("Fecha", date);
            result.put("Usuario", usuario);


            result.put("aire acondicionado", RB_aire_F);

            result.put("ATM da servicio a", RB_Personal_F);
            result.put("ATM da servicio a", RB_servicio_V);
            result.put("base de anclaje", RB_base_anclaje_F);
            result.put("carcaza", RB_carcaza_F);
            result.put("carga", carga_F);
            result.put("cerradura tipo H", RB_cerradura_F);
            result.put("cesto de basura", RB_cesto_F);
            result.put("cliente final", cliente_final_F);
            result.put("configuracion cable de red", RB_conf_cableado_conex_F);
            result.put("empotrado al muro", RB_empotrado_F);
            result.put("equipo de comunicación alertado", RB_equipo_con_alte_F);
            result.put("especifique carcaza", carcaza_F);
            result.put("estado CPU observaciones", ob_edo_CPU_F);
            result.put("estado CPU", RB_edo_CPU_F);
            result.put("estado del cableado", RB_edo_cableado_F);
            result.put("estado dispensador observaciones", ob_edo_dispensador_F);
            result.put("estado dispensador", RB_edo_dispensador_F);
            result.put("estado impresora observaciones", ob_edo_impresora_F);
            result.put("estado impresora", RB_edo_impresora_F);
            result.put("estado lectora observaciones", ob_edo_lectora_F);
            result.put("estado lectora", RB_edo_lectora_F);
            result.put("estado monitor observaciones", ob_edo_monitor_F);
            result.put("estado monitor", RB_edo_monitor_F);
            result.put("estado teclados observaciones", ob_edo_teclados_F);
            result.put("estado teclados", RB_edo_teclados_F);
            result.put("expuesto a polvo", RB_polvo_F);
            result.put("expuesto a sol", RB_sol_F);
            result.put("fase neutro voltaje pared", fase_neutro_pared_F);
            result.put("fase neutro voltaje regulado", fase_neutro_regulado_F);
            result.put("fase tierra voltaje pared", fase_tierra_pared_F);
            result.put("fase tierra voltaje regulado", fase_tierra_regulado_F);
            result.put("herramientas de seguridad instaladas h1", CB_CSDS_F);
            result.put("herramientas de seguridad instaladas h2", CB_CHECKER_F);
            result.put("herramientas de seguridad instaladas h3", CB_RKL_F);
            result.put("herramientas de seguridad instaladas h4", CB_FIX_WIN_F);
            result.put("id atm", id_tpv_F);
            result.put("iluminacion", RB_iluminacion_F);
            result.put("KVA regulador", regulador_kva_F);
            result.put("KVA UPS", UPS_kva_F);
            result.put("limpieza del sitio", RB_limpieza_F);

            result.put("localidad", localidad_F);
            result.put("logo", RB_logo_F);
            result.put("longitud cable", longitud_cable_F);
            result.put("marca router", marca_router_F);
            result.put("marca", marca_F);
            result.put("modelo", modelo_F);
            result.put("notas comunicaciones", notas_comunic_F);
            result.put("notas instalacion electrica", notas_inst_elect_F);
            result.put("notas seguridad", notas_seguridad_F);
            result.put("numero de inventario", inventario_F);
            result.put("perno de apertura", RB_perapertura_F);
            result.put("placa de seguridad sitio", RB_placa_seg_sitio_F);
            result.put("placa de seguridad", RB_placa_seguridad_F);
            result.put("polarizacion correcta", RB_polarizacion_correcta_F);
            result.put("postes exteriores", RB_postes_ext_F);
            result.put("pruebas ping resultado", pruebas_ping_result_F);
            result.put("pruebas ping", RB_pruebas_ping_F);
            result.put("puntos de anclaje otros", puntos_anclaje_otro_F);
            result.put("puntos de anclaje", RB_puntos_anclaje_F);
            result.put("reporte", reporte_F);

            result.put("señal carcaza", RB_senal_carcaza_F);
            result.put("señal impresora", RB_senal_impresora_F);
            result.put("señal lectora", RB_senal_lectora_F);
            result.put("señal salida efectivo", RB_senal_salida_efectivo_F);
            result.put("serie", serie_F);
            result.put("sistema operativo", sistema_operativo_F);
            result.put("supresor de picos", RB_supresor_F);
            result.put("temperatura", temperatura_F);
            result.put("tierra neutro voltaje pared", tierra_neutro_pared_F);
            result.put("tierra neutro voltaje regulado", tierra_neutro_regulado_F);
            result.put("tipo de anclaje", RB_tipo_anclaje_F);

            result.put("tipo de comunicación", RB_tipo_comunicacion_F);
            result.put("tipo de dial", RB_tipo_dial__F);

            result.put("tipo voltaje regulado", RB_tipo_regulado_F);
            result.put("UPS", RB_UPS_F);
            result.put("version checker", version_checker_F);
            result.put("version multivendor", version_multivendor_F);
            result.put("voltaje regulado suministrado por", RB_Folt_regulado_sum_F);


            result.put("banco", RB_banco_F);
            result.put("remoto_sucursal", RB_remo_suc_F);








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



    //TODO https://expocodetech.com/como-crear-una-lista-con-recyclerview/                 //////////////////////////////// http://www.hermosaprogramacion.com/2015/02/android-recyclerview-cardview/



    FirebaseStorage storage = FirebaseStorage.getInstance();










    public  void subir_imagen(View view){

        i_photos=i_photos+1;

        // valida q reporte y ID existan

        String reporte_photos = reporte.getText().toString();
        String ID_atm_photos = id_tpv.getText().toString();





        if (reporte.getText().toString().equals("")) {

            Context context = getApplicationContext();
            CharSequence text = "Favor documentar el reporte";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;


        }

        if (id_tpv.getText().toString().equals("")) {

            Context context = getApplicationContext();
            CharSequence text = "Favor documentar el ID de ATM";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;


        }


        if (flag_foto==0) {

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


        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

// Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child(pais).child("ATM_Prev/"+ID_atm_photos+"/"+reporte_photos+"_"+i_photos+".jpg");

// Create a reference to 'images/mountains.jpg'


        photo_ATM_prev.setDrawingCacheEnabled(true);
        photo_ATM_prev.buildDrawingCache();

        Bitmap bitmap = photo_ATM_prev.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);



        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                urlPhoto= downloadUrl.toString();
                url_photo.setText(urlPhoto);

            }
        });


        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Upload is " + progress + "% done");



                if (progress==100){


                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {


                            public void run() {




                                Picasso.with(contexto).load(urlPhoto).into(fotito);
                                idATM=id_tpv.getText().toString();

                                // actualizo la base si no hay chil lo creo

                                String key2;



                                key = database.child(pais).child(idATM).child("preventivos").push().getKey();

                                 notas_post=notas_image.getText().toString();

                                reporte_child=reporte.getText().toString();
                                idATM=id_tpv.getText().toString();



                                key2 = database.child(pais).child(idATM).child("preventivos").child("imagenes").push().getKey();

                                i_photos_post=""+i_photos;

                                Post_2 post_2 = new Post_2(urlPhoto, notas_post, i_photos_post);
                                Map<String, Object> post_2Values = post_2.toMap();

                                Map<String, Object> childUpdates = new HashMap<>();
                                //childUpdates.put(codigo + key, postValues);


                                childUpdates.put(""+i_photos, post_2Values);

                                database.child(pais).child("ATMs").child(idATM).child("preventivos").child(reporte_child).child("imagenes").updateChildren(childUpdates);



                                //database.child("ATMs").child(idATM).child(reporte_child).child("imagenes").child(""+i_photos).put(post);

                                //database.child("ATMs").child(idATM).child("imagenes").child(key2).setValue("ob_foto",notas_image);


                                añadir_im_recicler();

                            }
                        }, 2000);





                }




            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
            }
        });







    }
private String  i_photos_post;
    @IgnoreExtraProperties
    public class Post_2{

        public String urlPhoto;
        public String notas_post;
        public String i_photos_post;

        public Map<String, Boolean> stars = new HashMap<>();

        public Post_2() {
            // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        }

        public Post_2(String urlPhoto, String notas_post, String i_photos_post) {
            this.urlPhoto = urlPhoto;
            this.notas_post = notas_post;
            this.i_photos_post = i_photos_post;

        }

        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("url", urlPhoto);
            result.put("observaciones", notas_post);
            result.put("id_img", i_photos_post);


            return result;
        }

    }

    private  Context contexto = this;
    private int flag_foto;

   private String urlPhoto;
private  String notas_post;
    private  List items = new ArrayList();

    public  void añadir_im_recicler(){






        items.add(new Anime(urlPhoto, notas_image.getText().toString()));


// Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

// Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

// Crear un nuevo adaptador
        adapter = new AnimeAdapter(items);
        recycler.setAdapter(adapter);



        flag_foto=0;
        notas_image.setText("");
        progressBarPhoto.setVisibility(View.GONE);
        photo_ATM_prev.setImageResource(R.drawable.ic_shutter);


    }





}













