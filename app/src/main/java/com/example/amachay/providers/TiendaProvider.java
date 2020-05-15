package com.example.amachay.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.amachay.models.Tienda;

public class TiendaProvider {

    DatabaseReference mDatabase;


    //CREO UN CONSTRUCTOR
    public TiendaProvider()
    {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Tienda");

    }
    public Task<Void> create(Tienda tienda)
    {
        return mDatabase.child(tienda.getId()).setValue(tienda);

    }
}
