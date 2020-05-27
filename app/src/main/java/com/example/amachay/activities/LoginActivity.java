package com.example.amachay.activities;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dmax.dialog.SpotsDialog;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    //CREAMOS UN TEXTINPUT PARA EL EMAIL
    TextInputEditText mTextInputEmail;
    //CREAMOS OTRO TEXTINPUT PARA EL PASSSWORD
    TextInputEditText mTextInputPassword;

    //DE LA MISMA MANERA CON EL BOTÓN
    Button mButtonlogin;
    // CREAMOS UNA PROPIEDAD
    FirebaseAuth mAuth;
    //TAMBIÉN LA BASE DE DATOS EN TIEMPO REAL DE FIREBASE
    DatabaseReference mDatabase;
    // CREAMOS UNA PROPIEDAD (Androidapp)
    AlertDialog mDialog;

    SharedPreferences mPref;

    public static final String key= "cliente_registrado" ;
    public static final String key2= "tienda_registrado" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //CON ESTO MOSTRAMOS EL TOOLBAR




        //VAMOS A INSTANCIAR LAS VISTAS
        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputPassword=findViewById(R.id.textInputPassword);
        mButtonlogin = findViewById(R.id.btnlogin);
        //INSTANCIO EL FIREBASE
        mAuth = FirebaseAuth.getInstance();
        //INSTANCIAMOS LA DATA BASE
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //INTANCIAMOS EL ALERT DIALOG

        mDialog = new SpotsDialog.Builder().setContext(LoginActivity.this).setMessage("Espere un momento").build();
        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);



        mButtonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CREAMOS UNA FUNCIÓN LLAMADA LOGIN
                login();
            }
        });


    }
    // CREAMOS LA FUNCIÓN LOGIN
    public void login() {
        final SharedPreferences.Editor editor = mPref.edit();
        //OBTENEMOS LA INFORMACIÓN QUE INGRESA EL USUARIO
        final String email = Objects.requireNonNull(mTextInputEmail.getText()).toString();
        final String password = Objects.requireNonNull(mTextInputPassword.getText()).toString();

        //HAREMOS LAS VALIDACIONES
        //VERIFICAMOS SI EL USUARIO HA INGRESADO SU CORREO Y SI EL PASSWORD ESTÁ VACÍO

        //Empty : Vacío
        if(!email.isEmpty() && !password.isEmpty())
        {

            // HAGO UNA VALIDACIÓN PARA VERIFICAR QUE HA USADO UN PASSWORD MAYOR A 6
            if(password.length() >= 6)
            {

                //LLAMAMOS AL MÉTODO DIALOG Y LO MOSTRAMOS
                mDialog.show();


                //USAMOS LA PROPIEDAD AUTH

                //LE DECIMOS QUE QUE RECIBA COMO PARAMETROS UN EMAIL Y UN PASSWORD
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    //AQUÍ VEMOS QUÉ HAREMOS LO QUE DE SE TERMINE DE EJECUTAR
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //SI LA TAREA FUE EXITOSA
                        if (task.isSuccessful())
                        {
                            String user = mPref.getString("user","");
                            if(user.equals("client"))
                            {
                                //guardamos el email y pasword en las preferencias
                                editor.putString("emailClient",email);
                                editor.putString("paswordClient",password);
                                editor.commit();

                                Intent intent = new Intent(LoginActivity.this, menu_principal.class);
                                intent.putExtra(key,"10");
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);




                                /*Intent intent = new Intent(LoginActivity.this, MapClientActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);*/

                            }
                            if(user.equals("tienda"))
                            {
                                editor.putString("emailTienda",email);
                                editor.putString("paswordTienda",password);
                                editor.commit();

                                Intent intent = new Intent(LoginActivity.this, menu_principal.class);
                                intent.putExtra(key2,"11");
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);



                                /*Intent intent = new Intent(LoginActivity.this, MapDoctorActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);*/


                            }
                        }
                        //EN CASO CONTRARIO
                        else
                        {

                            Toast.makeText(LoginActivity.this,"La contraseña es incorrecta.",Toast.LENGTH_SHORT).show();

                        }
//DEJAR DE MOSTRAR EL DIALOG
                        mDialog.dismiss();
                    }
                });

            }

            else
            {

                Toast.makeText(this,"La contraseña debe tener más de 6 caracteres.",Toast.LENGTH_SHORT).show();

            }

        }
        else
        {

            Toast.makeText(this,"La contraseña y el correo son obligatorios.",Toast.LENGTH_SHORT).show();

        }



    }
}
