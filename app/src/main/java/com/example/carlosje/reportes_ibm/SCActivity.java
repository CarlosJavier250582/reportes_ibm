package com.example.carlosje.reportes_ibm;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.List;

public class SCActivity extends AppCompatActivity {

    private ListView listSC;
    private List items = new ArrayList();


    private String tipo,SC_Sel,ActCodResult,AC_Sel,Comp_sel,Act_sel;



    ListView simpleList;




    String SerCod[] = {"01", "08", "20", "31", "33", "36", "44", "45", "47", "48", "58", "60", "95"};
    String SerCodDesc[] = {"Mantenimiento correctivo", "Mantenimiento preventivo", "Instalación / Desinstalación / Descontnuar", "MES - Cambios de ventas", "ECA - Cambios de ingeniería", "Soporte a cliente / Requerimientos Per Call", "Actividades contratadas ( Actividades bajo un contrato de mantenimiento MTS-MA )", "Actividades adicionales que fluyen a clientes internos (STG, SWG)", "Soporte a Marketing", "Administración de cuenta", "Tiempo disponible ( no produtivo )", "Tiempo no disponible ( no produtivo )", "Centros de soporte de Hardware"};



    String ActCod_01[] = {"01", "02", "03", "08", "09", "95"};
    String ActCodDesc_01[] = {"Determinación de problema en sitio / Reparación ( Mantenimiento correctivo - Breake & Fix )", "Tiempo gastado para esperar partes para completar el llamado ( en instalaciones del cliente / En Sitio )", "Tiempo gastado para esperar disponibilidad del cliente / máquina ( en instalaciones del cliente / En Sitio )", "Tiempo gastado en acciones de reparación Easy Fix", "Asistencia / soporte En Sitio a un SSR IBM-MTS ( sólo se usará por el SSR o Top gun que presta su ayuda, no se usará por L2 )", "Tiempo dedicado a soporte remoto por parte del personal de campo       Aplicará únicamente en aquellos casos en los que el SSR apoya a un cliente vía telefónica y gracias a esta acción no es necesario acudir a cliente.A diferencia del SC 95 AC 06 ( Ejecución de actividades Fix on Phone ) el nuevo código contará como actividad de mantenimiento correctivo. Esta actividad NO APLICA a casos de call screening.Esta actividad NO APLICA en caso de que un SSR apoye a otro SSR."};

    String ActCod_08[] = {"NA"};
    String ActCodDesc_08[] = {"Actividades de mantenimiento preventivo"};

    String ActCod_20[] = {"16", "17", "18"};
    String ActCodDesc_20[] = {"Planeación de instalación ( Si se van a realizar actividades de SAR, favor de incluir número de orden )", "Actividades de Instalación / desinstalación ( Descontinuación )", "DOA Parts or Parts not availables on original Configuration for Non CSU machines"};


    String ActCod_31[] =  {"16", "17", "18"};
    String ActCodDesc_31[] = {"Installation Planning (for SAR activities, include Order number)", "Install / Uninstall (Discontinue)", "Partes defectuosas al arribar o partes no disponibles en la configuración original para máquinas que no son Customer Set Up."};

    String ActCod_33[] = {"16", "17", "21"};
    String ActCodDesc_33[] = {"Planeación de instalación y administración del Cambio de Ingeniería", "Instalación, remoción, pruebas o problemas correctivos, relacionados a cambios de ingeniería proporcionados por IBM", "Sporte a Storage de STG - Actividades de cambio de ingeniería de SW ( incluido en contratos de SWMA - Software Maintenance Agreement - )"};

    String ActCod_36[] = {"01", "02", "03", "04", "09", "10", "11", "12", "13", "14", "15", "19", "20"};
    String ActCodDesc_36[] = {"Determinación o reparación de problemas En Sitio ( Mantenimiento correctivo y Break & Fix )", "Tiempo gastado para esperar partes para completar el llamado ( en instalaciones del cliente / En Sitio )", "Tiempo gastado para esperar disponibilidad del cliente / máquina ( en instalaciones del cliente / En Sitio )", "Actividades de soporte de software En Sitio ( solicitado por el cliente, BIOS, PTFs, recarga de imagen, etc )", "Asistencia / soporte En Sitio a un SSR IBM-MTS ( sólo se usará por el SSR o Top gun que presta su ayuda, no se usará por L2 )", "Vandalismo ( Reparación, diagnóstico de problemas y reemplazo de partes debido a vandalismo )", "Inventario / Revisión de inventario", "Activiades de IMAC ( Instalación, Mover, Agregar, Cambiar )", "Activiades de Roll Out (Transacciones de Roll Out de alto volumen previamente acordado )", "Mantenimiento preventivo en plataformas No LOGO ( Donde Service Code 08 no aplica )", "Inspección ( no reparacón ) para determinar si el equipo puede o no entrar a servicios de mantenimiento.", "Stand by en sitio pagado y solicitado por el cliente.", "Recarga de imagen de Software En Sitio."};

    String ActCod_44[] = {"01", "04", "10", "11", "12", "13", "14", "15", "19", "20"};
    String ActCodDesc_44[] = {"Determinación o reparación de problemas En Sitio, no activides de Break and Fix no cubiertas en una garantía estándard pero sí cubiertas en una extensión de contrato de MTS ( NPRA / Acción de reparación que no requiere partes )", "Actividades de soporte de Software En Sitio cubiertos por un contrato de mantenimiento ( BIOS, PTFs, recarga de imagen, etc )", "Línea de servicio a vandalismo cubierto en contrato de mantenimiento ( Reparación, diagnósticos y reemplazo de partes debido a vandalismo)Vandalism baseline covered in MTS Contract (Repairs, diagnostic & Parts replecement due to Vandalism)", "Inventario / revisión de inventario, cubierto en un contrato de mantenimiento", "Actividades de IMAC cubiertas en un contrato de mantenimiento ( Instalación - Mover - Agregar - Cambios )", "Activiades de Roll Out (Transacciones de Roll Out de alto volumen previamente acordado ) incluido en un contrato de mantenimiento.", "Mantenimiento preventivo en plataformas No LOGO ( Donde Service Code 08 no aplica ) cubierto por un contrato de mantenimiento.", "Inspección ( no reparacón ) para determinar si el equipo puede o no entrar a servicios de mantenimiento cubierto en un contrato de mantenimiento.", "Stand by en sitio cubierto en un contrato de mantenimiento ( pagado por el cliente.)", "Recarga de imagen de Software En Sitio, cubiertas iniciales en un contrato de mantenimiento o SPL10"};


    String ActCod_45[] = {"35", "36", "37", "38", "39", "40", "41", "42"};
    String ActCodDesc_45[] = {"Determinación o reparación de problema interno En Sitio ( A ITS / SO - mantenimiento correctivo o Break & Fix )", "Desinstalación de máquina ( a solicitud de GARS - Global Asset Reutilization Services - Fluye a GARS-Global Financing)", "Actividades de reacondicionamiento ( Refurbished ) realizadas en un centro de reparación GARS - Global Asset Reutilization Services - (Fluye a GARS-Global Financing)", "Actividades especiales que fluyen a ITS en una tasa combinada ( Fluye a ITS - preaprobado)", "Actividades especiales que fluyen a ITS en una tasa de Machine Category ( Fluye a ITS - preaprobado)", "Actividades especiales ( IMACs, etc ) en campo acordadas previamente por la brand de STG( Fluye a las cuentas de ventas de las Brand )", "Asistencia En Sitio a productos ISS ( Fluye a cuentas de SWG ISS )", "Actividades realizadas fuera de enfoque en productos Netezza ( Instalación, servicios a cliente, servicios por horarios, responsabilidad del cliente, descontinuaciones y cambios de ingeniería - Todo debe fluir a SWG Netezza - preaprobado )"};

    String ActCod_47[] = {"NA"};
    String ActCodDesc_47[] = {"Soporte o asistencia general a Marketing."};

    String ActCod_48[] = {"NA"};
    String ActCodDesc_48[] = {"Actividades de administración de cuentas en general"};

    String ActCod_58[] = {"NA", "50", "51"};
    String ActCodDesc_58[] = {"Disponible para IBM pero no asignado, actividad no relacionada a algún tipo de máquina.", "Stand By como una petición por IBM y no se está trabajando en un tipo de máquina en específico ( Válido para Stand By por tiempo extra, Asignación de stand by )", "Soporte a contrato de mantenimiento de pre-venta o post-venta ( soporte al equipo de ventas )"};

    String ActCod_60[] = {"60", "61", "62", "63", "64", "65", "66"};
    String ActCodDesc_60[] = {"N/A", "Vacaciones, días festivos (no obligatorio por ley)", "Entrenamiento ( Clase, en línea etc)", "Feriado obligatorio por ley.", "Ausencia ( enfermedad, incapacidad, razones personales )", "Juntas de negocios ( junta de equipo, etc )", "Retornando a casa o de un llamado"};

    String ActCod_95[] = {"04", "05", "06", "07", "09", "16", "70", "72", "73", "74", "75", "76", "77", "78", "79", "80"};
    String ActCodDesc_95[] = {"Actividades de soporte en sitio de SW L2 ( Actividades relacionadas a temas de SW - atendiendo bajo contratos SPL10, PTFs, etc )", "Ejecución de CRU a L1 remoto ( sólo actividades de CRU )", "Ejecución de actividades de Fix On Phone remotos a nivel L1", "Soporte a nivel L2 ya sea Remoto o En Sitio a Business Partners (BPs, CAS)", "Asistencia de L2 a un técnico de campo ( SSR ) - Asistencia en Critical Situations, deteminación de problemas, etc", "Planeación de instalación para Soporte L2", "Preparación y/o proporcionar entrenamiento", "Actividades de soporte de Microcódigo ( descargar, pasar a CD/DVD, soporte BIOS, etc )", "Asignaciónes especiales / internacionales.", "Soporte interno (LPOR, Otros)", "Proporcionar asistencia a LA", "Juntas, llamadas de conferencias", "Soporte interno a brands, marketing, proyectos", "Asistencia o soporte telefónico.", "Análisis de Logs", "Consulta a Laboratorio"};




    String ListFalla[] = {"ATM" ,"ATM" ,"ATM" ,"ATM" ,"ATM" ,"ATM" ,"CAMARA" ,"CARTUCHO " ,"CHAPA" ,"CHASIS" ,"DISPENSADOR" ,"ELECTRICO" ,"HW" ,"IMPRESORA" ,"IMPRESORA" ,"LECTORA" ,"LECTORA" ,"LECTORA" ,"LECTORA" ,"MONITOR" ,"OTROS" ,"OTROS" ,"OTROS" ,"OTROS" ,"SENSOR ALARMAS" ,"SENSOR TEMPERATURA" ,"SENSOR VIBRACIÓN" ,"SHUTTER" ,"SW" ,"SW" ,"SW" ,"SW" ,"TECLADO" ,"VANDALISMO" ,"VIDEO"};
    String ListFallaDesc[] = {"ENVIA STATUS ERRONEO" ,"NO CONTACTADO" ,"SIN TRANSACCIONES" ,"BLOQUEADO" ,"FUERA DE SERVICIO" ,"SIN COMUNICACIÓN" ,"ERROR O FALLA" ,"ERROR O FALLA" ,"ERROR O FALLA" ,"FACIA ABIERTA O DAÑADA" ,"ERROR O FALLA" ,"FALLA EN NIVELES DE VOLTAJE" ,"DISCO DURO" ,"BAJO PAPEL EN IMPRESORA DE RECIBOS" ,"ERROR O FALLA" ,"DEMASIADAS TARJETAS ATORADAS O RETENIDAS" ,"DISPOSITIVO EXTRAÑO EN LECTORA" ,"TARJETA ATORADA" ,"ERROR O FALLA" ,"ERROR O FALLA" ,"FALLA EN LLAVES DE ENCRIPTOR" ,"REVISION DE CAJERO" ,"ERROR ANTISKIMMING" ,"BANDAS SUELTAS" ,"ERROR O FALLA" ,"ERROR O FALLA" ,"ERROR O FALLA" ,"ERROR O FALLA" ,"CHECK LIST" ,"ESPACIO DE DISCO DE JORNADA SATURADO" ,"RESET Y BORRADO DE ERRORES" ,"ERROR O FALLA APLICACIÓN" ,"ERROR O FALLA" ,"VANDALISMO" ,"ERROR O FALLA"};




    String ListSol_act[] = {"A" ,"B" ,"C" ,"D" ,"E" ,"F" ,"G" ,"H" ,"I" ,"J" ,"K" ,"L" ,"M" ,"N" ,"P" ,"Q" ,"R" ,"S" ,"T" ,"U" ,"V" ,"W" ,"X" ,"Y"};
    String ListSol_actDesc[] = {"REINICIO" ,"LIMPIEZA" ,"CONEXIÓN DE ENERGIA" ,"RETIRO DE OBJETO" ,"MANPRE RECUERRENTE" ,"CONEXIÓN COMS" ,"CONEXIÓN INTERNA" ,"REPARACIÓN / AJUSTE" ,"CARGA DE LLAVES" ,"BORRADO DE JOURNAL" ,"REMPLAZO" ,"MANPRE" ,"VANDALISMO" ,"CLAUSURADO / REMODELACIÓN / CAMBIO DE DOMICILIO" ,"ETV NO LLEGA A SITIO" ,"SIN ACCESO AL SITIO" ,"CLIENTE NO ACEPTA ETA" ,"REASIGADO 2DO NIVEL" ,"CARGA DE IMAGEN" ,"PLANCHADO DE IMAGEN" ,"BORRADO DE ERRORES" ,"CAMBIOS DE DENOMINACIÓN" ,"MIGRACIÓN", "OTRA"};

    String ListSol_comp[] = {"00" ,"01" ,"02" ,"03" ,"04" ,"05" ,"06" ,"07" ,"08" ,"09" ,"10" ,"11" ,"12" ,"13" ,"14" ,"15" ,"16" ,"17" ,"18" ,"19" ,"20" ,"21" ,"22" ,"23" ,"24" ,"25" ,"26" ,"27" ,"28" ,"29" ,"30" ,"31" ,"32" ,"33" ,"34" ,"35" ,"36" ,"37" ,"38" ,"39" ,"40" ,"41" ,"42"};
    String ListSol_compDesc[] = {"Main Board / CPU" ,"Lectora " ,"Router/Switch" ,"Botonera Lateral" ,"EPP/Teclado" ,"Impresora" ,"LCD/Pantalla/Touch" ,"Cabeza de Impresión" ,"AC Distribution" ,"HD" ,"Clips/Seguros" ,"Casetteros de Efectivo" ,"Casetteros de Retención" ,"Arness" ,"Dispensador" ,"FACIA" ,"Manija" ,"Puerta Falsa" ,"SW" ,"Todos" ,"Otros" ,"Presentador F/R" ,"Pick Module" ,"PCB Controller" ,"striper wheel" ,"Rollers" ,"bandas" ,"mangueras" ,"filtros" ,"chupetas" ,"pick line" ,"engranes " ,"feed shaft" ,"Take a way" ,"fender striper" ,"Shutter" ,"stacker" ,"LVDT" ,"Fuente de Poder" ,"Operator Panel" ,"Liberador de Cartucho" ,"Dispensador", "OTRA"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sc);

        tipo = getIntent().getStringExtra("tipo");


        AC_Sel="";
        Comp_sel="";


        if (tipo.equals("SC")){

            final int requestCode = 1;

            simpleList =  (ListView) findViewById(R.id.listSC);
            SC_Sel= getIntent().getStringExtra("SC_Sel");

            CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), SerCod, SerCodDesc);
            simpleList.setAdapter(customAdapter);


            simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final int pos = position;

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("SC_Sel",SerCod[pos]);
                    returnIntent.putExtra("AC_Sel",AC_Sel);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();

                }
            });

        }


        if (tipo.equals("AC")){

            final int requestCode = 1;

            simpleList =  (ListView) findViewById(R.id.listSC);

            SC_Sel= getIntent().getStringExtra("SC_Sel");

            simpleList =  (ListView) findViewById(R.id.listSC);




            if (SC_Sel.equals("01")){
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), ActCod_01, ActCodDesc_01);
                simpleList.setAdapter(customAdapter);

                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int pos = position;

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("AC_Sel",ActCod_01[pos]);
                        returnIntent.putExtra("SC_Sel",SC_Sel);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();

                    }
                });
            }

            if (SC_Sel.equals("08")){
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), ActCod_08, ActCodDesc_08);
                simpleList.setAdapter(customAdapter);
                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int pos = position;

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("AC_Sel",ActCod_08[pos]);
                        returnIntent.putExtra("SC_Sel",SC_Sel);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();

                    }
                });
            }

            if (SC_Sel.equals("20")){
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), ActCod_20, ActCodDesc_20);
                simpleList.setAdapter(customAdapter);
                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int pos = position;

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("AC_Sel",ActCod_20[pos]);
                        returnIntent.putExtra("SC_Sel",SC_Sel);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();

                    }
                });
            }

            if (SC_Sel.equals("31")){
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), ActCod_31, ActCodDesc_31);
                simpleList.setAdapter(customAdapter);
                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int pos = position;

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("AC_Sel",ActCod_31[pos]);
                        returnIntent.putExtra("SC_Sel",SC_Sel);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();

                    }
                });
            }

            if (SC_Sel.equals("33")){
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), ActCod_33, ActCodDesc_33);
                simpleList.setAdapter(customAdapter);
                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int pos = position;

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("AC_Sel",ActCod_33[pos]);
                        returnIntent.putExtra("SC_Sel",SC_Sel);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();

                    }
                });
            }

            if (SC_Sel.equals("36")){
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), ActCod_36, ActCodDesc_36);
                simpleList.setAdapter(customAdapter);
                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int pos = position;

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("AC_Sel",ActCod_36[pos]);
                        returnIntent.putExtra("SC_Sel",SC_Sel);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();

                    }
                });
            }

            if (SC_Sel.equals("44")){
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), ActCod_44, ActCodDesc_44);
                simpleList.setAdapter(customAdapter);
                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int pos = position;

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("AC_Sel",ActCod_44[pos]);
                        returnIntent.putExtra("SC_Sel",SC_Sel);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();

                    }
                });
            }

            if (SC_Sel.equals("45")){
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), ActCod_45, ActCodDesc_45);
                simpleList.setAdapter(customAdapter);
                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int pos = position;

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("AC_Sel",ActCod_45[pos]);
                        returnIntent.putExtra("SC_Sel",SC_Sel);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();

                    }
                });
            }

            if (SC_Sel.equals("47")){
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), ActCod_47, ActCodDesc_47);
                simpleList.setAdapter(customAdapter);
                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int pos = position;

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("AC_Sel",ActCod_47[pos]);
                        returnIntent.putExtra("SC_Sel",SC_Sel);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();

                    }
                });
            }

            if (SC_Sel.equals("48")){
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), ActCod_48, ActCodDesc_48);
                simpleList.setAdapter(customAdapter);
                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int pos = position;

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("AC_Sel",ActCod_48[pos]);
                        returnIntent.putExtra("SC_Sel",SC_Sel);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();

                    }
                });
            }

            if (SC_Sel.equals("58")){
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), ActCod_58, ActCodDesc_58);
                simpleList.setAdapter(customAdapter);
                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int pos = position;

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("AC_Sel",ActCod_58[pos]);
                        returnIntent.putExtra("SC_Sel",SC_Sel);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();

                    }
                });
            }

            if (SC_Sel.equals("60")){
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), ActCod_60, ActCodDesc_60);
                simpleList.setAdapter(customAdapter);
                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int pos = position;

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("AC_Sel",ActCod_60[pos]);
                        returnIntent.putExtra("SC_Sel",SC_Sel);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();

                    }
                });
            }

            if (SC_Sel.equals("95")){
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), ActCod_95, ActCodDesc_95);
                simpleList.setAdapter(customAdapter);
                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int pos = position;

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("AC_Sel",ActCod_95[pos]);
                        returnIntent.putExtra("SC_Sel",SC_Sel);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();

                    }
                });
            }











        }

        if (tipo.equals("falla")){


            simpleList =  (ListView) findViewById(R.id.listSC);

            CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), ListFalla, ListFallaDesc);
            simpleList.setAdapter(customAdapter);
            final int requestCode = 2;

            simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    final int pos = position;

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("falla_Sel",ListFalla[pos]+" "+ ListFallaDesc[pos]);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();

                }
            });

        }



        if (tipo.equals("act")){

            final int requestCode = 3;

            simpleList =  (ListView) findViewById(R.id.listSC);
            Act_sel= getIntent().getStringExtra("Act_sel");

            CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), ListSol_act, ListSol_actDesc);
            simpleList.setAdapter(customAdapter);


            simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final int pos = position;

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("Act_sel",ListSol_actDesc[pos]);
                    returnIntent.putExtra("Comp_sel",Comp_sel);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();

                }
            });

        }

        if (tipo.equals("comp")){

            final int requestCode = 4;

            simpleList =  (ListView) findViewById(R.id.listSC);


            CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), ListSol_comp, ListSol_compDesc);
            simpleList.setAdapter(customAdapter);


            simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final int pos = position;

                    Intent returnIntent = new Intent();

                    returnIntent.putExtra("Comp_sel",ListSol_compDesc[pos]);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();

                }
            });

        }




    }




}
