package com.example.bvarg.comercio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Precio extends AppCompatActivity {

    static String idlugar;
    static String idproducto;
    Button agregar;
    EditText precio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precio);

        agregar = (Button) findViewById(R.id.buttonPrecio);
        precio = (EditText) findViewById(R.id.editTextPrecio);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("idproducto",idproducto);
                Log.i("idlugar",idlugar);
                agregarproducto(idproducto, idlugar, precio.getText().toString(), MainActivity.sharedPreferences.getString("token", ""));
            }
        });
    }

    public void agregarproducto(final String id, final String idmarket, final String precio, final String token){
        StringRequest postRequest = null;
        String url = "https://food-manager.herokuapp.com/productsbymarkets";
        postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //sharedPreferences.edit().putString("token", response.substring(96,response.length()-2)).apply();
                        //sharedPreferences.edit().putString("id", response.substring(61,response.length()-277)).apply();
                        Log.i("token",response.toString());
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "fallo");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("productId", id);
                params.put("marketId", idmarket);
                params.put("price", precio);
                params.put("token", token);

                return params;
            }
        };
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        MyRequestQueue.add(postRequest);
    }
}
