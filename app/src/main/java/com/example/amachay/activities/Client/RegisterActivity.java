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
import com.example.amachay.activities.menu_principal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;
import com.example.amachay.includes.MyToolbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    //INSTANCIAMOS LAS VISTAS
    Button mButtonRegister;

    TextInputEditText mTextInputEmail;

    TextInputEditText mTextInputName;

    TextInputEditText mTextInputPassword;

    AlertDialog mDialog;
    SharedPreferences mPref;

    //nuveo firebase object
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MyToolbar.show(this, "Registro de Usuario", true);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //preferencias
        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

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

    void clickRegister() {
        final String name = mTextInputName.getText().toString();
        final String email = mTextInputEmail.getText().toString();
        final String password = mTextInputPassword.getText().toString();

//AHORA VALIDAMOS
        //SI EL NOMBRE NO ES VACIO, EL EMAIL NO ES VACIO , LA CONTRASEÑA NO ES VACIA
        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            if (password.length() >= 6) {

                mDialog.show();
                //CREAMOS UN MÉTODO QUE RECIBE UN EMAIL Y UN PASSWORD
                registerUser(name, email, password);
            } else {

                Toast.makeText(RegisterActivity.this, "La contraseña debe tener al menos 6 carácteres", Toast.LENGTH_SHORT).show();
            }
        } else {

            Toast.makeText(RegisterActivity.this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();

        }

    }

    public void registerUser(final String name, final String email, final String pasword) {
        final SharedPreferences.Editor editor = mPref.edit();
        mAuth.createUserWithEmailAndPassword(email, pasword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email", email);
                    map.put("pasword", pasword);
                    //podria colocarse las preferencias aqui
                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("Users").child("Cliente").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                //guardamos  preferencias cliente
                                editor.putString("emailClient",email);
                                editor.putString("paswordClient",pasword);
                                editor.putString("estado","menu");
                                editor.commit();

                                Intent intent = new Intent(RegisterActivity.this, menu_principal.class);
                                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("cliente", "1");
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "No se pudo regstrar al usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

