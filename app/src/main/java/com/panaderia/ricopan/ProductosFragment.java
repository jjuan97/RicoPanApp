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

    /*
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    */

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerProductos;
    ArrayList<Producto> productos;
    ProgressDialog dialog;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    public ProductosFragment() {
        // Required empty public constructor
    }

    /*
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsultaListaProductosFragment.
     */
    //TODO: Rename and change types and number of parameters
    /*
    public static ConsultaListaProductosFragment newInstance(String param1, String param2) {
        ConsultaListaProductosFragment fragment = new ConsultaListaProductosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productos = new ArrayList<>();
        request = Volley.newRequestQueue(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_producto_list, container, false);
        recyclerProductos = (RecyclerView) vista.findViewById(R.id.idRecyclerProductos);
        //llenar();
        descargarProductos();
        System.out.println("adapter");

        return vista;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        recyclerProductos.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerProductos.setHasFixedSize(true);

        MyProductoRecyclerViewAdapter adapter = new MyProductoRecyclerViewAdapter(productos, getContext());
        recyclerProductos.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);
        adapter.notifyDataSetChanged();
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
            Toast.makeText(getContext(), "You Clicked: " + thisItem.getTitle(), Toast.LENGTH_SHORT).show();

            //Create the intent for navigation purposes; the context is the current Activity, so getActivity() must be called
            //Intent i = new Intent(getActivity(), activityDescription.class);
            //Set information in the intent for the next Activity
            //i.putExtra("nombreLista", thisItem.getTitle());
            //Launch the new Activity
            //startActivity(i);
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

    private void descargarProductos(){
        String url ="http://"+loadPreferences()+"/app/productList.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "NO CONEXION:  "+ error.toString(), Toast.LENGTH_SHORT).show();
        System.out.println("NO CONEXION:  "+ error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("productos");

        try {
            for(int i=0; i<json.length(); i++){
                final Producto producto = new Producto();
                JSONObject jsonObject = json.getJSONObject(i);

                producto.setTitle(jsonObject.optString("pro_nombre"));
                String url = jsonObject.optString("pro_foto");
                if (url.length() > 1){
                    url = "http://"+loadPreferences()+"/"+ url;
                    url = url.replace(" ","%20");
                    producto.setImagenStr(url);
                    //System.out.println(producto.getImagenStr());
                }
                else{
                    producto.setImagenStr("none");
                    //producto.setImagen2(BitmapFactory.decodeResource(getContext().getResources(),
                    //R.mipmap.noimagen));
                    //System.out.println(producto.getImagenStr());

                }
                producto.setImagen2(BitmapFactory.decodeResource(getContext().getResources(),
                        R.mipmap.noimagen));
                productos.add(producto);
                // productosIDs.add(jsonObject.optInt("pro_id"));
            }

        } catch (JSONException e) {
            Toast.makeText(getContext(), "Error leyendo datos", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    //Codigo para obtener la ip configurada
    public String loadPreferences() {
        SharedPreferences ajustesAdmin = this.getActivity().getSharedPreferences("IPdePref", Context.MODE_PRIVATE);
        String IP = ajustesAdmin.getString("IPnueva", "IP no definida");
        return IP;
    }




}
