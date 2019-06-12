package com.panaderia.ricopan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
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

import java.util.ArrayList;

public class MyProductoRecyclerViewAdapter extends RecyclerView.Adapter<MyProductoRecyclerViewAdapter.ViewHolderProductos> {

    ArrayList<Producto> productos;
    Context contexto;
    private static View.OnClickListener mOnItemClickListener;
    RequestQueue request;

    public MyProductoRecyclerViewAdapter(ArrayList<Producto> productos, Context contexto) {
        this.productos = productos;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public ViewHolderProductos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_producto,
                null,false);
        return new ViewHolderProductos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProductos viewHolderProductos, int position) {
        viewHolderProductos.asignarDatos(productos.get(position));

    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolderProductos extends RecyclerView.ViewHolder {

        TextView titulo;
        ImageView imagen;

        public ViewHolderProductos(@NonNull View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.tvItem);
            imagen = (ImageView) itemView.findViewById(R.id.imagenItem);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        public void asignarDatos(final Producto producto) {
            titulo.setText(producto.getTitle());
            //imagen.setImageBitmap(producto.getImagen2());
            //imagen.setImageBitmap(BitmapFactory.decodeResource(contexto.getResources(), R.mipmap.noimagen));
            request = Volley.newRequestQueue(contexto);
            String url = producto.getImagenStr();
            System.out.println(url);
            if (url.equals("none") == false) {
                System.out.println("REQUEST");
                ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {

                    @Override
                    public void onResponse(Bitmap response) {
                        //producto.setImagen2(response);
                        System.out.println("Antes de Asignar Imagen");
                        imagen.setImageBitmap(response);
                        System.out.println("Asigna Imagen");
                    }
                }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Imagen no recibida");
                        Toast.makeText(contexto, "Error al cargar la imagen",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                request.add(imageRequest);
            }
            else{
                System.out.println("ELSE");
                imagen.setImageBitmap(producto.getImagen2());
            }


        }
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }
}
