package com.panaderia.ricopan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EdicionBandejasActivity extends AppCompatActivity {

    TextView nombrePan;
    private int idProducto;
    private String nombreProducto;
    private int idUser;
    private String tipoProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_bandejas);

        nombrePan=(TextView) findViewById(R.id.txt_datos_traidos_pan2);

        Intent datos = getIntent();
        idProducto = datos.getIntExtra("IDProducto", 0);
        nombreProducto = datos.getStringExtra("nombreProducto");
        idUser = datos.getIntExtra("IDUser", 0);
        tipoProducto = datos.getStringExtra("Tipo");

        System.out.println("Edicion\nIDProducto: "+ idProducto +"\nIDUser: "+idUser+"\nNombreProducto: "+nombreProducto);
        nombrePan.setText(nombreProducto);
    }

    public void PasarAunidad (View view){
        Intent pasar2 = new Intent(this, EdicionActivity.class);
        pasar2.putExtra("IDProducto", idProducto);
        pasar2.putExtra("nombreProducto", nombreProducto);
        pasar2.putExtra("IDUser", idUser);
        pasar2.putExtra("Tipo",tipoProducto);
        startActivity(pasar2);
    }

    @Override
    public void onBackPressed() {
        Intent volver = new Intent(this, EdicionActivity.class);
        volver.putExtra("IDProducto", idProducto);
        volver.putExtra("nombreProducto", nombreProducto);
        volver.putExtra("IDUser",idUser);
        volver.putExtra("Tipo",tipoProducto);
        startActivity(volver);
    }
}
