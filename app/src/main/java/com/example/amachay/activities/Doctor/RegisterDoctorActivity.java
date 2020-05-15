package com.example.amachay.activities.Doctor;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amachay.R;
import com.example.amachay.includes.MyToolbar;
import com.example.amachay.activities.menu_principal;
import com.example.amachay.models.Doctor;
import com.example.amachay.providers.AuthProvider;
import com.example.amachay.providers.DoctorProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class RegisterDoctorActivity extends AppCompatActivity {

    //CREAMOOS UN FIREBASE AUTH

    AuthProvider mAuthProvider;
    DoctorProvider mDoctorProvider;


    //INSTANCIAMOS LAS VISTAS
    Button mButtonRegister;
    TextInputEditText mTextInputName;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputColegiatura;
    TextInputEditText mTextInputPlaca;
    TextInputEditText mTextInputPassword;

    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_doctor);
        mAuthProvider = new AuthProvider();
        mDoctorProvider = new DoctorProvider();
//El último boolean hace referencia a si activo el botón de atrás
        MyToolbar.show(this, "Registro de Médico", true);

        //INSTANCIAMOS EL MAUTH
        //LOADING
        mDialog = new SpotsDialog.Builder().setContext(RegisterDoctorActivity.this).setMessage("Espere un momento").build();

        mButtonRegister = findViewById(R.id.btnregister);
        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputName = findViewById(R.id.textInputName);
        mTextInputPassword = findViewById(R.id.textInputPassword);
        mTextInputColegiatura = findViewById(R.id.textInputColegiatura);
        mTextInputPlaca = findViewById(R.id.textInputPlaca);

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
        //Creamos los String de lo último ingresado
        final String colegiatura = mTextInputColegiatura.getText().toString();
        final String placa = mTextInputPlaca.getText().toString();

//AHORA VALIDAMOS
        //SI EL NOMBRE NO ES VACIO, EL EMAIL NO ES VACIO , LA CONTRASEÑA NO ES VACIA
        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !colegiatura.isEmpty() && !placa.isEmpty())
        {
            if (password.length() >= 6) {

                mDialog.show();
                //CREAMOS UN MÉTODO QUE RECIBE UN EMAIL Y UN PASSWORD
                register(name, email, password , colegiatura, placa);
            } else {

                Toast.makeText(RegisterDoctorActivity.this, "La contraseña debe tener al menos 6 carácteres", Toast.LENGTH_SHORT).show();
            }
        } else {

            Toast.makeText(RegisterDoctorActivity.this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();

        }
    }

    void register(final String name, final String email, String password, final String colegiatura , final String placa) {
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.hide();
                //CON ESTO VALIDAMOS SI LA TAREA QUE SE HA GENERADO ES EXITOSA
                if (task.isSuccessful()) {
                    //CON ESTO SE OBTIENE EL ID DEL USUARIO
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Doctor doctor = new Doctor(id, name, email,colegiatura,placa);

                    //ESTO QUIERE DECIR QUE EL USUARIO SE REGISTRO CORRECTAMENTE
                    create(doctor);

                } else {
                    Toast.makeText(RegisterDoctorActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void create(Doctor doctor) {
        mDoctorProvider.create(doctor).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(RegisterDoctorActivity.this, menu_principal.class);
                       //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("doctor","2");

                        startActivity(intent);
                } else {

                    Toast.makeText(RegisterDoctorActivity.this, "No se pudo registrar el médico", Toast.LENGTH_SHORT).show();


                }
            }
        });


    }
}
