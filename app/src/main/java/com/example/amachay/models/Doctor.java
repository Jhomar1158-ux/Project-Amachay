package com.example.amachay.models;

public class Doctor {
    String id;
    String name;
    String email;
    String colegiatura;
    String placa;

    public Doctor() {
    }

    public Doctor(String id, String name, String email, String colegiatura, String placa) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.colegiatura = colegiatura;
        this.placa = placa;
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

    public String getColegiatura() {
        return colegiatura;
    }

    public void setColegiatura(String colegiatura) {
        this.colegiatura = colegiatura;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
