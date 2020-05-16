package com.example.amachay.includes;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amachay.R;
import com.squareup.picasso.Picasso;

public class ViewHolder2Novedades extends RecyclerView.ViewHolder {

    View mview;

    public ViewHolder2Novedades(@NonNull View itemView) {
        super(itemView);

        mview = itemView;
    }
    //Establesco los valores de recyclerView rom2
    public void setDetalles(Context ctx, String titulo, String imagen){
        TextView mTitulo = mview.findViewById(R.id.rTituloGaleria);
        ImageView mImagen = mview.findViewById(R.id.rImagenGaleria);

        //Establesco datos en view
        mTitulo.setText(titulo);
        Picasso.get().load(imagen).into(mImagen);
    }


}


