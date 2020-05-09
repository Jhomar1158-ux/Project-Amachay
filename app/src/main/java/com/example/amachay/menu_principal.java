package com.example.amachay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        //inicializando botones .... van primero botones, luego textos

        //botones de la barra inferior
        bot_consejos = (ImageButton) findViewById(R.id.boton_barra_consejos);
        bot_sintomas = (ImageButton) findViewById(R.id.boton_barra_sintomas);
        bot_novedades = (ImageButton) findViewById(R.id.boton_barra_novedades);
        //botones de las 6 opciones generales
        bot_covid_mundial = (ImageButton) findViewById(R.id.botonCoivdMUNDO);
        bot_covid_peru = (ImageButton) findViewById(R.id.botonCovidPERU);
        bot_triaje = (ImageButton) findViewById(R.id.botonTriaje);
        bot_emergencia = (ImageButton) findViewById(R.id.botonEmergencia);
        bot_mapa = (ImageButton) findViewById(R.id.botonMapa);

        //boton de perfil
        bot_perfil = (ImageButton) findViewById(R.id.boton_perfil);

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

        bot_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menu_principal.this,mapa.class);
                startActivity(i);
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
}
