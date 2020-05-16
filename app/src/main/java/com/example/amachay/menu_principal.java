package com.example.amachay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.amachay.activities.Client.MapClientActivity;
import com.example.amachay.activities.Tienda.MapTiendaActivity;
import com.example.amachay.activities.LoginActivity;

public class menu_principal extends AppCompatActivity {
    //declaracion de las variables
    ImageButton bot_consejos;
    ImageButton bot_sintomas;
    ImageButton bot_novedades;


    ImageButton bot_covid_mundial;
    ImageButton bot_covid_peru;
    ImageButton bot_triaje;
    ImageButton bot_emergencia;
    ImageButton bot_mapa;


    ImageButton bot_perfil;

    String valor_cliente;
    String valor_tienda;
    String cliente_registrado;
    String tienda_registrado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        // REGISTRO AL MENU PPRINCIPAL
        //en caso de ya estar registrado : cliente = 10 , doctor=11
        //en caso de registrarse recien : cliente = 1 , doctor = 2



        //inicializando botones .... van primero botones, luego textos

        //botones de la barra inferior
        bot_consejos = findViewById(R.id.boton_barra_consejos);
        bot_sintomas = findViewById(R.id.boton_barra_sintomas);
        bot_novedades = findViewById(R.id.boton_barra_novedades);
        //botones de las 6 opciones generales
        bot_covid_mundial = findViewById(R.id.botonCoivdMUNDO);
        bot_covid_peru = findViewById(R.id.botonCovidPERU);
        bot_triaje = findViewById(R.id.botonTriaje);
        bot_emergencia = findViewById(R.id.botonEmergencia);
        bot_mapa = findViewById(R.id.botonMapa);


        //boton de perfil
        bot_perfil = findViewById(R.id.boton_perfil);

        //textos cambiantes con la base de datos


        //accion bot_...
        bot_consejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menu_principal.this,pantalla_consejos.class);
                startActivity(i);
            }
        });

        bot_novedades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menu_principal.this,pantalla_novedades.class);
                startActivity(i);
            }
        });

        bot_sintomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menu_principal.this,pantalla_sintomas.class);
                startActivity(i);
            }
        });


        //accion bot_perfil
        bot_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menu_principal.this,perfil.class);
                startActivity(i);
            }
        });



        //acccion de los 6 botones
        bot_covid_mundial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menu_principal.this,covid_mundial.class);
                startActivity(i);
            }
        });

        bot_covid_peru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menu_principal.this,covid_peru.class);
                startActivity(i);
            }
        });

        bot_emergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menu_principal.this,numero_emergencia.class);
                startActivity(i);
            }
        });



        //JHOMAR
        bot_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            boton_mapa();
            }
        });






        bot_triaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menu_principal.this,triaje.class);
                startActivity(i);
            }
        });
    }

    public void boton_mapa(){
        //recibimos los strings

        recibir_datos_desde_loginActivity();
        recibir_datos_desde_loginActivity_1();
        recibir_datos_desde_RegisterActivity();
        recibir_datos_desde_RegisterTiendaActivity();

        //confirmamos que no apuntan a NULL

       if(cliente_registrado!=null){
           Intent intent = new Intent(menu_principal.this, MapClientActivity.class);
          // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(intent);
       }
       if(tienda_registrado!=null) {
           Intent intent = new Intent(menu_principal.this, MapTiendaActivity.class);
           //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(intent);
       }
       if(valor_cliente!=null){
           Intent intent = new Intent(menu_principal.this, MapClientActivity.class);
          // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(intent);
       }
       if(valor_tienda!=null){
           Intent intent = new Intent(menu_principal.this, MapTiendaActivity.class);
          // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(intent);
       }
    }



    public void recibir_datos_desde_loginActivity(){
        // se recibe "cliente registrado" = 10 && "tienda registrado" = 11
        Bundle recibe = getIntent().getExtras();
        if(recibe!=null){
            cliente_registrado=recibe.getString(LoginActivity.key);
        }

    }
    public void recibir_datos_desde_loginActivity_1(){
        // se recibe "cliente registrado" = 10 && "doctor registrado" = 11
        Bundle recibe = getIntent().getExtras();
        if(recibe!=null){
            tienda_registrado= recibe.getString(LoginActivity.key2);
        }

    }

    public void recibir_datos_desde_RegisterActivity(){
        // se recibe "cliente" = 1
        Bundle recibe = getIntent().getExtras();
        if(recibe!=null){
            valor_cliente = recibe.getString("cliente");
        }
    }

    public void recibir_datos_desde_RegisterTiendaActivity(){
        // se recibe "tienda" = 2
        Bundle recibe = getIntent().getExtras();
        if(recibe!=null) {
            valor_tienda = recibe.getString("tienda");
        }
    }


}
