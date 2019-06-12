package com.panaderia.ricopan;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class GestionActivity extends AppCompatActivity implements
        ProductosFragment.OnFragmentInteractionListener,
        Response.Listener<JSONObject>, Response.ErrorListener, IFragments{

    Button myButton1;
    Button myButton2;
    Button myButton3;
    Button myButton4;
    TextView lineaYellowone;
    TextView lineaYellowtwo;
    TextView lineaYellowthree;
    TextView lineaYellowfour;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private ArrayList<Integer> productosIDs;

    ProductosFragment listaProductosFragment;
    private static final String TAG_LIST_FRAGMENT="TAG_LIST_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);

        myButton1 = (Button)findViewById(R.id.btnPanes);
        myButton2 = (Button)findViewById(R.id.btnPasteles);
        myButton3 = (Button)findViewById(R.id.btnDulceria);
        myButton4 = (Button)findViewById(R.id.btnQueso);
        lineaYellowone = (TextView)findViewById(R.id.lineYellow1);
        lineaYellowtwo = (TextView)findViewById(R.id.lineYellow2);
        lineaYellowthree = (TextView)findViewById(R.id.lineYellow3);
        lineaYellowfour = (TextView)findViewById(R.id.lineYellow4);

        myButton1.setEnabled(false);
        myButton1.setTextColor(Color.parseColor("#000000"));
        lineaYellowone.setBackgroundColor(getResources().getColor(R.color.colorSecondary));

        myButton2.setEnabled(true);
        myButton2.setTextColor(Color.parseColor("#000000"));
        lineaYellowtwo.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));

        myButton3.setEnabled(true);
        myButton3.setTextColor(Color.parseColor("#000000"));
        lineaYellowthree.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));

        myButton4.setEnabled(true);
        myButton4.setTextColor(Color.parseColor("#000000"));
        lineaYellowfour.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));

        Fragment miFragment=new ProductosFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main,miFragment).commit();
    }




    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        Intent volverMain = new Intent(this, MainActivity.class);
        volverMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(volverMain);
    }

}
