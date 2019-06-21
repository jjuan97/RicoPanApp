package com.panaderia.ricopan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class ProductosFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TIPO = "tipoProducto";

    private OnFragmentInteractionListener mListener;
    private String tipoProducto;
    private int idUser;

    RecyclerView recyclerProductos;
    ArrayList<Producto> productos;
    ProgressDialog dialog;
    JsonObjectRequest jsonObjectRequest;




    public ProductosFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tipoProducto = getArguments().getString(GestionActivity.TIPO);
            idUser = getArguments().getInt(GestionActivity.IDUSER, 0);
            System.out.println("TIPO: "+tipoProducto);
            //System.out.println("ID : "+ idUser);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista= inflater.inflate(R.layout.fragment_producto_list, container, false);

        productos=new ArrayList<>();

        recyclerProductos = (RecyclerView) vista.findViewById(R.id.idRecyclerProducto);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerProductos.setHasFixedSize(true);

        // request= Volley.newRequestQueue(getContext());

        cargarWebService();

        return vista;
    }

    private void cargarWebService() {
        dialog=new ProgressDialog(getContext());
        dialog.setMessage("Consultando Productos");
        //dialog.show();
        String ip=loadPreferences();

        String url="http://"+ip+"/app/listProducts.php?tipo="+tipoProducto;
        System.out.println(url);
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        Producto producto=null;

        JSONArray json=response.optJSONArray("productos");
        try {
            for (int i=0;i<json.length();i++){
                producto=new Producto();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);

                producto.setTitle(jsonObject.optString("pro_nombre"));
                producto.setImagenStr(jsonObject.optString("pro_foto"));
                producto.setId(jsonObject.optInt("pro_id"));
                productos.add(producto);
            }
            //dialog.hide();
            MyProductoRecyclerViewAdapter adapter=new MyProductoRecyclerViewAdapter(productos, getContext());
            recyclerProductos.setAdapter(adapter);
            adapter.setOnItemClickListener(onItemClickListener);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();
            //dialog.hide();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "NO SE PUDO CONECTAR "+ error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        //dialog.hide();
        Log.d("ERROR: ", error.toString());
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Step 4 of 4: Finally call getTag() on the view.
            // This viewHolder will have all required values.
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            // viewHolder.getItemId();
            // viewHolder.getItemViewType();
            // viewHolder.itemView;
            Producto thisItem = productos.get(position);
            //Toast.makeText(getContext(), "You Clicked: " + thisItem.getDetails(), Toast.LENGTH_SHORT).show();
            //Create the intent for navigation purposes; the context is the current Activity, so getActivity() must be called
            Intent i = new Intent(getActivity(), EdicionActivity.class);
            //Set information in the intent for the next Activity
            i.putExtra("IDProducto", thisItem.getId());
            i.putExtra("nombreProducto", thisItem.getTitle());
            i.putExtra("IDUser", idUser);
            i.putExtra("Tipo",tipoProducto);
            //Launch the new Activity
            startActivity(i);
        }
    };


    private void llenar() {
        productos.add(new Producto("Producto 1", BitmapFactory.decodeResource(getContext().getResources(),
                R.mipmap.noimagen)));
        productos.add(new Producto("Producto 2", BitmapFactory.decodeResource(getContext().getResources(),
                R.mipmap.noimagen)));
        productos.add(new Producto("Producto 3", BitmapFactory.decodeResource(getContext().getResources(),
                R.mipmap.noimagen)));
        productos.add(new Producto("Producto 4", BitmapFactory.decodeResource(getContext().getResources(),
                R.mipmap.noimagen)));
    }

    //Codigo para obtener la ip configurada
    public String loadPreferences() {
        SharedPreferences ajustesAdmin = this.getActivity().getSharedPreferences("IPdePref", Context.MODE_PRIVATE);
        String IP = ajustesAdmin.getString("IPnueva", "IP no definida");
        return IP;
    }

}
