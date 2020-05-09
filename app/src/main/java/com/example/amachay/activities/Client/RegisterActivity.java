package com.example.amachay.activities.Client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.amachay.R;
import com.example.amachay.activities.Doctor.MapDoctorActivity;
import com.example.amachay.activities.Doctor.RegisterDoctorActivity;
import com.example.amachay.providers.AuthProvider;
import com.example.amachay.providers.ClientProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;
import com.example.amachay.includes.MyToolbar;
import com.example.amachay.models.Client;

public class RegisterActivity extends AppCompatActivity {

    //CREAMOOS UN FIREBASE AUTH

    AuthProvider mAuthProvider;
    ClientProvider mClientProvider;


    //INSTANCIAMOS LAS VISTAS
    Button mButtonRegister;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputName;
    TextInputEditText mTextInputPassword;

    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MyToolbar.show(this,"Registro de Usuario",true);

        mAuthProvider = new AuthProvider();

        mClientProvider = new ClientProvider();
//El último boolean hace referencia a si activo el botón de atrás

        //INSTANCIAMOS EL MAUTH
        //LOADING
        mDialog = new SpotsDialog.Builder().setContext(RegisterActivity.this).setMessage("Espere un momento").build();

        mButtonRegister = findViewById(R.id.btnregister);
        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputName = findViewById(R.id.textInputName);
        mTextInputPassword = findViewById(R.id.textInputPassword);

            mButtonRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clickRegister();
                }
            });

    }

    void clickRegister()
    {
        final String name = mTextInputName.getText().toString();
        final String email = mTextInputEmail.getText().toString();
        final String password = mTextInputPassword.getText().toString();

//AHORA VALIDAMOS
        //SI EL NOMBRE NO ES VACIO, EL EMAIL NO ES VACIO , LA CONTRASEÑA NO ES VACIA
        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            if (password.length() >= 6)
            {

                mDialog.show();
                    //CREAMOS UN MÉTODO QUE RECIBE UN EMAIL Y UN PASSWORD
              register(name, email,password);
            }
            else
            {

                Toast.makeText(RegisterActivity.this,"La contraseña debe tener al menos 6 carácteres", Toast.LENGTH_SHORT).show();
            }
        }

        else
        {

            Toast.makeText(RegisterActivity.this,"Ingrese todos los campos" , Toast.LENGTH_SHORT).show();

        }
    }

    void register( final String name,final String email, String password ) {
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.hide();
                //CON ESTO VALIDAMOS SI LA TAREA QUE SE HA GENERADO ES EXITOSA
                if (task.isSuccessful()) {
                    //CON ESTO SE OBTIENE EL ID DEL USUARIO
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Client client = new Client(id, name, email);
                    //ESTO QUIERE DECIR QUE EL USUARIO SE REGISTRO CORRECTAMENTE
                    create(client);

                }
                else {
                    Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void create(Client client) {
        mClientProvider.create(client).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Intent intent = new Intent(RegisterActivity.this, MapDoctorActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(RegisterActivity.this,"No se pudo registrar el usuario",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    /*void saveUser(String id, String name , String email)
    {

        String selectedUser = mPref.getString("user", "");
        User user = new User();
        user.setEmail(email);
        user.setName(name);

        //PARA GUARDAR EL USUARIO
        if(selectedUser.equals("com.example.amachay.activities.Medico"))
        {
            mDatabase.child("Users").child("Medicos").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //SI LA TAREA FUE EXITOSA
                    if(task.isSuccessful())
                    {
                        Toast.makeText(RegisterActivity.this,"Registro exitoso",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        Toast.makeText(RegisterActivity.this,"Fallo el registro",Toast.LENGTH_SHORT).show();

                    }
                }
            });



        }
        else if(selectedUser.equals("Client"))
        {
            mDatabase.child("Users").child("Clientes").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        Toast.makeText(RegisterActivity.this,"Registro exitoso",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        Toast.makeText(RegisterActivity.this,"Fallo el registro",Toast.LENGTH_SHORT).show();

                    }
                }
            });


           // mDatabase.child("Users").child("Usuarios").push().setValue(user);

        }


    }*/
}
