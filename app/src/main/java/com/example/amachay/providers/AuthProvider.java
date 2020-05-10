package com.example.amachay.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthProvider {

    FirebaseAuth mAuth;


    //CREAMOS UN CONSTUCTOR
    public AuthProvider()
    {

        mAuth = FirebaseAuth.getInstance();

    }

    public Task<AuthResult> register(String email, String password)
    {
        return mAuth.createUserWithEmailAndPassword(email, password);

    }
    public Task<AuthResult> login(String email, String password)
    {
        return mAuth.signInWithEmailAndPassword(email, password);

    }

    //Con esto estoy cerrando la sesión del usurio
    public void logout()
    {
        mAuth.signOut();

    }
    public String getId()
    {

        return mAuth.getCurrentUser().getUid();
    }
    public  boolean existSession()
    {
        boolean exist = false;
        if(mAuth.getCurrentUser()!=null)
        {

            exist = true;
        }
        return exist;

    }


}
