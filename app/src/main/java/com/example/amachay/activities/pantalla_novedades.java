package com.example.amachay.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.amachay.R;
import com.example.amachay.includes.GlobalPage;
import com.example.amachay.includes.NacionalPage;
import com.example.amachay.includes.VideosPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class pantalla_novedades extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_novedades);

        //al iniciar que empiece con el fragmento nacional
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new NacionalPage()).commit();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.neg_bar);
        bottomNavigationView.setItemIconTintList(null);// para q se vea animacion en navigationbuttom
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_nacional:
                            fragment = new NacionalPage();
                            break;
                        case R.id.nav_videos:
                            fragment = new VideosPage();
                            break;
                        case R.id.nav_global:
                            fragment = new GlobalPage();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
                    return true;
                }
            };

}
