package com.example.amachay.activities.Novedades;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amachay.R;

public class PostActivity extends AppCompatActivity {

    TextView mTituloTv, mFechaTv;
    ImageView mImagenIv;
    TextView mDescripcionTv;
    TextView mContenidoTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //inicializa views
        mTituloTv = findViewById(R.id.tituloNoticia);
        mFechaTv = findViewById(R.id.fechaNoticia);
        mImagenIv = findViewById(R.id.imagenNoticia);
        mDescripcionTv = findViewById(R.id.descripcionNoticia);
        mContenidoTv = findViewById(R.id.contenidoNoticia);

        //obtener los datos de Intent
        byte[] bytes = getIntent().getByteArrayExtra("Imagen");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        String titulo = getIntent().getStringExtra("Titulo");
        String fecha = getIntent().getStringExtra("Fecha");
        String descripcion = getIntent().getStringExtra("Descripcion");
        String contenido = getIntent().getStringExtra("Contenido");


        //establecer datos en view
        mTituloTv.setText(titulo);
        mFechaTv.setText(fecha);
        mImagenIv.setImageBitmap(bmp);
        mDescripcionTv.setText(descripcion);
        mContenidoTv.setText(contenido);

    }

    //handle onBackPressed(go to previus activity)

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

