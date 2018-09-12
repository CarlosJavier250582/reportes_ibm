package com.example.carlosje.reportes_ibm;

/**
 * Created by carlosje on 4/25/2018.
 */

public class Inventario {
    private String reporte;
    private String deter;
    private String marca;
    private String modelo;
    private String serie;
    private String description;
    private String tipo_equipo;


    public Inventario(String reporte, String deter, String marca, String modelo, String serie, String description, String tipo_equipo) {
        this.reporte = reporte;
        this.deter = deter;
        this.marca = marca;
        this.modelo = modelo;
        this.serie = serie;
        this.description=description;
        this.tipo_equipo=tipo_equipo;


    }

    public String getReporte() {
        return reporte;
    }

    public String getDeter() {
        return deter;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getSerie() {
        return serie;
    }

    public String getDescription() {
        return description;
    }

    public String getTipo_equipo() {
        return tipo_equipo;
    }



}
