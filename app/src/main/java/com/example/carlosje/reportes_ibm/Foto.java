package com.example.carlosje.reportes_ibm;

/**
 * Created by carlosje on 10/23/2017.
 */

public class Foto {
    private String imagen;
    private String nombre;


    public Foto(String imagen, String nombre) {
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
