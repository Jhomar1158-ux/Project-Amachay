package com.example.amachay.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import com.example.amachay.models.Client;

public class ClientProvider {
    DatabaseReference mDatabase;


    //CREO UN CONSTRUCTOR
    public  ClientProvider()
    {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Clientes");

    }
    public Task<Void> create(Client client)
    {
        Map<String,Object> map = new HashMap<>();

        map.put("name",client.getName());
        map.put("email",client.getEmail());

        return mDatabase.child(client.getId()).setValue(map);

    }

}
