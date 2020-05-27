package com.example.amachay.activities.Tienda;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amachay.R;
import com.example.amachay.includes.MyToolbar;
import com.example.amachay.activities.menu_principal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class RegisterTiendaActivity extends AppCompatActivity {

    //INSTANCIAMOS LAS VISTAS
    Button mButtonRegister;
    TextInputEditText mTextInputName;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputNombredelaTienda;
    TextInputEditText mTextInputPassword;

    AlertDialog mDialog;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    SharedPreferences mPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tienda);

//El último boolean hace referencia a si activo el botón de atrás
        MyToolbar.show(this, "Registro de tu tienda", true);

        //INSTANCIAMOS EL MAUTH

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mPref = getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);
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
                registerTienda(name, email, password , NombredelaTienda);
            }
            else
                {

                Toast.makeText(RegisterTiendaActivity.this, "La contraseña debe tener al menos 6 carácteres", Toast.LENGTH_SHORT).show();
            }
        } else {

            Toast.makeText(RegisterTiendaActivity.this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();

        }
    }

    public void registerTienda(final String name, final String email, final String pasword, final String nombretienda){
        final SharedPreferences.Editor editor = mPref.edit();
        mAuth.createUserWithEmailAndPassword(email,pasword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String,Object> map = new HashMap<>();
                    map.put("name",name);
                    map.put("email",email);
                    map.put("pasword",pasword);
                    map.put("idTienda",nombretienda);

                    //podrian colocarse las preferencias

                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("Users").child("Tienda").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()) {
                                //guardar las preferencias
                                //guardamos  preferencias cliente
                                editor.putString("emailTienda",email);
                                editor.putString("paswordTienda",pasword);
                                editor.commit();

                                Intent intent = new Intent(RegisterTiendaActivity.this, menu_principal.class);
                                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("tienda","2");
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(RegisterTiendaActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(RegisterTiendaActivity.this,"No se pudo regstrar al usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}


