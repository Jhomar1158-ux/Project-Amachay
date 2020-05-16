package com.example.amachay.includes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amachay.R;
import com.example.amachay.activities.Novedades.PostActivity;
import com.example.amachay.models.Novedades;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class GlobalPage extends Fragment {
    private LinearLayoutManager mLinearLayoutManager;

    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabaseReference;

    private FirebaseRecyclerAdapter<Novedades,ViewHolderNovedades> firebaseRecyclerAdapter;
    private FirebaseRecyclerOptions<Novedades> options;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.global, container, false);

        mLinearLayoutManager = new LinearLayoutManager(getContext());//cambie this por getContext
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        mRecyclerView = view.findViewById(R.id.recyclerView);//añadi view

        //Envio consulta a FirebaseDatabase
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();//cambie esto
        mDatabaseReference = mFirebaseDatabase.getReference("NoticiasGlobales");// referencia en firebase

        onStart();
        showData();

        return view;// necesario en fragmento
    }
    private void showData() {

        options = new FirebaseRecyclerOptions.Builder<Novedades>().setQuery(mDatabaseReference, Novedades.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Novedades, ViewHolderNovedades>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderNovedades holder, int position, @NonNull Novedades model) {

                //establece los objetos en ViewHolder
                //añadi getActivity() antes era getcontext
                holder.setDetails(getActivity(), model.getTitulo(), model.getImagen(), model.getFecha(), model.getDescripcion(),
                        model.getContenido());// establecer

            }

            @NonNull
            @Override
            //devuelve el ViewHolder seteado
            public ViewHolderNovedades onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_novedades, parent, false);

                ViewHolderNovedades viewHolder = new ViewHolderNovedades(itemView);

                viewHolder.setOnClickListener(new ViewHolderNovedades.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        //views
                        TextView mTituloTv = view.findViewById(R.id.rTituloNoticia);
                        TextView mFechaTv = view.findViewById(R.id.rFechaNoticia);
                        ImageView mImagenIv = view.findViewById(R.id.rImagenNoticia);
                        TextView mDescripcionTv = view.findViewById(R.id.rDescripcionNoticia);
                        TextView mContenidoTv = view.findViewById(R.id.rContenidoNoticia);

                        //obtengo datos de view
                        String mmTitulo = mTituloTv.getText().toString();
                        String mmFecha = mFechaTv.getText().toString();

                        Drawable mmImagen = mImagenIv.getDrawable();
                        Bitmap mBitmap = ((BitmapDrawable)mmImagen).getBitmap();

                        String mmDescripcion = mDescripcionTv.getText().toString();
                        String mmContenido = mContenidoTv.getText().toString();

                        //pasar data a la nueva actividad
                        Intent intent = new Intent(view.getContext(), PostActivity.class);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] bytes = stream.toByteArray();

                        intent.putExtra("Imagen", bytes); //poner bitmap imagen como array de bytes
                        intent.putExtra("Titulo", mmTitulo); //poner titulo
                        intent.putExtra("Fecha", mmFecha);//
                        intent.putExtra("Descripcion", mmDescripcion);
                        intent.putExtra("Contenido", mmContenido);

                        startActivity(intent); //star activity

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                        Toast.makeText(getContext(), "Noticia", Toast.LENGTH_SHORT).show();//cambie mainactivitv... por get context

                    }
                });
                return viewHolder;
            }
        };

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public void onStart(){

        super.onStart();

        if (firebaseRecyclerAdapter != null){
            firebaseRecyclerAdapter.startListening();
        }

    }

}





