package com.panaderia.ricopan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ConfigActivity extends AppCompatActivity {

    EditText campoIP;
    TextView campoIPActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        campoIP = (EditText) findViewById(R.id.txtEditIp);
        campoIPActual = (TextView) findViewById(R.id.txtShowIp);

        loadIP();

    }

    public void loadIP() {

        SharedPreferences ajustesAdmin = getSharedPreferences("IPdePref", Context.MODE_PRIVATE);
        String IPnueva = ajustesAdmin.getString("IPnueva","IP no definida");
        campoIPActual.setText(IPnueva);

    }

    public void saveConf (View view){
        SharedPreferences ajustesAdmin = getSharedPreferences("IPdePref", Context.MODE_PRIVATE);
        String IPnueva = campoIP.getText().toString();

        SharedPreferences.Editor editor = ajustesAdmin.edit();
        editor.putString("IPnueva", IPnueva);

        campoIPActual.setText(IPnueva);

        editor.commit();
    }

    public void exitConf (View view){ //agregar una confirmacion
        Intent volverMain = new Intent(this, MainActivity.class);
        volverMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(volverMain);
    }

    @Override
    public void onBackPressed() {
        Intent volverMain = new Intent(this, MainActivity.class);
        volverMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(volverMain);
    }
}
