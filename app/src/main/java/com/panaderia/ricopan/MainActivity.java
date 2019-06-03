package com.panaderia.ricopan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>,
        Response.ErrorListener {

    private Spinner sp1;
    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;
    private List<Integer> idUsers = new ArrayList<>();
    private List<String>  nameUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onStart(){
        super.onStart();
        setContentView(R.layout.activity_main);
        idUsers = new ArrayList<>();
        nameUsers = new ArrayList<>();
        request = Volley.newRequestQueue(this);

        nameUsers.add(0,"Lista de Nombres");
        sp1 = (Spinner)findViewById(R.id.spinner);
        cargarUsuarios();

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(parent.getItemAtPosition(position).toString().equals("Lista de Nombres")){
                    Toast.makeText(parent.getContext(), "Seleccione un Nombre!", Toast.LENGTH_SHORT).show();
                }
                else{
                    //NOTHING
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void cargarUsuarios() {

        String url = "http://"+loadPreferences()+"/app/showUsers.php";
        System.out.println(url);
        try {
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        }catch (Exception e){
            Intent passToConf = new Intent(this, ConfigActivity.class);
            startActivity(passToConf);
        }
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("RESPONSE:  "+error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("usuarios");

        try {
            for(int i=0; i<json.length(); i++){
                JSONObject jsonObject = json.getJSONObject(i);
                nameUsers.add( jsonObject.optString("nombre") + " " + jsonObject.optString("apellidos") );
                idUsers.add( jsonObject.optInt("personal_id") );
            }
            ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, nameUsers);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp1.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void entrar(View view){
        sp1 = (Spinner)findViewById(R.id.spinner);
        String  nombre= sp1.getSelectedItem().toString();

        if(nombre.equals("Lista de Nombres")){
            Toast.makeText(this, "Seleccione un Nombre!", Toast.LENGTH_SHORT).show();
        }
        else{
            int sp1Position = sp1.getSelectedItemPosition();
            int idUserSelected = idUsers.get(sp1Position - 1);
            String message1 = "ID: " + Integer.toString(idUserSelected) + " - " + nombre;
            Toast.makeText(this, message1, Toast.LENGTH_SHORT).show();

            //Intent aceptar = new Intent(this,activityManagement.class);
            //aceptar.putExtra("idUser", idUserSelected);
            //aceptar.putExtra("nameUser", nombre);
            //startActivity(aceptar);
        }

    }

    public void entrarConf(View view){
        Intent passToConf = new Intent(this, ConfigActivity.class);
        startActivity(passToConf);
    }

    public String loadPreferences() {
        SharedPreferences ajustesAdmin = getSharedPreferences("IPdePref", Context.MODE_PRIVATE);
        String IP = ajustesAdmin.getString("IPnueva", "192.168.1.1");
        return IP;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
