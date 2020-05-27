package com.example.amachay.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.amachay.R;
import com.example.amachay.providers.AuthProvider;

public class perfil_cliente extends AppCompatActivity {
    ImageButton bot_regreso_main;
    ImageButton bot_cerrar_sesion;
    private AuthProvider mAuthProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cliente);

        bot_regreso_main = findViewById(R.id.boton_regreso_a_menu);
        bot_cerrar_sesion = findViewById(R.id.boton_cerrar_sesion);
        mAuthProvider = new AuthProvider();





        bot_regreso_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(perfil_cliente.this, menu_principal.class);
                startActivity(i);
            }
        });

        bot_cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrar_sesion();
            }
        });


    }


    public void cerrar_sesion(){
        mAuthProvider.logout();
        Intent intent = new Intent(perfil_cliente.this, MainActivity.class);
        startActivity(intent);

        finish();
    }
}
