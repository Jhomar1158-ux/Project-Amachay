package com.example.amachay.includes;

import android.app.AppComponentFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.amachay.R;

public class MyToolbar {

    public static void show(AppCompatActivity activity, String  title , boolean upButton)
    {
        Toolbar toolbar= activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(title);
        //LE DECIMOS QUE TIENE UN BOTÓN QUE VA HACIA ATRÁS, le decimos que sí
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);




    }
}
