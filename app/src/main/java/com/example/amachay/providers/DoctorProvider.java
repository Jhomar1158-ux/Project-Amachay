package com.example.amachay.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.amachay.models.Doctor;

public class DoctorProvider {

    DatabaseReference mDatabase;


    //CREO UN CONSTRUCTOR
    public  DoctorProvider()
    {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Medicos");

    }
    public Task<Void> create(Doctor doctor)
    {
        return mDatabase.child(doctor.getId()).setValue(doctor);

    }
}
