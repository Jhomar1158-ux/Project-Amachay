package com.example.amachay.activities.Tienda;

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
import com.example.amachay.menu_principal;
import com.example.amachay.models.Tienda;
import com.example.amachay.providers.AuthProvider;
import com.example.amachay.providers.TiendaProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class RegisterTiendaActivity extends AppCompatActivity {

    //CREAMOOS UN FIREBASE AUTH

    AuthProvider mAuthProvider;
    TiendaProvider mTiendaProvider;


    //INSTANCIAMOS LAS VISTAS
    Button mButtonRegister;
    TextInputEditText mTextInputName;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputNombredelaTienda;
    TextInputEditText mTextInputPassword;

    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tienda);
        mAuthProvider = new AuthProvider();
        mTiendaProvider = new TiendaProvider();
//El último boolean hace referencia a si activo el botón de atrás
        MyToolbar.show(this, "Registro de tu tienda", true);

        //INSTANCIAMOS EL MAUTH
        //LOADING
        mDialog = new SpotsDialog.Builder().setContext(RegisterTiendaActivity.this).setMessage("Espere un momento").build();

        mButtonRegister = findViewById(R.id.btnregister);

        mTextInputEmail = findViewById(R.id.textInputEmail);

        mTextInputName = findViewById(R.id.textInputName);

        mTextInputPassword = findViewById(R.id.textInputPassword);

        mTextInputNombredelaTienda = findViewById(R.id.textInputNombredelaTienda);




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
        //Creamos los String de lo último ingresado
        final String NombredelaTienda = mTextInputNombredelaTienda.getText().toString();


//AHORA VALIDAMOS
        //SI EL NOMBRE NO ES VACIO, EL EMAIL NO ES VACIO , LA CONTRASEÑA NO ES VACIA
        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !NombredelaTienda.isEmpty() )
        {
            if (password.length() >= 6) {

                mDialog.show();
                //CREAMOS UN MÉTODO QUE RECIBE UN EMAIL Y UN PASSWORD
                register(name, email, password , NombredelaTienda);
            }
            else
                {

                Toast.makeText(RegisterTiendaActivity.this, "La contraseña debe tener al menos 6 carácteres", Toast.LENGTH_SHORT).show();
            }
        } else {

            Toast.makeText(RegisterTiendaActivity.this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();

        }
    }

    void register(final String name, final String email, String password, final String NombredelaTienda ) {
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.hide();
                //CON ESTO VALIDAMOS SI LA TAREA QUE SE HA GENERADO ES EXITOSA
                if (task.isSuccessful()) {
                    //CON ESTO SE OBTIENE EL ID DEL USUARIO
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Tienda tienda = new Tienda(id, name, email,NombredelaTienda);

                    //ESTO QUIERE DECIR QUE EL USUARIO SE REGISTRO CORRECTAMENTE
                    create(tienda);

                } else {
                    Toast.makeText(RegisterTiendaActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void create(Tienda tienda) {
        mTiendaProvider.create(tienda).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(RegisterTiendaActivity.this, menu_principal.class);
                       //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("tienda","2");

                        startActivity(intent);
                } else {

                    Toast.makeText(RegisterTiendaActivity.this, "No se pudo registrar la tienda", Toast.LENGTH_SHORT).show();


                }
            }
        });


    }
}
