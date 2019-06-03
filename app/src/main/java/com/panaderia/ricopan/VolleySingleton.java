package com.panaderia.ricopan;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

    private static VolleySingleton instaciaVolley;
    private RequestQueue request;
    public static Context contexto;

    private VolleySingleton(Context context) {
        contexto = contexto;
        request = getRequest();
    }

    public static synchronized VolleySingleton getInstaciaVolley(Context contexto) {
        if (instaciaVolley == null){
            instaciaVolley = new VolleySingleton(contexto);
        }
        return instaciaVolley;
    }

    public RequestQueue getRequest() {
        if (request == null){
            request = Volley.newRequestQueue(contexto);
        }
        return request;
    }

    public <T> void addToRequestQueue(Request<T> req){
        getRequest().add(req);
    }
   
}
