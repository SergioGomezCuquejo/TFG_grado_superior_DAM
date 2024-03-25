package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yim.R;

public class InicioSesion extends AppCompatActivity implements View.OnClickListener {

    Button iniciar_sesion_bt, registrarse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        //Referencias de las vistas
        iniciar_sesion_bt = findViewById(R.id.iniciar_sesion_bt);
        registrarse = findViewById(R.id.registrarse);

        //Listeners
        iniciar_sesion_bt.setOnClickListener(this);
        registrarse.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iniciar_sesion_bt) {
            cambiarActivity(Inicio.class);

        } else if (id == R.id.registrarse) {
            cambiarActivity(RegistroSesion.class);

        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }
}