package com.example.yim.vista.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.yim.R;

public class PoliticasPrivacidad extends AppCompatActivity implements View.OnClickListener {

    // Variables de instancias.
    ImageView atrasIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politicas_privacidad);

        //Referencias de las vistas.
        atrasIV = findViewById(R.id.atras_iv);

        //Listeners.
        atrasIV.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());

        if (id.equals("atras_iv")) {
            finish();
        }
    }
}