package com.panaderia.ricopan;

import android.graphics.Bitmap;

public class Producto {
    private int id;
    public String title;
    private String imagenStr;
    public int imagen;
    private Bitmap imagen2;

    public Producto(){

    }

    public Producto(String title, Bitmap imagen) {
        this.title = title;
        this.imagen2 = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagenStr() {
        return imagenStr;
    }

    public void setImagenStr(String imagenStr) {
        this.imagenStr = imagenStr;
    }

    public Bitmap getImagen2() {
        return imagen2;
    }

    public void setImagen2(Bitmap imagen2) {
        this.imagen2 = imagen2;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}