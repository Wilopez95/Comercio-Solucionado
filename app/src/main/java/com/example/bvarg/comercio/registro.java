package com.example.bvarg.comercio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class registro extends AppCompatActivity {

    Button registro;
    EditText correo;
    EditText password;
    EditText password2;
    EditText telefono;
    EditText nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        correo = (EditText) findViewById(R.id.editTextCorreo);
        password = (EditText) findViewById(R.id.editTextContrasena);
        password2 = (EditText) findViewById(R.id.editTextContrasena2);
        telefono = (EditText) findViewById(R.id.editTextTelefono);
        nombre = (EditText) findViewById(R.id.editTextNombre);

        registro = (Button) findViewById(R.id.buttonRegistrar);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!correo.getText().toString().equals("") && !password.getText().toString().equals("") && !password2.getText().toString().equals("") && !telefono.getText().toString().equals("") && !nombre.getText().toString().equals("")){
                    if(password.getText().toString().equals(password2.getText().toString())){
                        registrar(correo.getText().toString(), password.getText().toString(),nombre.getText().toString(),telefono.getText().toString());
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Las dos contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Se debe llenar toda la informacion", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registrar(final String email, final String password, final String name, final String phone){
        StringRequest postRequest = null;
        String url = "https://food-manager.herokuapp.com/users/signup";
        postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        Toast.makeText(getApplicationContext(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "No se pudo completar el registro", Toast.LENGTH_SHORT).show();
                        Log.d("Error.Response", "fallo");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                params.put("name", name);
                params.put("phone", phone);
                params.put("type", "1");

                return params;
            }
        };
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        MyRequestQueue.add(postRequest);
    }
}
