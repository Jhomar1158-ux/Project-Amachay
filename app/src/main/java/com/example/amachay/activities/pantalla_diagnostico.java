package com.example.amachay.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.amachay.R;

//Orden: Menu -> Sintomas -> Diagnostico

public class pantalla_diagnostico extends AppCompatActivity {
    public static final String nombre = "Nicolás";          //Variable con el nombre del usuario obtenida al registrarse
    public static String color = "rojo";              //Variable con el diagnóstico obtenido de la interfaz Diagnósticos
    public static final String malestar = "resfriado";      //Variable con el malestar, obtenido de la interfaz Diagnósticos(no terminado en esa interfaz)
    public static final boolean dia = true;                //Variable dependiente del tiempo actual
    public static boolean semaforoDesplegado = false;       //dependen del programa
    public static boolean malDesplegado = false;            //dependen del programa
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostico);
        Intent deDiagnostico = getIntent();
        color = deDiagnostico.getStringExtra("diagnostique");
        interfaz();                                         //inicializa el fondo y los botones
        diagnosticoFinal();                                 //muestra el diagnostico
    }

    public void interfaz(){
        //ImageView fondo = (ImageView) findViewById(R.id.fondo);                 //fondo de pantalla
        ImageButton continuar = (ImageButton) findViewById(R.id.continuar);     //boton continuar
        ImageButton triaje = (ImageButton) findViewById(R.id.triaje);           //boton triaje
        ImageView infoColores = (ImageView) findViewById(R.id.estadosDeSalud);  //informacion del semaforo de colores
        if(dia){
            //fondo.setImageResource(R.drawable.fondo_dia);
            continuar.setImageResource(R.drawable.continuar_dia);
            triaje.setImageResource(R.drawable.triaje_dia);
            infoColores.setImageResource(R.drawable.estados_de_salud_dia);
        }
        else{
            //fondo.setImageResource(R.drawable.fondo_noche);
            continuar.setImageResource(R.drawable.continuar_noche);
            triaje.setImageResource(R.drawable.triaje_noche);
            infoColores.setImageResource(R.drawable.estados_de_salud_noche);
        }
    }
//Código para escribir el saludo, no está definido completamente

/*
        public void saludar(){
            TextView saludo = (TextView) findViewById(R.id.saludo);
            if(color == "verde"){
                saludo.setText("¡Hola "+nombre+"!\nDe acuerdo al diagnóstico,\nusted:");
            }
            else if(color == "guinda"){
                saludo.setText("¡Hola "+nombre+"!\nDe acuerdo al diagnóstico,\nusted es:");
            }
            else{
                saludo.setText("¡Hola "+nombre+"!\nDe acuerdo al diagnóstico,\nusted presenta:");
            }
        }
*/
    public void diagnosticoFinal(){
        ImageView encabezado = (ImageView) findViewById(R.id.encabezado);           //encabezado con el saludo al usuario
        ImageView doctor = (ImageView) findViewById(R.id.doctor);                   //imagen del doctor
        ImageView diagnosticoColor = (ImageView) findViewById(R.id.bolaDeColor);    //diagnostico, dado con una bola de color
        ImageButton cuestion = (ImageButton) findViewById(R.id.pregunta);           //boton de ?, para esconderlo cuando el diagnostico es verde
        ImageView enfermedad = (ImageView) findViewById(R.id.posibleEnfermedad);    //informacion de la posible enfermedad que se tiene (no desarrollado en Diagnosticos)
        switch(color){
            case "verde":
                if(dia){
                    encabezado.setImageResource(R.drawable.encabezado_verde_dia);
                    doctor.setImageResource(R.drawable.doctor_verde_dia);
                    diagnosticoColor.setImageResource(R.drawable.diagnostico_verde_dia);
                    cuestion.setVisibility(View.GONE);                              //oculta el boton ?
                }
                else{
                    encabezado.setImageResource(R.drawable.encabezado_verde_noche);
                    doctor.setImageResource(R.drawable.doctor_verde_noche);
                    diagnosticoColor.setImageResource(R.drawable.diagnostico_verde_noche);
                    cuestion.setVisibility(View.GONE);
                }
                break;
            case "amarillo":
                if(dia){
                    encabezado.setImageResource(R.drawable.encabezado_rojo_dia);
                    doctor.setImageResource(R.drawable.doctor_amarillo_noche);      //la imagen del doctor de dia cierra al programa, esta reemplazada por la nocturna
                    diagnosticoColor.setImageResource(R.drawable.diagnostico_amarillo_dia);
                    if(malestar == "resfriado") enfermedad.setImageResource(R.drawable.informacion_resfriado_dia);      //dos posibilidades: resfriado y alergia (no desarrollado en Diagnosticos)
                    else enfermedad.setImageResource(R.drawable.informacion_alergia_dia);
                }
                else{
                    encabezado.setImageResource(R.drawable.encabezado_amarillo_noche);
                    doctor.setImageResource(R.drawable.doctor_amarillo_noche);
                    diagnosticoColor.setImageResource(R.drawable.diagnostico_amarillo_noche);
                    if(malestar == "resfriado") enfermedad.setImageResource(R.drawable.informacion_resfriado_noche);    //dos posibilidades: resfriado y alergia (no desarrollado en Diagnosticos)
                    else enfermedad.setImageResource(R.drawable.informacion_alergia_noche);
                }
                break;
            case "rojo":
                if(dia){
                    encabezado.setImageResource(R.drawable.encabezado_rojo_dia);
                    doctor.setImageResource(R.drawable.doctor_rojo_noche);          //la imagen del doctor de dia cierra al programa, esta reemplazada por la nocturna
                    diagnosticoColor.setImageResource(R.drawable.diagnostico_rojo_dia);
                    enfermedad.setImageResource(R.drawable.informacion_gripe_dia);
                }
                else{
                    encabezado.setImageResource(R.drawable.encabezado_rojo_noche);
                    doctor.setImageResource(R.drawable.doctor_rojo_noche);
                    diagnosticoColor.setImageResource(R.drawable.diagnostico_rojo_noche);
                    enfermedad.setImageResource(R.drawable.informacion_gripe_noche);
                }
                break;
            case "guinda":
                if(dia){
                    encabezado.setImageResource(R.drawable.encabezado_guinda_dia);
                    doctor.setImageResource(R.drawable.doctor_guinda_dia);
                    diagnosticoColor.setImageResource(R.drawable.diagnostico_guinda_dia);
                    enfermedad.setImageResource(R.drawable.informacion_covid_dia);
                }
                else{
                    encabezado.setImageResource(R.drawable.encabezado_guinda_noche);
                    doctor.setImageResource(R.drawable.doctor_guinda_noche);
                    diagnosticoColor.setImageResource(R.drawable.diagnostico_guinda_noche);
                    enfermedad.setImageResource(R.drawable.informacion_covid_noche);
                }
                break;
        }
    }

    public void desplegarSemaforo(View Vista){      //funcion activada al pulsar el boton de semaforo
        if(!malDesplegado){
            ImageView tablaSemaforo = (ImageView) findViewById(R.id.estadosDeSalud);
            if(!semaforoDesplegado){
                tablaSemaforo.setVisibility(View.VISIBLE);
                semaforoDesplegado = true;
            }
            else{
                tablaSemaforo.setVisibility(View.INVISIBLE);
                semaforoDesplegado = false;
            }
        }
    }

    public void desplegarMal(View Vista){           //funcion activada al pulsar el boton '?'
        if(!semaforoDesplegado){
            ImageView tablaMal = (ImageView) findViewById(R.id.posibleEnfermedad);
            if(!malDesplegado){
                tablaMal.setVisibility(View.VISIBLE);
                malDesplegado = true;
            }
            else{
                tablaMal.setVisibility(View.INVISIBLE);
                malDesplegado = false;
            }
        }
    }

    public void siguiente(View Vista){              //funcion activada al pulsar el boton siguiente
        Intent aMenu = new Intent(pantalla_diagnostico.this, menu_principal.class);       //Siguiente dirige al menu principal
        startActivity(aMenu);
    }

    public void hacerTriaje(View Vista){            //funcion activada al pulsar el boton triaje
        Intent aTriaje = new Intent(pantalla_diagnostico.this, triaje.class);   //Triaje dirige al triaje
        startActivity(aTriaje);
    }

    public void volver(View Vista){                 //funcion activada al pulsar el boton '<'
        Intent aSintomas = new Intent(pantalla_diagnostico.this, pantalla_sintomas.class);   //volver dirige a Sintomas
        startActivity(aSintomas);
    }
}