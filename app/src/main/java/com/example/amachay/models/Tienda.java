package com.example.amachay.models;

public class Tienda {
    String id;
    String name;
    String email;
    String NombredelaTienda;

    public Tienda() {
    }

    public Tienda(String id, String name, String email, String NombredelaTienda) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.NombredelaTienda = NombredelaTienda;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombredelaTienda() {
        return NombredelaTienda;
    }

    public void setNombredelaTienda(String NombredelaTienda) {
        this.NombredelaTienda = NombredelaTienda;
    }

}
