package com.example.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muyinteresanteNoTocar.NoticiaRSS;

import java.util.ArrayList;

public class NoticiaListAdapter extends RecyclerView.Adapter<NoticiaListAdapter.ViewHolderNoticias> {
    private ArrayList<NoticiaRSS> noticias;
    private ArrayList<NoticiaRSS> noticiasFiltradas;
    private onNoticiaItemClickListener listener;

    public NoticiaListAdapter(ArrayList<NoticiaRSS> noticias) {
        this.noticias = noticias;
    }

    interface  onNoticiaItemClickListener {
        void onItemClick(View view, NoticiaRSS noticia);
    }

    public void setOnNoticiaItemClickListener(onNoticiaItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolderNoticias extends RecyclerView.ViewHolder {

        public TextView textView_titulo;

        public ViewHolderNoticias(View itemView) {
            super(itemView);
            this.textView_titulo = itemView.findViewById(R.id.titulo);
        }
    }

    @NonNull
    @Override
    public ViewHolderNoticias onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noticia, parent, false);
        ViewHolderNoticias holder = new ViewHolderNoticias(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNoticias holder, int position) {
        NoticiaRSS noticia = noticias.get(position);
        holder.textView_titulo.setText(noticia.getTitulo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, noticia);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }
}
