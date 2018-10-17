package com.example.carlosje.reportes_ibm;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.lang.String.valueOf;


public class ATMprevActivity extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{


    private Boolean flag_banco;
    private String idATM;
    private String comas=  "\"";
    private String REV;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private ProgressBar progressBarPhoto;
    private NestedScrollView nestedScrollView;
    private CardView card1, card2, card3, card4, card5, card6;
    private ImageView photo_ATM_prev,fotito;
    private Button bt_nuevo, bt_buscar, bt_fecha;
    private LinearLayout lay_buscar, lay_nuevo, lay_gral;
    private FloatingActionButton fl_btn_add_photo, fl_btn_save_atm_prev,fl_btn_respaldo;
    private String usuarioid;
    private TextView tv_user, fecha,url_photo, mLatitude,mLongitude,mPosicion,TV_sc,TV_ac,TV_ac_label,TV_fecha_ini,TV_fecha_fin,TV_hr_ini,TV_hr_fin;
    private EditText notas_image, reporte, id_tpv, localidad, marca, modelo, serie, carga, inventario, cliente_final, sistema_operativo, version_multivendor, version_checker, puntos_anclaje_otro;
    private EditText carcaza, temperatura, UPS_kva, regulador_kva, fase_neutro_v_pared, fase_neutro_v_regulado, fase_tierra_v_pared, fase_tierra_v_regulado, tierra_neutro_v_pared, tierra_neutro_v_regulado;
    private EditText notas_seguridad, notas_inst_elect, notas_comunic;
    private EditText ob_edo_impresora, ob_edo_lectora, ob_edo_teclados, ob_edo_CPU, ob_edo_monitor, ob_edo_dispensador;
    private EditText marca_router, longitud_cable, pruebas_ping_result, vobo,puesto, folio,solucion,banco_otro;
    private EditText capacidad_hd, RAM, nombre_atm, ob_esp_sitio, obs_telecontrol, tamaño_monitor, procesador, vel_procesador, version_antivirus;
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
    private RadioButton RB_empotrado_si, RB_empotrado_no, RB_banco_banamex, RB_banco_bancomer ,RB_banco_banorte, RB_banco_bancoppel, RB_banco_santander,RB_banco_CCK, RB_remo_suc_remoto, RB_remo_suc_sucursal ;
    private RadioButton RB_ADSL, RB_3_4_G,RB_regulador_si,RB_regulador_no,RB_banco_otro;
    private RadioButton RB_01800_cambio, RB_01800_deteriorada, RB_01800_no, RB_01800_si, RB_antiskimmer_ebras, RB_antiskimmer_no, RB_antiskimmer_si, RB_blindaje_cab_no, RB_blindaje_cab_si, RB_braile_no, RB_braile_si, RB_caseta_prefab_no, RB_caseta_prefab_si, RB_chapa_rand_no, RB_chapa_rand_si, RB_entintado_billete_no, RB_entintado_billete_si, RB_jumper_no, RB_jumper_si, RB_logo_cambio, RB_nicho_de_protec_no, RB_nicho_de_protec_si, RB_tipo_placa_antivand_der, RB_tipo_placa_antivand_izq, RB_placa_antivand_no, RB_placa_antivand_si, RB_rack_no, RB_rack_si, RB_seg_shutter_no, RB_seg_shutter_si, RB_senal_carcaza_cambio, RB_senal_impresora_cambio, RB_senal_lectora_cambio, RB_senal_salida_efectivo_cambio, RB_telecontrol_conect_dañado, RB_telecontrol_conect_no, RB_telecontrol_conect_si, RB_tipo_telecontrol_IBOOT, RB_tipo_telecontrol_MTC, RB_tipo_telecontrol_Ninguno;
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
    private String RB_senal_01800_V;
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
    private String RB_regulador_V;
    private String RB_antiskimmer_V;
    private String RB_blindaje_V;
    private String RB_braile_V;
    private String RB_caseta_prefab_V;
    private String RB_chapa_rand_V;
    private String RB_entintado_billete_V;
    private String RB_jumper_V;
    private String RB_nicho_de_protec_V;
    private String RB_tipo_placa_antivand_V;
    private String RB_placa_antivand_V;
    private String RB_rack_V;
    private String RB_seg_shutter_V;
    private String RB_telecontrol_conect_V;
    private String RB_tipo_telecontrol_V;
    private String CB_CSDS_V;
    private String CB_CHECKER_V;
    private String CB_RKL_V;
    private String CB_FIX_WIN_V;
    private CheckBox CB_CSDS, CB_CHECKER, CB_RKL, CB_FIX_WIN;
    private Location mLastLocation;
    private  String prim_child;
    RequestQueue requestQueue;
    private String tipo_fecha="";
    private String hr_ini_V, hr_fin_V, fecha_ini_V, fecha_fin_V, SC_V, AC_V;
    private String cual_campo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atmprev);

        requestQueue = RequestQueueSingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        prim_child="ATMs";
        pais="México";
        mLatitude = (TextView) findViewById(R.id.mLatitude);
        mLongitude = (TextView) findViewById(R.id.mLongitude);


        usuarioid = getIntent().getStringExtra("usuario");
        fl_btn_add_photo = (FloatingActionButton) findViewById(R.id.fl_btn_add_photo);
        fl_btn_save_atm_prev = (FloatingActionButton) findViewById(R.id.fl_btn_save_atm_prev);
        fl_btn_respaldo= (FloatingActionButton) findViewById(R.id.fl_btn_respaldo);
        photo_ATM_prev = (ImageView) findViewById(R.id.photo_ATM_prev);
        fotito= (ImageView) findViewById(R.id.fotito);
        nestedScrollView=(NestedScrollView) findViewById(R.id.nestedScrollView);
        progressBarPhoto=(ProgressBar) findViewById(R.id.progressBarPhoto);
        card1 = (CardView) findViewById(R.id.card1);
        card2 = (CardView) findViewById(R.id.card2);
        card3 = (CardView) findViewById(R.id.card3);
        card4 = (CardView) findViewById(R.id.card4);
        card5 = (CardView) findViewById(R.id.card5);
        card6 = (CardView) findViewById(R.id.card6);
        TV_fecha_ini= (TextView) findViewById(R.id.TV_fecha_ini);
        TV_fecha_fin= (TextView) findViewById(R.id.TV_fecha_fin);
        TV_hr_fin= (TextView) findViewById(R.id.TV_hr_fin);
        TV_hr_ini= (TextView) findViewById(R.id.TV_hr_ini);
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
        vobo = (EditText) findViewById(R.id.vobo);
        puesto= (EditText) findViewById(R.id.puesto);
        folio= (EditText) findViewById(R.id.folio);
        solucion= (EditText) findViewById(R.id.solucion);
        ob_edo_impresora = (EditText) findViewById(R.id.ob_edo_impresora);
        ob_edo_lectora = (EditText) findViewById(R.id.ob_edo_lectora);
        ob_edo_teclados = (EditText) findViewById(R.id.ob_edo_teclados);
        ob_edo_CPU = (EditText) findViewById(R.id.ob_edo_CPU);
        ob_edo_monitor = (EditText) findViewById(R.id.ob_edo_monitor);
        ob_edo_dispensador = (EditText) findViewById(R.id.ob_edo_dispensador);
        marca_router = (EditText) findViewById(R.id.marca_router);
        longitud_cable = (EditText) findViewById(R.id.longitud_cable);
        pruebas_ping_result = (EditText) findViewById(R.id.pruebas_ping_result);
        capacidad_hd=(EditText) findViewById(R.id.capacidad_hd);
        RAM=(EditText) findViewById(R.id.RAM);
        nombre_atm=(EditText) findViewById(R.id.nombre_atm);
        ob_esp_sitio=(EditText) findViewById(R.id.ob_esp_sitio);
        obs_telecontrol=(EditText) findViewById(R.id.obs_telecontrol);
        tamaño_monitor=(EditText) findViewById(R.id.tamaño_monitor);
        procesador=(EditText) findViewById(R.id.procesador);
        vel_procesador=(EditText) findViewById(R.id.vel_procesador);
        version_antivirus=(EditText) findViewById(R.id.version_antivirus);
        RB_01800_cambio=(RadioButton) findViewById(R.id.RB_01800_cambio);
        RB_01800_deteriorada=(RadioButton) findViewById(R.id.RB_01800_deteriorada);
        RB_01800_no=(RadioButton) findViewById(R.id.RB_01800_no);
        RB_01800_si=(RadioButton) findViewById(R.id.RB_01800_si);
        RB_antiskimmer_ebras=(RadioButton) findViewById(R.id.RB_antiskimmer_ebras);
        RB_antiskimmer_no=(RadioButton) findViewById(R.id.RB_antiskimmer_no);
        RB_antiskimmer_si=(RadioButton) findViewById(R.id.RB_antiskimmer_si);
        RB_blindaje_cab_no=(RadioButton) findViewById(R.id.RB_blindaje_cab_no);
        RB_blindaje_cab_si=(RadioButton) findViewById(R.id.RB_blindaje_cab_si);
        RB_braile_no=(RadioButton) findViewById(R.id.RB_braile_no);
        RB_braile_si=(RadioButton) findViewById(R.id.RB_braile_si);
        RB_caseta_prefab_no=(RadioButton) findViewById(R.id.RB_caseta_prefab_no);
        RB_caseta_prefab_si=(RadioButton) findViewById(R.id.RB_caseta_prefab_si);
        RB_chapa_rand_no=(RadioButton) findViewById(R.id.RB_chapa_rand_no);
        RB_chapa_rand_si=(RadioButton) findViewById(R.id.RB_chapa_rand_si);
        RB_entintado_billete_no=(RadioButton) findViewById(R.id.RB_entintado_billete_no);
        RB_entintado_billete_si=(RadioButton) findViewById(R.id.RB_entintado_billete_si);
        RB_jumper_no=(RadioButton) findViewById(R.id.RB_jumper_no);
        RB_jumper_si=(RadioButton) findViewById(R.id.RB_jumper_si);
        RB_logo_cambio=(RadioButton) findViewById(R.id.RB_logo_cambio);
        RB_nicho_de_protec_no=(RadioButton) findViewById(R.id.RB_nicho_de_protec_no);
        RB_nicho_de_protec_si=(RadioButton) findViewById(R.id.RB_nicho_de_protec_si);
        RB_tipo_placa_antivand_der=(RadioButton) findViewById(R.id.RB_tipo_placa_antivand_der);
        RB_tipo_placa_antivand_izq=(RadioButton) findViewById(R.id.RB_tipo_placa_antivand_izq);
        RB_placa_antivand_no=(RadioButton) findViewById(R.id.RB_placa_antivand_no);
        RB_placa_antivand_si=(RadioButton) findViewById(R.id.RB_placa_antivand_si);
        RB_rack_no=(RadioButton) findViewById(R.id.RB_rack_no);
        RB_rack_si=(RadioButton) findViewById(R.id.RB_rack_si);
        RB_seg_shutter_no=(RadioButton) findViewById(R.id.RB_seg_shutter_no);
        RB_seg_shutter_si=(RadioButton) findViewById(R.id.RB_seg_shutter_si);
        RB_senal_carcaza_cambio=(RadioButton) findViewById(R.id.RB_senal_carcaza_cambio);
        RB_senal_impresora_cambio=(RadioButton) findViewById(R.id.RB_senal_impresora_cambio);
        RB_senal_lectora_cambio=(RadioButton) findViewById(R.id.RB_senal_lectora_cambio);
        RB_senal_salida_efectivo_cambio=(RadioButton) findViewById(R.id.RB_senal_salida_efectivo_cambio);
        RB_telecontrol_conect_dañado=(RadioButton) findViewById(R.id.RB_telecontrol_conect_dañado);
        RB_telecontrol_conect_no=(RadioButton) findViewById(R.id.RB_telecontrol_conect_no);
        RB_telecontrol_conect_si=(RadioButton) findViewById(R.id.RB_telecontrol_conect_si);
        RB_tipo_telecontrol_IBOOT=(RadioButton) findViewById(R.id.RB_tipo_telecontrol_IBOOT);
        RB_tipo_telecontrol_MTC=(RadioButton) findViewById(R.id.RB_tipo_telecontrol_MTC);
        RB_tipo_telecontrol_Ninguno=(RadioButton) findViewById(R.id.RB_tipo_telecontrol_Ninguno);
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
        RB_banco_CCK= (RadioButton) findViewById(R.id.RB_banco_CCK);
        RB_remo_suc_remoto = (RadioButton) findViewById(R.id.RB_remo_suc_remoto);
        RB_remo_suc_sucursal = (RadioButton) findViewById(R.id.RB_remo_suc_sucursal);
        RB_ADSL = (RadioButton) findViewById(R.id.RB_ADSL);
        RB_3_4_G = (RadioButton) findViewById(R.id.RB_3_4_G);
        RB_regulador_si = (RadioButton) findViewById(R.id.RB_regulador_si);
        RB_regulador_no = (RadioButton) findViewById(R.id.RB_regulador_no);
        CB_CSDS = (CheckBox) findViewById(R.id.CB_CSDS);
        CB_CHECKER = (CheckBox) findViewById(R.id.CB_CHECKER);
        CB_RKL = (CheckBox) findViewById(R.id.CB_RKL);
        CB_FIX_WIN = (CheckBox) findViewById(R.id.CB_FIX_WIN);
        tv_user.setText(usuarioid);
        TV_sc= (TextView) findViewById(R.id.TV_sc);
        TV_ac= (TextView) findViewById(R.id.TV_ac);
        TV_ac_label= (TextView) findViewById(R.id.TV_ac_label);
        banco_otro= (EditText) findViewById(R.id.banco_otro);
        RB_banco_otro= (RadioButton) findViewById(R.id.RB_banco_otro);

        card2.setVisibility(View.GONE);
        card3.setVisibility(View.GONE);
        card4.setVisibility(View.GONE);
        card5.setVisibility(View.GONE);
        card6.setVisibility(View.GONE);
        fl_btn_save_atm_prev.setVisibility(View.VISIBLE);
        TV_ac.setVisibility(View.GONE);
        TV_ac_label.setVisibility(View.GONE);

        flag_banco= false;
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
     API Google GEo
    -------------------------------------------------- */



        /////TODO Geolocalizacion

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
        CharSequence text = "Favor documentar" +cual_campo;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
/* --------------------------------------------------
     valida que esten llenos los campos
-------------------------------------------------- */


    public void valida_campos(View view){


        if (!RB_banco_otro.isChecked() && !RB_banco_banamex.isChecked() && !RB_banco_bancomer.isChecked()&& !RB_banco_banorte.isChecked()&& !RB_banco_bancoppel.isChecked()&& !RB_banco_santander.isChecked() && !RB_banco_CCK.isChecked()){
            cual_campo=" Banco / CCK";
            mensaje_valida();
            return;
        }

        if(flag_banco==true && banco_otro.getText().toString().equals("") ){
            cual_campo="banco (otro)";
            mensaje_valida();
            return;
        }


        /* --------------------------------------------------
        Valida para UPS CCK
        -------------------------------------------------- */

        if(RB_banco_CCK.isChecked()){

            if (fecha.getText().toString().equals("Fecha")) {
                cual_campo=" fecha de ateción";
                mensaje_valida();
                return;
            }
            if (reporte.getText().toString().equals("")) {
                cual_campo=" el reporte";
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
            if (id_tpv.getText().toString().equals("")) {
                cual_campo=" Determinante";
                mensaje_valida();
                return;
            }
            if (marca.getText().toString().equals("")) {
                cual_campo=" marca";
                mensaje_valida();
                return;
            }
            if (modelo.getText().toString().equals("")) {
                cual_campo=" modelo";
                mensaje_valida();
                return;
            }
            if (serie.getText().toString().equals("")) {
                cual_campo=" serie";
                mensaje_valida();
                return;
            }
            if (nombre_atm.getText().toString().equals("")) {
                cual_campo=" nombre de Tienda";
                mensaje_valida();
                return;
            }
            if (!RB_aire_si.isChecked() && !RB_aire_no.isChecked()){
                cual_campo=" aire acondiconado";
                mensaje_valida();
                return;
            }
            if (!RB_limpieza_buena.isChecked() && !RB_limpieza_mala.isChecked()&& !RB_limpieza_regular.isChecked()){
                cual_campo=" limpieza del sitio";
                mensaje_valida();
                return;
            }
            if (!RB_polvo_si.isChecked() && !RB_polvo_no.isChecked()){
                cual_campo=" expuesto a polvo";
                mensaje_valida();
                return;
            }
            if (!RB_sol_si.isChecked() && !RB_sol_no.isChecked()){
                cual_campo=" expuesto a sol";
                mensaje_valida();
                return;
            }

            if (TV_fecha_ini.getText().toString().equals("Fecha de llegada")) {
                cual_campo = " Fecha de llegada";
                mensaje_valida();
                return;
            }
            if (TV_fecha_fin.getText().toString().equals("Fecha de terminación")) {
                cual_campo = " Fecha de terminación";
                mensaje_valida();
                return;
            }
            if (TV_hr_ini.getText().toString().equals("Hora de llegada")) {
                cual_campo = " Hora de llegada";
                mensaje_valida();
                return;
            }
            if (TV_hr_fin.getText().toString().equals("Hora de terminación")) {
                cual_campo = " Hora de terminación";
                mensaje_valida();
                return;
            }
            if (TV_sc.getText().toString().equals("")) {
                cual_campo = " SC";
                mensaje_valida();
                return;
            }
            if (TV_ac.getText().toString().equals("")) {
                cual_campo = " AC";
                mensaje_valida();
                return;
            }
            if (vobo.getText().toString().equals("")) {
                cual_campo=" VoBo";
                mensaje_valida();
                return;
            }
            if (puesto.getText().toString().equals("")) {
                cual_campo=" puesto";
                mensaje_valida();
                return;
            }
            //observacions
            if (solucion.getText().toString().equals("")) {
                cual_campo=" Observaciones";
                mensaje_valida();
                return;
            }

            muestraDialogo();

        }else {


            if (fecha.getText().toString().equals("Fecha")) {
                cual_campo = " fecha de ateción";
                mensaje_valida();
                return;
            }
            if (reporte.getText().toString().equals("")) {
                cual_campo = " el reporte";
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
            if (id_tpv.getText().toString().equals("")) {
                cual_campo = " el ID de ATM";
                mensaje_valida();
                return;
            }
            if (localidad.getText().toString().equals("")) {
                cual_campo = " localidad";
                mensaje_valida();
                return;
            }
            if (marca.getText().toString().equals("")) {
                cual_campo = " marca";
                mensaje_valida();
                return;
            }
            if (modelo.getText().toString().equals("")) {
                cual_campo = " modelo";
                mensaje_valida();
                return;
            }
            if (carga.getText().toString().equals("")) {
                cual_campo = " carga";
                mensaje_valida();
                return;
            }
            if (serie.getText().toString().equals("")) {
                cual_campo = " serie";
                mensaje_valida();
                return;
            }
            if (inventario.getText().toString().equals("")) {
                cual_campo = " inventario";
                mensaje_valida();
                return;
            }
            if (cliente_final.getText().toString().equals("")) {
                cual_campo = " cliente final";
                mensaje_valida();
                return;
            }
            if (sistema_operativo.getText().toString().equals("")) {
                cual_campo = " sistema operativo";
                mensaje_valida();
                return;
            }
            if (version_multivendor.getText().toString().equals("")) {
                cual_campo = " versiín multivendor";
                mensaje_valida();
                return;
            }
            if (temperatura.getText().toString().equals("")) {
                cual_campo = " temperatura";
                mensaje_valida();
                return;
            }
            if (UPS_kva.getText().toString().equals("")) {
                cual_campo = " UPS kva";
                mensaje_valida();
                return;
            }
            if (regulador_kva.getText().toString().equals("")) {
                cual_campo = " regulador kva";
                mensaje_valida();
                return;
            }
            if (fase_neutro_v_pared.getText().toString().equals("")) {
                cual_campo = " fase neutro v pared";
                mensaje_valida();
                return;
            }
            if (fase_neutro_v_regulado.getText().toString().equals("")) {
                cual_campo = " fase neutro v regulado";
                mensaje_valida();
                return;
            }
            if (fase_tierra_v_pared.getText().toString().equals("")) {
                cual_campo = " fase tierra v pared";
                mensaje_valida();
                return;
            }
            if (fase_tierra_v_regulado.getText().toString().equals("")) {
                cual_campo = " fase tierra v regulado";
                mensaje_valida();
                return;
            }
            if (tierra_neutro_v_pared.getText().toString().equals("")) {
                cual_campo = " tierra neutro  v pared";
                mensaje_valida();
                return;
            }
            if (tierra_neutro_v_regulado.getText().toString().equals("")) {
                cual_campo = " tierra neutro v regulado";
                mensaje_valida();
                return;
            }
            if (marca_router.getText().toString().equals("")) {
                cual_campo = "  marca router";
                mensaje_valida();
                return;
            }
            if (longitud_cable.getText().toString().equals("")) {
                cual_campo = " longitud de cable";
                mensaje_valida();
                return;
            }
            if (pruebas_ping_result.getText().toString().equals("")) {
                cual_campo = " resultado pruebas ping";
                mensaje_valida();
                return;
            }
            if (folio.getText().toString().equals("")) {
                cual_campo = " folio";
                mensaje_valida();
                return;
            }
            if (solucion.getText().toString().equals("")) {
                cual_campo = " solución";
                mensaje_valida();
                return;
            }
            if (vobo.getText().toString().equals("")) {
                cual_campo = " VoBo";
                mensaje_valida();
                return;
            }
            if (puesto.getText().toString().equals("")) {
                cual_campo = " puesto";
                mensaje_valida();
                return;
            }
            if (capacidad_hd.getText().toString().equals("")) {
                cual_campo = " capacidad HD";
                mensaje_valida();
                return;
            }
            if (RAM.getText().toString().equals("")) {
                cual_campo = " memoria RAM";
                mensaje_valida();
                return;
            }
            if (nombre_atm.getText().toString().equals("")) {
                cual_campo = " nombre de ATM";
                mensaje_valida();
                return;
            }
            if (tamaño_monitor.getText().toString().equals("")) {
                cual_campo = " tamaño de monitor";
                mensaje_valida();
                return;
            }
            if (procesador.getText().toString().equals("")) {
                cual_campo = "  procesador";
                mensaje_valida();
                return;
            }
            if (vel_procesador.getText().toString().equals("")) {
                cual_campo = " velocidad de procesador";
                mensaje_valida();
                return;
            }
            if (version_antivirus.getText().toString().equals("")) {
                cual_campo = " ver. antivirus";
                mensaje_valida();
                return;
            }
            if (!CB_CSDS.isChecked() && !CB_CHECKER.isChecked() && !CB_RKL.isChecked() && !CB_FIX_WIN.isChecked()) {
                cual_campo = " herramientas de seguridad";
                mensaje_valida();
                return;
            }
            if (!RB_01800_cambio.isChecked() && !RB_01800_deteriorada.isChecked() && !RB_01800_no.isChecked() && !RB_01800_si.isChecked()) {
                cual_campo = " 01 800";
                mensaje_valida();
                return;
            }
            if (!RB_blindaje_cab_no.isChecked() && !RB_blindaje_cab_si.isChecked()) {
                cual_campo = " Blindaje de cableado";
                mensaje_valida();
                return;
            }
            if (!RB_braile_no.isChecked() && !RB_braile_si.isChecked()) {
                cual_campo = " Braile en teclado";
                mensaje_valida();
                return;
            }
            if (!RB_caseta_prefab_no.isChecked() && !RB_caseta_prefab_si.isChecked()) {
                cual_campo = " caseta prefabricada";
                mensaje_valida();
                return;
            }
            if (!RB_chapa_rand_no.isChecked() && !RB_chapa_rand_si.isChecked()) {
                cual_campo = " chapa randómica";
                mensaje_valida();
                return;
            }
            if (!RB_entintado_billete_no.isChecked() && !RB_entintado_billete_si.isChecked()) {
                cual_campo = " entindato de billete";
                mensaje_valida();
                return;
            }
            if (!RB_jumper_no.isChecked() && !RB_jumper_si.isChecked()) {
                cual_campo = " jumper";
                mensaje_valida();
                return;
            }
            if (!RB_nicho_de_protec_no.isChecked() && !RB_nicho_de_protec_si.isChecked()) {
                cual_campo = " nicho de protección";
                mensaje_valida();
                return;
            }
            if (!RB_tipo_placa_antivand_der.isChecked() && !RB_tipo_placa_antivand_izq.isChecked()) {
                cual_campo = " tipo de placa antivandalismo";
                mensaje_valida();
                return;
            }
            if (!RB_placa_antivand_no.isChecked() && !RB_placa_antivand_si.isChecked()) {
                cual_campo = "  placa antivandalismo";
                mensaje_valida();
                return;
            }
            if (!RB_rack_no.isChecked() && !RB_rack_si.isChecked()) {
                cual_campo = "  Rack";
                mensaje_valida();
                return;
            }
            if (!RB_seg_shutter_no.isChecked() && !RB_seg_shutter_si.isChecked()) {
                cual_campo = "  seguridad de shutter";
                mensaje_valida();
                return;
            }
            if (!RB_telecontrol_conect_dañado.isChecked() && !RB_telecontrol_conect_no.isChecked() && !RB_telecontrol_conect_si.isChecked()) {
                cual_campo = " telecontrol conectado";
                mensaje_valida();
                return;
            }
            if (!RB_tipo_telecontrol_IBOOT.isChecked() && !RB_tipo_telecontrol_MTC.isChecked() && !RB_tipo_telecontrol_Ninguno.isChecked()) {
                cual_campo = " tipo de telecontrol";
                mensaje_valida();
                return;
            }
            if (!RB_antiskimmer_ebras.isChecked() && !RB_antiskimmer_no.isChecked() && !RB_antiskimmer_si.isChecked()) {
                cual_campo = " Antiskimmer";
                mensaje_valida();
                return;
            }

            if (!RB_remo_suc_remoto.isChecked() && !RB_remo_suc_sucursal.isChecked()) {
                cual_campo = " Remoto / Sucursal";
                mensaje_valida();
                return;
            }
            if (!RB_publico.isChecked() && !RB_Personal.isChecked()) {
                cual_campo = " Remoto / Sucursal";
                mensaje_valida();
                return;
            }
            if (!RB_tipo_dial_mecanico.isChecked() && !RB_tipo_dial_electrico.isChecked() && !RB_tipo_dial_randomico.isChecked()) {
                cual_campo = " tipo de Dial";
                mensaje_valida();
                return;
            }
            if (!RB_cerradura_si.isChecked() && !RB_cerradura_no.isChecked()) {
                cual_campo = " tipo de Cerradura H";
                mensaje_valida();
                return;
            }
            if (!RB_puntos_anclaje_6.isChecked() && !RB_puntos_anclaje_4.isChecked() && !RB_puntos_anclaje_otro.isChecked()) {
                cual_campo = " puntos de anclaje";
                mensaje_valida();
                return;
            }
            if (!RB_tipo_anclaje_RM16.isChecked() && !RB_tipo_anclaje_tradicional.isChecked()) {
                cual_campo = " tipo de anclaje";
                mensaje_valida();
                return;
            }
            if (!RB_base_anclaje_no.isChecked() && !RB_base_anclaje_si.isChecked()) {
                cual_campo = " base de anclaje";
                mensaje_valida();
                return;
            }
            if (!RB_placa_seguridad_si.isChecked() && !RB_placa_seguridad_no.isChecked()) {
                cual_campo = " placa de seguridad";
                mensaje_valida();
                return;
            }
            if (!RB_perno_apertura_si.isChecked() && !RB_perno_apertura_no.isChecked()) {
                cual_campo = " perno de apertura";
                mensaje_valida();
                return;
            }
            if (!RB_placa_seg_sitio_si.isChecked() && !RB_placa_seg_sitio_no.isChecked()) {
                cual_campo = " placa de seguridad en sitio";
                mensaje_valida();
                return;
            }
            if (!RB_postes_ext_si.isChecked() && !RB_postes_ext_no.isChecked()) {
                cual_campo = " postes exteriores";
                mensaje_valida();
                return;
            }
            if (!RB_empotrado_si.isChecked() && !RB_empotrado_no.isChecked()) {
                cual_campo = " empotrado a muro";
                mensaje_valida();
                return;
            }
            if (!RB_carcaza_si.isChecked() && !RB_carcaza_no.isChecked()) {
                cual_campo = " carcaza/copete";
                mensaje_valida();
                return;
            }
            if (!RB_aire_si.isChecked() && !RB_aire_no.isChecked()) {
                cual_campo = " aire acondiconado";
                mensaje_valida();
                return;
            }
            if (!RB_limpieza_buena.isChecked() && !RB_limpieza_mala.isChecked() && !RB_limpieza_regular.isChecked()) {
                cual_campo = " limpieza del sitio";
                mensaje_valida();
                return;
            }
            if (!RB_cesto_si.isChecked() && !RB_cesto_no.isChecked()) {
                cual_campo = " cesto de basura";
                mensaje_valida();
                return;
            }
            if (!RB_polvo_si.isChecked() && !RB_polvo_no.isChecked()) {
                cual_campo = " expuesto a polvo";
                mensaje_valida();
                return;
            }
            if (!RB_sol_si.isChecked() && !RB_sol_no.isChecked()) {
                cual_campo = " expuesto a sol";
                mensaje_valida();
                return;
            }
            if (!RB_iluminacion_si.isChecked() && !RB_iluminacion_no.isChecked()) {
                cual_campo = " iluminacion";
                mensaje_valida();
                return;
            }
            if (!RB_supresor_si.isChecked() && !RB_supresor_no.isChecked()) {
                cual_campo = " supresor de picos";
                mensaje_valida();
                return;
            }
            if (!RB_UPS_si.isChecked() && !RB_UPS_no.isChecked()) {
                cual_campo = " UPS";
                mensaje_valida();
                return;
            }
            if (!RB_regulador_si.isChecked() && !RB_regulador_no.isChecked()) {
                cual_campo = " regulador";
                mensaje_valida();
                return;
            }
            if (!RB_polarizacion_correcta_si.isChecked() && !RB_polarizacion_correcta_no.isChecked()) {
                cual_campo = " polarizacion";
                mensaje_valida();
                return;
            }
            if (!RB_volt_regulado_sum_UPS.isChecked() && !RB_volt_regulado_sum_regulador.isChecked()) {
                cual_campo = " voltaje regulado por";
                mensaje_valida();
                return;
            }
            if (!RB_tipo_regulado_Twist_lock.isChecked() && !RB_tipo_regulado_normal.isChecked()) {
                cual_campo = " tipo de conector";
                mensaje_valida();
                return;
            }
            if (!RB_logo_si.isChecked() && !RB_logo_no.isChecked() && !RB_logo_deteriorada.isChecked() && !RB_logo_cambio.isChecked()) {
                cual_campo = " señalización logo";
                mensaje_valida();
                return;
            }
            if (!RB_senal_lectora_deteriorada.isChecked() && !RB_senal_lectora_si.isChecked() && !RB_senal_lectora_no.isChecked() && !RB_senal_lectora_cambio.isChecked()) {
                cual_campo = " señalización lectora";
                mensaje_valida();
                return;
            }
            if (!RB_senal_impresora_si.isChecked() && !RB_senal_impresora_no.isChecked() && !RB_senal_impresora_deteriorada.isChecked() && !RB_senal_impresora_cambio.isChecked()) {
                cual_campo = " señalización impresora";
                mensaje_valida();
                return;
            }
            if (!RB_senal_salida_efectivo_si.isChecked() && !RB_senal_salida_efectivo_no.isChecked() && !RB_senal_salida_efectivo_deteriorada.isChecked() && !RB_senal_salida_efectivo_cambio.isChecked()) {
                cual_campo = " señalización salida de efectivo";
                mensaje_valida();
                return;
            }
            if (!RB_senal_carcaza_si.isChecked() && !RB_senal_carcaza_no.isChecked() && !RB_senal_carcaza_deteriorada.isChecked() && !RB_senal_carcaza_cambio.isChecked()) {
                cual_campo = " señalización carcaza";
                mensaje_valida();
                return;
            }
            if (!RB_edo_impresora_ok.isChecked() && !RB_edo_impresora_danado.isChecked()) {
                cual_campo = " estado impresora";
                mensaje_valida();
                return;
            }
            if (!RB_edo_lectora_ok.isChecked() && !RB_edo_lectora_danado.isChecked()) {
                cual_campo = " estado lectora";
                mensaje_valida();
                return;
            }
            if (!RB_edo_teclados_ok.isChecked() && !RB_edo_teclados_danado.isChecked()) {
                cual_campo = " estado teclados";
                mensaje_valida();
                return;
            }
            if (!RB_edo_CPU_ok.isChecked() && !RB_edo_CPU_danado.isChecked()) {
                cual_campo = " estado CPU";
                mensaje_valida();
                return;
            }
            if (!RB_edo_monitor_ok.isChecked() && !RB_edo_monitor_danado.isChecked()) {
                cual_campo = " estado monitor";
                mensaje_valida();
                return;
            }
            if (!RB_edo_dispensador_ok.isChecked() && !RB_edo_dispensador_danado.isChecked()) {
                cual_campo = " estado dispensador";
                mensaje_valida();
                return;
            }
            if (!RB_satelital.isChecked() && !RB_DIAL.isChecked() && !RB_ADSL.isChecked() && !RB_3_4_G.isChecked()) {
                cual_campo = " tipo de comunicación";
                mensaje_valida();
                return;
            }
            if (!RB_edo_cableado_maltratado.isChecked() && !RB_edo_cableado_bueno.isChecked() && !RB_edo_cableado_a_la_vista.isChecked()) {
                cual_campo = " estado del cableado";
                mensaje_valida();
                return;
            }
            if (!RB_conf_cableado_conex_directa.isChecked() && !RB_conf_cableado_conex_cruzado.isChecked()) {
                cual_campo = " configuración de cableado";
                mensaje_valida();
                return;
            }
            if (!RB_equipo_con_alte_si.isChecked() && !RB_equipo_con_alte_no.isChecked()) {
                cual_campo = " equipo de cumunicacion alertado";
                mensaje_valida();
                return;
            }
            if (!RB_pruebas_ping_si.isChecked() && !RB_pruebas_ping_no.isChecked()) {
                cual_campo = " equipo pruebas de ping";
                mensaje_valida();
                return;
            }
            if (TV_sc.getText().toString().equals("")) {
                cual_campo = " SC";
                mensaje_valida();
                return;
            }
            if (TV_ac.getText().toString().equals("")) {
                cual_campo = " AC";
                mensaje_valida();
                return;
            }
            if (TV_fecha_ini.getText().toString().equals("Fecha de llegada")) {
                cual_campo = " Fecha de llegada";
                mensaje_valida();
                return;
            }
            if (TV_fecha_fin.getText().toString().equals("Fecha de terminación")) {
                cual_campo = " Fecha de terminación";
                mensaje_valida();
                return;
            }
            if (TV_hr_ini.getText().toString().equals("Hora de llegada")) {
                cual_campo = " Hora de llegada";
                mensaje_valida();
                return;
            }
            if (TV_hr_fin.getText().toString().equals("Hora de terminación")) {
                cual_campo = " Hora de terminación";
                mensaje_valida();
                return;
            }

            muestraDialogo();

        }

    }



/* --------------------------------------------------
     muestra dialogo de confirmacion de guardado
-------------------------------------------------- */

    public void muestraDialogo() {
        AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
        myBuild.setTitle("Importante");
        myBuild.setMessage("¿Guardar Checklist? Al confirmar se cargaran los datos documentados?");
        myBuild.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                asigna_variables();


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

public void asigna_variables(){

    date=fecha.getText().toString();
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
    hr_ini_V=TV_hr_ini.getText().toString();
    hr_fin_V=TV_hr_fin.getText().toString();
    fecha_ini_V=TV_fecha_ini.getText().toString();
    fecha_fin_V=TV_fecha_fin.getText().toString();
    SC_V=TV_sc.getText().toString();
    AC_V=TV_ac.getText().toString();

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
    if (RB_logo_cambio.isChecked()) {
        RB_logo_V = RB_logo_cambio.getText().toString();
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
    if (RB_senal_lectora_cambio.isChecked()) {
        RB_senal_lectora_V = RB_senal_lectora_cambio.getText().toString();
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
    if (RB_senal_impresora_cambio.isChecked()) {
        RB_senal_impresora_V = RB_senal_impresora_cambio.getText().toString();
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
    if (RB_senal_salida_efectivo_cambio.isChecked()) {
        RB_senal_salida_efectivo_V = RB_senal_salida_efectivo_cambio.getText().toString();
    }
    if (RB_01800_si.isChecked()) {
        RB_senal_01800_V = RB_01800_si.getText().toString();
    }
    if (RB_01800_no.isChecked()) {
        RB_senal_01800_V = RB_01800_no.getText().toString();
    }
    if (RB_01800_deteriorada.isChecked()) {
        RB_senal_01800_V = RB_01800_deteriorada.getText().toString();
    }
    if (RB_01800_cambio.isChecked()) {
        RB_senal_01800_V = RB_01800_cambio.getText().toString();
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
    if (RB_senal_carcaza_cambio.isChecked()) {
        RB_senal_carcaza_V = RB_senal_carcaza_cambio.getText().toString();
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
    if (RB_banco_CCK.isChecked()) {
        RB_banco_V = RB_banco_CCK.getText().toString();
    }
    if (RB_banco_otro.isChecked()) {
        RB_banco_V = banco_otro.getText().toString();
    }
    if (RB_remo_suc_remoto.isChecked()) {
        RB_remo_suc_V = RB_remo_suc_remoto.getText().toString();
    }
    if (RB_remo_suc_sucursal.isChecked()) {
        RB_remo_suc_V = RB_remo_suc_sucursal.getText().toString();
    }
    if (RB_regulador_si.isChecked()) {
        RB_regulador_V = RB_regulador_si.getText().toString();
    }
    if (RB_regulador_no.isChecked()) {
        RB_regulador_V = RB_regulador_no.getText().toString();
    }
    if (RB_01800_si.isChecked()) {
        RB_senal_01800_V = RB_01800_si.getText().toString();
    }
    if (RB_01800_no.isChecked()) {
        RB_senal_01800_V = RB_01800_no.getText().toString();
    }
    if (RB_01800_deteriorada.isChecked()) {
        RB_senal_01800_V = RB_01800_deteriorada.getText().toString();
    }
    if (RB_01800_cambio.isChecked()) {
        RB_senal_01800_V = RB_01800_cambio.getText().toString();
    }
    if (RB_antiskimmer_ebras.isChecked()) {
        RB_antiskimmer_V = RB_antiskimmer_ebras.getText().toString();
    }
    if (RB_antiskimmer_no.isChecked()) {
        RB_antiskimmer_V = RB_antiskimmer_no.getText().toString();
    }
    if (RB_antiskimmer_si.isChecked()) {
        RB_antiskimmer_V = RB_antiskimmer_si.getText().toString();
    }
    if (RB_blindaje_cab_no.isChecked()) {
        RB_blindaje_V = RB_blindaje_cab_no.getText().toString();
    }
    if (RB_blindaje_cab_si.isChecked()) {
        RB_blindaje_V = RB_blindaje_cab_si.getText().toString();
    }
    if (RB_braile_no.isChecked()) {
        RB_braile_V = RB_braile_no.getText().toString();
    }
    if (RB_braile_si.isChecked()) {
        RB_braile_V = RB_braile_si.getText().toString();
    }
    if (RB_caseta_prefab_no.isChecked()) {
        RB_caseta_prefab_V = RB_caseta_prefab_no.getText().toString();
    }
    if (RB_caseta_prefab_si.isChecked()) {
        RB_caseta_prefab_V = RB_caseta_prefab_si.getText().toString();
    }
    if (RB_chapa_rand_no.isChecked()) {
        RB_chapa_rand_V = RB_chapa_rand_no.getText().toString();
    }
    if (RB_chapa_rand_si.isChecked()) {
        RB_chapa_rand_V = RB_chapa_rand_si.getText().toString();
    }
    if (RB_entintado_billete_no.isChecked()) {
        RB_entintado_billete_V = RB_entintado_billete_no.getText().toString();
    }
    if (RB_entintado_billete_si.isChecked()) {
        RB_entintado_billete_V = RB_entintado_billete_si.getText().toString();
    }
    if (RB_jumper_no.isChecked()) {
        RB_jumper_V = RB_jumper_no.getText().toString();
    }
    if (RB_jumper_si.isChecked()) {
        RB_jumper_V = RB_jumper_si.getText().toString();
    }
    if (RB_nicho_de_protec_no.isChecked()) {
        RB_nicho_de_protec_V = RB_nicho_de_protec_no.getText().toString();
    }
    if (RB_nicho_de_protec_si.isChecked()) {
        RB_nicho_de_protec_V = RB_nicho_de_protec_si.getText().toString();
    }
    if (RB_tipo_placa_antivand_der.isChecked()) {
        RB_tipo_placa_antivand_V = RB_tipo_placa_antivand_der.getText().toString();
    }
    if (RB_tipo_placa_antivand_izq.isChecked()) {
        RB_tipo_placa_antivand_V = RB_tipo_placa_antivand_izq.getText().toString();
    }
    if (RB_placa_antivand_no.isChecked()) {
        RB_placa_antivand_V = RB_placa_antivand_no.getText().toString();
    }
    if (RB_placa_antivand_si.isChecked()) {
        RB_placa_antivand_V = RB_placa_antivand_si.getText().toString();
    }
    if (RB_rack_no.isChecked()) {
        RB_rack_V = RB_rack_no.getText().toString();
    }
    if (RB_rack_si.isChecked()) {
        RB_rack_V = RB_rack_si.getText().toString();
    }
    if (RB_seg_shutter_no.isChecked()) {
        RB_seg_shutter_V = RB_seg_shutter_no.getText().toString();
    }
    if (RB_seg_shutter_si.isChecked()) {
        RB_seg_shutter_V = RB_seg_shutter_si.getText().toString();
    }
    if (RB_telecontrol_conect_dañado.isChecked()) {
        RB_telecontrol_conect_V = RB_telecontrol_conect_dañado.getText().toString();
    }
    if (RB_telecontrol_conect_no.isChecked()) {
        RB_telecontrol_conect_V = RB_telecontrol_conect_no.getText().toString();
    }
    if (RB_telecontrol_conect_si.isChecked()) {
        RB_telecontrol_conect_V = RB_telecontrol_conect_si.getText().toString();
    }
    if (RB_tipo_telecontrol_IBOOT.isChecked()) {
        RB_tipo_telecontrol_V = RB_tipo_telecontrol_IBOOT.getText().toString();
    }
    if (RB_tipo_telecontrol_MTC.isChecked()) {
        RB_tipo_telecontrol_V = RB_tipo_telecontrol_MTC.getText().toString();
    }
    if (RB_tipo_telecontrol_Ninguno.isChecked()) {
        RB_tipo_telecontrol_V = RB_tipo_telecontrol_Ninguno.getText().toString();
    }
    saveATM_prev();
}

/* --------------------------------------------------
            salva los datos
-------------------------------------------------- */
    private void saveATM_prev() {
        Post post = new Post();
        Map<String, Object> postValues = post.toMap();

        /* --------------------------------------------------
            Añade a Cloudant
        -------------------------------------------------- */
        String url=getResources().getString(R.string.urlCloudant)+"/preventivos";


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
                            comas+"IDkey"+comas+":"+comas+idkey+comas+","+
                            comas+"Fecha"+comas+":"+comas+date+comas+","+
                            comas+"Usuario"+comas+":"+comas+usuarioid+comas+","+
                            comas+"aire_acondicionado"+comas+":"+comas+RB_aire_V+comas+","+
                            comas+"ATM_da_servicio_a"+comas+":"+comas+RB_servicio_V+comas+","+
                            comas+"base_de_anclaje"+comas+":"+comas+RB_base_anclaje_V+comas+","+
                            comas+"carcaza"+comas+":"+comas+RB_carcaza_V+comas+","+
                            comas+"carga"+comas+":"+comas+carga_V+comas+","+
                            comas+"cerradura_tipo_H"+comas+":"+comas+RB_cerradura_V+comas+","+
                            comas+"cesto_de_basura"+comas+":"+comas+RB_cesto_V+comas+","+
                            comas+"cliente_final"+comas+":"+comas+cliente_final_V+comas+","+
                            comas+"configuracion_cable_de_red"+comas+":"+comas+RB_conf_cableado_conex_V+comas+","+
                            comas+"empotrado_al_muro"+comas+":"+comas+RB_empotrado_V+comas+","+
                            comas+"equipo_de_comunicacion_alertado"+comas+":"+comas+RB_equipo_con_alte_V+comas+","+
                            comas+"especifique_carcaza"+comas+":"+comas+carcaza_V+comas+","+
                            comas+"estado_CPU_observaciones"+comas+":"+comas+ob_edo_CPU_V+comas+","+
                            comas+"estado_CPU"+comas+":"+comas+RB_edo_CPU_V+comas+","+
                            comas+"estado_del_cableado"+comas+":"+comas+RB_edo_cableado_V+comas+","+
                            comas+"estado_dispensador_observaciones"+comas+":"+comas+ob_edo_dispensador_V+comas+","+
                            comas+"estado_dispensador"+comas+":"+comas+RB_edo_dispensador_V+comas+","+
                            comas+"estado_impresora_observaciones"+comas+":"+comas+ob_edo_impresora_V+comas+","+
                            comas+"estado_impresora"+comas+":"+comas+RB_edo_impresora_V+comas+","+
                            comas+"estado_lectora_observaciones"+comas+":"+comas+ob_edo_lectora_V+comas+","+
                            comas+"estado_lectora"+comas+":"+comas+RB_edo_lectora_V+comas+","+
                            comas+"estado_monitor_observaciones"+comas+":"+comas+ob_edo_monitor_V+comas+","+
                            comas+"estado_monitor"+comas+":"+comas+RB_edo_monitor_V+comas+","+
                            comas+"estado_teclados_observaciones"+comas+":"+comas+ob_edo_teclados_V+comas+","+
                            comas+"estado_teclados"+comas+":"+comas+RB_edo_teclados_V+comas+","+
                            comas+"expuesto_a_polvo"+comas+":"+comas+RB_polvo_V+comas+","+
                            comas+"expuesto_a_sol"+comas+":"+comas+RB_sol_V+comas+","+
                            comas+"fase_neutro_voltaje_pared"+comas+":"+comas+fase_neutro_v_pared_V+comas+","+
                            comas+"fase_neutro_voltaje_regulado"+comas+":"+comas+fase_neutro_v_regulado_V+comas+","+
                            comas+"fase_tierra_voltaje_pared"+comas+":"+comas+fase_tierra_v_pared_V+comas+","+
                            comas+"fase_tierra_voltaje_regulado"+comas+":"+comas+fase_tierra_v_regulado_V+comas+","+
                            comas+"herramientas_de_seguridad_instaladas_h1"+comas+":"+comas+CB_CSDS_V+comas+","+
                            comas+"herramientas_de_seguridad_instaladas_h2"+comas+":"+comas+CB_CHECKER_V+comas+","+
                            comas+"herramientas_de_seguridad_instaladas_h3"+comas+":"+comas+CB_RKL_V+comas+","+
                            comas+"herramientas_de_seguridadinstaladas_h4"+comas+":"+comas+CB_FIX_WIN_V+comas+","+
                            comas+"id_ATM"+comas+":"+comas+id_tpv_V+comas+","+
                            comas+"iluminacion"+comas+":"+comas+RB_iluminacion_V+comas+","+
                            comas+"KVA_regulador"+comas+":"+comas+regulador_kva_V+comas+","+
                            comas+"KVA_UPS"+comas+":"+comas+UPS_kva_V+comas+","+
                            comas+"limpieza_del_sitio"+comas+":"+comas+RB_limpieza_V+comas+","+
                            comas+"localidad"+comas+":"+comas+localidad_V+comas+","+
                            comas+"logo"+comas+":"+comas+RB_logo_V+comas+","+
                            comas+"longitud_cable"+comas+":"+comas+longitud_cable_V+comas+","+
                            comas+"marca_router"+comas+":"+comas+marca_router_V+comas+","+
                            comas+"marca"+comas+":"+comas+marca_V+comas+","+
                            comas+"modelo"+comas+":"+comas+modelo_V+comas+","+
                            comas+"notas_comunicaciones"+comas+":"+comas+notas_comunic_V+comas+","+
                            comas+"notas_instalacion_electrica"+comas+":"+comas+notas_inst_elect_V+comas+","+
                            comas+"notas_seguridad"+comas+":"+comas+notas_seguridad_V+comas+","+
                            comas+"numero_de_inventario"+comas+":"+comas+inventario_V+comas+","+
                            comas+"perno_de_apertura"+comas+":"+comas+RB_perapertura_V+comas+","+
                            comas+"placa_de_seguridad_sitio"+comas+":"+comas+RB_placa_seg_sitio_V+comas+","+
                            comas+"placa_de_seguridad"+comas+":"+comas+RB_placa_seguridad_V+comas+","+
                            comas+"polarizacion_correcta"+comas+":"+comas+RB_polarizacion_correcta_V+comas+","+
                            comas+"postes_exteriores"+comas+":"+comas+RB_postes_ext_V+comas+","+
                            comas+"pruebas_ping_resultado"+comas+":"+comas+pruebas_ping_result_V+comas+","+
                            comas+"pruebas_ping"+comas+":"+comas+RB_pruebas_ping_V+comas+","+
                            comas+"puntos_de_anclaje_otros"+comas+":"+comas+puntos_anclaje_otro_V+comas+","+
                            comas+"puntos_de_anclaje"+comas+":"+comas+RB_puntos_anclaje_V+comas+","+
                            comas+"reporte"+comas+":"+comas+reporte_V+comas+","+
                            comas+"señal_carcaza"+comas+":"+comas+RB_senal_carcaza_V+comas+","+
                            comas+"señal_impresora"+comas+":"+comas+RB_senal_impresora_V+comas+","+
                            comas+"señal_lectora"+comas+":"+comas+RB_senal_lectora_V+comas+","+
                            comas+"señal_salida_efectivo"+comas+":"+comas+RB_senal_salida_efectivo_V+comas+","+
                            comas+"serie"+comas+":"+comas+serie_V+comas+","+
                            comas+"sistema_operativo"+comas+":"+comas+sistema_operativo_V+comas+","+
                            comas+"supresor_de_picos"+comas+":"+comas+RB_supresor_V+comas+","+
                            comas+"temperatura"+comas+":"+comas+temperatura_V+comas+","+
                            comas+"tierra_neutro_voltaje_pared"+comas+":"+comas+tierra_neutro_v_pared_V+comas+","+
                            comas+"tierra_neutro_voltaje_regulado"+comas+":"+comas+tierra_neutro_v_regulado_V+comas+","+
                            comas+"tipo_de_anclaje"+comas+":"+comas+RB_tipo_anclaje_V+comas+","+
                            comas+"regulador"+comas+":"+comas+RB_regulador_V+comas+","+
                            comas+"tipo_de_comunicacion"+comas+":"+comas+RB_tipo_comunicacion_V+comas+","+
                            comas+"tipo_de_dial"+comas+":"+comas+RB_tipo_dial_V+comas+","+
                            comas+"tipo_voltaje_regulado"+comas+":"+comas+RB_tipo_regulado_V+comas+","+
                            comas+"UPS"+comas+":"+comas+RB_UPS_V+comas+","+
                            comas+"version_checker"+comas+":"+comas+version_checker_V+comas+","+
                            comas+"version_multivendor"+comas+":"+comas+version_multivendor_V+comas+","+
                            comas+"voltaje_regulado_suministrado_por"+comas+":"+comas+RB_volt_regulado_sum_V+comas+","+
                            comas+"banco"+comas+":"+comas+RB_banco_V+comas+","+
                            comas+"remoto_sucursal"+comas+":"+comas+RB_remo_suc_V+comas+","+
                            comas+"pais"+comas+":"+comas+"Mexico"+comas+","+
                            comas+"equipo"+comas+":"+comas+"ATM"+comas+","+
                            comas+"tipo_reporte"+comas+":"+comas+"Preventivo"+comas+","+
                            comas+"solucion"+comas+":"+comas+solucion.getText().toString()+comas+","+
                            comas+"Vobo"+comas+":"+comas+vobo.getText().toString()+comas+","+
                            comas+"puesto"+comas+":"+comas+puesto.getText().toString()+comas+","+
                            comas+"folio"+comas+":"+comas+folio.getText().toString()+comas+","+
                            comas+"latitud"+comas+":"+comas+mLatitude.getText().toString()+comas+","+
                            comas+"longitud"+comas+":"+comas+mLongitude.getText().toString()+comas+","+
                            comas+"hr_ini"+comas+":"+comas+hr_ini_V+comas+","+
                            comas+"hr_fin"+comas+":"+comas+hr_fin_V+comas+","+
                            comas+"fecha_ini"+comas+":"+comas+fecha_ini_V+comas+","+
                            comas+"fecha_fin"+comas+":"+comas+fecha_fin_V+comas+","+
                            comas+"SC"+comas+":"+comas+SC_V+comas+","+
                            comas+"AC"+comas+":"+comas+AC_V+comas+","+
                            comas+"capacidad_hd"+comas+":"+comas+capacidad_hd.getText()+comas+","+
                            comas+"RAM"+comas+":"+comas+RAM.getText()+comas+","+
                            comas+"nombre_atm"+comas+":"+comas+nombre_atm.getText()+comas+","+
                            comas+"ob_esp_sitio"+comas+":"+comas+ob_esp_sitio.getText()+comas+","+
                            comas+"obs_telecontrol"+comas+":"+comas+obs_telecontrol.getText()+comas+","+
                            comas+"tamano_monitor"+comas+":"+comas+tamaño_monitor.getText()+comas+","+
                            comas+"procesador"+comas+":"+comas+procesador.getText()+comas+","+
                            comas+"vel_procesador"+comas+":"+comas+vel_procesador.getText()+comas+","+
                            comas+"version_antivirus"+comas+":"+comas+version_antivirus.getText()+comas+","+
                            comas+"senal_01800"+comas+":"+comas+RB_senal_01800_V+comas+","+
                            comas+"antiskimmer"+comas+":"+comas+RB_antiskimmer_V+comas+","+
                            comas+"blindaje"+comas+":"+comas+RB_blindaje_V+comas+","+
                            comas+"braile"+comas+":"+comas+RB_braile_V+comas+","+
                            comas+"caseta_prefab"+comas+":"+comas+RB_caseta_prefab_V+comas+","+
                            comas+"chapa_rand"+comas+":"+comas+RB_chapa_rand_V+comas+","+
                            comas+"entintado_billete"+comas+":"+comas+RB_entintado_billete_V+comas+","+
                            comas+"jumper"+comas+":"+comas+RB_jumper_V+comas+","+
                            comas+"nicho_de_protec"+comas+":"+comas+RB_nicho_de_protec_V+comas+","+
                            comas+"tipo_placa_antivand"+comas+":"+comas+RB_tipo_placa_antivand_V+comas+","+
                            comas+"placa_antivand"+comas+":"+comas+RB_placa_antivand_V+comas+","+
                            comas+"rack"+comas+":"+comas+RB_rack_V+comas+","+
                            comas+"seg_shutter"+comas+":"+comas+RB_seg_shutter_V+comas+","+
                            comas+"telecontrol_conect"+comas+":"+comas+RB_telecontrol_conect_V+comas+","+
                            comas+"tipo_telecontrol"+comas+":"+comas+RB_tipo_telecontrol_V+comas+
                            "}";


                    Long tsLong = System.currentTimeMillis()/1000;
                    String ts = tsLong.toString();
                    String filename= "PR_"+ts+"_"+reporte.getText().toString()+".txt";
                    OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(filename, Activity.MODE_PRIVATE));
                    archivo.write(guardaPost);
                    archivo.flush();
                    archivo.close();
                    Context context = getApplicationContext();
                    CharSequence text = "Problema al subir file, almacenado en pendientes. " + filename;
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    firma();

                } catch (IOException e) {
                }
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
Post mapa a cloudant
-------------------------------------------------- */



    public class Post {
        public Post() {
        }

        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("IDkey", idkey);
            result.put("Fecha", date);
            result.put("Usuario", usuarioid);
            result.put("aire_acondicionado", RB_aire_V);
            result.put("ATM_da_servicio_a", RB_servicio_V);
            result.put("base_de_anclaje", RB_base_anclaje_V);
            result.put("carcaza", RB_carcaza_V);
            result.put("carga", carga_V);
            result.put("cerradura_tipo_H", RB_cerradura_V);
            result.put("cesto_de_basura", RB_cesto_V);
            result.put("cliente_final", cliente_final_V);
            result.put("configuracion_cable_de_red", RB_conf_cableado_conex_V);
            result.put("empotrado_al_muro", RB_empotrado_V);
            result.put("equipo_de_comunicacion_alertado", RB_equipo_con_alte_V);
            result.put("especifique_carcaza", carcaza_V);
            result.put("estado_CPU_observaciones", ob_edo_CPU_V);
            result.put("estado_CPU", RB_edo_CPU_V);
            result.put("estado_del_cableado", RB_edo_cableado_V);
            result.put("estado_dispensador_observaciones", ob_edo_dispensador_V);
            result.put("estado_dispensador", RB_edo_dispensador_V);
            result.put("estado_impresora_observaciones", ob_edo_impresora_V);
            result.put("estado_impresora", RB_edo_impresora_V);
            result.put("estado_lectora_observaciones", ob_edo_lectora_V);
            result.put("estado_lectora", RB_edo_lectora_V);
            result.put("estado_monitor_observaciones", ob_edo_monitor_V);
            result.put("estado_monitor", RB_edo_monitor_V);
            result.put("estado_teclados_observaciones", ob_edo_teclados_V);
            result.put("estado_teclados", RB_edo_teclados_V);
            result.put("expuesto_a_polvo", RB_polvo_V);
            result.put("expuesto_a_sol", RB_sol_V);
            result.put("fase_neutro_voltaje_pared", fase_neutro_v_pared_V);
            result.put("fase_neutro_voltaje_regulado", fase_neutro_v_regulado_V);
            result.put("fase_tierra_voltaje_pared", fase_tierra_v_pared_V);
            result.put("fase_tierra_voltaje_regulado", fase_tierra_v_regulado_V);
            result.put("herramientas_de_seguridad_instaladas_h1", CB_CSDS_V);
            result.put("herramientas_de_seguridad_instaladas_h2", CB_CHECKER_V);
            result.put("herramientas_de_seguridad_instaladas_h3", CB_RKL_V);
            result.put("herramientas_de_seguridad instaladas_h4", CB_FIX_WIN_V);
            result.put("id_ATM", id_tpv_V);
            result.put("iluminacion", RB_iluminacion_V);
            result.put("KVA_regulador", regulador_kva_V);
            result.put("KVA_UPS", UPS_kva_V);
            result.put("limpieza_del_sitio", RB_limpieza_V);
            result.put("localidad", localidad_V);
            result.put("logo", RB_logo_V);
            result.put("longitud_cable", longitud_cable_V);
            result.put("marca_router", marca_router_V);
            result.put("marca", marca_V);
            result.put("modelo", modelo_V);
            result.put("notas_comunicaciones", notas_comunic_V);
            result.put("notas_instalacion_electrica", notas_inst_elect_V);
            result.put("notas_seguridad", notas_seguridad_V);
            result.put("numero_de_inventario", inventario_V);
            result.put("perno_de_apertura", RB_perapertura_V);
            result.put("placa_de_seguridad_sitio", RB_placa_seg_sitio_V);
            result.put("placa_de_seguridad", RB_placa_seguridad_V);
            result.put("polarizacion_correcta", RB_polarizacion_correcta_V);
            result.put("postes_exteriores", RB_postes_ext_V);
            result.put("pruebas_ping_resultado", pruebas_ping_result_V);
            result.put("pruebas_ping", RB_pruebas_ping_V);
            result.put("puntos_de_anclaje_otros", puntos_anclaje_otro_V);
            result.put("puntos_de_anclaje", RB_puntos_anclaje_V);
            result.put("reporte", reporte_V);
            result.put("señal_carcaza", RB_senal_carcaza_V);
            result.put("señal_impresora", RB_senal_impresora_V);
            result.put("señal_lectora", RB_senal_lectora_V);
            result.put("señal_salida_efectivo", RB_senal_salida_efectivo_V);
            result.put("serie", serie_V);
            result.put("sistema_operativo", sistema_operativo_V);
            result.put("supresor_de_picos", RB_supresor_V);
            result.put("temperatura", temperatura_V);
            result.put("tierra_neutro_voltaje_pared", tierra_neutro_v_pared_V);
            result.put("tierra_neutro_voltaje_regulado", tierra_neutro_v_regulado_V);
            result.put("tipo_de_anclaje", RB_tipo_anclaje_V);
            result.put("regulador", RB_regulador_V);
            result.put("tipo_de_comunicacion", RB_tipo_comunicacion_V);
            result.put("tipo_de_dial", RB_tipo_dial_V);
            result.put("tipo_voltaje_regulado", RB_tipo_regulado_V);
            result.put("UPS", RB_UPS_V);
            result.put("version_checker", version_checker_V);
            result.put("version_multivendor", version_multivendor_V);
            result.put("voltaje_regulado_suministrado_por", RB_volt_regulado_sum_V);
            result.put("banco", RB_banco_V);
            result.put("remoto_sucursal", RB_remo_suc_V);
            result.put("pais", "Mexico");
            result.put("equipo", "ATM");
            result.put("tipo_reporte", "Preventivo");
            result.put("solucion", solucion.getText().toString());
            result.put("Vobo", vobo.getText().toString());
            result.put("puesto", puesto.getText().toString());
            result.put("folio", folio.getText().toString());
            result.put("latitud", mLatitude.getText().toString());
            result.put("longitud", mLongitude.getText().toString());
            result.put("hr_ini",hr_ini_V);
            result.put("hr_fin",hr_fin_V);
            result.put("fecha_ini",fecha_ini_V);
            result.put("fecha_fin",fecha_fin_V);
            result.put("SC",SC_V);
            result.put("AC",AC_V);
            result.put("capacidad_hd",capacidad_hd.getText().toString());
            result.put("RAM",RAM.getText().toString());
            result.put("nombre_atm",nombre_atm.getText().toString());
            result.put("ob_esp_sitio",ob_esp_sitio.getText().toString());
            result.put("obs_telecontrol",obs_telecontrol.getText().toString());
            result.put("tamano_monitor",tamaño_monitor.getText().toString());
            result.put("procesador",procesador.getText().toString());
            result.put("vel_procesador",vel_procesador.getText().toString());
            result.put("version_antivirus",version_antivirus.getText().toString());
            result.put("senal_01800",RB_senal_01800_V);
            result.put("antiskimmer",RB_antiskimmer_V);
            result.put("blindaje",RB_blindaje_V);
            result.put("braile",RB_braile_V);
            result.put("caseta_prefab",RB_caseta_prefab_V);
            result.put("chapa_rand",RB_chapa_rand_V);
            result.put("entintado_billete",RB_entintado_billete_V);
            result.put("jumper",RB_jumper_V);
            result.put("nicho_de_protec",RB_nicho_de_protec_V);
            result.put("tipo_placa_antivand",RB_tipo_placa_antivand_V);
            result.put("placa_antivand",RB_placa_antivand_V);
            result.put("rack",RB_rack_V);
            result.put("seg_shutter",RB_seg_shutter_V);
            result.put("telecontrol_conect",RB_telecontrol_conect_V);
            result.put("tipo_telecontrol",RB_tipo_telecontrol_V);
            return result;
        }
    }

/* --------------------------------------------------
    ///abrir calendario
-------------------------------------------------- */

    public void showDatePickerDialog_fecha_fin(View v) {
        tipo_fecha="fecha_fin";
        DialogFragment newFragment = new DatePick();
        Bundle args = new Bundle();
        args.putInt("num", 2);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showDatePickerDialog_fecha_ini(View v) {
        tipo_fecha="fecha_ini";
        DialogFragment newFragment = new DatePick();
        Bundle args = new Bundle();
        args.putInt("num", 2);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showDatePickerDialog_Head(View v) {

        tipo_fecha="head";
        DialogFragment newFragment = new DatePick();
        Bundle args = new Bundle();
        args.putInt("num", 2);
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


    /* --------------------------------------------------
    muestra y ocultatextbox cliente otro
    -------------------------------------------------- */



    public void verotro_banco(View view) {
        banco_otro.setVisibility(View.VISIBLE);
        flag_banco = true;
    }
    public void ocultarotro_banco(View view){
        banco_otro.setVisibility(View.GONE);
        flag_banco=false;
    }

/* --------------------------------------------------
cambia card view
-------------------------------------------------- */


    public void anterior1(View view) {
        card1.setVisibility(View.VISIBLE);
        card2.setVisibility(View.GONE);

        nestedScrollView.scrollTo(0, 0);

    }
    public void anterior2(View view) {
        card2.setVisibility(View.VISIBLE);
        card3.setVisibility(View.GONE);
        nestedScrollView.scrollTo(0, 0);
    }
    public void anterior3(View view) {
        card3.setVisibility(View.VISIBLE);
        card4.setVisibility(View.GONE);
        nestedScrollView.scrollTo(0, 0);
    }
    public void anterior4(View view) {
        card4.setVisibility(View.VISIBLE);
        card5.setVisibility(View.GONE);
        nestedScrollView.scrollTo(0, 0);
    }
    public void anterior5(View view) {
        card5.setVisibility(View.VISIBLE);
        card6.setVisibility(View.GONE);
        nestedScrollView.scrollTo(0, 0);
    }
    public void siguiente2(View view) {
        card2.setVisibility(View.VISIBLE);
        card1.setVisibility(View.GONE);
        nestedScrollView.scrollTo(0, 0);
    }
    public void siguiente3(View view) {
        card2.setVisibility(View.GONE);
        card3.setVisibility(View.VISIBLE);
        nestedScrollView.scrollTo(0, 0);
    }
    public void siguiente4(View view) {
        card3.setVisibility(View.GONE);
        card4.setVisibility(View.VISIBLE);
        nestedScrollView.scrollTo(0, 0);
    }
    public void siguiente5(View view) {
        card4.setVisibility(View.GONE);
        card5.setVisibility(View.VISIBLE);
        nestedScrollView.scrollTo(0, 0);
    }
    public void siguiente6(View view) {
        card5.setVisibility(View.GONE);
        card6.setVisibility(View.VISIBLE);
        nestedScrollView.scrollTo(0, 0);
    }
    public void siguiente7(View view) {
        card6.setVisibility(View.GONE);
        nestedScrollView.scrollTo(0, 0);
    }

    private static final int PICK_IMAGE_ID = 234; // the number doesn't matter

/* --------------------------------------------------
abre camara
-------------------------------------------------- */
    private void llamarIntent() {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }
/* --------------------------------------------------
resultado de camara y listas
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

    }

/* --------------------------------------------------
abre listas
-------------------------------------------------- */

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
al precionas back
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



    private int i_photos=0;
    private String encodedImage;
/* --------------------------------------------------
sube imagen a cloudan
-------------------------------------------------- */
    public  void subir_imagen(View view){

        reporte_V = reporte.getText().toString();
        id_tpv_V = id_tpv.getText().toString();
        i_photos=i_photos+1;
        // valida q reporte y ID existan
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
        notas_post=notas_image.getText().toString();
        idATM=id_tpv.getText().toString();
        i_photos_post=""+i_photos;
        Post_2 post_2 = new Post_2();
        Map<String, Object> post_2Values = post_2.toMap();

                    /* --------------------------------------------------
                    Añade imagen a Cloudant
                    -------------------------------------------------- */


        String url=getResources().getString(R.string.urlCloudant)+"/ima_prev";
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
                    añadir_im_recicler();
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
                            comas+"equipo"+comas+":"+comas+"ATM"+comas+","+
                            comas+"tipo_reporte"+comas+":"+comas+"Correctivo"+comas+","+
                            comas+"latitud"+comas+":"+comas+mLatitude.getText().toString()+comas+","+
                            comas+"longitud"+comas+":"+comas+mLongitude.getText().toString()+comas+
                            "}";

                    ////Genera JSON de variables
                    Long tsLong = System.currentTimeMillis()/1000;
                    String ts = tsLong.toString();

                    String filename= "IP_"+ts+"_"+reporte.getText().toString()+".txt";

                    OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(filename, Activity.MODE_PRIVATE));
                    archivo.write(guardaPost);
                    archivo.flush();
                    archivo.close();

                    Context context = getApplicationContext();
                    CharSequence text = "Problema al subir file, almacenado en pendientes. " + filename;
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } catch (IOException e) {
                }
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
mapa de para subir imagen
-------------------------------------------------- */

private String  i_photos_post;
    public class Post_2{
        public Map<String, Boolean> stars = new HashMap<>();

        public Post_2() {
        }
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("imagen", encodedImage);
            result.put("observaciones", notas_post);
            result.put("id_img", i_photos_post);
            result.put("id_ATM", id_tpv_V);
            result.put("reporte", reporte_V);
            result.put("equipo", "ATM");
            result.put("tipo_reporte", "Preventivo");
            result.put("latitud", mLatitude.getText().toString());
            result.put("longitud", mLongitude.getText().toString());
            return result;
        }

    }


    private int flag_foto;
    private  String notas_post;
    private  List items = new ArrayList();

    /* --------------------------------------------------
   añade imagen en el recycler view
   -------------------------------------------------- */
    public  void añadir_im_recicler(){

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

        flag_foto=0;
        notas_image.setText("");
        progressBarPhoto.setVisibility(View.GONE);
        photo_ATM_prev.setImageResource(R.drawable.ic_shutter);
    }

/* --------------------------------------------------
abre intent para encuesta y firma
-------------------------------------------------- */

    public void firma(){

        Intent activity_signature = new Intent(getApplicationContext(), SignatureActivity.class);
        activity_signature.putExtra("usuario", usuarioid);
        activity_signature.putExtra("pais", "México");
        activity_signature.putExtra("reporte", reporte.getText().toString());
        activity_signature.putExtra("id_serie", idATM);
        activity_signature.putExtra("corr_prev", "Preventivo");
        activity_signature.putExtra("Vobo", vobo.getText().toString());
        activity_signature.putExtra("puesto", puesto.getText().toString());
        activity_signature.putExtra("tipo_rep", "Preventivo");
        activity_signature.putExtra("folio", folio.getText().toString());
        activity_signature.putExtra("latitud", mLatitude.getText().toString());
        activity_signature.putExtra("longitud", mLongitude.getText().toString());
        activity_signature.putExtra("fecha", fecha.getText().toString());
        activity_signature.putExtra("serie", serie.getText().toString());
        activity_signature.putExtra("cliente", "ATM "+ RB_banco_V);
        startActivity(activity_signature);
    }
    private String mJSONURLString;




/* --------------------------------------------------
        GEOLOCALIZACION
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
                        REQUEST_LOCATION);
            }
        } else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                mLatitude.setText(String.valueOf(mLastLocation.getLatitude()));
                mLongitude.setText(String.valueOf(mLastLocation.getLongitude()));
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
                    mLatitude.setText(String.valueOf(mLastLocation.getLatitude()));
                    mLongitude.setText(String.valueOf(mLastLocation.getLongitude()));
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
          crea respaldo al detener app
    -------------------------------------------------- */
    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
        if(reporte.getText().toString().equals("")){
        }else {
            crea_respaldo();
            ///guarda respaldo
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
        List<Address> addresses = null;
        mPosicion.setText(String.valueOf(mLastLocation.getExtras()));
        mLatitude.setText(String.valueOf(mLastLocation.getLatitude()));
        mLongitude.setText(String.valueOf(mLastLocation.getLongitude()));
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
        solicita continar comn respaldo si lo encuentrra
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

    /* --------------------------------------------------
            funciones para resplados
    -------------------------------------------------- */
    public void   lee_respaldo_c(){    ////lee al iniciar automáticamente
        String archivoBusca="respaldo_p.txt";
        String[] archivos = fileList();

        for (int f = 0; f < archivos.length; f++){
            if (archivoBusca.equals(archivos[f])){
                muestraDialogo_confirmar(archivoBusca);  //si encuentra file de respaldo solicita confirmacion para escribir

            }
        }
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

    public void crea_respaldo(){
        String archivoBusca="respaldo_p.txt";
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

        date=fecha.getText().toString();
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
        hr_ini_V=TV_hr_ini.getText().toString();
        hr_fin_V=TV_hr_fin.getText().toString();
        fecha_ini_V=TV_fecha_ini.getText().toString();
        fecha_fin_V=TV_fecha_fin.getText().toString();
        SC_V=TV_sc.getText().toString();
        AC_V=TV_ac.getText().toString();

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
        if (RB_logo_cambio.isChecked()) {
            RB_logo_V = RB_logo_cambio.getText().toString();
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
        if (RB_senal_lectora_cambio.isChecked()) {
            RB_senal_lectora_V = RB_senal_lectora_cambio.getText().toString();
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
        if (RB_senal_impresora_cambio.isChecked()) {
            RB_senal_impresora_V = RB_senal_impresora_cambio.getText().toString();
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
        if (RB_senal_salida_efectivo_cambio.isChecked()) {
            RB_senal_salida_efectivo_V = RB_senal_salida_efectivo_cambio.getText().toString();
        }

        if (RB_01800_si.isChecked()) {
            RB_senal_01800_V = RB_01800_si.getText().toString();
        }
        if (RB_01800_no.isChecked()) {
            RB_senal_01800_V = RB_01800_no.getText().toString();
        }
        if (RB_01800_deteriorada.isChecked()) {
            RB_senal_01800_V = RB_01800_deteriorada.getText().toString();
        }
        if (RB_01800_cambio.isChecked()) {
            RB_senal_01800_V = RB_01800_cambio.getText().toString();
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
        if (RB_senal_carcaza_cambio.isChecked()) {
            RB_senal_carcaza_V = RB_senal_carcaza_cambio.getText().toString();
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
        if (RB_banco_CCK.isChecked()) {
            RB_banco_V = RB_banco_CCK.getText().toString();
        }
        if (RB_banco_otro.isChecked()) {
            RB_banco_V = banco_otro.getText().toString();
        }
        if (RB_remo_suc_remoto.isChecked()) {
            RB_remo_suc_V = RB_remo_suc_remoto.getText().toString();
        }
        if (RB_remo_suc_sucursal.isChecked()) {
            RB_remo_suc_V = RB_remo_suc_sucursal.getText().toString();
        }

        if (RB_regulador_si.isChecked()) {
            RB_regulador_V = RB_regulador_si.getText().toString();
        }
        if (RB_regulador_no.isChecked()) {
            RB_regulador_V = RB_regulador_no.getText().toString();
        }
        if (RB_01800_si.isChecked()) {
            RB_senal_01800_V = RB_01800_si.getText().toString();
        }
        if (RB_01800_no.isChecked()) {
            RB_senal_01800_V = RB_01800_no.getText().toString();
        }
        if (RB_01800_deteriorada.isChecked()) {
            RB_senal_01800_V = RB_01800_deteriorada.getText().toString();
        }
        if (RB_01800_cambio.isChecked()) {
            RB_senal_01800_V = RB_01800_cambio.getText().toString();
        }
        if (RB_antiskimmer_ebras.isChecked()) {
            RB_antiskimmer_V = RB_antiskimmer_ebras.getText().toString();
        }
        if (RB_antiskimmer_no.isChecked()) {
            RB_antiskimmer_V = RB_antiskimmer_no.getText().toString();
        }
        if (RB_antiskimmer_si.isChecked()) {
            RB_antiskimmer_V = RB_antiskimmer_si.getText().toString();
        }
        if (RB_blindaje_cab_no.isChecked()) {
            RB_blindaje_V = RB_blindaje_cab_no.getText().toString();
        }
        if (RB_blindaje_cab_si.isChecked()) {
            RB_blindaje_V = RB_blindaje_cab_si.getText().toString();
        }
        if (RB_braile_no.isChecked()) {
            RB_braile_V = RB_braile_no.getText().toString();
        }
        if (RB_braile_si.isChecked()) {
            RB_braile_V = RB_braile_si.getText().toString();
        }
        if (RB_caseta_prefab_no.isChecked()) {
            RB_caseta_prefab_V = RB_caseta_prefab_no.getText().toString();
        }
        if (RB_caseta_prefab_si.isChecked()) {
            RB_caseta_prefab_V = RB_caseta_prefab_si.getText().toString();
        }
        if (RB_chapa_rand_no.isChecked()) {
            RB_chapa_rand_V = RB_chapa_rand_no.getText().toString();
        }
        if (RB_chapa_rand_si.isChecked()) {
            RB_chapa_rand_V = RB_chapa_rand_si.getText().toString();
        }
        if (RB_entintado_billete_no.isChecked()) {
            RB_entintado_billete_V = RB_entintado_billete_no.getText().toString();
        }
        if (RB_entintado_billete_si.isChecked()) {
            RB_entintado_billete_V = RB_entintado_billete_si.getText().toString();
        }
        if (RB_jumper_no.isChecked()) {
            RB_jumper_V = RB_jumper_no.getText().toString();
        }
        if (RB_jumper_si.isChecked()) {
            RB_jumper_V = RB_jumper_si.getText().toString();
        }
        if (RB_nicho_de_protec_no.isChecked()) {
            RB_nicho_de_protec_V = RB_nicho_de_protec_no.getText().toString();
        }
        if (RB_nicho_de_protec_si.isChecked()) {
            RB_nicho_de_protec_V = RB_nicho_de_protec_si.getText().toString();
        }
        if (RB_tipo_placa_antivand_der.isChecked()) {
            RB_tipo_placa_antivand_V = RB_tipo_placa_antivand_der.getText().toString();
        }
        if (RB_tipo_placa_antivand_izq.isChecked()) {
            RB_tipo_placa_antivand_V = RB_tipo_placa_antivand_izq.getText().toString();
        }
        if (RB_placa_antivand_no.isChecked()) {
            RB_placa_antivand_V = RB_placa_antivand_no.getText().toString();
        }
        if (RB_placa_antivand_si.isChecked()) {
            RB_placa_antivand_V = RB_placa_antivand_si.getText().toString();
        }
        if (RB_rack_no.isChecked()) {
            RB_rack_V = RB_rack_no.getText().toString();
        }
        if (RB_rack_si.isChecked()) {
            RB_rack_V = RB_rack_si.getText().toString();
        }
        if (RB_seg_shutter_no.isChecked()) {
            RB_seg_shutter_V = RB_seg_shutter_no.getText().toString();
        }
        if (RB_seg_shutter_si.isChecked()) {
            RB_seg_shutter_V = RB_seg_shutter_si.getText().toString();
        }
        if (RB_telecontrol_conect_dañado.isChecked()) {
            RB_telecontrol_conect_V = RB_telecontrol_conect_dañado.getText().toString();
        }
        if (RB_telecontrol_conect_no.isChecked()) {
            RB_telecontrol_conect_V = RB_telecontrol_conect_no.getText().toString();
        }
        if (RB_telecontrol_conect_si.isChecked()) {
            RB_telecontrol_conect_V = RB_telecontrol_conect_si.getText().toString();
        }
        if (RB_tipo_telecontrol_IBOOT.isChecked()) {
            RB_tipo_telecontrol_V = RB_tipo_telecontrol_IBOOT.getText().toString();
        }
        if (RB_tipo_telecontrol_MTC.isChecked()) {
            RB_tipo_telecontrol_V = RB_tipo_telecontrol_MTC.getText().toString();
        }
        if (RB_tipo_telecontrol_Ninguno.isChecked()) {
            RB_tipo_telecontrol_V = RB_tipo_telecontrol_Ninguno.getText().toString();
        }
        String comas=  "\"";
        String guardaPost = "{" +
                comas+"IDkey"+comas+":"+comas+idkey+comas+","+
                comas+"Fecha"+comas+":"+comas+date+comas+","+
                comas+"Usuario"+comas+":"+comas+usuarioid+comas+","+
                comas+"aire_acondicionado"+comas+":"+comas+RB_aire_V+comas+","+
                comas+"ATM_da_servicio_a"+comas+":"+comas+RB_servicio_V+comas+","+
                comas+"base_de_anclaje"+comas+":"+comas+RB_base_anclaje_V+comas+","+
                comas+"carcaza"+comas+":"+comas+RB_carcaza_V+comas+","+
                comas+"carga"+comas+":"+comas+carga_V+comas+","+
                comas+"cerradura_tipo_H"+comas+":"+comas+RB_cerradura_V+comas+","+
                comas+"cesto_de_basura"+comas+":"+comas+RB_cesto_V+comas+","+
                comas+"cliente_final"+comas+":"+comas+cliente_final_V+comas+","+
                comas+"configuracion_cable_de_red"+comas+":"+comas+RB_conf_cableado_conex_V+comas+","+
                comas+"empotrado_al_muro"+comas+":"+comas+RB_empotrado_V+comas+","+
                comas+"equipo_de_comunicacion_alertado"+comas+":"+comas+RB_equipo_con_alte_V+comas+","+
                comas+"especifique_carcaza"+comas+":"+comas+carcaza_V+comas+","+
                comas+"estado_CPU_observaciones"+comas+":"+comas+ob_edo_CPU_V+comas+","+
                comas+"estado_CPU"+comas+":"+comas+RB_edo_CPU_V+comas+","+
                comas+"estado_del_cableado"+comas+":"+comas+RB_edo_cableado_V+comas+","+
                comas+"estado_dispensador_observaciones"+comas+":"+comas+ob_edo_dispensador_V+comas+","+
                comas+"estado_dispensador"+comas+":"+comas+RB_edo_dispensador_V+comas+","+
                comas+"estado_impresora_observaciones"+comas+":"+comas+ob_edo_impresora_V+comas+","+
                comas+"estado_impresora"+comas+":"+comas+RB_edo_impresora_V+comas+","+
                comas+"estado_lectora_observaciones"+comas+":"+comas+ob_edo_lectora_V+comas+","+
                comas+"estado_lectora"+comas+":"+comas+RB_edo_lectora_V+comas+","+
                comas+"estado_monitor_observaciones"+comas+":"+comas+ob_edo_monitor_V+comas+","+
                comas+"estado_monitor"+comas+":"+comas+RB_edo_monitor_V+comas+","+
                comas+"estado_teclados_observaciones"+comas+":"+comas+ob_edo_teclados_V+comas+","+
                comas+"estado_teclados"+comas+":"+comas+RB_edo_teclados_V+comas+","+
                comas+"expuesto_a_polvo"+comas+":"+comas+RB_polvo_V+comas+","+
                comas+"expuesto_a_sol"+comas+":"+comas+RB_sol_V+comas+","+
                comas+"fase_neutro_voltaje_pared"+comas+":"+comas+fase_neutro_v_pared_V+comas+","+
                comas+"fase_neutro_voltaje_regulado"+comas+":"+comas+fase_neutro_v_regulado_V+comas+","+
                comas+"fase_tierra_voltaje_pared"+comas+":"+comas+fase_tierra_v_pared_V+comas+","+
                comas+"fase_tierra_voltaje_regulado"+comas+":"+comas+fase_tierra_v_regulado_V+comas+","+
                comas+"herramientas_de_seguridad_instaladas_h1"+comas+":"+comas+CB_CSDS_V+comas+","+
                comas+"herramientas_de_seguridad_instaladas_h2"+comas+":"+comas+CB_CHECKER_V+comas+","+
                comas+"herramientas_de_seguridad_instaladas_h3"+comas+":"+comas+CB_RKL_V+comas+","+
                comas+"herramientas_de_seguridadinstaladas_h4"+comas+":"+comas+CB_FIX_WIN_V+comas+","+
                comas+"id_ATM"+comas+":"+comas+id_tpv_V+comas+","+
                comas+"iluminacion"+comas+":"+comas+RB_iluminacion_V+comas+","+
                comas+"KVA_regulador"+comas+":"+comas+regulador_kva_V+comas+","+
                comas+"KVA_UPS"+comas+":"+comas+UPS_kva_V+comas+","+
                comas+"limpieza_del_sitio"+comas+":"+comas+RB_limpieza_V+comas+","+
                comas+"localidad"+comas+":"+comas+localidad_V+comas+","+
                comas+"logo"+comas+":"+comas+RB_logo_V+comas+","+
                comas+"longitud_cable"+comas+":"+comas+longitud_cable_V+comas+","+
                comas+"marca_router"+comas+":"+comas+marca_router_V+comas+","+
                comas+"marca"+comas+":"+comas+marca_V+comas+","+
                comas+"modelo"+comas+":"+comas+modelo_V+comas+","+
                comas+"notas_comunicaciones"+comas+":"+comas+notas_comunic_V+comas+","+
                comas+"notas_instalacion_electrica"+comas+":"+comas+notas_inst_elect_V+comas+","+
                comas+"notas_seguridad"+comas+":"+comas+notas_seguridad_V+comas+","+
                comas+"numero_de_inventario"+comas+":"+comas+inventario_V+comas+","+
                comas+"perno_de_apertura"+comas+":"+comas+RB_perapertura_V+comas+","+
                comas+"placa_de_seguridad_sitio"+comas+":"+comas+RB_placa_seg_sitio_V+comas+","+
                comas+"placa_de_seguridad"+comas+":"+comas+RB_placa_seguridad_V+comas+","+
                comas+"polarizacion_correcta"+comas+":"+comas+RB_polarizacion_correcta_V+comas+","+
                comas+"postes_exteriores"+comas+":"+comas+RB_postes_ext_V+comas+","+
                comas+"pruebas_ping_resultado"+comas+":"+comas+pruebas_ping_result_V+comas+","+
                comas+"pruebas_ping"+comas+":"+comas+RB_pruebas_ping_V+comas+","+
                comas+"puntos_de_anclaje_otros"+comas+":"+comas+puntos_anclaje_otro_V+comas+","+
                comas+"puntos_de_anclaje"+comas+":"+comas+RB_puntos_anclaje_V+comas+","+
                comas+"reporte"+comas+":"+comas+reporte_V+comas+","+
                comas+"señal_carcaza"+comas+":"+comas+RB_senal_carcaza_V+comas+","+
                comas+"señal_impresora"+comas+":"+comas+RB_senal_impresora_V+comas+","+
                comas+"señal_lectora"+comas+":"+comas+RB_senal_lectora_V+comas+","+
                comas+"señal_salida_efectivo"+comas+":"+comas+RB_senal_salida_efectivo_V+comas+","+
                comas+"serie"+comas+":"+comas+serie_V+comas+","+
                comas+"sistema_operativo"+comas+":"+comas+sistema_operativo_V+comas+","+
                comas+"supresor_de_picos"+comas+":"+comas+RB_supresor_V+comas+","+
                comas+"temperatura"+comas+":"+comas+temperatura_V+comas+","+
                comas+"tierra_neutro_voltaje_pared"+comas+":"+comas+tierra_neutro_v_pared_V+comas+","+
                comas+"tierra_neutro_voltaje_regulado"+comas+":"+comas+tierra_neutro_v_regulado_V+comas+","+
                comas+"tipo_de_anclaje"+comas+":"+comas+RB_tipo_anclaje_V+comas+","+
                comas+"regulador"+comas+":"+comas+RB_regulador_V+comas+","+
                comas+"tipo_de_comunicacion"+comas+":"+comas+RB_tipo_comunicacion_V+comas+","+
                comas+"tipo_de_dial"+comas+":"+comas+RB_tipo_dial_V+comas+","+
                comas+"tipo_voltaje_regulado"+comas+":"+comas+RB_tipo_regulado_V+comas+","+
                comas+"UPS"+comas+":"+comas+RB_UPS_V+comas+","+
                comas+"version_checker"+comas+":"+comas+version_checker_V+comas+","+
                comas+"version_multivendor"+comas+":"+comas+version_multivendor_V+comas+","+
                comas+"voltaje_regulado_suministrado_por"+comas+":"+comas+RB_volt_regulado_sum_V+comas+","+
                comas+"banco"+comas+":"+comas+RB_banco_V+comas+","+
                comas+"remoto_sucursal"+comas+":"+comas+RB_remo_suc_V+comas+","+
                comas+"pais"+comas+":"+comas+"Mexico"+comas+","+
                comas+"equipo"+comas+":"+comas+"ATM"+comas+","+
                comas+"tipo_reporte"+comas+":"+comas+"Preventivo"+comas+","+
                comas+"solucion"+comas+":"+comas+solucion.getText().toString()+comas+","+
                comas+"Vobo"+comas+":"+comas+vobo.getText().toString()+comas+","+
                comas+"puesto"+comas+":"+comas+puesto.getText().toString()+comas+","+
                comas+"folio"+comas+":"+comas+folio.getText().toString()+comas+","+
                comas+"latitud"+comas+":"+comas+mLatitude.getText().toString()+comas+","+
                comas+"longitud"+comas+":"+comas+mLongitude.getText().toString()+comas+","+
                comas+"hr_ini"+comas+":"+comas+hr_ini_V+comas+","+
                comas+"hr_fin"+comas+":"+comas+hr_fin_V+comas+","+
                comas+"fecha_ini"+comas+":"+comas+fecha_ini_V+comas+","+
                comas+"fecha_fin"+comas+":"+comas+fecha_fin_V+comas+","+
                comas+"SC"+comas+":"+comas+SC_V+comas+","+
                comas+"AC"+comas+":"+comas+AC_V+comas+","+
                comas+"capacidad_hd"+comas+":"+comas+capacidad_hd.getText()+comas+","+
                comas+"RAM"+comas+":"+comas+RAM.getText()+comas+","+
                comas+"nombre_atm"+comas+":"+comas+nombre_atm.getText()+comas+","+
                comas+"ob_esp_sitio"+comas+":"+comas+ob_esp_sitio.getText()+comas+","+
                comas+"obs_telecontrol"+comas+":"+comas+obs_telecontrol.getText()+comas+","+
                comas+"tamano_monitor"+comas+":"+comas+tamaño_monitor.getText()+comas+","+
                comas+"procesador"+comas+":"+comas+procesador.getText()+comas+","+
                comas+"vel_procesador"+comas+":"+comas+vel_procesador.getText()+comas+","+
                comas+"version_antivirus"+comas+":"+comas+version_antivirus.getText()+comas+","+
                comas+"senal_01800"+comas+":"+comas+RB_senal_01800_V+comas+","+
                comas+"antiskimmer"+comas+":"+comas+RB_antiskimmer_V+comas+","+
                comas+"blindaje"+comas+":"+comas+RB_blindaje_V+comas+","+
                comas+"braile"+comas+":"+comas+RB_braile_V+comas+","+
                comas+"caseta_prefab"+comas+":"+comas+RB_caseta_prefab_V+comas+","+
                comas+"chapa_rand"+comas+":"+comas+RB_chapa_rand_V+comas+","+
                comas+"entintado_billete"+comas+":"+comas+RB_entintado_billete_V+comas+","+
                comas+"jumper"+comas+":"+comas+RB_jumper_V+comas+","+
                comas+"nicho_de_protec"+comas+":"+comas+RB_nicho_de_protec_V+comas+","+
                comas+"tipo_placa_antivand"+comas+":"+comas+RB_tipo_placa_antivand_V+comas+","+
                comas+"placa_antivand"+comas+":"+comas+RB_placa_antivand_V+comas+","+
                comas+"rack"+comas+":"+comas+RB_rack_V+comas+","+
                comas+"seg_shutter"+comas+":"+comas+RB_seg_shutter_V+comas+","+
                comas+"telecontrol_conect"+comas+":"+comas+RB_telecontrol_conect_V+comas+","+
                comas+"tipo_telecontrol"+comas+":"+comas+RB_tipo_telecontrol_V+comas+

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
                capacidad_hd.setText(todoJson.getString("capacidad_hd"));
                //folio.setText(todoJson.getString("folio"));
                mLatitude.setText(todoJson.getString("latitud"));
                mLongitude.setText(todoJson.getString("longitud"));
                nombre_atm.setText(todoJson.getString("nombre_atm"));
                ob_esp_sitio.setText(todoJson.getString("ob_esp_sitio"));
                obs_telecontrol.setText(todoJson.getString("obs_telecontrol"));
                procesador.setText(todoJson.getString("procesador"));
                puesto.setText(todoJson.getString("puesto"));
                RAM.setText(todoJson.getString("RAM"));
                solucion.setText(todoJson.getString("solucion"));
                tamaño_monitor.setText(todoJson.getString("tamano_monitor"));
                vel_procesador.setText(todoJson.getString("vel_procesador"));
                version_antivirus.setText(todoJson.getString("version_antivirus"));
                vobo.setText(todoJson.getString("Vobo"));
                fecha.setText(todoJson.getString("Fecha"));
                id_tpv.setText(todoJson.getString("id_ATM"));
                TV_ac.setText(todoJson.getString("AC"));
                carcaza.setText(todoJson.getString("especifique_carcaza"));
                carga.setText(todoJson.getString("carga"));
                cliente_final.setText(todoJson.getString("cliente_final"));
                fase_neutro_v_pared.setText(todoJson.getString("fase_neutro_voltaje_pared"));
                fase_neutro_v_regulado.setText(todoJson.getString("fase_neutro_voltaje_regulado"));
                fase_tierra_v_pared.setText(todoJson.getString("fase_tierra_voltaje_pared"));
                fase_tierra_v_regulado.setText(todoJson.getString("fase_tierra_voltaje_regulado"));
                TV_fecha_fin.setText(todoJson.getString("fecha_fin"));
                TV_fecha_ini.setText(todoJson.getString("fecha_ini"));
                TV_hr_fin.setText(todoJson.getString("hr_fin"));
                TV_hr_ini.setText(todoJson.getString("hr_ini"));
                id_tpv.setText(todoJson.getString("id_ATM"));
                inventario.setText(todoJson.getString("numero_de_inventario"));
                localidad.setText(todoJson.getString("localidad"));
                longitud_cable.setText(todoJson.getString("longitud_cable"));
                marca_router.setText(todoJson.getString("marca_router"));
                marca.setText(todoJson.getString("marca"));
                modelo.setText(todoJson.getString("modelo"));
                notas_comunic.setText(todoJson.getString("notas_comunicaciones"));
                notas_inst_elect.setText(todoJson.getString("notas_instalacion_electrica"));
                notas_seguridad.setText(todoJson.getString("notas_seguridad"));
                ob_edo_CPU.setText(todoJson.getString("estado_CPU_observaciones"));
                ob_edo_dispensador.setText(todoJson.getString("estado_dispensador_observaciones"));
                ob_edo_impresora.setText(todoJson.getString("estado_impresora_observaciones"));
                ob_edo_lectora.setText(todoJson.getString("estado_lectora_observaciones"));
                ob_edo_monitor.setText(todoJson.getString("estado_monitor_observaciones"));
                ob_edo_teclados.setText(todoJson.getString("estado_teclados_observaciones"));
                pruebas_ping_result.setText(todoJson.getString("pruebas_ping_resultado"));
                puntos_anclaje_otro.setText(todoJson.getString("puntos_de_anclaje_otros"));
                regulador_kva.setText(todoJson.getString("KVA_regulador"));
                reporte.setText(todoJson.getString("reporte"));
                TV_sc.setText(todoJson.getString("SC"));
                serie.setText(todoJson.getString("serie"));
                sistema_operativo.setText(todoJson.getString("sistema_operativo"));
                temperatura.setText(todoJson.getString("temperatura"));
                tierra_neutro_v_pared.setText(todoJson.getString("tierra_neutro_voltaje_pared"));
                tierra_neutro_v_regulado.setText(todoJson.getString("tierra_neutro_voltaje_regulado"));
                UPS_kva.setText(todoJson.getString("KVA_UPS"));
                version_checker.setText(todoJson.getString("version_checker"));
                version_multivendor.setText(todoJson.getString("version_multivendor"));
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
                if(todoJson.getString("banco").equals("CCK")){
                    RB_banco_CCK.setChecked(true);
                }
                if(todoJson.getString("remoto_sucursal").equals("Remoto")){
                    RB_remo_suc_remoto.setChecked(true);
                }
                if(todoJson.getString("remoto_sucursal").equals("Sucursal")){
                    RB_remo_suc_sucursal.setChecked(true);
                }
                if(todoJson.getString("herramientas_de_seguridad_instaladas_h1").equals("")){
                }else{
                    CB_CSDS.setChecked(true);
                }
                if(todoJson.getString("herramientas_de_seguridad_instaladas_h2").equals("")){
                }else{
                    CB_CHECKER.setChecked(true);
                }
                if(todoJson.getString("herramientas_de_seguridad_instaladas_h3").equals("")){
                }else{
                    CB_RKL.setChecked(true);
                }
                if(todoJson.getString("herramientas_de_seguridadinstaladas_h4").equals("")){
                }else{
                    CB_FIX_WIN.setChecked(true);
                }
                if(todoJson.getString("aire_acondicionado").equals("Si")){
                    RB_aire_si.setChecked(true);
                }
                if(todoJson.getString("aire_acondicionado").equals("No")){
                    RB_aire_no.setChecked(true);
                }
                if(todoJson.getString("antiskimmer").equals("Si")){
                    RB_antiskimmer_si.setChecked(true);
                }
                if(todoJson.getString("antiskimmer").equals("No")){
                    RB_antiskimmer_no.setChecked(true);
                }
                if(todoJson.getString("antiskimmer").equals("Cuenta con ebras")){
                    RB_antiskimmer_ebras.setChecked(true);
                }
                if(todoJson.getString("base_de_anclaje").equals("Si")){
                    RB_base_anclaje_si.setChecked(true);
                }
                if(todoJson.getString("base_de_anclaje").equals("No")){
                    RB_base_anclaje_no.setChecked(true);
                }
                if(todoJson.getString("blindaje").equals("Si")){
                    RB_blindaje_cab_si.setChecked(true);
                }
                if(todoJson.getString("blindaje").equals("No")){
                    RB_blindaje_cab_no.setChecked(true);
                }
                if(todoJson.getString("braile").equals("Si")){
                    RB_braile_si.setChecked(true);
                }
                if(todoJson.getString("braile").equals("No")){
                    RB_braile_no.setChecked(true);
                }
                if(todoJson.getString("carcaza").equals("Si")){
                    RB_carcaza_si.setChecked(true);
                }
                if(todoJson.getString("carcaza").equals("No")){
                    RB_carcaza_no.setChecked(true);
                }
                if(todoJson.getString("caseta_prefab").equals("Si")){
                    RB_caseta_prefab_si.setChecked(true);
                }
                if(todoJson.getString("caseta_prefab").equals("No")){
                    RB_caseta_prefab_no.setChecked(true);
                }
                if(todoJson.getString("cerradura_tipo_H").equals("Si")){
                    RB_cerradura_si.setChecked(true);
                }
                if(todoJson.getString("cerradura_tipo_H").equals("No")){
                    RB_cerradura_no.setChecked(true);
                }
                if(todoJson.getString("cesto_de_basura").equals("Si")){
                    RB_cesto_si.setChecked(true);
                }
                if(todoJson.getString("cesto_de_basura").equals("No")){
                    RB_cesto_no.setChecked(true);
                }
                if(todoJson.getString("chapa_rand").equals("Si")){
                    RB_chapa_rand_si.setChecked(true);
                }
                if(todoJson.getString("chapa_rand").equals("No")){
                    RB_chapa_rand_no.setChecked(true);
                }
                if(todoJson.getString("configuracion_cable_de_red").equals("Conexión directa")){
                    RB_conf_cableado_conex_directa.setChecked(true);
                }
                if(todoJson.getString("configuracion_cable_de_red").equals("Cruzado")){
                    RB_conf_cableado_conex_cruzado.setChecked(true);
                }
                if(todoJson.getString("estado_del_cableado").equals("Bueno")){
                    RB_edo_cableado_bueno.setChecked(true);
                }
                if(todoJson.getString("estado_del_cableado").equals("A la vista de los usuarios")){
                    RB_edo_cableado_a_la_vista.setChecked(true);
                }
                if(todoJson.getString("estado_del_cableado").equals("Maltratado")){
                    RB_edo_cableado_maltratado.setChecked(true);
                }
                if(todoJson.getString("estado_CPU").equals("Ok")){
                    RB_edo_CPU_ok.setChecked(true);
                }
                if(todoJson.getString("estado_CPU").equals("Dañado")){
                    RB_edo_CPU_danado.setChecked(true);
                }
                if(todoJson.getString("estado_dispensador").equals("Ok")){
                    RB_edo_dispensador_ok.setChecked(true);
                }
                if(todoJson.getString("estado_dispensador").equals("Dañado")){
                    RB_edo_dispensador_danado.setChecked(true);
                }
                if(todoJson.getString("estado_impresora").equals("Ok")){
                    RB_edo_impresora_ok.setChecked(true);
                }
                if(todoJson.getString("estado_impresora").equals("Dañado")){
                    RB_edo_impresora_danado.setChecked(true);
                }
                if(todoJson.getString("estado_lectora").equals("Ok")){
                    RB_edo_lectora_ok.setChecked(true);
                }
                if(todoJson.getString("estado_lectora").equals("Dañado")){
                    RB_edo_lectora_danado.setChecked(true);
                }
                if(todoJson.getString("estado_monitor").equals("Ok")){
                    RB_edo_monitor_ok.setChecked(true);
                }
                if(todoJson.getString("estado_monitor").equals("Dañado")){
                    RB_edo_monitor_danado.setChecked(true);
                }
                if(todoJson.getString("estado_teclados").equals("Ok")){
                    RB_edo_teclados_ok.setChecked(true);
                }
                if(todoJson.getString("estado_teclados").equals("Dañado")){
                    RB_edo_teclados_danado.setChecked(true);
                }
                if(todoJson.getString("empotrado_al_muro").equals("Si")){
                    RB_empotrado_si.setChecked(true);
                }
                if(todoJson.getString("empotrado_al_muro").equals("No")){
                    RB_empotrado_no.setChecked(true);
                }
                if(todoJson.getString("entintado_billete").equals("Si")){
                    RB_entintado_billete_si.setChecked(true);
                }
                if(todoJson.getString("entintado_billete").equals("No")){
                    RB_entintado_billete_no.setChecked(true);
                }
                if(todoJson.getString("equipo_de_comunicacion_alertado").equals("Si")){
                    RB_equipo_con_alte_si.setChecked(true);
                }
                if(todoJson.getString("equipo_de_comunicacion_alertado").equals("No")){
                    RB_equipo_con_alte_no.setChecked(true);
                }
                if(todoJson.getString("iluminacion").equals("Si")){
                    RB_iluminacion_si.setChecked(true);
                }
                if(todoJson.getString("iluminacion").equals("No")){
                    RB_iluminacion_no.setChecked(true);
                }
                if(todoJson.getString("jumper").equals("Si")){
                    RB_jumper_si.setChecked(true);
                }
                if(todoJson.getString("jumper").equals("No")){
                    RB_jumper_no.setChecked(true);
                }
                if(todoJson.getString("limpieza_del_sitio").equals("Buena")){
                    RB_limpieza_buena.setChecked(true);
                }
                if(todoJson.getString("limpieza_del_sitio").equals("Mala")){
                    RB_limpieza_mala.setChecked(true);
                }
                if(todoJson.getString("limpieza_del_sitio").equals("Regular")){
                    RB_limpieza_regular.setChecked(true);
                }
                if(todoJson.getString("logo").equals("Si")){
                    RB_logo_si.setChecked(true);
                }
                if(todoJson.getString("logo").equals("No")){
                    RB_logo_no.setChecked(true);
                }
                if(todoJson.getString("logo").equals("Deteriorada")){
                    RB_logo_deteriorada.setChecked(true);
                }
                if(todoJson.getString("logo").equals("Se cambió")){
                    RB_logo_cambio.setChecked(true);
                }
                if(todoJson.getString("senal_01800").equals("Si")){
                    RB_01800_si.setChecked(true);
                }
                if(todoJson.getString("senal_01800").equals("No")){
                    RB_01800_no.setChecked(true);
                }
                if(todoJson.getString("senal_01800").equals("Deteriorada")){
                    RB_01800_deteriorada.setChecked(true);
                }
                if(todoJson.getString("senal_01800").equals("Se cambió")){
                    RB_01800_cambio.setChecked(true);
                }
                if(todoJson.getString("señal_carcaza").equals("Si")){
                    RB_senal_carcaza_si.setChecked(true);
                }
                if(todoJson.getString("señal_carcaza").equals("No")){
                    RB_senal_carcaza_no.setChecked(true);
                }
                if(todoJson.getString("señal_carcaza").equals("Deteriorada")){
                    RB_senal_carcaza_deteriorada.setChecked(true);
                }
                if(todoJson.getString("señal_carcaza").equals("Se cambió")){
                    RB_senal_carcaza_cambio.setChecked(true);
                }
                if(todoJson.getString("señal_impresora").equals("Si")){
                    RB_senal_impresora_si.setChecked(true);
                }
                if(todoJson.getString("señal_impresora").equals("No")){
                    RB_senal_impresora_no.setChecked(true);
                }
                if(todoJson.getString("señal_impresora").equals("Deteriorada")){
                    RB_senal_impresora_deteriorada.setChecked(true);
                }
                if(todoJson.getString("señal_impresora").equals("Se cambió")){
                    RB_senal_impresora_cambio.setChecked(true);
                }
                if(todoJson.getString("señal_lectora").equals("Si")){
                    RB_senal_lectora_si.setChecked(true);
                }
                if(todoJson.getString("señal_lectora").equals("No")){
                    RB_senal_lectora_no.setChecked(true);
                }
                if(todoJson.getString("señal_lectora").equals("Deteriorada")){
                    RB_senal_lectora_deteriorada.setChecked(true);
                }
                if(todoJson.getString("señal_lectora").equals("Se cambió")){
                    RB_senal_lectora_cambio.setChecked(true);
                }
                if(todoJson.getString("señal_salida_efectivo").equals("Si")){
                    RB_senal_salida_efectivo_si.setChecked(true);
                }
                if(todoJson.getString("señal_salida_efectivo").equals("No")){
                    RB_senal_salida_efectivo_no.setChecked(true);
                }
                if(todoJson.getString("señal_salida_efectivo").equals("Deteriorada")){
                    RB_senal_salida_efectivo_deteriorada.setChecked(true);
                }
                if(todoJson.getString("señal_salida_efectivo").equals("Se cambió")){
                    RB_senal_salida_efectivo_cambio.setChecked(true);
                }
                if(todoJson.getString("nicho_de_protec").equals("Si")){
                    RB_nicho_de_protec_si.setChecked(true);
                }
                if(todoJson.getString("nicho_de_protec").equals("No")){
                    RB_nicho_de_protec_no.setChecked(true);
                }
                if(todoJson.getString("perno_de_apertura").equals("Si")){
                    RB_perno_apertura_si.setChecked(true);
                }
                if(todoJson.getString("perno_de_apertura").equals("No")){
                    RB_perno_apertura_no.setChecked(true);
                }
                if(todoJson.getString("placa_antivand").equals("Si")){
                    RB_placa_antivand_si.setChecked(true);
                }
                if(todoJson.getString("placa_antivand").equals("No")){
                    RB_placa_antivand_no.setChecked(true);
                }
                if(todoJson.getString("placa_de_seguridad_sitio").equals("Si")){
                    RB_placa_seg_sitio_si.setChecked(true);
                }
                if(todoJson.getString("placa_de_seguridad_sitio").equals("No")){
                    RB_placa_seg_sitio_no.setChecked(true);
                }
                if(todoJson.getString("placa_de_seguridad").equals("Si")){
                    RB_placa_seguridad_si.setChecked(true);
                }
                if(todoJson.getString("placa_de_seguridad").equals("No")){
                    RB_placa_seguridad_no.setChecked(true);
                }
                if(todoJson.getString("polarizacion_correcta").equals("Si")){
                    RB_polarizacion_correcta_si.setChecked(true);
                }
                if(todoJson.getString("polarizacion_correcta").equals("No")){
                    RB_polarizacion_correcta_no.setChecked(true);
                }
                if(todoJson.getString("expuesto_a_polvo").equals("Si")){
                    RB_polvo_si.setChecked(true);
                }
                if(todoJson.getString("expuesto_a_polvo").equals("No")){
                    RB_polvo_no.setChecked(true);
                }
                if(todoJson.getString("postes_exteriores").equals("Si")){
                    RB_postes_ext_si.setChecked(true);
                }
                if(todoJson.getString("postes_exteriores").equals("No")){
                    RB_postes_ext_no.setChecked(true);
                }
                if(todoJson.getString("pruebas_ping").equals("Si")){
                    RB_pruebas_ping_si.setChecked(true);
                }
                if(todoJson.getString("pruebas_ping").equals("No")){
                    RB_pruebas_ping_no.setChecked(true);
                }
                if(todoJson.getString("puntos_de_anclaje").equals("4")){
                    RB_puntos_anclaje_4.setChecked(true);
                }
                if(todoJson.getString("puntos_de_anclaje").equals("6")){
                    RB_puntos_anclaje_6.setChecked(true);
                }
                if(todoJson.getString("puntos_de_anclaje").equals("otro")){
                    RB_puntos_anclaje_otro.setChecked(true);
                }
                if(todoJson.getString("rack").equals("Si")){
                    RB_rack_si.setChecked(true);
                }
                if(todoJson.getString("rack").equals("No")){
                    RB_rack_no.setChecked(true);
                }
                if(todoJson.getString("regulador").equals("Si")){
                    RB_regulador_si.setChecked(true);
                }
                if(todoJson.getString("regulador").equals("No")){
                    RB_regulador_no.setChecked(true);
                }
                if(todoJson.getString("seg_shutter").equals("Si")){
                    RB_seg_shutter_si.setChecked(true);
                }
                if(todoJson.getString("seg_shutter").equals("No")){
                    RB_seg_shutter_no.setChecked(true);
                }
                if(todoJson.getString("expuesto_a_sol").equals("Si")){
                    RB_sol_si.setChecked(true);
                }
                if(todoJson.getString("expuesto_a_sol").equals("No")){
                    RB_sol_no.setChecked(true);
                }
                if(todoJson.getString("supresor_de_picos").equals("Si")){
                    RB_supresor_si.setChecked(true);
                }
                if(todoJson.getString("supresor_de_picos").equals("No")){
                    RB_supresor_no.setChecked(true);
                }
                if(todoJson.getString("telecontrol_conect").equals("Si")){
                    RB_telecontrol_conect_si.setChecked(true);
                }
                if(todoJson.getString("telecontrol_conect").equals("No")){
                    RB_telecontrol_conect_no.setChecked(true);
                }
                if(todoJson.getString("telecontrol_conect").equals("Dañado")){
                    RB_telecontrol_conect_dañado.setChecked(true);
                }
                if(todoJson.getString("UPS").equals("Si")){
                    RB_UPS_si.setChecked(true);
                }
                if(todoJson.getString("UPS").equals("No")){
                    RB_UPS_no.setChecked(true);
                }
                if(todoJson.getString("ATM_da_servicio_a").equals("Todo púbico")){
                    RB_publico.setChecked(true);
                }
                if(todoJson.getString("ATM_da_servicio_a").equals("Solo personal de la empresa")){
                    RB_Personal.setChecked(true);
                }
                if(todoJson.getString("tipo_de_anclaje").equals("RM16")){
                    RB_tipo_anclaje_RM16.setChecked(true);
                }
                if(todoJson.getString("tipo_de_anclaje").equals("Tradicional")){
                    RB_tipo_anclaje_tradicional.setChecked(true);
                }
                if(todoJson.getString("tipo_de_comunicacion").equals("Satelital")){
                    RB_satelital.setChecked(true);
                }
                if(todoJson.getString("tipo_de_comunicacion").equals("DIAL-UP(TELLABS)")){
                    RB_DIAL.setChecked(true);
                }
                if(todoJson.getString("tipo_de_comunicacion").equals("ADSL")){
                    RB_ADSL.setChecked(true);
                }
                if(todoJson.getString("tipo_de_comunicacion").equals("3G / 4G")){
                    RB_3_4_G.setChecked(true);
                }
                if(todoJson.getString("tipo_de_dial").equals("Mecánico")){
                    RB_tipo_dial_mecanico.setChecked(true);
                }
                if(todoJson.getString("tipo_de_dial").equals("Electrónico")){
                    RB_tipo_dial_electrico.setChecked(true);
                }
                if(todoJson.getString("tipo_de_dial").equals("Randomico")){
                    RB_tipo_dial_randomico.setChecked(true);
                }
                if(todoJson.getString("tipo_placa_antivand").equals("Izq")){
                    RB_tipo_placa_antivand_izq.setChecked(true);
                }
                if(todoJson.getString("tipo_placa_antivand").equals("Der")){
                    RB_tipo_placa_antivand_der.setChecked(true);
                }
                if(todoJson.getString("tipo_voltaje_regulado").equals("Twist lock")){
                    RB_tipo_regulado_Twist_lock.setChecked(true);
                }
                if(todoJson.getString("tipo_voltaje_regulado").equals("Normal")){
                    RB_tipo_regulado_normal.setChecked(true);
                }
                if(todoJson.getString("tipo_telecontrol").equals("IBOOT")){
                    RB_tipo_telecontrol_IBOOT.setChecked(true);
                }
                if(todoJson.getString("tipo_telecontrol").equals("MTC")){
                    RB_tipo_telecontrol_MTC.setChecked(true);
                }
                if(todoJson.getString("tipo_telecontrol").equals("Ninguno")){
                    RB_tipo_telecontrol_Ninguno.setChecked(true);
                }
                if(todoJson.getString("voltaje_regulado_suministrado_por").equals("UPS")){
                    RB_volt_regulado_sum_UPS.setChecked(true);
                }
                if(todoJson.getString("voltaje_regulado_suministrado_por").equals("Regulador")){
                    RB_volt_regulado_sum_regulador.setChecked(true);
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


    /* --------------------------------------------------
       //TODO   busca todos los num de folio y añade el mayor +1 y lo crea asi la prox busqueda ya no lo duplicará
    -------------------------------------------------- */

    private Integer a ,  array_len_folios;
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

        ATMprevActivity.Post_folio_consecutivo post_folio_consecutivo = new ATMprevActivity.Post_folio_consecutivo();
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



}













