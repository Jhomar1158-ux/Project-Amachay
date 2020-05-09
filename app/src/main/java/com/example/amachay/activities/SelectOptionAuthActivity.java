package com.example.amachay.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.amachay.R;
import com.example.amachay.activities.Client.RegisterActivity;
import com.example.amachay.activities.Doctor.RegisterDoctorActivity;
import com.example.amachay.includes.MyToolbar;

public class SelectOptionAuthActivity extends AppCompatActivity {

    SharedPreferences mPref;

    //REFERENCIAMOS NUESTRO TOOLBAR
//IMPORTAMOS APPCOMPAT ARRIBA

    Button mButtongotoLogin;
    Button mButtongotoRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);
        //REFERENCIAMOS AL TOOLBAR

        MyToolbar.show(this,"Seleccionar una opción",true);
        //LE DECIMOS QUE TIENE UN BOTÓN QUE VA HACIA ATRÁS, le decimos que sí



         //INSTACIAMOS NUESTRO PRIMER BOTÓN
        mButtongotoLogin = findViewById(R.id.btngotologin);
        //INSTANCIAMOS NUESTRO SEGUNGO BOTÓN
        mButtongotoRegister= findViewById(R.id.btngotoregister);

        mButtongotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotologin();
            }
        });
        mButtongotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EJECUTAMOS EL MÉTODO GO TO REGISTER
                gotoregister();
            }
        });

        mPref = getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);
    }

    private void gotologin() {

        Intent intent = new Intent (SelectOptionAuthActivity.this, LoginActivity.class);
        startActivity(intent);

    }
    private void gotoregister() {
        String typeUser = mPref.getString("user","");
        if(typeUser.equals("client"))
        {
            Intent intent = new Intent(SelectOptionAuthActivity.this, RegisterActivity.class);
            startActivity(intent);

        }
        else
            {

            Intent intent = new Intent(SelectOptionAuthActivity.this, RegisterDoctorActivity.class);
            startActivity(intent);
        }
    }
}
