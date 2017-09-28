package com.example.carlosje.reportes_ibm;

/**
 * Created by carlosje on 9/24/2017.
 */

public class Anime {
    private String imagen;
    private String nombre;


    public Anime(String imagen, String nombre) {
        this.imagen = imagen;
        this.nombre = nombre;

    }

    public String getNombre() {
        return nombre;
    }



    public String getImagen() {
        return imagen;
    }
}