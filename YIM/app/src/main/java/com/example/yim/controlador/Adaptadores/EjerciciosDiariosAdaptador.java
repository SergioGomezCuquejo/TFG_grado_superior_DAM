package com.example.yim.controlador.Adaptadores;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yim.R;
import com.example.yim.modelo.tablas.TablaDiaRutinaActiva;
import com.example.yim.modelo.tablas.TablaEjercicioActivo;
import com.example.yim.vista.controlador.CambiarActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class EjerciciosDiariosAdaptador extends RecyclerView.Adapter<EjerciciosDiariosAdaptador.EjerciciosRutinasViewHolder> {
    private final TablaDiaRutinaActiva diaRutinaActiva;
    private final ArrayList<TablaEjercicioActivo> ejerciciosActivos;
    private final Context context;

    public EjerciciosDiariosAdaptador(Context context, TablaDiaRutinaActiva diaRutinaActiva) {
        this.context = context;
        this.diaRutinaActiva = diaRutinaActiva;
        ejerciciosActivos = diaRutinaActiva.getEjercicios();
    }

    @NonNull
    @Override
    public EjerciciosRutinasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ejercicios_diarios, parent, false);

        return new EjerciciosRutinasViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EjerciciosRutinasViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TablaEjercicioActivo ejercicioActivo = ejerciciosActivos.get(position);
        StringBuilder musculos = new StringBuilder();
        int seriesRealizadas = ejercicioActivo.getSeries_realizadas();
        int seriesNecesarias = ejercicioActivo.getSeries_necesarias();
        int color;

        holder.ejercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CambiarActivity.cambiar(context, diaRutinaActiva, (position+1));
                ((Activity) context).finish();
            }
        });

        holder.imagen.setImageResource(R.drawable.curl_de_biceps_hombre);
        holder.nombre.setText(ejercicioActivo.getNombre());

        for(String musculo : ejercicioActivo.getMusculos()){
            musculos.append(musculo).append(", ");
        }
        musculos = new StringBuilder(musculos.substring(0, musculos.length() - 2));
        holder.musculos.setText(musculos.toString());


        holder.progreso.setText( seriesRealizadas + "/" + seriesNecesarias);
        if (seriesRealizadas >= seriesNecesarias){
            color = R.color.verde_clarito;
        } else if (seriesRealizadas >= seriesNecesarias/2){
            color = R.color.naranja;
        } else{
            color = R.color.rojo;
        }
        holder.progreso.setTextColor(ContextCompat.getColor(context, color));
    }

    @Override
    public int getItemCount() {
        return ejerciciosActivos.size();
    }

    public static class EjerciciosRutinasViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout ejercicio;
        ShapeableImageView imagen;
        TextView nombre, musculos, progreso;

        public EjerciciosRutinasViewHolder(@NonNull View itemView) {
            super(itemView);
            ejercicio = itemView.findViewById(R.id.ejercicio);

            imagen = itemView.findViewById(R.id.imagen);

            nombre = itemView.findViewById(R.id.nombreTV);
            musculos = itemView.findViewById(R.id.musculos);
            progreso = itemView.findViewById(R.id.progreso);
        }
    }
}
