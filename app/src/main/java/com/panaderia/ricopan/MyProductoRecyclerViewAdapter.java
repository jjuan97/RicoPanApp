package com.panaderia.ricopan;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;
import java.util.ArrayList;

public class MyProductoRecyclerViewAdapter extends RecyclerView.Adapter<MyProductoRecyclerViewAdapter.ViewHolderProductos> {

    ArrayList<Producto> productos;
    Context context;
    private View.OnClickListener mOnItemClickListener;

    public MyProductoRecyclerViewAdapter(ArrayList<Producto> productos, Context context) {
        this.productos = productos;
        this.context = context;
    }


    @Override
    public ViewHolderProductos onCreateViewHolder(ViewGroup parent, int i) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_producto,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new ViewHolderProductos(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolderProductos holder, int position) {
        holder.titulo.setText(productos.get(position).getTitle().toString());

        if (productos.get(position).getImagenStr()!=null){
            //
            cargarImagenWebService(productos.get(position).getImagenStr(),holder);
        }else{
            holder.imagen.setImageResource(R.drawable.noimagen);
        }
    }

    private void cargarImagenWebService(String rutaImagen, final ViewHolderProductos holder) {

        //String ip=context.getString(R.string.ip);
        String ip=loadPreferences();
        //System.out.println(ip);

        String urlImagen="http://"+ip+"/"+rutaImagen;
        //System.out.println(urlImagen);
        urlImagen=urlImagen.replace(" ","%20");

        ImageRequest imageRequest=new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.imagen.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"Error al cargar la imagen",Toast.LENGTH_SHORT).show();
                holder.imagen.setImageResource(R.drawable.noimagen);
            }
        });
        //request.add(imageRequest);
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(imageRequest);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolderProductos extends RecyclerView.ViewHolder {
        TextView titulo;
        ImageView imagen;

        public ViewHolderProductos(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.idNombre);
            imagen = (ImageView) itemView.findViewById(R.id.idImagen);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }


    public String loadPreferences() {
        SharedPreferences ajustesAdmin = context.getSharedPreferences("IPdePref", Context.MODE_PRIVATE);
        String IP = ajustesAdmin.getString("IPnueva", "IP no definida");
        return IP;
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }
}
