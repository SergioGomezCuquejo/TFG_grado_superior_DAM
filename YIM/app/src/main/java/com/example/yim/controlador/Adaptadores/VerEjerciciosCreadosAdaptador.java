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
import com.example.yim.modelo.tablas.TablaEjercicioCreado;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.controlador.MostratToast;

import java.lang.reflect.Modifier;
import java.util.ArrayList;


public class VerEjerciciosCreadosAdaptador extends RecyclerView.Adapter<VerEjerciciosCreadosAdaptador.VerEjerciciosViewHolder> {

    private ArrayList<TablaEjercicioCreado> ejercicios;

    private Context context;

    public VerEjerciciosCreadosAdaptador(Context context, ArrayList<TablaEjercicioCreado> ejercicios) {
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
        String musculos, nombre;
        TablaEjercicioCreado ejercicioUsuario = ejercicios.get(position);
        String nombreEjercicio = ejercicioUsuario.getNombre();


        holder.ejercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CambiarActivity.cambiar(context, ejercicioUsuario);
            }
        });

        if(ejercicioUsuario.getImagen() != null){
            Imagenes.mostrarImagen(context, ejercicioUsuario.getImagen(),  holder.imagenIV);
        }else{
            holder.imagenTexto.setVisibility(View.VISIBLE);
            holder.imagenIV.setVisibility(View.INVISIBLE);
            nombre = ejercicioUsuario.getNombre().toUpperCase();
            nombre = nombre.substring(1);
            if (nombre.length() >= 3) {
                nombre = nombre.substring(0, 3);
            }
            holder.imagenTexto.setText(nombre);
        }

        holder.nombre.setText(nombreEjercicio.substring(1));

        musculos = "";
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
        ImageView imagenIV;
        TextView nombre, musculos, imagenTexto;


        public VerEjerciciosViewHolder(@NonNull View itemView) {
            super(itemView);
            ejercicio = itemView.findViewById(R.id.ejercicio);
            imagenIV = itemView.findViewById(R.id.imagen);
            imagenTexto = itemView.findViewById(R.id.imagen_texto);
            nombre = itemView.findViewById(R.id.nombreTV);
            musculos = itemView.findViewById(R.id.musculos);
        }
    }
}
