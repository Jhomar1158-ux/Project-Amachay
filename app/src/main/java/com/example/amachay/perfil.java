package com.example.amachay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class perfil extends AppCompatActivity {
    Button bot_regreso_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        bot_regreso_main = (Button) findViewById(R.id.boton_regreso_a_menu);






        bot_regreso_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(perfil.this,menu_principal.class);
                startActivity(i);
            }
        });

    }
}
