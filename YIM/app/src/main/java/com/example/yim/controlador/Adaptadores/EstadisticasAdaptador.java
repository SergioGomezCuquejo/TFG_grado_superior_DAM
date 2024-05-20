package com.example.yim.controlador.Adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yim.R;
import com.example.yim.modelo.tablas.TablaEjerciciosUsuario;
import com.example.yim.modelo.tablas.TablaEstadisticas;

import java.util.ArrayList;
import java.util.HashMap;


public class EstadisticasAdaptador extends RecyclerView.Adapter<EstadisticasAdaptador.EstadisticasViewHolder> {
    private ArrayList<TablaEjerciciosUsuario> ejerciciosUsuarios;
    private Context context;
    HashMap<String, String> musculosHM;

    public EstadisticasAdaptador(Context context, ArrayList<TablaEjerciciosUsuario> ejerciciosUsuarios, HashMap<String, String> musculosHM) {
        this.context = context;
        this.ejerciciosUsuarios = ejerciciosUsuarios;
        this.musculosHM = musculosHM;
    }

    @NonNull
    @Override
    public EstadisticasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_estadisticas, parent, false);

        return new EstadisticasViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EstadisticasViewHolder holder, int position) {
        TablaEjerciciosUsuario ejercicio = ejerciciosUsuarios.get(position);
        ArrayList<TablaEstadisticas> estadisticas = ejercicio.getEstadisticas();
        holder.nombre.setText(ejercicio.getNombre());

        for(int i = 0; i < ejercicio.getMusculos().size(); i++ ){
            if(i == 0){
                if(musculosHM.containsKey(ejercicio.getMusculos().get(i))){
                    holder.musculo1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(musculosHM.get(ejercicio.getMusculos().get(i)))));
                }
            } else if (i == 1) {
                holder.musculo2.setVisibility(View.VISIBLE);
                if(musculosHM.containsKey(ejercicio.getMusculos().get(i))){
                    holder.musculo2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(musculosHM.get(ejercicio.getMusculos().get(i)))));
                }
            } else {
                holder.musculo3.setVisibility(View.VISIBLE);
                if(musculosHM.containsKey(ejercicio.getMusculos().get(i))){
                    holder.musculo2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(musculosHM.get(ejercicio.getMusculos().get(i)))));
                }
            }
        }

        int peso1 = estadisticas.get(0).getPeso();
        int peso2 = estadisticas.get(1).getPeso();
        int peso3 = estadisticas.get(2).getPeso();
        int pesoMax = Math.max(peso1, Math.max(peso2, peso3));
        if (peso1 == pesoMax){
            holder.ll1.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.rojo_clarito));
        } else if (peso2 == pesoMax) {
            holder.ll2.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.rojo_clarito));
        } else {
            holder.ll3.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.rojo_clarito));
        }



        holder.semana1.setText("Semana " + estadisticas.get(0).getSemana());
        holder.semana2.setText("Semana " + estadisticas.get(1).getSemana());
        holder.semana3.setText("Semana " + estadisticas.get(2).getSemana());

        holder.peso1.setText(String.valueOf(peso1));
        holder.peso2.setText(String.valueOf(peso2));
        holder.peso3.setText(String.valueOf(peso3));

        holder.repeticiones1.setText(String.valueOf(estadisticas.get(0).getRepeticiones()));
        holder.repeticiones2.setText(String.valueOf(estadisticas.get(1).getRepeticiones()));
        holder.repeticiones3.setText(String.valueOf(estadisticas.get(2).getRepeticiones()));
    }

    @Override
    public int getItemCount() {
        return ejerciciosUsuarios.size();
    }

    public static class EstadisticasViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, semana1, semana2, semana3, peso1, peso2, peso3, repeticiones1, repeticiones2, repeticiones3;
        View musculo1, musculo2, musculo3;
        LinearLayout ll1, ll2, ll3;

        public EstadisticasViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreTV);

            musculo1 = itemView.findViewById(R.id.musculo1);
            musculo2 = itemView.findViewById(R.id.musculo2);
            musculo3 = itemView.findViewById(R.id.musculo3);

            ll1 = itemView.findViewById(R.id.ll1);
            ll2 = itemView.findViewById(R.id.ll2);
            ll3 = itemView.findViewById(R.id.ll3);

            semana1 = itemView.findViewById(R.id.semana1);
            semana2 = itemView.findViewById(R.id.semana2);
            semana3 = itemView.findViewById(R.id.semana3);
            peso1 = itemView.findViewById(R.id.peso1);
            peso2 = itemView.findViewById(R.id.peso2);
            peso3 = itemView.findViewById(R.id.peso3);
            repeticiones1 = itemView.findViewById(R.id.repeticiones1);
            repeticiones2 = itemView.findViewById(R.id.repeticiones2);
            repeticiones3 = itemView.findViewById(R.id.repeticiones3);


        }
    }
}
