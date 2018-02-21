package com.example.carlosje.reportes_ibm;


import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ibm.bluemix.appid.android.api.AppID;
import com.ibm.bluemix.appid.android.api.AuthorizationException;
import com.ibm.bluemix.appid.android.api.AuthorizationListener;
import com.ibm.bluemix.appid.android.api.LoginWidget;
import com.ibm.bluemix.appid.android.api.TokenResponseListener;
import com.ibm.bluemix.appid.android.api.tokens.AccessToken;
import com.ibm.bluemix.appid.android.api.tokens.IdentityToken;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {


    private String username, password,tenantId,identifiedAccessToken,datoError;
    private EditText user, pass;
    private TextView title_text,tv_no_conect,tv_ver1,tv_ver2,tv_Ver;
    private ProgressBar progressBar;
    private RelativeLayout RL_login;

    RequestQueue requestQueue;


    @Override
    protected void onSaveInstanceState(Bundle guardarEstado) {
        super.onSaveInstanceState(guardarEstado);
        username =user.getText().toString();
        password =pass.getText().toString();

        guardarEstado.putString("username", username);
        guardarEstado.putString("password", password);
    }

    @Override
    protected void onRestoreInstanceState(Bundle recEstado) {
        super.onRestoreInstanceState(recEstado);
        username = recEstado.getString("username");
        password = recEstado.getString("password");
        user.setText(username);
        pass.setText(password);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestQueue = RequestQueueSingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        //Quitamos barra de titulo de la aplicacion
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Quitamos barra de notificaciones
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tenantId="3de6ef4d-6f82-4bfc-9944-30b5e8028e06";
        AppID.getInstance().initialize(getApplicationContext(), tenantId, AppID.REGION_US_SOUTH);

        RL_login=(RelativeLayout) findViewById(R.id.RL_login);

        user = (EditText) findViewById(R.id.field_email);
        pass = (EditText) findViewById(R.id.field_password);
        title_text= (TextView) findViewById(R.id.title_text);
        tv_no_conect= (TextView) findViewById(R.id.tv_no_conect);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);

        tv_ver1= (TextView) findViewById(R.id.tv_ver1);
        tv_ver2= (TextView) findViewById(R.id.tv_ver2);
        tv_Ver= (TextView) findViewById(R.id.tv_Ver);

        RL_login.setVisibility(View.GONE);
        tv_ver1.setVisibility(View.GONE);
        tv_ver2.setVisibility(View.GONE);



        tv_no_conect.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);



        String versionName = "";
        int versionCode = -1;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            //versionCode = packageInfo.versionCode;

            tv_Ver.setText(versionName);

            //String url = "https://6620c8ed-e3c8-49b5-8420-fa3cb622c51e-bluemix.cloudant.com/android_version/version";

            String url=getResources().getString(R.string.urlCloudant)+"/android_version/version";

            JsonObjectRequest obreq2 = new JsonObjectRequest(Request.Method.GET, url, null,
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
                                String versionA = obj2.getString("name");



                                if(versionA.equals(tv_Ver.getText().toString())){
                                    //RL_login.setVisibility(View.VISIBLE);

                                    checkPermission();
                                }else {
                                    tv_ver1.setVisibility(View.VISIBLE);
                                    tv_ver2.setVisibility(View.VISIBLE);
                                }




                            }


                            // Try and catch are included to handle any errors due to JSON
                            catch (JSONException e) {

                                Context context = getApplicationContext();
                                CharSequence text = "" + e ;
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();

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

                            Context context = getApplicationContext();
                            CharSequence text = "Error:  Verifica tu conexión y vuelve a iniciar la aplicación" ;
                            int duration = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
            );
            // Adds the JSON object request "obreq" to the request queue
            requestQueue.add(obreq2);







        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }







    }

    private static final int REQUEST_CODE_ASK_PERMISSIONS_camera = 123;
    private static final int REQUEST_CODE_ASK_PERMISSIONS_Storage_read = 321;
    private static final int REQUEST_CODE_ASK_PERMISSIONS_Storage_write = 789;
    private static final int REQUEST_CODE_ASK_PERMISSIONS_location = 987;

    public void examplePermission(View view) {

    }

    private void checkPermission() {



        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            //Toast.makeText(this, "This version is not Android 6 or later " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();
            RL_login.setVisibility(View.VISIBLE);
        } else {

            int hasWriteContactsPermission_camera = checkSelfPermission(android.Manifest.permission.CAMERA);
            int hasWriteContactsPermission_storage_write = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasWriteContactsPermission_storage_read = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int hasWriteContactsPermission_location = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);


            if (hasWriteContactsPermission_camera != PackageManager.PERMISSION_GRANTED) {

                RL_login.setVisibility(View.GONE);
                ActivityCompat.requestPermissions(
                        this, new String[]{android.Manifest.permission.CAMERA},  REQUEST_CODE_ASK_PERMISSIONS_camera);

                Toast.makeText(this, "Requesting permissions Camera", Toast.LENGTH_SHORT).show();
                return;

            }else if (hasWriteContactsPermission_camera == PackageManager.PERMISSION_GRANTED){

               // Toast.makeText(this, "The permissions are already granted ", Toast.LENGTH_LONG).show();
                // openCamera();
                RL_login.setVisibility(View.VISIBLE);
            }


            if (hasWriteContactsPermission_storage_read != PackageManager.PERMISSION_GRANTED) {

                RL_login.setVisibility(View.GONE);
                ActivityCompat.requestPermissions(
                        this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},  REQUEST_CODE_ASK_PERMISSIONS_Storage_read);

                Toast.makeText(this, "Requesting permissions Storage", Toast.LENGTH_SHORT).show();
                return;

            }else if (hasWriteContactsPermission_storage_read == PackageManager.PERMISSION_GRANTED){

               // Toast.makeText(this, "The permissions are already granted ", Toast.LENGTH_LONG).show();
                // openCamera();
                RL_login.setVisibility(View.VISIBLE);
            }

            if (hasWriteContactsPermission_storage_write != PackageManager.PERMISSION_GRANTED) {

                RL_login.setVisibility(View.GONE);
                ActivityCompat.requestPermissions(
                        this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},  REQUEST_CODE_ASK_PERMISSIONS_Storage_write);

                Toast.makeText(this, "Requesting permissions Storage", Toast.LENGTH_SHORT).show();
                return;

            }else if (hasWriteContactsPermission_storage_write == PackageManager.PERMISSION_GRANTED){

                //Toast.makeText(this, "The permissions are already granted ", Toast.LENGTH_LONG).show();
                // openCamera();
                RL_login.setVisibility(View.VISIBLE);
            }

            if (hasWriteContactsPermission_location != PackageManager.PERMISSION_GRANTED) {

                RL_login.setVisibility(View.GONE);
                ActivityCompat.requestPermissions(
                        this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},  REQUEST_CODE_ASK_PERMISSIONS_location);

                Toast.makeText(this, "Requesting permissions Ubication", Toast.LENGTH_SHORT).show();
                return;

            }else if (hasWriteContactsPermission_location == PackageManager.PERMISSION_GRANTED){

                //Toast.makeText(this, "The permissions are already granted ", Toast.LENGTH_LONG).show();
                // openCamera();
                RL_login.setVisibility(View.VISIBLE);
            }




        }

        return;

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {




        if(grantResults.length > 0 && REQUEST_CODE_ASK_PERMISSIONS_camera == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "OK Permissions granted ! <img draggable="false" class="emoji" alt="🙂" src="https://s.w.org/images/core/emoji/2.4/svg/1f642.svg"> " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();
                RL_login.setVisibility(View.VISIBLE);


            } else {
                RL_login.setVisibility(View.GONE);

            }

            checkPermission();
        }


        if(grantResults.length > 0 && REQUEST_CODE_ASK_PERMISSIONS_Storage_read == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                RL_login.setVisibility(View.VISIBLE);

            } else {
                RL_login.setVisibility(View.GONE);

            }
            checkPermission();
        }


        if(grantResults.length > 0 && REQUEST_CODE_ASK_PERMISSIONS_Storage_write == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                RL_login.setVisibility(View.VISIBLE);

            } else {
                RL_login.setVisibility(View.GONE);

            }
            checkPermission();
        }

        if(grantResults.length > 0 && REQUEST_CODE_ASK_PERMISSIONS_location == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                RL_login.setVisibility(View.VISIBLE);

            } else {
                RL_login.setVisibility(View.GONE);

            }
            checkPermission();
        }
        //checkPermission();







    }







    public void crearAccesoDirectoAlInstalar(Activity actividad)  {
        SharedPreferences preferenciasapp;
        boolean aplicacioninstalada = Boolean.FALSE;

/*
* Compruebo si es la primera vez que se ejecuta la alicación,
* entonces es cuando creo el acceso directo
*/
        preferenciasapp = PreferenceManager.getDefaultSharedPreferences(actividad);
        aplicacioninstalada = preferenciasapp.getBoolean("aplicacioninstalada", Boolean.FALSE);

        if(!aplicacioninstalada)
        {
/*
* Código creación acceso directo
*/
            Intent shortcutIntent = new Intent(actividad, LoginActivity.class);
            shortcutIntent.setAction(Intent.ACTION_MAIN);
            Intent intent = new Intent();
            intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Digital IBM TSS");
            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,Intent.ShortcutIconResource.fromContext(actividad, R.drawable.icon));
            intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            actividad.sendBroadcast(intent);

/*
* Indico que ya se ha creado el acceso directo para que no se vuelva a crear mas
*/
            SharedPreferences.Editor editor = preferenciasapp.edit();
            editor.putBoolean("aplicacioninstalada", true);
            editor.commit();
        }
    }




    public  void login(View v){

        tv_no_conect.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        username=user.getText().toString();
        password=pass.getText().toString();



        if (user.getText().toString().equals("")){

            Context context = getApplicationContext();
            CharSequence text = "Por favor introduce tu email" ;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;


        }
        if (pass.getText().toString().equals("")){
            Context context = getApplicationContext();
            CharSequence text = "Por favor introduce tu password" ;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;



        }




        String validaIBM = username.substring(username.length() - 7);

        if (validaIBM.equals("ibm.com")){
            appid();




        }else{
            Context context = getApplicationContext();
            CharSequence text = "Correo no válido, Favor de acceder con cuenta de IBM " ;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();



        }


    }


    private String picUrl = null;
    private String displayName = null;


    private void appid(){


        AppID.getInstance().obtainTokensWithROP(getApplicationContext(), username, password,
                new TokenResponseListener() {
                    @Override
                    public void onAuthorizationFailure (AuthorizationException exception) {
                        //Exception occurred

                        NoConect();


                    }

                    @Override
                    public void onAuthorizationSuccess (AccessToken accessToken, IdentityToken identityToken) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.INVISIBLE);

                            }
                        });

                        String usuario =user.getText().toString();
                        Intent activity__menu = new Intent(getApplicationContext(), MenuActivity.class);
                        activity__menu.putExtra("usuario",user.getText().toString());
                        startActivity(activity__menu);
                        identifiedAccessToken = accessToken.toString();
                        extractAndDisplayDataFromIdentityToken(identityToken);

                    }
                });



    }



    private void NoConect(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                findViewById(R.id.tv_no_conect).setVisibility(View.VISIBLE);
            }
        });


    }



    private void extractAndDisplayDataFromIdentityToken(IdentityToken identityToken) {

        try {
            String userId = identityToken.getSubject();
            if (identityToken.isAnonymous()) {
                picUrl = null;
                displayName = "Anonymous User ( " + userId + " )";



            } else {
                picUrl = identityToken.getPicture();
                displayName = identityToken.getName() + " ( " + userId + " )";


            }
            //logger.info("User is: " + userId);
            //showPictureAndName(picUrl, displayName);
            //logger.info("extractAndDisplayDataFromIdentityToken done");
        } catch (Exception e) {
            displayName="error";
        }
    }


    public  void ver(View v){

        if(displayName == null) {

            Context context = getApplicationContext();
            CharSequence text = "Email o PW errones";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }else {
            Context context = getApplicationContext();
            CharSequence text = ""+ displayName;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }




    }


    public void singup(View v){
        LoginWidget loginWidget = AppID.getInstance().getLoginWidget();
        loginWidget.launchSignUp(this, new AuthorizationListener() {
            @Override
            public void onAuthorizationFailure (AuthorizationException exception) {
                //Exception occurred
            }

            @Override
            public void onAuthorizationCanceled () {
                //Sign up canceled by the user
            }

            @Override
            public void onAuthorizationSuccess (AccessToken accessToken, IdentityToken identityToken) {
                if (accessToken != null && identityToken != null) {
                    //User authenticated
                } else {
                    //email verification is required
                }

            }
        });


    }


    public void reset(View v){
        LoginWidget loginWidget = AppID.getInstance().getLoginWidget();
        loginWidget.launchForgotPassword(this, new AuthorizationListener() {
            @Override
            public void onAuthorizationFailure (AuthorizationException exception) {
                //Exception occurred
            }

            @Override
            public void onAuthorizationCanceled () {
                //forogt password canceled by the user
            }

            @Override
            public void onAuthorizationSuccess (AccessToken accessToken, IdentityToken identityToken) {
                //forgot password finished, in this case accessToken and identityToken will be null.

            }
        });


    }


}
