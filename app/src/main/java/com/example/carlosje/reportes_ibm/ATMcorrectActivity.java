package com.example.carlosje.reportes_ibm;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

public class ATMcorrectActivity extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    private ProgressBar progressBarPhoto;


    private ScrollView sv_nuevo;

    private CardView card1,card2;

    private ImageView photo_ATM_prev,fotito;


    private Button bt_nuevo, bt_buscar, bt_fecha;
    private LinearLayout lay_buscar, lay_nuevo, lay_gral;

    private FloatingActionButton fl_btn_add_photo, fl_btn_save_atm_prev;

    private String usuarioid;
    private int dia, mes, ano;
    private TextView tv_user, fecha,url_photo;

    private EditText indicaciones_soporte, soporte_nombre, notas_image, reporte, id_tpv, localidad, marca, modelo, serie,  inventario, cliente_final, partes_solicitadas,falla,solucion,vobo,folio,comentarios;


    private RadioButton RB_soporte_resuelve_si,RB_soporte_resuelve_no, RB_soporte_si,RB_soporte_no,RB_partes_si, RB_partes_no,RB_banco_banamex, RB_banco_bancomer ,RB_banco_banorte, RB_banco_bancoppel, RB_banco_santander, RB_remo_suc_remoto, RB_remo_suc_sucursal;



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

    private String partes_solicitadas_V;
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

    private String pais;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atmcorrect);
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
        serie = (EditText) findViewById(R.id.serie);
        inventario = (EditText) findViewById(R.id.inventario);
        cliente_final = (EditText) findViewById(R.id.cliente_final);
        partes_solicitadas = (EditText) findViewById(R.id.partes_solicitadas);
        falla = (EditText) findViewById(R.id.falla);
        solucion = (EditText) findViewById(R.id.solucion);
        comentarios = (EditText) findViewById(R.id.comentarios);
        folio = (EditText) findViewById(R.id.folio);
        vobo = (EditText) findViewById(R.id.vobo);
        soporte_nombre=(EditText) findViewById(R.id.soporte_nombre);
        indicaciones_soporte=(EditText) findViewById(R.id.indicaciones_soporte);





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

        RB_soporte_resuelve_si=(RadioButton) findViewById(R.id.RB_soporte_resuelve_si);
        RB_soporte_resuelve_no=(RadioButton) findViewById(R.id.RB_soporte_resuelve_no);




        valida_fecha = fecha.getText().toString();
        valida_IDatm = id_tpv.getText().toString();
        valida_reporte = reporte.getText().toString();


        tv_user.setText(usuarioid);


        //// Invisible a cards //





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
        DialogFragment newFragment = new DatePickCorrectATM();
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
                Bitmap bitmap = Bitmap.createBitmap(photo_ATM_prev.getDrawingCache());
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
        partes_solicitadas_V = partes_solicitadas.getText().toString();
        serie_V = serie.getText().toString();
        cliente_final_V = cliente_final.getText().toString();
        inventario_V = inventario.getText().toString();
        soporte_nombre_V=soporte_nombre.getText().toString();

        falla_V = falla.getText().toString();
        solucion_V = solucion.getText().toString();
        vobo_V = vobo.getText().toString();
        folio_V = folio.getText().toString();
        comentarios_V = comentarios.getText().toString();
        indicaciones_soporte_V=indicaciones_soporte.getText().toString();



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










        final String reporte_F = reporte_V;
        final String id_tpv_F = id_tpv_V;
        final String localidad_F = localidad_V;
        final String marca_F = marca_V;
        final String modelo_F = modelo_V;
        final String serie_F = serie_V;
        final String inventario_F = inventario_V;
        final String cliente_final_F = cliente_final_V;
        final String soporte_nombre_F=soporte_nombre_V;
        final String indicaciones_soporte_F=indicaciones_soporte_V;


        final String partes_solicitadas_F = partes_solicitadas_V;
        final String falla_F = falla_V;
        final String solucion_F = solucion_V;
        final String vobo_F = vobo_V;
        final String folio_F = folio_V;
        final String comentarios_F = comentarios_V;
        final String RB_partes_F = RB_partes_V;

        final String RB_banco_F = RB_banco_V;
        final String RB_remo_suc_F = RB_remo_suc_V;

        final String RB_soporte_F=RB_soporte_V;
        final String RB_soporte_resuelve_F=RB_soporte_resuelve_V;



        final String date = fecha.getText().toString();
        final String usuario = usuarioid;
        final String ubicacion = "mexico2";


        ////////////////////////////valida datos obligatorios




        key = database.child(pais).child(idATM).child("correctivos").push().getKey();






        reporte_child=reporte.getText().toString();





        Post post = new Post(RB_soporte_resuelve_F,indicaciones_soporte_F, soporte_nombre_F, RB_soporte_F,RB_banco_F, RB_remo_suc_F, RB_partes_F, comentarios_F, folio_F, vobo_F, solucion_F, falla_F, partes_solicitadas_F, usuario, idkey, date,  cliente_final_F, inventario_F, serie_F,  modelo_F, marca_F, localidad_F, id_tpv_F, reporte_F);


        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        //childUpdates.put(codigo + key, postValues);


        childUpdates.put("checklist", postValues);

        database.child(pais).child("ATMs").child(idATM).child("correctivos").child(reporte_child).updateChildren(childUpdates);


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
        public String serie_F;
        public String inventario_F;
        public String cliente_final_F;

        public String indicaciones_soporte_F;
        public String soporte_nombre_F;



        public String partes_solicitadas_F;
        public String falla_F;
        public String solucion_F;
        public String vobo_F;
        public String folio_F;
        public String comentarios_F;


        public String RB_soporte_F;


        public String RB_banco_F;
        public String RB_remo_suc_F;
        public String RB_partes_F;
        public String RB_soporte_resuelve_F;





        public Post() {
            // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        }

        public Post (String RB_soporte_resuelve_F, String indicaciones_soporte_F, String soporte_nombre_F, String RB_soporte_F, String RB_banco_F, String RB_remo_suc_F, String RB_partes_F, String comentarios_F, String folio_F, String vobo_F, String solucion_F, String falla_F,  String partes_solicitadas_F,  String usuario, String idkey, String date, String cliente_final_F, String inventario_F, String serie_F, String modelo_F, String marca_F, String localidad_F, String id_tpv_F, String reporte_F){            //////////////////////////////////////////////aqui poner variables
            this.date = date;
            this.usuario = usuario;
            this.ubicacion = ubicacion;
            this.idkey = idkey;

            this.reporte_F = reporte_F;
            this.id_tpv_F = id_tpv_F;
            this.localidad_F = localidad_F;
            this.marca_F = marca_F;
            this.modelo_F = modelo_F;

            this.serie_F = serie_F;
            this.inventario_F = inventario_F;
            this.cliente_final_F = cliente_final_F;

            this.indicaciones_soporte_F=indicaciones_soporte_F;

            this.partes_solicitadas_F = partes_solicitadas_F;
            this.falla_F = falla_F;
            this.solucion_F = solucion_F;
            this.vobo_F = vobo_F;
            this.folio_F = folio_F;
            this.comentarios_F = comentarios_F;
            this.soporte_nombre_F = soporte_nombre_F;


            this.RB_banco_F = RB_banco_F;
            this.RB_remo_suc_F = RB_remo_suc_F;
            this.RB_soporte_F=RB_soporte_F;
            this.RB_partes_F=RB_partes_F;
            this.RB_soporte_resuelve_F=RB_soporte_resuelve_F;












        }

        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("IDkey", idkey);
            result.put("Fecha", date);
            result.put("Usuario", usuario);

            result.put("serie", serie_F);
            result.put("inventario", inventario_F);
            result.put("cliente final", cliente_final_F);
            result.put("solicita partes", RB_partes_F);

            result.put("partes solicitadas", partes_solicitadas_F);
            result.put("falla", falla_F);
            result.put("solución", solucion_F);
            result.put("Vobo", vobo_F);
            result.put("folio", folio_F);
            result.put("comentarios", comentarios_F);
            result.put("Usuario", usuario);

            result.put("banco", RB_banco_F);
            result.put("remoto_sucursal", RB_remo_suc_F);
            result.put("Recibe ayuda de soporte",RB_soporte_F);
            result.put("nombre soporte",soporte_nombre_F);
            result.put("indicaciones de soporte",indicaciones_soporte_F);
            result.put("Resuelve problema ayuda de soporte",RB_soporte_resuelve_F);

            result.put("pais", "Mexico");




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
        StorageReference mountainsRef = storageRef.child(pais).child("ATM_Correct/"+ID_atm_photos+"/"+reporte_photos+"_"+i_photos+".jpg");

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



                            key = database.child(pais).child(idATM).child("correctivos").push().getKey();

                            notas_post=notas_image.getText().toString();

                            reporte_child=reporte.getText().toString();
                            idATM=id_tpv.getText().toString();



                            key2 = database.child(pais).child(idATM).child("correctivos").child("imagenes").push().getKey();

                            i_photos_post=""+i_photos;

                            ATMcorrectActivity.Post_2 post_2 = new ATMcorrectActivity.Post_2(urlPhoto, notas_post, i_photos_post);
                            Map<String, Object> post_2Values = post_2.toMap();

                            Map<String, Object> childUpdates = new HashMap<>();
                            //childUpdates.put(codigo + key, postValues);


                            childUpdates.put(""+i_photos, post_2Values);



                            database.child(pais).child("ATMs").child(idATM).child("correctivos").child(reporte_child).child("imagenes").updateChildren(childUpdates);



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
    private List items = new ArrayList();

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

    public void anterior1(View view) {
        card2.setVisibility(View.GONE);
        card1.setVisibility(View.VISIBLE);

    }


    public void siguiente2(View view) {
        card2.setVisibility(View.VISIBLE);
        card1.setVisibility(View.GONE);

    }


}



