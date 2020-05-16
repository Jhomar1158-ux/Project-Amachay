package com.example.amachay.includes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amachay.R;
import com.example.amachay.models.Novedades;
import com.example.amachay.models.Novedades2;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VideosPage extends Fragment {


    private LinearLayoutManager mLinearLayoutManager;

    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabaseReference;

    private FirebaseRecyclerAdapter<Novedades2,ViewHolder2Novedades> firebaseRecyclerAdapter;
    private FirebaseRecyclerOptions<Novedades2> options;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.videos, container, false);

        mLinearLayoutManager = new LinearLayoutManager(getContext());//cambie this por getContext(porq es fragmento)
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        mRecyclerView = view.findViewById(R.id.recyclerviewGaleria);//añadi view

        //Envio consulta a FirebaseDatabase
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("Galeria");// referencia en firebase

        onStart();
        showData();

        return view;// necesario en fragmento
    }

    private void showData() {

        options = new FirebaseRecyclerOptions.Builder<Novedades2>().setQuery(mDatabaseReference, Novedades2.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Novedades2, ViewHolder2Novedades>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder2Novedades holder, int position, @NonNull Novedades2 model) {

                //añadi getActivity() antes era getcontext
                //establece los objetos en ViewHolder2
                holder.setDetalles(getActivity(), model.getTitulo(), model.getImagen());// establecer
            }

            @NonNull
            @Override
            //devuelve el ViewHolder2 seteado
            public ViewHolder2Novedades onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row2_novedades, parent, false);

                return new ViewHolder2Novedades(itemView);
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


