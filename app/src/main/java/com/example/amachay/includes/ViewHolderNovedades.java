package com.example.amachay.includes;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amachay.R;
import com.squareup.picasso.Picasso;

public class ViewHolderNovedades extends RecyclerView.ViewHolder {

    View mview;

    public ViewHolderNovedades(@NonNull View itemView) {
        super(itemView);

        mview = itemView;

        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mClickListener.onItemClick(view,getAdapterPosition());

            }
        });
        //item long click
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                mClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            }
        });
    }

    //Establesco los valores de recyclerView rom
    public void setDetails(Context ctx, String titulo, String imagen, String fecha, String descripcion, String contenido) {
        TextView mTitulo = mview.findViewById(R.id.rTituloNoticia);
        TextView mFecha = mview.findViewById(R.id.rFechaNoticia);
        ImageView mImagen = mview.findViewById(R.id.rImagenNoticia);
        TextView mDescripcion = mview.findViewById(R.id.rDescripcionNoticia);
        TextView mContenido = mview.findViewById(R.id.rContenidoNoticia);

        //Establesco datos en view
        mTitulo.setText(titulo);
        mFecha.setText(fecha);
        mDescripcion.setText(descripcion);
        mContenido.setText(contenido);

        Picasso.get().load(imagen).into(mImagen);
    }

    private ViewHolderNovedades.ClickListener mClickListener;
    //interfaz para enviar devoluciones de llamadas
    public interface ClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

    }
    public void setOnClickListener(ViewHolderNovedades.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
