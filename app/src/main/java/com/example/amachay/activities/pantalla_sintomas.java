package com.example.amachay.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.amachay.R;

public class pantalla_sintomas extends AppCompatActivity implements View.OnClickListener {
    ImageButton atras;
    Button fiebre, dificultad, ojosirri, tos, fatiga, debilidad, estornudo, congestion;
    boolean estadoF=true, estadoDe=true, estadoO=true, estadoT=true, estadoD=true, estadoE=true, estadoC=true, estadoFa=true;
    int suma=0;
    String color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_sintomas);
        //TITULO DEL TOOLBAR
        TextView tituloToolbar = findViewById(R.id.text);
        tituloToolbar.setText("Seleccione los síntomas\n que presenta hoy:");
        //IMAGEBUTTON ATRAS
        atras=findViewById(R.id.atras);
        findViewById(R.id.atras).setOnClickListener(this);


        findViewById(R.id.fiebre).setOnClickListener(this);
        fiebre=findViewById(R.id.fiebre);
        findViewById(R.id.dificultad).setOnClickListener(this);
        dificultad=findViewById(R.id.dificultad);
        findViewById(R.id.ojos).setOnClickListener(this);
        ojosirri=findViewById(R.id.ojos);
        findViewById(R.id.tos).setOnClickListener(this);
        tos=findViewById(R.id.tos);
        findViewById(R.id.fatiga).setOnClickListener(this);
        fatiga=findViewById(R.id.fatiga);
        findViewById(R.id.debilidad).setOnClickListener(this);
        debilidad=findViewById(R.id.debilidad);
        findViewById(R.id.estornudos).setOnClickListener(this);
        estornudo =findViewById(R.id.estornudos);
        findViewById(R.id.congestion).setOnClickListener(this);
        congestion=findViewById(R.id.congestion);
        findViewById(R.id.diagnostico).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //ASIGNAR FUNCION A BOTONES
        switch (v.getId()) {
            case R.id.fiebre:
                if (estadoF) {
                    fiebre.setTextColor(Color.parseColor("#DFDFDF"));
                    fiebre.setBackgroundResource(R.drawable.rounded_sel);
                    suma = suma + 2;
                    estadoF = false;
                } else {
                    fiebre.setTextColor(Color.parseColor("#3A6978"));
                    fiebre.setBackgroundResource(R.drawable.rounded);
                    suma = suma - 2;
                    estadoF = true;
                }
                break;
            case R.id.tos:
                if (estadoT) {
                    suma = suma + 2;
                    tos.setTextColor(Color.parseColor("#DFDFDF"));
                    tos.setBackgroundResource(R.drawable.rounded_sel);
                    estadoT = false;
                } else {
                    suma = suma - 2;
                    tos.setTextColor(Color.parseColor("#3A6978"));
                    tos.setBackgroundResource(R.drawable.rounded);
                    estadoT = true;
                }
                break;
            case R.id.dificultad:
                if (estadoD) {
                    suma = suma + 6;
                    dificultad.setTextColor(Color.parseColor("#DFDFDF"));
                    dificultad.setBackgroundResource(R.drawable.rounded_sel);
                    estadoD = false;
                } else {
                    suma = suma - 6;
                    dificultad.setTextColor(Color.parseColor("#3A6978"));
                    dificultad.setBackgroundResource(R.drawable.rounded);
                    estadoD = true;
                }
                break;
            case R.id.ojos:
                if (estadoO) {
                    suma = suma + 1;
                    ojosirri.setTextColor(Color.parseColor("#DFDFDF"));
                    ojosirri.setBackgroundResource(R.drawable.rounded_sel);
                    estadoO = false;
                } else {
                    suma = suma - 1;
                    ojosirri.setTextColor(Color.parseColor("#3A6978"));
                    ojosirri.setBackgroundResource(R.drawable.rounded);
                    estadoO = true;
                }
                break;
            case R.id.fatiga:
                if (estadoFa) {
                    suma = suma + 1;
                    fatiga.setTextColor(Color.parseColor("#DFDFDF"));
                    fatiga.setBackgroundResource(R.drawable.rounded_sel);
                    estadoFa = false;
                } else {
                    suma = suma - 1;
                    fatiga.setTextColor(Color.parseColor("#3A6978"));
                    fatiga.setBackgroundResource(R.drawable.rounded);
                    estadoFa = true;
                }
                break;
            case R.id.debilidad:
                if (estadoDe) {
                    suma = suma + 1;
                    debilidad.setTextColor(Color.parseColor("#DFDFDF"));
                    debilidad.setBackgroundResource(R.drawable.rounded_sel);
                    estadoDe = false;
                } else {
                    suma = suma - 1;
                    debilidad.setTextColor(Color.parseColor("#3A6978"));
                    debilidad.setBackgroundResource(R.drawable.rounded);
                    estadoDe = true;
                }
                break;
            case R.id.estornudos:
                if (estadoE) {
                    suma = suma + 1;
                    estornudo.setTextColor(Color.parseColor("#DFDFDF"));
                    estornudo.setBackgroundResource(R.drawable.rounded_sel);
                    estadoE = false;
                } else {
                    suma = suma - 1;
                    estornudo.setTextColor(Color.parseColor("#3A6978"));
                    estornudo.setBackgroundResource(R.drawable.rounded);
                    estadoE = true;
                }
                break;
            case R.id.congestion:
                if (estadoC) {
                    suma = suma + 2;
                    congestion.setTextColor(Color.parseColor("#DFDFDF"));
                    congestion.setBackgroundResource(R.drawable.rounded_sel);
                    estadoC = false;
                } else {
                    suma = suma - 2;
                    congestion.setTextColor(Color.parseColor("#3A6978"));
                    congestion.setBackgroundResource(R.drawable.rounded);
                    estadoC = true;
                }
                break;
            //BOTON CONTINUAR
            case R.id.diagnostico:
                if (suma == 0) {
                    color = "verde";
                }

                if (suma > 0 && suma <= 4) {
                    color = "amarillo";
                }

                if (suma > 4 && suma <= 7) {
                    color = "rojo";
                }

                if (suma > 7) {
                    color = "guinda";
                }


                break;

            case R.id.atras:
                Intent inten =new Intent(pantalla_sintomas.this, menu_principal.class);
                startActivity(inten);
                break;
            }
        }
    }