package com.example.yim.controlador.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yim.R;
import com.example.yim.modelo.tablas.TablaEjercicioUsuario;

import java.util.ArrayList;


public class PopupAgregarEjercicioAdaptador extends RecyclerView.Adapter<PopupAgregarEjercicioAdaptador.PopupAgregarEjercicioAdaptadorViewHolder> {

    private ArrayList<TablaEjercicioUsuario> ejercicios;

    private Context context;

    public PopupAgregarEjercicioAdaptador(Context context, ArrayList<TablaEjercicioUsuario> ejercicios) {
        this.context = context;
        this.ejercicios = ejercicios;
    }

    @NonNull
    @Override
    public PopupAgregarEjercicioAdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_popup_agregar_ejercicio, parent, false);

        return new PopupAgregarEjercicioAdaptadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopupAgregarEjercicioAdaptadorViewHolder holder, int position) {
        TablaEjercicioUsuario ejercicio = ejercicios.get(position);
        String musculos = "";

        holder.ejercicioRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.imagen.setImageResource(R.drawable.curl_de_biceps_hombre);

        holder.nombreTV.setText(ejercicio.getNombre());

        for(String musculo : ejercicio.getMusculos()){
            musculos += musculo + ", ";
        }
        musculos = musculos.substring(0, musculos.length() - 2);
        holder.musculosTV.setText(musculos);

    }

    @Override
    public int getItemCount() {
        return ejercicios.size();
    }

    public static class PopupAgregarEjercicioAdaptadorViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout ejercicioRL;
        ImageView imagen;
        TextView nombreTV, musculosTV;

        public PopupAgregarEjercicioAdaptadorViewHolder(@NonNull View itemView) {
            super(itemView);
            ejercicioRL = itemView.findViewById(R.id.ejercicio_rl);
            imagen = itemView.findViewById(R.id.imagen);
            nombreTV = itemView.findViewById(R.id.nombre_tv);
            musculosTV = itemView.findViewById(R.id.musculos_tv);
        }
    }
}
