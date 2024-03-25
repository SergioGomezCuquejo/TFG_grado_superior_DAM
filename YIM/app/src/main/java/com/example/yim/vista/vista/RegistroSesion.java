package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yim.R;

public class RegistroSesion extends AppCompatActivity implements View.OnClickListener {

    Button registrarse_bt, iniciar_sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_sesion);

        //Referencias de las vistas
        registrarse_bt = findViewById(R.id.registrarse_bt);
        iniciar_sesion = findViewById(R.id.iniciar_sesion);

        //Listeners
        registrarse_bt.setOnClickListener(this);
        iniciar_sesion.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.registrarse_bt) {
            cambiarActivity(PreguntasRegistro.class);

        } else if (id == R.id.iniciar_sesion) {
            cambiarActivity(InicioSesion.class);

        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }
}