package com.example.carlosje.reportes_ibm;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.lang.String.valueOf;


public class InventarioActivity extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private EditText reporte,deter,localidad,marca,modelo, serie,cliente_otro,description, equipo_otro, folio;
    private ProgressBar progressBarINV;
    private String  pais,usuario,tipo_fecha, cliente, tipo_equipo ;
    private RadioButton RB_cliente_ALSEA,RB_cliente_Banorte_Suc,RB_cliente_Banorte_Nod,RB_cliente_CCK,RB_cliente_otro, RB_POS1,RB_POS2,RB_POS3, RB_POS4, RB_equipo_otro;
    private TextView tv_user,fecha;
    private Boolean flag_cliente, flag_equipo;
    private String comas=  "\"";
    private String REV;
    private Integer i_inv;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);


        reporte= (EditText) findViewById(R.id.reporte);
        deter= (EditText) findViewById(R.id.deter);
        localidad= (EditText) findViewById(R.id.localidad);
        marca= (EditText) findViewById(R.id.marca);
        modelo= (EditText) findViewById(R.id.modelo);
        serie= (EditText) findViewById(R.id.serie);
        cliente_otro= (EditText) findViewById(R.id.cliente_otro);
        description= (EditText) findViewById(R.id.description);
        folio= (EditText) findViewById(R.id.folio);
        progressBarINV=(ProgressBar) findViewById(R.id.progressBarINV);
        equipo_otro=(EditText) findViewById(R.id.equipo_otro);
        RB_cliente_ALSEA = (RadioButton) findViewById(R.id.RB_cliente_ALSEA);
        RB_cliente_Banorte_Suc = (RadioButton) findViewById(R.id.RB_cliente_Banorte_Suc);
        RB_cliente_Banorte_Nod = (RadioButton) findViewById(R.id.RB_cliente_Banorte_Nod);
        RB_cliente_CCK = (RadioButton) findViewById(R.id.RB_cliente_CCK);
        RB_cliente_otro = (RadioButton) findViewById(R.id.RB_cliente_otro);
        RB_POS1 = (RadioButton) findViewById(R.id.RB_POS1);
        RB_POS2 = (RadioButton) findViewById(R.id.RB_POS2);
        RB_POS3 = (RadioButton) findViewById(R.id.RB_POS3);
        RB_POS4 = (RadioButton) findViewById(R.id.RB_POS4);
        RB_equipo_otro = (RadioButton) findViewById(R.id.RB_equipo_otro);
        tv_user=(TextView) findViewById(R.id.tv_user);
        fecha=(TextView) findViewById(R.id.fecha);
        flag_cliente= false;
        flag_equipo= false;
        cliente="";
        pais = "México";
        usuario = getIntent().getStringExtra("usuario");
        tv_user.setText(usuario);
        requestQueue = RequestQueueSingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        i_inv=0;
        lee_respaldo_i();
        crea_folio();

    }
      /* --------------------------------------------------
     /lee al iniciar automáticamente repaldos
     -------------------------------------------------- */

    public void   lee_respaldo_i(){    ////lee al iniciar automáticamente
        String archivoBusca="respaldo_i.txt";
        String[] archivos = fileList();

        for (int f = 0; f < archivos.length; f++){
            if (archivoBusca.equals(archivos[f])){
                muestraDialogo_confirmar(archivoBusca);  //si encuentra file de respaldo solicita confirmacion para escribir
            }
        }
    }

    public void muestraDialogo_confirmar(String archivoBusca) {
        final String archivoBuscaOk= archivoBusca;
        AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
        myBuild.setTitle("Importante");
        myBuild.setMessage("¿Desea continuar con la última seción?");
        myBuild.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Carga_datos_respaldo_i(archivoBuscaOk);
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

/* --------------------------------------------------
     /fecha
     -------------------------------------------------- */
    public void showDatePickerDialog_Head_i(View v) {
        DialogFragment newFragment = new DatePick();
        Bundle args = new Bundle();
        args.putInt("num", 3);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
 /* --------------------------------------------------
    ///recoge datos calendario
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
        fecha.setText(valueOf(year)+ "/"+ m_v + "/" + d_v  );

    }

/* --------------------------------------------------
    muestra oculta selecciones en otro
-------------------------------------------------- */

    public void verotro(View view){
        cliente_otro.setVisibility(View.VISIBLE);
        flag_cliente=true;
    }
    public void ocultarotro(View view){
        cliente_otro.setVisibility(View.GONE);
        flag_cliente=false;
    }
    public void ver_eq_otro(View view){
        equipo_otro.setVisibility(View.VISIBLE);
        flag_equipo=true;
    }
    public void ocultar_eq_otro(View view){
        equipo_otro.setVisibility(View.GONE);
        flag_equipo=false;
    }
 /* --------------------------------------------------
 Valida inventario
-------------------------------------------------- */

    public void save_in(View view){

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
            CharSequence text = "Favor documentar reporte";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        if (reporte.getText().toString().length()!=7) {
            Context context = getApplicationContext();
            CharSequence text = "Favor documentar correctamente reporte IBM (7 caracteres)";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        if (reporte.getText().toString().substring(0,1).equals("P")) {}
        else{
            Context context = getApplicationContext();
            CharSequence text = "Favor documentar correctamente reporte IBM";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        if (deter.getText().toString().equals("")) {
            Context context = getApplicationContext();
            CharSequence text = "Favor documentar determinante";
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
        if (localidad.getText().toString().equals("")) {
            Context context = getApplicationContext();
            CharSequence text = "Favor documentar localidad";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        if (description.getText().toString().equals("")) {
            Context context = getApplicationContext();
            CharSequence text = "Favor documentar descripción del equipo";
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
        if (!RB_cliente_ALSEA.isChecked() && !RB_cliente_Banorte_Suc.isChecked() && !RB_cliente_Banorte_Nod.isChecked() && !RB_cliente_CCK.isChecked() && !RB_cliente_otro.isChecked()) {
            int duration = Toast.LENGTH_SHORT;
            Context context = getApplicationContext();
            CharSequence text = "Favor seleccionar cliente";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        if (!RB_POS1.isChecked() && !RB_POS2.isChecked() && !RB_POS3.isChecked() && !RB_POS4.isChecked() && !RB_equipo_otro.isChecked()) {
            int duration = Toast.LENGTH_SHORT;
            Context context = getApplicationContext();
            CharSequence text = "Favor seleccionar tipo de equipo";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        if(flag_cliente==true && cliente_otro.getText().toString().equals("") ){
            Context context = getApplicationContext();
            CharSequence text = "Favor documentar cliente (otro)";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        if(flag_equipo==true && equipo_otro.getText().toString().equals("") ){
            Context context = getApplicationContext();
            CharSequence text = "Favor documentar equipo (otro)";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
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
        if (RB_POS1.isChecked() ) {
            tipo_equipo=RB_POS1.getText().toString();
        }
        if (RB_POS2.isChecked() ) {
            tipo_equipo=RB_POS2.getText().toString();
        }
        if (RB_POS3.isChecked() ) {
            tipo_equipo=RB_POS3.getText().toString();
        }
        if (RB_POS4.isChecked() ) {
            tipo_equipo=RB_POS4.getText().toString();
        }
        if (RB_equipo_otro.isChecked() ) {
            tipo_equipo=equipo_otro.getText().toString();
        }

        progressBarINV.setVisibility(View.VISIBLE);
        muestraDialogo();
    }
  /* --------------------------------------------------
  Al precionar atras en app
-------------------------------------------------- */

    @Override
    public void onBackPressed() {

        muestraDialogo2();
    }

    public void muestraDialogo2() {
        AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
        myBuild.setTitle("Importante");
        myBuild.setMessage("¿Salir de la carga de inventario?");
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
       ///confirma guardado
   -------------------------------------------------- */
    public void muestraDialogo() {
        AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
        myBuild.setTitle("Importante");
        myBuild.setMessage("¿Guardar Inventario? Al confirmar se cargaran los datos documentados.");
        myBuild.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                save_inventario();
            }
        });

        myBuild.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressBarINV.setVisibility(View.GONE);
                dialog.cancel();

            }
        });

        AlertDialog dialog = myBuild.create();
        dialog.show();
    }

 /* --------------------------------------------------
       Guarda
   -------------------------------------------------- */

    public void save_inventario(){
        addResicler();
        Post post = new Post();
        Map<String, Object> postValues = post.toMap();


         /* --------------------------------------------------
            Añade a Cloudant
        -------------------------------------------------- */

        String url=getResources().getString(R.string.urlCloudant)+"/inventarios";
        JsonObjectRequest jar1 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(postValues), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    REV = jsonObject.getString("rev");
                    String validaRespuesta=response.toString().substring(0, 10);
                    if(validaRespuesta.equals("{" +comas + "ok" +comas+ ":true")){
                        Context context = getApplicationContext();
                        CharSequence text = "Inventario Almacenado Correctamente. " ;
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        marca.setText("");
                        modelo.setText("");
                        serie.setText("");
                        description.setText("");
                        progressBarINV.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    /// si no hay conexcion o hay error guarda el post en file txt
                    String comas=  "\"";
                    String guardaPost = "{" +
                            comas+"reporte"+comas+":"+comas+reporte.getText().toString()+comas+","+
                            comas+"cliente"+comas+":"+comas+cliente+comas+","+
                            comas+"Fecha"+comas+":"+comas+fecha.getText().toString()+comas+","+
                            comas+"marca"+comas+":"+comas+marca.getText().toString()+comas+","+
                            comas+"modelo"+comas+":"+comas+modelo.getText().toString()+comas+","+
                            comas+"serie"+comas+":"+comas+serie.getText().toString()+comas+","+
                            comas+"determinante"+comas+":"+comas+deter.getText().toString()+comas+","+
                            comas+"localidad"+comas+":"+comas+localidad.getText().toString()+comas+","+
                            comas+"descripcion"+comas+":"+comas+description.getText().toString()+comas+","+
                            comas+"tipo_equipo"+comas+":"+comas+tipo_equipo+comas+","+
                            comas+"Usuario"+comas+":"+comas+usuario+comas+
                            "}";

                    ////Genera JSON de variables
                    Random r = new Random();
                    i_inv = r.nextInt(100) + 1;
                    Long tsLong = System.currentTimeMillis()/1000;
                    String ts = tsLong.toString();
                    String filename= "IN_"+ts+"_"+reporte.getText().toString()+"_"+i_inv+".txt";
                    OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(filename, Activity.MODE_PRIVATE));
                    archivo.write(guardaPost);
                    archivo.flush();
                    archivo.close();
                    Context context = getApplicationContext();
                    CharSequence text = "Problema al subir file, almacenado en pendientes. " + filename;
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    progressBarINV.setVisibility(View.GONE);
                    marca.setText("");
                    modelo.setText("");
                    serie.setText("");
                    description.setText("");

                } catch (IOException ee) {
                    progressBarINV.setVisibility(View.GONE);
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
        RB_POS1.setChecked(false);
        RB_POS2.setChecked(false);
        RB_POS3.setChecked(false);
        RB_POS4.setChecked(false);
        RB_equipo_otro.setChecked(false);
        equipo_otro.setText("");
    }



/* --------------------------------------------------
            Mapa cloudant
-------------------------------------------------- */

    public class Post {
        public Post() {
        }
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("cliente", cliente);
            result.put("Fecha", fecha.getText().toString());
            result.put("marca", marca.getText().toString());
            result.put("modelo", modelo.getText().toString());
            result.put("serie", serie.getText().toString());
            result.put("determinante", deter.getText().toString());
            result.put("localidad", localidad.getText().toString());
            result.put("reporte", reporte.getText().toString());
            result.put("Usuario", usuario);
            result.put("folio", folio.getText().toString());
            result.put("descripcion", description.getText().toString());
            result.put("tipo_equipo", tipo_equipo);
            return result;
        }
    }

    private List items = new ArrayList();

      /* --------------------------------------------------
            Añade aal recicler
        -------------------------------------------------- */

    public void addResicler(){

        items.add(new Inventario(reporte.getText().toString(), deter.getText().toString(),marca.getText().toString(),modelo.getText().toString(),serie.getText().toString(),description.getText().toString(), tipo_equipo));

// Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);
// Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(lManager);
// Crear un nuevo adaptador
        adapter = new InventarioAdapter(items);
        recycler.setAdapter(adapter);
    }

    /* --------------------------------------------------
               Carga el respaldo    / fuciones de respaldo
     -------------------------------------------------- */
    public void Carga_datos_respaldo_i(String archivoBusca){

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
                reporte.setText(todoJson.getString("reporte"));
                deter.setText(todoJson.getString("determinante"));
                //fecha.setText(todoJson.getString("Fecha"));
                marca.setText(todoJson.getString("marca"));
                modelo.setText(todoJson.getString("modelo"));
                serie.setText(todoJson.getString("serie"));
                localidad.setText(todoJson.getString("localidad"));
                description.setText(todoJson.getString("descripcion"));
                Integer i_otros=0;
                Integer i_equipo=0;
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
                if(todoJson.getString("tipo_equipo").equals("POS1")){
                    RB_POS1.setChecked(true);
                    i_equipo=1;
                }
                if(todoJson.getString("tipo_equipo").equals("POS2")){
                    RB_POS2.setChecked(true);
                    i_equipo=1;
                }
                if(todoJson.getString("tipo_equipo").equals("POS3")){
                    RB_POS3.setChecked(true);
                    i_equipo=1;
                }
                if(todoJson.getString("tipo_equipo").equals("POS4")){
                    RB_POS4.setChecked(true);
                    i_equipo=1;
                }
                if(i_equipo==0){
                    RB_equipo_otro.setChecked(true);
                    equipo_otro.setText(todoJson.getString("tipo_equipo"));
                    equipo_otro.setVisibility(View.VISIBLE);
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

    @Override
    protected void onStop() {
        super.onStop();
        if(reporte.getText().toString().equals("")){
        }else {
            crea_respaldo();
            ///guarda respaldo
        }
    }

    String archivoBusca;
    public void crea_respaldo(){

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
        if (RB_POS1.isChecked() ) {
            tipo_equipo=RB_POS1.getText().toString();
        }
        if (RB_POS2.isChecked() ) {
            tipo_equipo=RB_POS2.getText().toString();
        }
        if (RB_POS3.isChecked() ) {
            tipo_equipo=RB_POS3.getText().toString();
        }
        if (RB_POS3.isChecked() ) {
            tipo_equipo=RB_POS3.getText().toString();
        }
        if (RB_equipo_otro.isChecked() ) {
            tipo_equipo=equipo_otro.getText().toString();
        }
        archivoBusca="respaldo_i.txt";

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
        String comas=  "\"";
        String guardaPost = "{" +
                comas+"reporte"+comas+":"+comas+reporte.getText().toString()+comas+","+
                comas+"cliente"+comas+":"+comas+cliente+comas+","+
                //comas+"Fecha"+comas+":"+comas+fecha.getText().toString()+comas+","+
                comas+"marca"+comas+":"+comas+marca.getText().toString()+comas+","+
                comas+"modelo"+comas+":"+comas+modelo.getText().toString()+comas+","+
                comas+"serie"+comas+":"+comas+serie.getText().toString()+comas+","+
                comas+"determinante"+comas+":"+comas+deter.getText().toString()+comas+","+
                comas+"localidad"+comas+":"+comas+localidad.getText().toString()+comas+","+
                comas+"descripcion"+comas+":"+comas+description.getText().toString()+comas+","+
                comas+"tipo_equipo"+comas+":"+comas+tipo_equipo+comas+","+
                comas+"Usuario"+comas+":"+comas+usuario+comas+
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
       //TODO   busca todos los num de folio y añade el mayor +1 y lo crea asi la prox busqueda ya no lo duplicará
    -------------------------------------------------- */

    private Integer a ,  array_len_folios;
    private String folio_b;
    private String mJSONURLString;
    private void crea_folio() {

        folio_b="";
        a=0;

        array_len_folios=0;
        //mJSONURLString = getResources().getString(R.string.urlCloudant) + "/folio_consecutivo/_all_docs";
        mJSONURLString = getResources().getString(R.string.urlCloudant) + "/folio_consecutivo/_design_docs/";
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

        InventarioActivity.Post_folio_consecutivo post_folio_consecutivo = new InventarioActivity.Post_folio_consecutivo();
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

    public void scanNow(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Scanea el código de barras");
        integrator.setResultDisplayDuration(0);
        integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();

    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String scanContent="";

        if (scanningResult.getContents() != null) {
            //we have a result


            scanContent = scanningResult.getContents();


            // display it on screen



        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"No se recibió Código de Barras", Toast.LENGTH_SHORT);
            toast.show();


        }

        serie.setText(scanContent);
    }


}
