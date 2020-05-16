package com.example.amachay.models;

public class Novedades {
    String imagen;
    String titulo;
    String fecha;
    String descripcion;
    String contenido;

    public Novedades(){

    }

    public String getImagen(){
        return this.imagen;
    }
    public String getTitulo(){
        return this.titulo;
    }
    public String getFecha(){
        return this.fecha;
    }
    public String getDescripcion(){
        return this.descripcion;
    }
    public String getContenido(){
        return this.contenido;
    }

    public void setImagen(String imagen){
        this.imagen = imagen;
    }
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    public void setFecha(String fecha){
        this.fecha = fecha;
    }
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    public void setContenido(String contenido){
        this.contenido = contenido;
    }

}


