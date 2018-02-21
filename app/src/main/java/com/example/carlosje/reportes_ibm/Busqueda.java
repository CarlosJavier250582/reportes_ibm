package com.example.carlosje.reportes_ibm;

import android.widget.TextView;

/**
 * Created by carlosje on 10/2/2017.
 */

public class Busqueda {
    private String reporte;
    private String falla;
    private String solucion;
    private String partes1;
    private String partes2;
    private String partes3;
    private String partes4;
    private String partes5;
    private String partes6;
    private String partes7;
    private String partes8;
    private String soporte;
    private String fecha;
    private String usuario;
    private String comentarios;




    public Busqueda(String reporte, String falla, String solucion, String partes1, String partes2, String partes3, String partes4, String partes5, String partes6, String partes7, String partes8, String soporte, String fecha, String usuario, String comentarios) {
        this.reporte = reporte;
        this.falla = falla;
        this.solucion = solucion;
        this.partes1 = partes1;
        this.partes2 = partes2;
        this.partes3 = partes3;
        this.partes4 = partes4;
        this.partes5 = partes5;
        this.partes6 = partes6;
        this.partes7 = partes7;
        this.partes8 = partes8;
        this.soporte = soporte;
        this.fecha = fecha;
        this.usuario = usuario;
        this.comentarios = comentarios;


    }

    public String getReporte() {
        return reporte;
    }

    public String getFalla() {
        return falla;
    }

    public String getSolucion() {
        return solucion;
    }
    public String getPartes1() {
        return partes1;
    }
    public String getPartes2() {
        return partes2;
    }
    public String getPartes3() {
        return partes3;
    }
    public String getPartes4() {
        return partes4;
    }
    public String getPartes5() {
        return partes5;
    }
    public String getPartes6() {
        return partes6;
    }
    public String getPartes7() {
        return partes7;
    }
    public String getPartes8() {
        return partes8;
    }


    public String getSoporte() {
        return soporte;
    }


    public String getFecha() {
        return fecha;
    }


    public String getUsuario() {
        return usuario;
    }


    public String getComentarios() {
        return comentarios;
    }



}