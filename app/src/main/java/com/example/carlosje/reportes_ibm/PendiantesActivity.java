package com.example.carlosje.reportes_ibm;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PendiantesActivity extends AppCompatActivity {
    private RecyclerView reciclador;

    private RecyclerView.LayoutManager lManager;
    private RecyclerView.Adapter adapter;
    private List items = new ArrayList();

    private ImageView ima_up;


    private ProgressBar progressBar;

    private Boolean flagTermina=false;
    private int CuentaCiclos=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendiantes);

        ima_up= (ImageView) findViewById(R.id.ima_up);

        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        ima_up.setVisibility(View.GONE);


        requestQueue = RequestQueueSingleton.getInstance(this.getApplicationContext()).getRequestQueue();




        llenarRecicler();

        final PendientesAdapter adapter = new PendientesAdapter(items);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = reciclador.getChildAdapterPosition(v);
                final long id = reciclador.getChildItemId(v);
                Log.i("DemoRecView", "Pulsado el elemento " + position);
                Log.i("DemoRecView", "Pulsado el elemento " + id);

            }
        });


        reciclador.setAdapter(adapter);






    }



    private void llenarRecicler() {
        ima_up.setVisibility(View.GONE);

        reciclador = (RecyclerView) findViewById(R.id.reciclador_evi);

        lManager = new GridLayoutManager(PendiantesActivity.this, 2);

        items.clear();

        reciclador.setLayoutManager(lManager);


        adapter = new PendientesAdapter(items);
        reciclador.setAdapter(adapter);


        String[] archivos = fileList();


        for (int f = 0; f < archivos.length; f++){

            String FilePendiente=archivos[f];

            String valida=FilePendiente.substring(0, 2);



            ////Verifica los files por sus 2 primeros caracteres
            if(valida.equals("EV") || valida.equals("FO") || valida.equals("EN") || valida.equals("CO") || valida.equals("PR") || valida.equals("IC") || valida.equals("IP") ) {

                String s =FilePendiente;
                String[] split = s.split(".txt");
                String firstSubString = split[0];

                ima_up.setVisibility(View.VISIBLE);

                items.add(new Pendientes(firstSubString));


                reciclador = (RecyclerView) findViewById(R.id.reciclador_evi);
                reciclador.setHasFixedSize(true);


                lManager = new GridLayoutManager(PendiantesActivity.this, 2);
                reciclador.setLayoutManager(lManager);


                adapter = new PendientesAdapter(items);
                reciclador.setAdapter(adapter);


            }


        }







    }



    private String F_todo;
    private String REV;
    private String base="";
    private String FilePendiente;
    RequestQueue requestQueue;

    public void intentaSubir(View view) {
        SubirPendientes();
    }

    public void refrescar(View view) {
        llenarRecicler();
    }

    private int i=0;
    private String comas=  "\"";
    private void SubirPendientes() {


        int itemCount = reciclador.getAdapter().getItemCount();
        progressBar.setVisibility(View.VISIBLE);

        String[] archivos = fileList();


        if(archivos.length>itemCount ){
            i=1;
        }



        if( archivos.length>-1){

            FilePendiente= archivos[i];
            String validaFile=FilePendiente.substring(0, 2);
            ////Verifica los files por sus 2 primeros caracteres

            if(validaFile.equals("EV")  ) {
                base="evidencias";
            }

            if(validaFile.equals("FO")  ) {
                base="folios";
            }
            if(validaFile.equals("EN")  ) {
                base="encuestas";
            }
            if(validaFile.equals("CO")  ) {
                base="correctivos";
            }
            if(validaFile.equals("PR")  ) {
                base="preventivos";
            }
            if(validaFile.equals("IC")  ) {
                base="ima_cor";
            }

            if(validaFile.equals("IP")  ) {
                base="ima_prev";
            }

            if(validaFile.equals("EV") || validaFile.equals("FO") || validaFile.equals("EN") || validaFile.equals("CO") || validaFile.equals("PR") || validaFile.equals("IC") || validaFile.equals("IP") ) {

                try {////////////llena el post

                    InputStreamReader archivo = new InputStreamReader(openFileInput(FilePendiente));
                    BufferedReader br = new BufferedReader(archivo);
                    String linea = br.readLine();
                    F_todo = "";
                    while (linea != null) {
                        F_todo = F_todo + linea;
                        linea = br.readLine();

                    }
                    br.close();
                    archivo.close();


                    try {/////////////envia el file

                        String nestedJSON = F_todo;

                        String url = getResources().getString(R.string.urlCloudant) + "/" + base;


                        JsonObjectRequest jar1 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(nestedJSON), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.toString());
                                    REV = jsonObject.getString("rev");

                                    String validaRespuesta=response.toString().substring(0, 10);

                                    if(validaRespuesta.equals("{" +comas + "ok" +comas+ ":true")){

                                        borrafile(FilePendiente);

                                    }



                                    //todo ver response

                                    // progressBar.setVisibility(View.GONE);
                                    //llenarRecicler();



                                } catch (JSONException e) {
                                    progressBar.setVisibility(View.GONE);

                                    Context context = getApplicationContext();
                                    CharSequence text = "Error de conexón: " ;
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();


                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Json Error Res: ", "" + error);

                                progressBar.setVisibility(View.GONE);
                                Context context = getApplicationContext();
                                CharSequence text = "Error de conexón: ";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();


                            }
                        });

                        requestQueue.add(jar1);

                        jar1.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        //jar1.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


                    } catch (Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Context context = getApplicationContext();
                        CharSequence text = "Error de conexón:";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();



                    }







                } catch (IOException e) {
                    progressBar.setVisibility(View.GONE);
                    Context context = getApplicationContext();
                    CharSequence text = "Error al leer archivo: " +e;
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }


            }



        }










    }


    private void borrafile(String FilePendiente) {

        Boolean flagBorra=false;

        while (flagBorra == false) {

            try {

                this.deleteFile(FilePendiente);


                flagBorra = true;

                llenarRecicler();


            } catch (Exception e) {

            }

        }

        if(flagBorra == true){

            int itemCountFlag = reciclador.getAdapter().getItemCount();

            if(itemCountFlag==0){

             progressBar.setVisibility(View.GONE);
                Context context2 = getApplicationContext();
                CharSequence text2 = "Carga completada";
                int duration2 = Toast.LENGTH_SHORT;
                Toast toast2 = Toast.makeText(context2, text2, duration2);
                toast2.show();

             }else {

                 progressBar.setVisibility(View.GONE);
                 SubirPendientes();
             }
        }






    }



}
