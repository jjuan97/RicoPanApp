package com.panaderia.ricopan;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.TextView;

public class EdicionActivity extends AppCompatActivity {

    TextView nombrePan;
    private int idProducto;
    private String nombreProducto;
    private int idUser;
    private String tipoProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion);

        nombrePan=(TextView) findViewById(R.id.txt_datos_traidos_pan);

        Intent datos = getIntent();
        idProducto = datos.getIntExtra("IDProducto", 0);
        nombreProducto = datos.getStringExtra("nombreProducto");
        idUser = datos.getIntExtra("IDUser", 0);
        tipoProducto = datos.getStringExtra("Tipo");

        System.out.println("Edicion\nIDProducto: "+ idProducto +"\nIDUser: "+idUser+"\nNombreProducto: "+nombreProducto);
        nombrePan.setText(nombreProducto);
    }

    public void PasarAbandeja (View view){
        Intent pasar1 = new Intent(this, EdicionBandejasActivity.class);
        pasar1.putExtra("IDProducto", idProducto);
        pasar1.putExtra("nombreProducto", nombreProducto);
        pasar1.putExtra("IDUser", idUser);
        pasar1.putExtra("Tipo",tipoProducto);
        startActivity(pasar1);
    }
    @Override
    public void onBackPressed() {
        Intent volver = new Intent(this, GestionActivity.class);
        volver.putExtra("IDUser",idUser);
        startActivity(volver);
    }

    public void registrarProduction(View view){

    }
}
