package com.example.recyclerview;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muyinteresanteNoTocar.DescargaNoticiasRSS;
import com.example.muyinteresanteNoTocar.NoticiaRSS;
import com.example.muyinteresanteNoTocar.iNoticiaRSS;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements iNoticiaRSS {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        descargarNoticias();
    }

    @Override
    public void onRecibeNoticiasRSS(ArrayList<NoticiaRSS> listaNoticias) {
        // Obtener ListView principal
        RecyclerView recyclerView_noticias = this.findViewById(R.id.recyclerView);

        NoticiaListAdapter adapter = new NoticiaListAdapter(listaNoticias);

        adapter.setOnNoticiaItemClickListener(new NoticiaListAdapter.onNoticiaItemClickListener() {
            @Override
            public void onItemClick(View view, NoticiaRSS noticia) {
                view.setBackgroundColor(Color.RED);
            }
        });

        recyclerView_noticias.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // Asignar adaptador
        recyclerView_noticias.setAdapter(adapter);

        Log.i("lista de noticias", listaNoticias.toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_actualizar) { // Opcion de menu Actualizar
            descargarNoticias();
        }

        return super.onOptionsItemSelected(item);
    }

    // Se lanza la tarea que descarga, generara la lista de noticias
    // y al finalizar entregara al metodo onRecibeNoticiasRSS
    public void descargarNoticias() {
        new DescargaNoticiasRSS(this, this).execute("http://feeds.feedburner.com/Muyinteresantees?format=xml", NoticiaRSS.RSS_MUY_INTERESANTE);
    }

}
