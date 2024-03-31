package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.yim.R;

public class Logo extends AppCompatActivity {
    ImageView logo;
    ObjectAnimator alpha, rotacion;
    AnimatorSet animatorSet;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);


        //Referencia de la vista
        logo = findViewById(R.id.logo);


        alpha = ObjectAnimator.ofFloat(logo, View.ALPHA, 1.0f, 0.0f);
        alpha.setDuration(6000);

        rotacion = ObjectAnimator.ofFloat(logo, "rotation", 0f, 720f);
        rotacion.setDuration(10000);

        animatorSet = new AnimatorSet();
        animatorSet.playTogether(alpha, rotacion);
        animatorSet.start();

        //Al cargar ir al inicio de sesión
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cambiar(Logo.this, InicioSesion.class);
            }
        }, 4000);
    }
}