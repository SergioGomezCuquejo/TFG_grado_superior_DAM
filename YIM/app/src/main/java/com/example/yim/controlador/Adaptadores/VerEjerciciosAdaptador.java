package com.example.yim.controlador.Adaptadores;

import static androidx.browser.customtabs.CustomTabsClient.getPackageName;

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
import com.example.yim.modelo.tablas.TablaEjercicioPorDefecto;
import com.example.yim.vista.controlador.CambiarActivity;

import java.util.ArrayList;


public class VerEjerciciosAdaptador extends RecyclerView.Adapter<VerEjerciciosAdaptador.VerEjerciciosViewHolder> {

    private ArrayList<TablaEjercicioPorDefecto> ejercicios;

    private Context context;

    public VerEjerciciosAdaptador(Context context, ArrayList<TablaEjercicioPorDefecto> ejercicios) {
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
        TablaEjercicioPorDefecto ejercicioUsuario = ejercicios.get(position);
        String nombreEjercicio = ejercicioUsuario.getNombre();


        holder.ejercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CambiarActivity.cambiar(context, ejercicioUsuario);
            }
        });

        if(ejercicioUsuario.getImagen() != null){
            holder.imagen.setImageResource(context.getResources().getIdentifier(ejercicioUsuario.getImagen(), "drawable", context.getPackageName()));
        }
        holder.nombre.setText(nombreEjercicio);

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
        TextView nombre, musculos, imagenTexto;


        public VerEjerciciosViewHolder(@NonNull View itemView) {
            super(itemView);
            ejercicio = itemView.findViewById(R.id.ejercicio);
            imagen = itemView.findViewById(R.id.imagen);
            imagenTexto = itemView.findViewById(R.id.imagen_texto);
            nombre = itemView.findViewById(R.id.nombreTV);
            musculos = itemView.findViewById(R.id.musculos);
        }
    }
}
