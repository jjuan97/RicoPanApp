package com.panaderia.ricopan;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
        ProductosFragment.OnFragmentInteractionListener, IFragments{

    Button myButton1, myButton2, myButton3, myButton4, currentButton;
    TextView lineaYellowone, lineaYellowtwo, lineaYellowthree, lineaYellowfour, currentLine;
    private ArrayList<Integer> productosIDs;
    private int idUser;

    Fragment listaPanPeqFragment,
            listaPanGrandeFragment,
            listaDulceriaFragment,
            listaQuesoFragment,
            currentFragment;

    public static final String TIPO="tipoProducto";
    public static final String IDUSER="IDUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);

        Intent data= getIntent();
        idUser= data.getIntExtra("IDUser", 0);
        System.out.println("ID User - "+idUser);

        myButton1 = (Button)findViewById(R.id.btnPanes);
        myButton2 = (Button)findViewById(R.id.btnGrandes);
        myButton3 = (Button)findViewById(R.id.btnDulceria);
        myButton4 = (Button)findViewById(R.id.btnQueso);
        lineaYellowone = (TextView)findViewById(R.id.lineYellow1);
        lineaYellowtwo = (TextView)findViewById(R.id.lineYellow2);
        lineaYellowthree = (TextView)findViewById(R.id.lineYellow3);
        lineaYellowfour = (TextView)findViewById(R.id.lineYellow4);

        currentButton = myButton1;
        currentLine = lineaYellowone;
        currentButton.setEnabled(false);
        currentButton.setTextColor(getResources().getColor(R.color.textoSelect));
        currentLine.setBackgroundColor(getResources().getColor(R.color.colorSecondary));

        listaPanPeqFragment=new ProductosFragment();
        Bundle bundle= new Bundle();
        bundle.putString(TIPO,"6");
        bundle.putInt(IDUSER,idUser);
        listaPanPeqFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.content_main,listaPanPeqFragment).commit();
        currentFragment = listaPanPeqFragment;
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

    public void changeToPan(View view){
        //getSupportFragmentManager().beginTransaction().remove(listaPanPeqFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();

        if(listaPanPeqFragment == null){
            listaPanPeqFragment= new ProductosFragment();
            Bundle bundle= new Bundle();
            bundle.putString(TIPO,"6");
            bundle.putInt(IDUSER,idUser);
            listaPanPeqFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.content_main,listaPanPeqFragment).commit();
            currentFragment = listaPanPeqFragment;
        }
        else{
            getSupportFragmentManager().beginTransaction().show(listaPanPeqFragment).commit();
            currentFragment = listaPanPeqFragment;
        }
        changeColors(myButton1, lineaYellowone);
    }

    public void changeToPanGrande(View view){
        //getSupportFragmentManager().beginTransaction().remove(listaPanPeqFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();

        if(listaPanGrandeFragment == null){
            listaPanGrandeFragment= new ProductosFragment();
            Bundle bundle= new Bundle();
            bundle.putString(TIPO,"5");
            bundle.putInt(IDUSER,idUser);
            listaPanGrandeFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.content_main,listaPanGrandeFragment).commit();
            currentFragment = listaPanGrandeFragment;
        }
        else{
            getSupportFragmentManager().beginTransaction().show(listaPanGrandeFragment).commit();
            currentFragment = listaPanGrandeFragment;
        }
        changeColors(myButton2, lineaYellowtwo);
    }

    public void changeToDulceria(View view){
        //getSupportFragmentManager().beginTransaction().remove(listaPanPeqFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();

        if(listaDulceriaFragment == null){
            listaDulceriaFragment= new ProductosFragment();
            Bundle bundle= new Bundle();
            bundle.putString(TIPO,"4");
            bundle.putInt(IDUSER,idUser);
            listaDulceriaFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.content_main,listaDulceriaFragment).commit();
            currentFragment = listaDulceriaFragment;
        }
        else{
            getSupportFragmentManager().beginTransaction().show(listaDulceriaFragment).commit();
            currentFragment = listaDulceriaFragment;
        }
        changeColors(myButton3, lineaYellowthree);
    }


    public void changeToPanQueso(View view) {
        //getSupportFragmentManager().beginTransaction().remove(listaPanPeqFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();

        if(listaQuesoFragment == null){
            listaQuesoFragment= new ProductosFragment();
            Bundle bundle= new Bundle();
            bundle.putString(TIPO,"3");
            bundle.putInt(IDUSER,idUser);
            listaQuesoFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.content_main,listaQuesoFragment).commit();
            currentFragment = listaQuesoFragment;
        }
        else{
            getSupportFragmentManager().beginTransaction().show(listaQuesoFragment).commit();
            currentFragment = listaQuesoFragment;
        }
        changeColors(myButton4, lineaYellowfour);
    }

    public void changeColors(Button thisButton, TextView thisLine){
        currentButton.setEnabled(true);
        currentButton.setTextColor(Color.parseColor("#000000"));
        currentLine.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));

        thisButton.setEnabled(false);
        thisButton.setTextColor(getResources().getColor(R.color.textoSelect));
        thisLine.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
        currentButton = thisButton;
        currentLine = thisLine;
    }
}
