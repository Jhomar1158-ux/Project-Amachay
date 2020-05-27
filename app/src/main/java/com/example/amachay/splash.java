package com.example.amachay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.amachay.activities.MainActivity;
import com.example.amachay.activities.Tienda.MapTiendaActivity;
import com.example.amachay.activities.menu_principal;
import com.google.firebase.auth.FirebaseAuth;

public class splash extends AppCompatActivity {
    SharedPreferences mPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
        final SharedPreferences.Editor editor = mPref.edit();
        editor.putString("estado","registro");
        Intent intent = new Intent(splash.this, MainActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser()!= null)
        {
            String user = mPref.getString("estado","");
            if(user.equals("menu"))
            {
                Intent intent = new Intent(splash.this, menu_principal.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
            if(user.equals("registro"))
            {
                Intent intent = new Intent(splash.this, MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }

        }
    }
}
