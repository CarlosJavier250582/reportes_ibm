package com.example.carlosje.reportes_ibm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    private RadioButton RB_Categoria_ATM,RB_Preventivo, RB_Correctivo;
    private TextView tv_user;
    private String usuarioid;
    private LinearLayout lay_select_rep;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lay_select_rep = (LinearLayout) findViewById(R.id.lay_select_rep);



        usuarioid = getIntent().getStringExtra("usuario");

        tv_user = (TextView) findViewById(R.id.tv_user);
        tv_user.setText(usuarioid);

        RB_Categoria_ATM = (RadioButton) findViewById(R.id.RB_Categoria_ATM);
        RB_Preventivo = (RadioButton) findViewById(R.id.RB_Preventivo);
        RB_Correctivo = (RadioButton) findViewById(R.id.RB_Correctivo);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        progressBar.setVisibility(View.GONE);




    }






    public  void nuevo(View view){
        lay_select_rep.setVisibility(View.VISIBLE);




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
                Intent activity__ATMcorrect = new Intent(getApplicationContext(), ATMcorrectActivity.class);
                activity__ATMcorrect.putExtra("usuario",usuarioid);
                startActivity(activity__ATMcorrect);

            }

            if (!RB_Correctivo.isChecked() && !RB_Preventivo.isChecked()){




                Context context = getApplicationContext();
                CharSequence text = "Por favor selecciona Tipo de reporte";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }


        }






    }


    @Override
    public void onRestart() {
        super.onRestart();

        progressBar.setVisibility(View.GONE);
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
    }


}
