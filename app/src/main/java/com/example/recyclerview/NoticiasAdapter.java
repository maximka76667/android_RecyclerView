package com.example.recyclerview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.muyinteresanteNoTocar.AsignaImagenDeURL;
import com.example.muyinteresanteNoTocar.NoticiaRSS;

import java.util.ArrayList;
import java.util.List;

public class NoticiasAdapter extends ArrayAdapter<NoticiaRSS> {

    protected Activity activity;
    protected int layoutId;
    protected ArrayList<NoticiaRSS> noticias;

    public NoticiasAdapter(@NonNull Context context, int resource, @NonNull List<NoticiaRSS> objects) {
        super(context, resource, objects);
        this.activity = (Activity) context;
        this.layoutId = resource;
        this.noticias = (ArrayList<NoticiaRSS>) objects;
    }

    @SuppressLint("NewApi")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        References refs;

        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(layoutId, null);

            refs = new References();

            refs.textView_titulo = convertView.findViewById(R.id.titulo);
            refs.textView_fecha = convertView.findViewById(R.id.fecha);
            refs.textView_descripcion = convertView.findViewById(R.id.descripcion);

            convertView.setTag(refs);
        } else {
            refs = (References) convertView.getTag();
            refs.tarea.cancel(true);
        }

        NoticiaRSS noticia = noticias.get(position);

        refs.textView_titulo.setText(noticia.getTitulo());
        refs.textView_fecha.setText(noticia.getFechaComoStringFormateado());
        refs.textView_descripcion.setText(noticia.getDescripcion());

        refs.tarea = new AsignaImagenDeURL(convertView.findViewById(R.id.imagen), activity);
        refs.tarea.execute(noticia.getUrlImagen());

        return convertView;
    }

    static class References {
        TextView textView_titulo;
        TextView textView_fecha;
        TextView textView_descripcion;
        AsignaImagenDeURL tarea;
    }
}
