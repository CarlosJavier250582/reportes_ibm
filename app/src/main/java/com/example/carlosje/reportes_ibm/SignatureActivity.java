package com.example.carlosje.reportes_ibm;

import android.*;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.Map;

public class SignatureActivity extends AppCompatActivity {

    Button mClear, mGetSign, mCancel, mSR;
    File file;
    LinearLayout mContent;
    View view;
    signature mSignature;
    Bitmap bitmap;

    ProgressBar progressBar;
    // Creating Separate Directory for saving Generated Images
    String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/UserSignature/";
    String pic_name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    String StoredPath = DIRECTORY + pic_name + ".png";

    private String usuario;
    private String pais ;
    private String reporte;

    private String idATM;
    private String corr_prev;
    private String Vobo,puesto;
    private String tipo_rep;
    private String folio;
    private String latitud;
    private String longitud;
    private String fecha;
    private String coments="";
    private FloatingActionButton fl_btn_save_firma;
    private LinearLayout lay_encuesta;
    private TextView lb_gracias,tv_reporte_r;
    private EditText coments_enc;
    private String cliente;
    private Integer flag_SR;


    private ImageView e1_w1,e1_w2,e1_w3,e1_w4,e1_w5,e2_w1,e2_w2,e2_w3,e2_w4,e2_w5,e3_w1,e3_w2,e3_w3,e3_w4,e3_w5;

    private int i;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        flag_SR=0;
        i=0;
        Res_E1=0;
        Res_E2=0;
        Res_E3=0;
        iE1=0;
        iE2=0;
        iE3=0;


        usuario = getIntent().getStringExtra("usuario");
        pais = getIntent().getStringExtra("pais");
        reporte = getIntent().getStringExtra("reporte");
        idATM= getIntent().getStringExtra("id_serie");
        corr_prev= getIntent().getStringExtra("corr_prev");
        Vobo= getIntent().getStringExtra("Vobo");
        puesto= getIntent().getStringExtra("puesto");
        tipo_rep= getIntent().getStringExtra("tipo_rep");
        folio= getIntent().getStringExtra("folio");
        latitud= getIntent().getStringExtra("latitud");
        longitud= getIntent().getStringExtra("longitud");
        fecha= getIntent().getStringExtra("fecha");
        cliente= getIntent().getStringExtra("cliente");

        setContentView(R.layout.activity_signature);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        fl_btn_save_firma=(FloatingActionButton) findViewById(R.id.fl_btn_save_firma);
        lay_encuesta=(LinearLayout) findViewById(R.id.lay_encuesta);

        e1_w1 =(ImageView) findViewById(R.id.e1_w1);
        e1_w2 =(ImageView) findViewById(R.id.e1_w2);
        e1_w3 =(ImageView) findViewById(R.id.e1_w3);
        e1_w4 =(ImageView) findViewById(R.id.e1_w4);
        e1_w5 =(ImageView) findViewById(R.id.e1_w5);
        e2_w1 =(ImageView) findViewById(R.id.e2_w1);
        e2_w2 =(ImageView) findViewById(R.id.e2_w2);
        e2_w3 =(ImageView) findViewById(R.id.e2_w3);
        e2_w4 =(ImageView) findViewById(R.id.e2_w4);
        e2_w5 =(ImageView) findViewById(R.id.e2_w5);
        e3_w1 =(ImageView) findViewById(R.id.e3_w1);
        e3_w2 =(ImageView) findViewById(R.id.e3_w2);
        e3_w3 =(ImageView) findViewById(R.id.e3_w3);
        e3_w4 =(ImageView) findViewById(R.id.e3_w4);
        e3_w5 =(ImageView) findViewById(R.id.e3_w5);



        coments_enc=(EditText) findViewById(R.id.coments_enc);
        mContent = (LinearLayout) findViewById(R.id.canvasLayout);
        mSignature = new signature(getApplicationContext(), null);
        mSignature.setBackgroundColor(Color.WHITE);
        // Dynamically generating Layout through java code
        mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mClear = (Button) findViewById(R.id.clear);
        mGetSign = (Button) findViewById(R.id.getsign);
        mSR= (Button) findViewById(R.id.SR);
        mGetSign.setEnabled(false);
        mCancel = (Button) findViewById(R.id.cancel);
        tv_reporte_r= (TextView) findViewById(R.id.tv_reporte_r);
        view = mContent;
        mGetSign.setOnClickListener(onButtonClick);
        mClear.setOnClickListener(onButtonClick);
        mCancel.setOnClickListener(onButtonClick);
        mSR.setOnClickListener(onButtonClick);


        mContent.setVisibility(View.GONE);
        //lb_gracias.setVisibility(View.GONE);
        lay_encuesta.setVisibility(View.VISIBLE);

        tv_reporte_r.setText("Call "+reporte);


        fl_btn_save_firma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coments=coments_enc.getText().toString();
                if(i==3) {
                    i=i+1;
                    subir_encuesta();
                    lay_encuesta.setVisibility(View.GONE);
                    mContent.setVisibility(View.VISIBLE);
                    //lb_gracias.setVisibility(View.VISIBLE);
                    return;

                }
                if(i==4) {
                    subir_firma();
                }
                if (i<3 || flag_SR==1){

                    Context context = getApplicationContext();
                    CharSequence text = "Por favor completar la encuesta";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });
    }

    // Method to create Directory, if the Directory doesn't exists



    Button.OnClickListener onButtonClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (v == mClear) {

                if(i<4){

                    i=0;
                    Res_E1=0;
                    Res_E2=0;
                    Res_E3=0;
                    iE1=0;
                    iE2=0;
                    iE3=0;
                    e1_w1.setVisibility(View.VISIBLE);
                    e1_w2.setVisibility(View.VISIBLE);
                    e1_w3 .setVisibility(View.VISIBLE);
                    e1_w4.setVisibility(View.VISIBLE);
                    e1_w5.setVisibility(View.VISIBLE);
                    e2_w1.setVisibility(View.VISIBLE);
                    e2_w2.setVisibility(View.VISIBLE);
                    e2_w3 .setVisibility(View.VISIBLE);
                    e2_w4.setVisibility(View.VISIBLE);
                    e2_w5.setVisibility(View.VISIBLE);
                    e3_w1.setVisibility(View.VISIBLE);
                    e3_w2.setVisibility(View.VISIBLE);
                    e3_w3 .setVisibility(View.VISIBLE);
                    e3_w4.setVisibility(View.VISIBLE);
                    e3_w5.setVisibility(View.VISIBLE);
                    return;
                }
                mSignature.clear();
                mGetSign.setEnabled(false);
            }

            if (v == mSR) {
                subir_encuesta_no_resp();
                lay_encuesta.setVisibility(View.GONE);
                mContent.setVisibility(View.VISIBLE);
                return;
            }

            else if(v == mCancel){
                finish();
            }
        }
    };



    public class signature extends View {

        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();
        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v, String StoredPath) {

            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(bitmap);
            try {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                v.draw(canvas);

                // Convert the output file to Image such as .png
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                Intent intent = new Intent(SignatureActivity.this, ATMcorrectActivity.class);
                intent.putExtra("imagePath", StoredPath);
                startActivity(intent);
                finish();
                mFileOutStream.flush();
                mFileOutStream.close();

            } catch (Exception e) {

            }

        }

        public void clear() {
            path.reset();
            invalidate();
            mGetSign.setEnabled(false);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            mGetSign.setEnabled(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {



        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }



    private  Context contexto = this;
    private String key;
    private Bitmap BitFirma;



    private String urlPhoto;
    private String encodedImage;

    public  void subir_firma(){

       progressBar.setVisibility(View.VISIBLE);
        //////CLOUDANT
        mContent.destroyDrawingCache();
        mContent.buildDrawingCache();
        Bitmap image = mContent.getDrawingCache();
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
        byte[] imageBytes = baos1.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        SignatureActivity.Post_2 post_2 = new SignatureActivity.Post_2(reporte);
        Map<String, Object> post_2Values = post_2.toMap();

///////////////////TODO subir a cloudant firma
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url=getResources().getString(R.string.urlCloudant)+"/folios";

        JsonObjectRequest jar1 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(post_2Values), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
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
                            comas+"url"+comas+":"+comas+urlPhoto+comas+","+
                            comas+"reporte"+comas+":"+comas+reporte+comas+","+
                            comas+"pais"+comas+":"+comas+pais+comas+","+
                            comas+"Usuario"+comas+":"+comas+usuario+comas+","+
                            comas+"id_ATM"+comas+":"+comas+idATM+comas+","+
                            comas+"serie"+comas+":"+comas+idATM+comas+","+
                            comas+"Vobo"+comas+":"+comas+Vobo+comas+","+
                            comas+"puesto"+comas+":"+comas+puesto+comas+","+
                            comas+"folio"+comas+":"+comas+folio+comas+","+
                            comas+"latitud"+comas+":"+comas+latitud+comas+","+
                            comas+"longitud"+comas+":"+comas+longitud+comas+","+
                            comas+"fecha"+comas+":"+comas+fecha+comas+","+
                            comas+"imagen"+comas+":"+comas+encodedImage+comas+","+
                            comas+"cliente"+comas+":"+comas+cliente+comas+","+
                            comas+"comentarios"+comas+":"+comas+coments+comas+

                            "}";

                    ////Genera JSON de variables

                    Long tsLong = System.currentTimeMillis()/1000;
                    String ts = tsLong.toString();

                    String filename= "FO_"+ts+"_"+reporte+".txt";

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
        progressBar.setVisibility(View.GONE);

        Context context = getApplicationContext();
        CharSequence text = "Firma cargada";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        finish();

    }

////encuestas


    public class Post_2{
        public Map<String, Boolean> stars = new HashMap<>();
        public Post_2() {
            // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        }

        public Post_2(String reporte) {
        }


        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("url", urlPhoto);
            result.put("reporte", reporte);
            result.put("pais", pais);
            result.put("Usuario", usuario);
            result.put("id_ATM", idATM);
            result.put("serie", idATM);
            result.put("Vobo", Vobo);
            result.put("puesto", puesto);
            result.put("folio", folio);
            result.put("latitud", latitud);
            result.put("longitud", longitud);
            result.put("fecha", fecha);
            result.put("imagen", encodedImage);
            result.put("comentarios", coments);
            result.put("cliente", cliente);
            return result;
        }

    }


/* --------------------------------------------------
encuestas
 -------------------------------------------------- */

/* --------------------------------------------------
E1
 -------------------------------------------------- */


    private int Res_E1,Res_E2,Res_E3, iE1,iE2,iE3;

    public  void Selec_e1_w1(View view){
        if(iE1==1){
            iE1=0;
            i=i-1;
            Res_E1=0;
            e1_w1.setVisibility(View.VISIBLE);
            e1_w2.setVisibility(View.VISIBLE);
            e1_w3 .setVisibility(View.VISIBLE);
            e1_w4.setVisibility(View.VISIBLE);
            e1_w5.setVisibility(View.VISIBLE);
            return;
        }
        if (iE1==0){
            i=i+1;
        }
        iE1=1;
        Res_E1=1;
        e1_w1.setVisibility(View.VISIBLE);
        e1_w2.setVisibility(View.GONE);
        e1_w3 .setVisibility(View.GONE);
        e1_w4.setVisibility(View.GONE);
        e1_w5.setVisibility(View.GONE);
    }

    public  void Selec_e1_w2(View view){
        if(iE1==1){
            iE1=0;
            i=i-1;
            Res_E1=0;
            e1_w1.setVisibility(View.VISIBLE);
            e1_w2.setVisibility(View.VISIBLE);
            e1_w3 .setVisibility(View.VISIBLE);
            e1_w4.setVisibility(View.VISIBLE);
            e1_w5.setVisibility(View.VISIBLE);
            return;
        }
        if (iE1==0){
            i=i+1;
        }
        iE1=1;
        Res_E1=2;
        e1_w1.setVisibility(View.GONE);
        e1_w2.setVisibility(View.VISIBLE);
        e1_w3 .setVisibility(View.GONE);
        e1_w4.setVisibility(View.GONE);
        e1_w5.setVisibility(View.GONE);
    }
    public  void Selec_e1_w3(View view){
        if(iE1==1){
            iE1=0;
            i=i-1;
            Res_E1=0;
            e1_w1.setVisibility(View.VISIBLE);
            e1_w2.setVisibility(View.VISIBLE);
            e1_w3 .setVisibility(View.VISIBLE);
            e1_w4.setVisibility(View.VISIBLE);
            e1_w5.setVisibility(View.VISIBLE);
            return;
        }
        if (iE1==0){
            i=i+1;
        }
        iE1=1;
        Res_E1=3;
        e1_w1.setVisibility(View.GONE);
        e1_w2.setVisibility(View.GONE);
        e1_w3 .setVisibility(View.VISIBLE);
        e1_w4.setVisibility(View.GONE);
        e1_w5.setVisibility(View.GONE);
    }
    public  void Selec_e1_w4(View view){
        if(iE1==1){
            iE1=0;
            i=i-1;
            Res_E1=0;
            e1_w1.setVisibility(View.VISIBLE);
            e1_w2.setVisibility(View.VISIBLE);
            e1_w3 .setVisibility(View.VISIBLE);
            e1_w4.setVisibility(View.VISIBLE);
            e1_w5.setVisibility(View.VISIBLE);
            return;
        }
        if (iE1==0){
            i=i+1;
        }
        iE1=1;
        Res_E1=4;
        e1_w1.setVisibility(View.GONE);
        e1_w2.setVisibility(View.GONE);
        e1_w3 .setVisibility(View.GONE);
        e1_w4.setVisibility(View.VISIBLE);
        e1_w5.setVisibility(View.GONE);
    }
    public  void Selec_e1_w5(View view){
        if(iE1==1){
            iE1=0;
            i=i-1;
            Res_E1=0;
            e1_w1.setVisibility(View.VISIBLE);
            e1_w2.setVisibility(View.VISIBLE);
            e1_w3 .setVisibility(View.VISIBLE);
            e1_w4.setVisibility(View.VISIBLE);
            e1_w5.setVisibility(View.VISIBLE);
            return;
        }
        if (iE1==0){
            i=i+1;
        }
        iE1=1;
        Res_E1=5;
        e1_w1.setVisibility(View.GONE);
        e1_w2.setVisibility(View.GONE);
        e1_w3 .setVisibility(View.GONE);
        e1_w4.setVisibility(View.GONE);
        e1_w5.setVisibility(View.VISIBLE);
    }


/* --------------------------------------------------
E2
 -------------------------------------------------- */

    public  void Selec_e2_w1(View view){
        if(iE2==1){
            iE2=0;
            i=i-1;
            Res_E2=0;
            e2_w1.setVisibility(View.VISIBLE);
            e2_w2.setVisibility(View.VISIBLE);
            e2_w3 .setVisibility(View.VISIBLE);
            e2_w4.setVisibility(View.VISIBLE);
            e2_w5.setVisibility(View.VISIBLE);
            return;
        }
        if (iE2==0){
            i=i+1;
        }
        iE2=1;
        Res_E2=1;
        e2_w1.setVisibility(View.VISIBLE);
        e2_w2.setVisibility(View.GONE);
        e2_w3.setVisibility(View.GONE);
        e2_w4.setVisibility(View.GONE);
        e2_w5.setVisibility(View.GONE);
    }

    public  void Selec_e2_w2(View view){
        if(iE2==1){
            iE2=0;
            i=i-1;
            Res_E2=0;
            e2_w1.setVisibility(View.VISIBLE);
            e2_w2.setVisibility(View.VISIBLE);
            e2_w3 .setVisibility(View.VISIBLE);
            e2_w4.setVisibility(View.VISIBLE);
            e2_w5.setVisibility(View.VISIBLE);
            return;
        }
        if (iE2==0){
            i=i+1;
        }
        iE2=1;
        Res_E2=2;
        e2_w1.setVisibility(View.GONE);
        e2_w2.setVisibility(View.VISIBLE);
        e2_w3.setVisibility(View.GONE);
        e2_w4.setVisibility(View.GONE);
        e2_w5.setVisibility(View.GONE);
    }
    public  void Selec_e2_w3(View view){
        if(iE2==1){
            iE2=0;
            i=i-1;
            Res_E2=0;
            e2_w1.setVisibility(View.VISIBLE);
            e2_w2.setVisibility(View.VISIBLE);
            e2_w3 .setVisibility(View.VISIBLE);
            e2_w4.setVisibility(View.VISIBLE);
            e2_w5.setVisibility(View.VISIBLE);
            return;
        }
        if (iE2==0){
            i=i+1;
        }
        iE2=1;
        Res_E2=3;
        e2_w1.setVisibility(View.GONE);
        e2_w2.setVisibility(View.GONE);
        e2_w3.setVisibility(View.VISIBLE);
        e2_w4.setVisibility(View.GONE);
        e2_w5.setVisibility(View.GONE);
    }
    public  void Selec_e2_w4(View view){
        if(iE2==1){
            iE2=0;
            i=i-1;
            Res_E2=0;
            e2_w1.setVisibility(View.VISIBLE);
            e2_w2.setVisibility(View.VISIBLE);
            e2_w3 .setVisibility(View.VISIBLE);
            e2_w4.setVisibility(View.VISIBLE);
            e2_w5.setVisibility(View.VISIBLE);
            return;
        }
        if (iE2==0){
            i=i+1;
        }
        iE2=1;
        Res_E2=4;
        e2_w1.setVisibility(View.GONE);
        e2_w2.setVisibility(View.GONE);
        e2_w3.setVisibility(View.GONE);
        e2_w4.setVisibility(View.VISIBLE);
        e2_w5.setVisibility(View.GONE);
    }
    public  void Selec_e2_w5(View view){
        if(iE2==1){
            iE2=0;
            i=i-1;
            Res_E2=0;
            e2_w1.setVisibility(View.VISIBLE);
            e2_w2.setVisibility(View.VISIBLE);
            e2_w3 .setVisibility(View.VISIBLE);
            e2_w4.setVisibility(View.VISIBLE);
            e2_w5.setVisibility(View.VISIBLE);
            return;
        }
        if (iE2==0){
            i=i+1;
        }
        iE2=1;
        Res_E2=5;
        e2_w1.setVisibility(View.GONE);
        e2_w2.setVisibility(View.GONE);
        e2_w3.setVisibility(View.GONE);
        e2_w4.setVisibility(View.GONE);
        e2_w5.setVisibility(View.VISIBLE);
    }
/* --------------------------------------------------
E3
 -------------------------------------------------- */

    public  void Selec_e3_w1(View view){
        if(iE3==1){
            iE3=0;
            i=i-1;
            Res_E3=0;
            e3_w1.setVisibility(View.VISIBLE);
            e3_w2.setVisibility(View.VISIBLE);
            e3_w3 .setVisibility(View.VISIBLE);
            e3_w4.setVisibility(View.VISIBLE);
            e3_w5.setVisibility(View.VISIBLE);
            return;
        }
        if (iE3==0){
            i=i+1;
        }
        iE3=1;
        Res_E3=1;
        e3_w1.setVisibility(View.VISIBLE);
        e3_w2.setVisibility(View.GONE);
        e3_w3.setVisibility(View.GONE);
        e3_w4.setVisibility(View.GONE);
        e3_w5.setVisibility(View.GONE);
    }

    public  void Selec_e3_w2(View view){
        if(iE3==1){
            iE3=0;
            i=i-1;
            Res_E3=0;
            e3_w1.setVisibility(View.VISIBLE);
            e3_w2.setVisibility(View.VISIBLE);
            e3_w3 .setVisibility(View.VISIBLE);
            e3_w4.setVisibility(View.VISIBLE);
            e3_w5.setVisibility(View.VISIBLE);
            return;        }
        if (iE3==0){
            i=i+1;
        }
        iE3=1;
        Res_E3=2;
        e3_w1.setVisibility(View.GONE);
        e3_w2.setVisibility(View.VISIBLE);
        e3_w3.setVisibility(View.GONE);
        e3_w4.setVisibility(View.GONE);
        e3_w5.setVisibility(View.GONE);
    }
    public  void Selec_e3_w3(View view){
        if(iE3==1){
            iE3=0;
            i=i-1;
            Res_E3=0;
            e3_w1.setVisibility(View.VISIBLE);
            e3_w2.setVisibility(View.VISIBLE);
            e3_w3 .setVisibility(View.VISIBLE);
            e3_w4.setVisibility(View.VISIBLE);
            e3_w5.setVisibility(View.VISIBLE);
            return;
        }
        if (iE3==0){
            i=i+1;        }
        iE3=1;
        Res_E3=3;
        e3_w1.setVisibility(View.GONE);
        e3_w2.setVisibility(View.GONE);
        e3_w3.setVisibility(View.VISIBLE);
        e3_w4.setVisibility(View.GONE);
        e3_w5.setVisibility(View.GONE);

    }
    public  void Selec_e3_w4(View view){
        if(iE3==1){
            iE3=0;
            i=i-1;
            Res_E3=0;
            e3_w1.setVisibility(View.VISIBLE);
            e3_w2.setVisibility(View.VISIBLE);
            e3_w3 .setVisibility(View.VISIBLE);
            e3_w4.setVisibility(View.VISIBLE);
            e3_w5.setVisibility(View.VISIBLE);
            return;
        }
        if (iE3==0){
            i=i+1;
        }
        iE3=1;
        Res_E3=4;
        e3_w1.setVisibility(View.GONE);
        e3_w2.setVisibility(View.GONE);
        e3_w3.setVisibility(View.GONE);
        e3_w4.setVisibility(View.VISIBLE);
        e3_w5.setVisibility(View.GONE);
    }
    public  void Selec_e3_w5(View view){
        if(iE3==1){
            iE3=0;
            i=i-1;
            Res_E3=0;
            e3_w1.setVisibility(View.VISIBLE);
            e3_w2.setVisibility(View.VISIBLE);
            e3_w3 .setVisibility(View.VISIBLE);
            e3_w4.setVisibility(View.VISIBLE);
            e3_w5.setVisibility(View.VISIBLE);
            return;
        }
        if (iE3==0){
            i=i+1;
        }
        iE3=1;
        Res_E3=5;
        e3_w1.setVisibility(View.GONE);
        e3_w2.setVisibility(View.GONE);
        e3_w3.setVisibility(View.GONE);
        e3_w4.setVisibility(View.GONE);
        e3_w5.setVisibility(View.VISIBLE);
    }

    private String REV;

    public void  subir_encuesta(){

        progressBar.setVisibility(View.VISIBLE);

        SignatureActivity.Post_3 post_3 = new SignatureActivity.Post_3();
        Map<String, Object> post_3Values = post_3.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(reporte, post_3Values);

////////////TODO////////Añade a Cloudant

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url=getResources().getString(R.string.urlCloudant)+"/encuestas";

        JsonObjectRequest jar1 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(post_3Values), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
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
                            comas+"reporte"+comas+":"+comas+reporte+comas+","+
                            comas+"pais"+comas+":"+comas+pais+comas+","+
                            comas+"Usuario"+comas+":"+comas+usuario+comas+","+
                            comas+"id_ATM"+comas+":"+comas+idATM+comas+","+
                            comas+"serie"+comas+":"+comas+idATM+comas+","+
                            comas+"Vobo"+comas+":"+comas+Vobo+comas+","+
                            comas+"puesto"+comas+":"+comas+puesto+comas+","+
                            comas+"folio"+comas+":"+comas+folio+comas+","+
                            comas+"latitud"+comas+":"+comas+latitud+comas+","+
                            comas+"longitud"+comas+":"+comas+longitud+comas+","+
                            comas+"Encuesta_1"+comas+":"+comas+Res_E1+comas+","+
                            comas+"Encuesta_2"+comas+":"+comas+Res_E2+comas+","+
                            comas+"Encuesta_3"+comas+":"+comas+Res_E3+comas+","+
                            comas+"fecha"+comas+":"+comas+fecha+comas+","+
                            comas+"cliente"+comas+":"+comas+cliente+comas+","+
                            comas+"comentarios"+comas+":"+comas+coments+comas+
                            "}";
                    ////Genera JSON de variables

                    Long tsLong = System.currentTimeMillis()/1000;
                    String ts = tsLong.toString();

                    String filename= "EN_"+ts+"_"+reporte+".txt";

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

        progressBar.setVisibility(View.GONE);

        Context context = getApplicationContext();
        CharSequence text = "Gracias, su resultados han sido guardados, por favor firme en la pantalla.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        progressBar.setVisibility(View.GONE);

    }

    public class Post_3{

        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("reporte", reporte);
            result.put("pais", pais);
            result.put("Usuario", usuario);
            result.put("id_ATM", idATM);
            result.put("serie", idATM);
            result.put("Vobo", Vobo);
            result.put("puesto", puesto);
            result.put("folio", folio);
            result.put("latitud", latitud);
            result.put("longitud", longitud);
            result.put("Encuesta_1", Res_E1);
            result.put("Encuesta_2", Res_E2);
            result.put("Encuesta_3", Res_E3);
            result.put("fecha", fecha);
            result.put("comentarios", coments);
            result.put("cliente", cliente);
            return result;
        }

    }

    public void  subir_encuesta_no_resp(){

        progressBar.setVisibility(View.VISIBLE);
        flag_SR=1;
        SignatureActivity.Post_4 post_4 = new SignatureActivity.Post_4();
        Map<String, Object> post_4Values = post_4.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(reporte, post_4Values);

////////////TODO////////Añade a Cloudant

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url=getResources().getString(R.string.urlCloudant)+"/encuestas";

        JsonObjectRequest jar1 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(post_4Values), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
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
                            comas+"reporte"+comas+":"+comas+reporte+comas+","+
                            comas+"pais"+comas+":"+comas+pais+comas+","+
                            comas+"Usuario"+comas+":"+comas+usuario+comas+","+
                            comas+"id_ATM"+comas+":"+comas+idATM+comas+","+
                            comas+"serie"+comas+":"+comas+idATM+comas+","+
                            comas+"Vobo"+comas+":"+comas+Vobo+comas+","+
                            comas+"puesto"+comas+":"+comas+puesto+comas+","+
                            comas+"folio"+comas+":"+comas+folio+comas+","+
                            comas+"latitud"+comas+":"+comas+latitud+comas+","+
                            comas+"longitud"+comas+":"+comas+longitud+comas+","+
                            comas+"Encuesta_1"+comas+":NA,"+
                            comas+"Encuesta_2"+comas+":NA,"+
                            comas+"Encuesta_3"+comas+":NA,"+
                            comas+"fecha"+comas+":"+comas+fecha+comas+","+
                            comas+"cliente"+comas+":"+comas+cliente+comas+","+
                            comas+"comentarios"+comas+":"+comas+coments+comas+

                            "}";
                    ////Genera JSON de variables

                    Long tsLong = System.currentTimeMillis()/1000;
                    String ts = tsLong.toString();

                    String filename= "EN_"+ts+"_"+reporte+".txt";

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

        progressBar.setVisibility(View.GONE);

        Context context = getApplicationContext();
        CharSequence text = "Gracias, su resultados han sido guardados, por favor firme en la pantalla.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        progressBar.setVisibility(View.GONE);
    }

    public class Post_4{

        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("reporte", reporte);
            result.put("pais", pais);
            result.put("Usuario", usuario);
            result.put("id_ATM", idATM);
            result.put("serie", idATM);
            result.put("Vobo", Vobo);
            result.put("puesto", puesto);
            result.put("folio", folio);
            result.put("latitud", latitud);
            result.put("longitud", longitud);
            result.put("Encuesta_1", "NA");
            result.put("Encuesta_2", "NA");
            result.put("Encuesta_3", "NA");
            result.put("fecha", fecha);
            result.put("comentarios", coments);
            result.put("cliente", cliente);
            return result;
        }
    }
}