package com.example.carlosje.reportes_ibm;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

public class EvidenciaActivity  extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{
    private String usuarioid,notas_post;
    private FloatingActionButton fl_btn_add_photo;
    private int flag_foto;
    private ImageView photo_Evidencia;
    private EditText reporte,notas_image;
    private TextView mLatitude,mLongitude,tv_files;
    private Location mLastLocation;
    private String REV;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private String comas=  "\"";
    private ProgressBar progressBar;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evidencia);

        requestQueue = RequestQueueSingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        usuarioid = getIntent().getStringExtra("usuario");
        fl_btn_add_photo = (FloatingActionButton) findViewById(R.id.fl_btn_add_photo);
        reporte = (EditText) findViewById(R.id.reporte);
        notas_image = (EditText) findViewById(R.id.notas_image);
        mLatitude = (TextView) findViewById(R.id.mLatitude);
        mLongitude = (TextView) findViewById(R.id.mLongitude);
        photo_Evidencia= (ImageView) findViewById(R.id.photo_Evidencia);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);


        fl_btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamarIntent();

            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .build();






    }





    private static final int PICK_IMAGE_ID = 234; // the number doesn't matter




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
                ImageView img = (ImageView) findViewById(R.id.photo_Evidencia);

                img.setImageBitmap(image);

                flag_foto = 1;

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            }
        }
    }



    private String encodedImage;
    private int i_photos, sube;


    public void subir_imagen_e(View view) {



        i_photos = i_photos + 1;

        notas_post = notas_image.getText().toString();

        if (reporte.getText().toString().equals("")) {

            Context context = getApplicationContext();
            CharSequence text = "Favor documentar el reporte";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;


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


        progressBar.setVisibility(View.VISIBLE);



        final EvidenciaActivity.Post_2 post_2 = new EvidenciaActivity.Post_2();
        final Map<String, Object> post_2Values = post_2.toMap();

        /////////////////////////////TODO////////Añade a Cloudant



       // String url = "https://6620c8ed-e3c8-49b5-8420-fa3cb622c51e-bluemix.cloudant.com/evidencias";

        String url=getResources().getString(R.string.urlCloudant)+"/evidencias";


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
                            comas+"imagen"+comas+":"+comas+ encodedImage+comas+", "+
                            comas+"observaciones"+comas+":"+comas+ notas_post+comas+", "+
                            comas+"reporte"+comas+":"+comas+ reporte.getText().toString()+comas+", "+
                            comas+"latitud"+comas+":"+comas+ mLatitude.getText().toString()+comas+", "+
                            comas+"longitud"+comas+":"+comas+ mLongitude.getText().toString()+comas+", "+
                            comas+"Usuario"+comas+":"+comas+ usuarioid+comas+

                            "}";

                    ////Genera JSON de variables




                    Long tsLong = System.currentTimeMillis()/1000;
                    String ts = tsLong.toString();

                    String filename= "EV_"+ts+"_"+reporte.getText().toString()+".txt";

                    OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(filename, Activity.MODE_PRIVATE));
                    archivo.write(guardaPost);
                    archivo.flush();
                    archivo.close();


                    Context context = getApplicationContext();
                    CharSequence text = "Problema al subir file, almacenado en pendientes. " + filename;
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    añadir_im_recicler();

                } catch (IOException e) {
                }



            }
        });







        requestQueue.add(jar1);

        jar1.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //jar1.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }
    private Boolean flagSube=false;

    public class Post_2 {


        public Map<String, Boolean> stars = new HashMap<>();

        public Post_2() {
            // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        }



        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("imagen", encodedImage);
            result.put("observaciones", notas_post);
            result.put("reporte", reporte.getText().toString());
            result.put("latitud", mLatitude.getText().toString());
            result.put("longitud", mLongitude.getText().toString());
            result.put("Usuario", usuarioid);



            return result;
        }

    }

    private List items = new ArrayList();
    public void añadir_im_recicler() {
        progressBar.setVisibility(View.GONE);
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

        photo_Evidencia.setImageResource(R.drawable.ic_shutter);


    }


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








    private boolean existe(String[] archivos, String archbusca) {
        for (int f = 0; f < archivos.length; f++)
            if (archbusca.equals(archivos[f]))
                return true;
        return false;
    }










}
