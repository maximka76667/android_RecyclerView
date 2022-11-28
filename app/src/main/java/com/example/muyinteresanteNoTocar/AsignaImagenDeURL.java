package com.example.muyinteresanteNoTocar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class AsignaImagenDeURL extends AsyncTask<String,Void,Void> {
	ImageView img;
	Bitmap mapaDeBits;
	File f;
	Context contexto;
	
	public AsignaImagenDeURL(ImageView img, Context c){
		this.img =img;
		contexto = c;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		img.setImageBitmap(null);
		mapaDeBits = null;
		f=null;
	}

	@Override						// Recibe la url de la imagen a descargar
	protected Void doInBackground(String... param) {
        try {
            URL url = new URL(param[0]);
			f =Utilidades.getDiretorioCache(contexto);
            if (f!=null) {  										
            	f = new File(f,"imagenes");
            	f.mkdirs(); // Crea las carpetas
     
            	// hasCode proporciona un valor numerico unico dado el texto de un URL, que a su vez es unico
            	f = new File(f,String.valueOf(url.hashCode()));
            
	            if (f.exists()){
	            	mapaDeBits = BitmapFactory.decodeStream(new FileInputStream(f));
	            }
	            else {
		            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
		            conexion.connect();
		            InputStream entrada = conexion.getInputStream();
		            mapaDeBits = BitmapFactory.decodeStream(entrada);
					if (mapaDeBits!=null) {
						try { // Guardamos la imagen para evitar posteriores descargas
							mapaDeBits.compress(CompressFormat.PNG, 95, new FileOutputStream(f));
						} catch (Exception e) { e.printStackTrace(); try {f.delete();} catch(Exception ex){} }
					}
		            entrada.close();
		        }
            }
            else {
				mapaDeBits = null;
			}

        } catch (Exception e) {
        	e.printStackTrace();
        	mapaDeBits = null;
        }
        
        return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		
		 img.setImageBitmap(mapaDeBits);

	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		
		 if (f!=null && f.exists()) {
			 try {f.delete();} catch(Exception ex){} 
		  }
	}
	
	


}
