package com.example.carlosje.reportes_ibm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class SearchActivity extends AppCompatActivity {




    private ProgressBar PB_busqueda;

    private RadioButton RB_ATM,RB_Logo_OEM;
    private FloatingActionButton fl_btn_buscar;
    private EditText serie;
    private String flag;
    private RecyclerView reciclador;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private  Context contexto = this;
    private List items = new ArrayList();


    RequestQueue requestQueue;
    private String serie_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_search);

        requestQueue = RequestQueueSingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        reciclador= (RecyclerView) findViewById(R.id.reciclador);

        RB_ATM = (RadioButton) findViewById(R.id.RB_ATM);
        RB_Logo_OEM = (RadioButton) findViewById(R.id.RB_Logo_OEM);
        fl_btn_buscar =(FloatingActionButton) findViewById(R.id.fl_btn_buscar);
        serie = (EditText) findViewById(R.id.serie);
        PB_busqueda=(ProgressBar)findViewById(R.id.PB_busqueda);
        PB_busqueda.setVisibility(View.GONE);
        serie.setVisibility(View.GONE);
        fl_btn_buscar.setVisibility(View.GONE);


        fl_btn_buscar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                serie_id = serie.getText().toString();

                if(serie_id.equals("")){


                    Context context = getApplicationContext();
                    CharSequence text = "Por favor captura el ID o serie de equipo";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }



                añadir_busq_recicler();

            }
        });


        RB_ATM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serie.setVisibility(View.VISIBLE);
                fl_btn_buscar.setVisibility(View.VISIBLE);

                serie.setHint("ID ATM");
                flag="ATM";


            }
        });


        RB_Logo_OEM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                serie.setVisibility(View.VISIBLE);
                fl_btn_buscar.setVisibility(View.VISIBLE);

                serie.setHint("Serie");
                flag="LOGO";


            }
        });



    }



    public String reporte;
    public String falla;
    public String solucion;
    public String partes1,partes2, partes3, partes4,partes5,partes6,partes7, partes8,comentarios;
    public String soporte;
    public String fecha;
    public String child1;
    public String usuario;

    private String flag1;

    private String pais,mJSONURLString,url2;



    public  void añadir_busq_recicler(){

        flag1="false";
        serie_id=serie.getText().toString();


        pais="México";



        items.clear();

        reciclador.setHasFixedSize(true);

// Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(contexto);
        reciclador.setLayoutManager(lManager);

// Crear un nuevo adaptador
        adapter = new BusquedaAdapter(items);
        reciclador.setAdapter(adapter);






       // mJSONURLString = "https://6620c8ed-e3c8-49b5-8420-fa3cb622c51e-bluemix.cloudant.com/correctivos/_all_docs";

       mJSONURLString=getResources().getString(R.string.urlCloudant)+"/correctivos/_all_docs";


        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, mJSONURLString, null,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {



                            JSONObject obj = response;

                            JSONArray jsonArray  = obj.getJSONArray("rows");





                            for (int i = 0; i < jsonArray.length()+1; i++) {



                                JSONObject jsonObjectRow = jsonArray.getJSONObject(i);


                                String id = jsonObjectRow.getString("id");




                                //url2 = "https://6620c8ed-e3c8-49b5-8420-fa3cb622c51e-bluemix.cloudant.com/correctivos/"+id;

                                String url2=getResources().getString(R.string.urlCloudant)+"/correctivos/"+id;


                                JsonObjectRequest obreq2 = new JsonObjectRequest(Request.Method.GET, url2, null,
                                        // The third parameter Listener overrides the method onResponse() and passes
                                        //JSONObject as a parameter
                                        new Response.Listener<JSONObject>() {

                                            // Takes the response from the JSON request
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {



                                                    JSONObject obj2 = response;
                                                    // Retrieves the string labeled "colorName" and "description" from
                                                    //the response JSON Object
                                                    //and converts them into javascript objects
                                                    String serieCloudant = obj2.getString("serie");


                                                    if(serieCloudant.equals(serie_id)){


                                                        flag1="true";
                                                        reciclador.setRecycledViewPool(new RecyclerView.RecycledViewPool());

                                                        reporte=obj2.getString("reporte");
                                                        falla=obj2.getString("falla");
                                                        solucion=obj2.getString("solucion");
                                                        comentarios=obj2.getString("comentarios");
                                                        partes1=obj2.getString("partes_solicitadas_1");
                                                        partes2=obj2.getString("partes_solicitadas_2");
                                                        partes3=obj2.getString("partes_solicitadas_3");
                                                        partes4=obj2.getString("partes_solicitadas_4");
                                                        partes5=obj2.getString("partes_solicitadas_5");
                                                        partes6=obj2.getString("partes_solicitadas_6");
                                                        partes7=obj2.getString("partes_solicitadas_7");
                                                        partes8=obj2.getString("partes_solicitadas_8");
                                                        soporte=obj2.getString("nombre_soporte");
                                                        fecha=obj2.getString("Fecha");
                                                        usuario=obj2.getString("Usuario");

                                                        items.add(new Busqueda(reporte, falla, solucion, partes1, partes2,partes3,partes4,partes5,partes6,partes7,partes8, soporte, fecha,usuario,comentarios));




                                                        reciclador.setHasFixedSize(true);

// Usar un administrador para LinearLayout
                                                        lManager = new LinearLayoutManager(contexto);
                                                        reciclador.setLayoutManager(lManager);

// Crear un nuevo adaptador
                                                        adapter = new BusquedaAdapter(items);
                                                        reciclador.setAdapter(adapter);

                                                        //items.clear();

                                                        class ComparadorFechas implements Comparator<Busqueda> {
                                                            public int compare(Busqueda a, Busqueda b) {
                                                                return b.getFecha().compareTo(a.getFecha());
                                                            }
                                                        }

                                                        Collections.sort(items, new ComparadorFechas());

                                                    }




                                                }


                                                // Try and catch are included to handle any errors due to JSON
                                                catch (JSONException e) {

                                                }
                                            }


                                        },
                                        // The final parameter overrides the method onErrorResponse() and passes VolleyError
                                        //as a parameter
                                        new Response.ErrorListener() {
                                            @Override
                                            // Handles errors that occur due to Volley
                                            public void onErrorResponse(VolleyError error) {
                                                Log.e("Volley", "Error");


                                            }
                                        }
                                );
                                // Adds the JSON object request "obreq" to the request queue
                                requestQueue.add(obreq2);



                            }





                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }




                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }



        );
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(obreq);



        ////TODO validar order by date









    }



}
