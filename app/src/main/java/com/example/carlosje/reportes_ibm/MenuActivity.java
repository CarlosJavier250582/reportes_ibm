package com.example.carlosje.reportes_ibm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;





public class MenuActivity extends AppCompatActivity {

    private RadioButton RB_Categoria_ATM,RB_Categoria_LOGO,RB_Preventivo, RB_Correctivo;
    private TextView tv_user;
    private String usuarioid;
    private RelativeLayout lay_select_rep;
    private ProgressBar progressBar;
    private RadioGroup op_tipo_reporte,op_categoria_reporte;
    private String REV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        usuarioid = getIntent().getStringExtra("usuario");
        lay_select_rep = (RelativeLayout) findViewById(R.id.lay_select_rep);
        tv_user = (TextView) findViewById(R.id.tv_user);
        tv_user.setText(usuarioid);
        RB_Categoria_ATM = (RadioButton) findViewById(R.id.RB_Categoria_ATM);
        RB_Categoria_LOGO = (RadioButton) findViewById(R.id.RB_Categoria_LOGO);
        RB_Preventivo = (RadioButton) findViewById(R.id.RB_Preventivo);
        RB_Correctivo = (RadioButton) findViewById(R.id.RB_Correctivo);
        op_tipo_reporte = (RadioGroup) findViewById(R.id.op_tipo_reporte);
        op_categoria_reporte = (RadioGroup) findViewById(R.id.op_categoria_reporte);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        op_categoria_reporte.clearCheck();
        op_tipo_reporte.clearCheck();
    }

    public  void buscar(View view){
        lay_select_rep.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Intent activity_inventario = new Intent(getApplicationContext(), InventarioActivity.class);
        activity_inventario.putExtra("usuario", usuarioid);
        startActivity(activity_inventario);
    }

    public  void evidencia(View view){
        lay_select_rep.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Intent activity__evidencia = new Intent(getApplicationContext(), EvidenciaActivity.class);
        activity__evidencia.putExtra("usuario",usuarioid);
        startActivity(activity__evidencia);
    }

    public  void pendientes(View view){
        lay_select_rep.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Intent activity__pendientes = new Intent(getApplicationContext(), PendiantesActivity.class);
        startActivity(activity__pendientes);
    }
    public  void nuevo(View view){
        lay_select_rep.setVisibility(View.VISIBLE);
        op_categoria_reporte.clearCheck();
        op_tipo_reporte.clearCheck();
    }

    public  void select_nuevo(View view){
        if (RB_Categoria_ATM.isChecked()){
            if (RB_Preventivo.isChecked()){
                lay_select_rep.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Intent activity__ATMprev = new Intent(getApplicationContext(), ATMprevActivity.class);
                activity__ATMprev.putExtra("usuario",usuarioid);
                startActivity(activity__ATMprev);
            }

            if (RB_Correctivo.isChecked()){
                lay_select_rep.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Intent activity_ATMcorrect = new Intent(getApplicationContext(), ATMcorrectActivity.class);
                activity_ATMcorrect.putExtra("usuario",usuarioid);
                activity_ATMcorrect.putExtra("tipo","ATM");
                startActivity(activity_ATMcorrect);
            }

            if (!RB_Correctivo.isChecked() && !RB_Preventivo.isChecked()){
                Context context = getApplicationContext();
                CharSequence text = "Por favor selecciona Tipo de reporte";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }


        if (RB_Categoria_LOGO.isChecked()){
            if (RB_Preventivo.isChecked()){
                Context context = getApplicationContext();
                CharSequence text = "Pronto estará disponible esta opción";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            if (RB_Correctivo.isChecked()){
                lay_select_rep.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Intent activity_ATMcorrect = new Intent(getApplicationContext(), ATMcorrectActivity.class);
                activity_ATMcorrect.putExtra("usuario",usuarioid);
                activity_ATMcorrect.putExtra("tipo","LOGO");
                startActivity(activity_ATMcorrect);
            }
            if (!RB_Correctivo.isChecked() && !RB_Preventivo.isChecked()){
                Context context = getApplicationContext();
                CharSequence text = "Por favor selecciona Tipo de reporte";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }


        if (!RB_Categoria_LOGO.isChecked() && !RB_Categoria_ATM.isChecked()){
            Context context = getApplicationContext();
            CharSequence text = "Por favor haz una selección";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }

    public void ver_link(View view){

        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://ibmtssdigital.mybluemix.net/"));
        startActivity(viewIntent);

    }


    @Override
    public void onRestart() {
        super.onRestart();
        progressBar.setVisibility(View.GONE);
        op_categoria_reporte.clearCheck();
        op_tipo_reporte.clearCheck();

        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
    }



}
