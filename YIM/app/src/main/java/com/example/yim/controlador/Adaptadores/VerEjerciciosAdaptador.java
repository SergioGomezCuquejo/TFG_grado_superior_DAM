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
import com.example.yim.modelo.tablas.TablaEjerciciosUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.vista.PopupVerEjercicios;

import java.util.ArrayList;


public class VerEjerciciosAdaptador extends RecyclerView.Adapter<VerEjerciciosAdaptador.VerEjerciciosViewHolder> {

    private ArrayList<TablaEjerciciosUsuario> ejercicios;

    private Context context;

    public VerEjerciciosAdaptador(Context context, ArrayList<TablaEjerciciosUsuario> ejercicios) {
        this.context = context;
        this.ejercicios = ejercicios;
    }

    @NonNull
    @Override
    public VerEjerciciosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ver_ejercicios, parent, false);

        return new VerEjerciciosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerEjerciciosViewHolder holder, int position) {
        String musculos = "";
        TablaEjerciciosUsuario ejercicioUsuario = ejercicios.get(position);

        holder.ejercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CambiarActivity.cambiar(context, ejercicioUsuario);
            }
        });

        holder.imagen.setImageResource(R.drawable.curl_de_biceps_hombre);
        holder.nombre.setText(ejercicioUsuario.getNombre());

        for(String musculo : ejercicioUsuario.getMusculos()){
            musculos += musculo + ", ";
        }
        musculos = musculos.substring(0, musculos.length() - 2);

        holder.musculos.setText(musculos);
    }

    @Override
    public int getItemCount() {
        return ejercicios.size();
    }

    public static class VerEjerciciosViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout ejercicio;
        ImageView imagen;
        TextView nombre, musculos;

        public VerEjerciciosViewHolder(@NonNull View itemView) {
            super(itemView);
            ejercicio = itemView.findViewById(R.id.ejercicio);
            imagen = itemView.findViewById(R.id.imagen);
            nombre = itemView.findViewById(R.id.nombre);
            musculos = itemView.findViewById(R.id.musculos);
        }
    }
}
